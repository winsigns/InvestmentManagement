package com.winsigns.investment.sequenceService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHypermediaSupport(type = {HypermediaType.HAL})
public class SequenceServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SequenceServiceApplication.class, args);
  }

  @Bean
  public RedisTemplate<String, Integer> stringIntegerRedisTemplate(
      RedisConnectionFactory redisConnectionFactory) {

    RedisTemplate<String, Integer> template = new RedisTemplate<String, Integer>();
    RedisSerializer<String> stringSerializer = new StringRedisSerializer();
    GenericToStringSerializer<Integer> integerSerializer =
        new GenericToStringSerializer<Integer>(Integer.class);

    template.setKeySerializer(stringSerializer);
    template.setValueSerializer(integerSerializer);
    template.setHashKeySerializer(stringSerializer);
    template.setHashValueSerializer(integerSerializer);

    template.setConnectionFactory(redisConnectionFactory);
    template.afterPropertiesSet();

    return template;
  }
}

