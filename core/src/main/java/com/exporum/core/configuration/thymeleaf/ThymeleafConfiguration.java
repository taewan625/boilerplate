package com.exporum.core.configuration.thymeleaf;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 7.
 * @description :
 */


public class ThymeleafConfiguration {


    @Value("${spring.thymeleaf.suffix}")
    private String suffix;

    @Value("${spring.thymeleaf.mode}")
    private String mode;

    @Value("${spring.thymeleaf.encoding}")
    private String encoding;

    @Value("${spring.thymeleaf.prefix}")
    private String prefix;


    @Bean
    public TemplateEngine thymeleafTemplateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(safeTemplateResolver());
        return templateEngine;
    }


    @Bean
    public ITemplateResolver safeTemplateResolver() {
        SafeTemplateResolver resolver = new SafeTemplateResolver();
        resolver.setPrefix(prefix); // ex: "templates/"
        resolver.setSuffix(suffix); // ex: ".html"
        resolver.setTemplateMode(mode); // ex: "HTML"
        resolver.setCharacterEncoding(encoding); // ex: "UTF-8"
        resolver.setCheckExistence(true);
        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
