package com.winsigns.investment.framework.redis;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configurable
public class RedisConfig {

  @Bean
  public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory factory) {

    RedisTemplate<K, V> template = new RedisTemplate<K, V>();
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

    template.setConnectionFactory(factory);
    template.afterPropertiesSet();

    return template;
  }
}
