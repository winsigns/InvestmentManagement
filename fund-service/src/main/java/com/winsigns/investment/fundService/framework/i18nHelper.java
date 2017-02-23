package com.winsigns.investment.fundService.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * Created by colin on 2017/2/23.
 */

public class i18nHelper {

    protected static MessageSource messageSource;

    private i18nHelper() {}

    public static String i18n(String resourceKey) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(resourceKey, null, locale);
    }

    public static String i18n(Enum literal) {
        String resourceKey = literal.getClass().getSimpleName() + "." + literal.toString();
        return i18n(resourceKey);
    }

    @Component
    public static class MessageSourceInjector {
        @Autowired
        private MessageSource messageSource;

        @PostConstruct
        public void postConstruct() {
            i18nHelper.messageSource= messageSource;
        }
    }
}
