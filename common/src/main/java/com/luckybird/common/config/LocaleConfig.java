//package com.luckybird.common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
//
//import java.util.Locale;
//
///**
// * 本地化配置类
// *
// * @author 新云鸟
// */
//@Configuration
//public class LocaleConfig {
//
//    private static final Locale DEFAULT_LOCALE = Locale.CHINA;
//
//    @Bean
//    public ResourceBundleMessageSource messageSource() {
//        Locale.setDefault(DEFAULT_LOCALE);
//        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
//        source.setUseCodeAsDefaultMessage(true);
//        source.setDefaultEncoding("UTF-8");
//        Locale DefaultLocale = Locale.getDefault();
//        return source;
//    }
//
//    @Bean
//    public WebMvcConfigurer localeInterceptor() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
//                localeInterceptor.setParamName("lang");
//                registry.addInterceptor(localeInterceptor);
//            }
//        };
//    }
//}
