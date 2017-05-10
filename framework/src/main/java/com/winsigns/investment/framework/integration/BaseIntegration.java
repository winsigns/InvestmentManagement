package com.winsigns.investment.framework.integration;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 服务间调用的基类
 * 
 * <p>
 * 注入了LoadBalancerClient和RestOperations
 * 
 * @author yimingjin
 * @since 0.0.4
 */
public class BaseIntegration {

  @Autowired
  protected RestOperations restTemplate;

  protected ObjectMapper objectMapper =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  /**
   * 抓取本次http请求中header的参数
   * 
   * @param key
   * @return
   */
  protected String getHeaderParam(String key) {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes == null) {
      return null;
    }
    Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
    HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
    if (servletRequest == null) {
      return null;
    }
    return servletRequest.getHeader(key);
  }

  /**
   * 根据URL返回一个Json节点
   * <p>
   * 会抓取本次http的请求获取语言信息
   * 
   * @param url
   * @return
   */
  protected ObjectNode getNode(String url) {

    HttpHeaders reponseHeaders = new HttpHeaders();
    String language = this.getHeaderParam("accept-language");
    reponseHeaders.set("accept-language", language);
    try {
      RequestEntity<String> request =
          new RequestEntity<String>(reponseHeaders, HttpMethod.GET, new URI(url));
      ResponseEntity<ObjectNode> resultStr = restTemplate.exchange(request, ObjectNode.class);

      return resultStr.getBody();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 带有requestBody的http请求
   * 
   * @param body
   * @param url
   * @return
   * 
   */
  protected <T> ObjectNode getNode(T body, String url) {

    HttpHeaders reponseHeaders = new HttpHeaders();
    reponseHeaders.set("accept-language", this.getHeaderParam("accept-language"));
    try {
      RequestEntity<T> request =
          new RequestEntity<T>(body, reponseHeaders, HttpMethod.GET, new URI(url));
      ResponseEntity<ObjectNode> resultStr = restTemplate.exchange(request, ObjectNode.class);

      return resultStr.getBody();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 根据URL返回一个Json节点
   * <p>
   * 会抓取本次http的请求获取语言信息
   * 
   * @param url
   * @return
   */
  protected String getJson(String url) {

    HttpHeaders reponseHeaders = new HttpHeaders();
    String language = this.getHeaderParam("accept-language");
    reponseHeaders.set("accept-language", language);
    try {
      RequestEntity<String> request =
          new RequestEntity<String>(reponseHeaders, HttpMethod.GET, new URI(url));
      ResponseEntity<String> resultStr = restTemplate.exchange(request, String.class);

      return resultStr.getBody();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
