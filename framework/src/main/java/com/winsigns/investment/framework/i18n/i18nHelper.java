package com.winsigns.investment.framework.i18n;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 用来构建enum的国际化
 * <p>
 * 在资源目录下增加i18n目录,其下增加资源文件用来国际化
 * 
 * @author Created by colin on 2017/2/23.
 */

public class i18nHelper {

  protected static MessageSource messageSource;

  private i18nHelper() {}

  public static String i18n(String resourceKey) {
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(resourceKey, null, locale);
  }

  public static String i18n(Enum<?> literal) {
    String resourceKey = literal.getClass().getSimpleName() + "." + literal.toString();
    return i18n(resourceKey);
  }

  @Configuration
  protected static class MessageSourceConfiguration {
    @Bean
    public MessageSource messageSource() {
      ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
      messageSource.setBasename("i18n/message");
      messageSource.setDefaultEncoding("UTF-8");
      return messageSource;
    }
  }

  @Configuration
  protected static class MessageSourceInjector {
    @Bean
    public MessageSourceInjector messageSourceInjector(MessageSource messageSource) {
      i18nHelper.messageSource = messageSource;
      return new MessageSourceInjector();
    }
  }
}
