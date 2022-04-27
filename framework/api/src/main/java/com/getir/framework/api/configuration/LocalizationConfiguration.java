package com.getir.framework.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

import static com.getir.framework.core.model.culture.LocaleContent.TURKISH_LOCALE;

@Configuration
public class LocalizationConfiguration {


    @Bean
    public ResourceBundleMessageSource messageSource() {
        Locale.setDefault(TURKISH_LOCALE);
        final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("i18n/messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }
}
