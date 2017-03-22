package com.winsigns.investment.framework.measure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by colin on 2017/3/3.
 */

@Configuration
@ComponentScan
public class MeasureConfiguration {

  @Autowired
  RedisConnectionFactory connectionFactory;

  @Bean
  public MeasureValueRepository measureValueRepository() {
    return new MeasureValueRepository();
  }

  @Bean
  public RedisTemplate<String, Double> stringDoubleRedisTemplate(RedisConnectionFactory factory) {

    RedisTemplate<String, Double> template = new RedisTemplate<String, Double>();
    RedisSerializer<String> stringSerializer = new StringRedisSerializer();
    GenericToStringSerializer<Double> doubleSerializer =
        new GenericToStringSerializer<Double>(Double.class);

    template.setKeySerializer(stringSerializer);
    template.setValueSerializer(doubleSerializer);
    template.setHashKeySerializer(stringSerializer);
    template.setHashValueSerializer(doubleSerializer);

    template.setConnectionFactory(factory);
    template.afterPropertiesSet();

    return template;
  }
}
