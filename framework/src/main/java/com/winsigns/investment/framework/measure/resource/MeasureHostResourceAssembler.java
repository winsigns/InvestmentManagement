package com.winsigns.investment.framework.measure.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.winsigns.investment.framework.measure.MeasureHost;

/**
 * 宿主的资源转换
 * <p>
 * 宿主需要增加指标的显示
 * 
 * @author yimingjin
 * @since 0.0.3
 * 
 * @param <T> 宿主类型
 * @param <D> 宿主资源类型
 * 
 */
public abstract class MeasureHostResourceAssembler<T extends MeasureHost, D extends MeasureHostResource<T>>
    extends ResourceAssemblerSupport<T, D> {

  public MeasureHostResourceAssembler(Class<?> controllerClass, Class<D> resourceType) {
    super(controllerClass, resourceType);
  }

  public D toResourceWithMeasures(T measureHost) {
    D resource = toResource(measureHost);

    resource.addMeasures();

    return resource;
  }

}
