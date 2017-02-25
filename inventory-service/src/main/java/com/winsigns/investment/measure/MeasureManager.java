package com.winsigns.investment.measure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.winsigns.investment.measure.IMeasure;
import org.apache.log4j.Logger;

public class MeasureManager {
	private Set<String> sources = new HashSet<String>();
	private List<IMeasure> measureList = new ArrayList<IMeasure>();
	private Logger log = Logger.getLogger(MeasureManager.class);
	
	public String[] getSources(){
		if (sources.size() == 0){
			for (IMeasure measure : measureList){
				log.info(String.format("measure=%s.%s", measure.getHost(), measure.getName()));
				for (String dep : measure.getDependencies()){
					if (!measure.getHost().equals(measure.getHostOfKey(dep))){
						sources.add(dep);
					}
				}
			}
		}
		
		return sources.toArray(new String[0]);
	}
	
	public void addMeasure(IMeasure measure){
		this.measureList.add(measure);
	}
	
	public List<IMeasure> getMeasures(){
		return measureList;
	}
}
