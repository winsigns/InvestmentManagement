package com.winsigns.investment.measure;

import org.apache.log4j.Logger;

import com.winsigns.investment.measure.data.MeasureData;

import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

public class MeasureProcessor extends AbstractProcessor<String, MeasureData> {

  private KeyValueStore<String, MeasureData> kvStore;
  private String currVersion;
  private IMeasure measure;
  private Logger log = Logger.getLogger(MeasureProcessor.class);
  private FakeDataProvider cache = FakeDataProvider.getInst();

  public MeasureProcessor(IMeasure measure) {
    this.measure = measure;
  }

  @Override
  public void init(ProcessorContext context) {
    super.init(context);
    kvStore = (KeyValueStore<String, MeasureData>) context
        .getStateStore(String.format("%s.%s.store", measure.getHost(), measure.getName()));
  }

  @Override
  public void process(String key, MeasureData value) {
    kvStore.put(key, value);

    log.info(String.format("measure=%s.%s, key=%s", measure.getHost(), measure.getName(), key));

    boolean needToCommit = false;

    if (key.endsWith(".internal")) {
      if (measure.needToProcessInternal()) {
        //内部指标变化，不管版本，优先处理
        MeasureData result = measure
            .calc(key, value, true, kvStore.get(measure.getKey("internal")));

        kvStore.put(measure.getKey("internal"), result);
        context().forward(measure.getKey("internal"), result);
        needToCommit = true;
      }

    }

    if (key.equals("timer.sequence")) {
      currVersion = String.format("v%d", value.version);
      MeasureData data = new MeasureData();
      data.version = value.version;
      data.value = 1;
      kvStore.put(String.format("readyToCalc.%s", currVersion), data);
      log.info(String.format("measure=%s.%s, time.seq recved, currVersion=%s", measure.getHost(),
          measure.getName(), currVersion));
    }

    MeasureData readyToCalc = kvStore.get(String.format("readyToCalc.%s", currVersion));
    log.info(String.format("measure=%s.%s, readyToCalc=%s, currVersion=%s", measure.getHost(),
        measure.getName(), (readyToCalc != null && readyToCalc.value > 0) ? "true" : "false",
        currVersion));
    if (readyToCalc != null && readyToCalc.value > 0) {
      //FIXME: 这里原意是在本地直接检查对应的数据是否准备好。
      //FIXME: 但复杂数据是存放在value中的，因此能否依赖简单的key来决定
      //FIXME: 数据是否准备好这一点存疑。暂时屏蔽掉这段检查，直接在
      //FIXME: 具体计算过程内部检查数据准备情况。
//			for (String dep : measure.getDependencies()){
//				if (dep.equals("timer.sequence")){
//					//不检查时序消息是否ready
//					continue;
//				}
//				String k = String.format("%s.%s", dep, currVersion);
//				if (!measure.getHost().equals(measure.getHostOfKey(k))){
//					//外部数据，需要检查是否准备好
//					log.info(String.format("k=%s", k));
//					if(kvStore.get(k) == null){
//						log.info("not ready");
//						return;
//					}
//					log.info("ready");
//				}
//			}

      MeasureData result = measure.calc(key, value, false, kvStore.get(measure.getBaseKey()));
      if (result == null) {
        return;
      }

      kvStore.put(measure.getBaseKey(), result);
      //TODO: 需要写到redis
      cache.put(measure.getKey(currVersion), result.value);

      context().forward(measure.getKey(currVersion), result);
      needToCommit = true;

      readyToCalc.value = 0;
      kvStore.put(String.format("readyToCalc.%s", currVersion), readyToCalc);
    }

    if (needToCommit) {
      context().commit();
    }
  }
}
