package com.winsigns.investment.sequenceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHypermediaSupport(type = {HypermediaType.HAL})
public class SequenceServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SequenceServiceApplication.class, args);
  }

  private static Logger logger = LoggerFactory.getLogger(SequenceServiceApplication.class);

  @Bean
  @ConfigurationProperties(prefix = "spring.redis")
  public JedisPoolConfig getRedisConfig() {
    JedisPoolConfig config = new JedisPoolConfig();
    return config;
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.redis")
  public JedisConnectionFactory getConnectionFactory() {
    JedisConnectionFactory factory = new JedisConnectionFactory();
    JedisPoolConfig config = getRedisConfig();
    factory.setPoolConfig(config);
    logger.info("JedisConnectionFactory bean init success.");
    return factory;
  }

  @Bean
  public RedisTemplate<String, Integer> redisTemplate() {

    RedisTemplate<String, Integer> template = new RedisTemplate<String, Integer>();
    RedisSerializer<String> stringSerializer = new StringRedisSerializer();
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
        new Jackson2JsonRedisSerializer<Object>(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);

    template.setKeySerializer(stringSerializer);
    template.setValueSerializer(jackson2JsonRedisSerializer);
    template.setHashKeySerializer(stringSerializer);
    template.setHashValueSerializer(jackson2JsonRedisSerializer);

    template.setConnectionFactory(getConnectionFactory());
    template.afterPropertiesSet();

    return template;
  }
}

