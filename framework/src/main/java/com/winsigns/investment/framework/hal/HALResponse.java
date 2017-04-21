package com.winsigns.investment.framework.hal;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.core.Relation;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * hal返回的资源基类
 * 
 * @author Created by colin on 2017/3/2.
 *
 * @param <T>
 * 
 * @since 0.0.1
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HALResponse<T> extends Resource<T> {

  @JsonProperty("_embedded")
  private Map<String, Object> embedded;

  public HALResponse(T content) {
    super(content);
    this.embedded = new HashMap<>();
  }

  public void add(String name, Object embedded) {
    this.embedded.put(name, embedded);
  }

  /**
   * 移除embedded的指定项
   * 
   * @param name
   */
  public void remove(String name) {
    this.embedded.remove(name);
  }

  public Map<String, Object> getEmbedded() {
    return embedded;
  }

  public void setEmbedded(Map<String, Object> embedded) {
    this.embedded = embedded;
  }

  /**
   * 增加集合的Link
   * 
   * @param controllerClass 控制类
   * @param modelType 模型类
   * 
   * @since 0.0.3
   */
  public void addCollectionLink(Class<?> controllerClass, Class<?> modelType) {

    Assert.notNull(controllerClass);
    Assert.notNull(modelType);

    this.add(linkTo((controllerClass))
        .withRel(modelType.getAnnotation(Relation.class).collectionRelation()));
  }
}
