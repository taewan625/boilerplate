package com.exporum.core.configuration.thymeleaf;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;

import java.util.Map;

public class SafeTemplateResolver extends ClassLoaderTemplateResolver {
    @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate, String template, String resourceName, String characterEncoding, Map<String, Object> templateResolutionAttributes) {
        ITemplateResource resource = super.computeTemplateResource(configuration, ownerTemplate, template, resourceName, characterEncoding, templateResolutionAttributes);

        if (resource == null || !resource.exists()) {
            return super.computeTemplateResource(
                    configuration,
                    ownerTemplate,
                    "error/not-ready", // logical name
                    "templates/error/not-ready.html", // physical location
                    characterEncoding,
                    templateResolutionAttributes
            );
        }

        return resource;
    }
}
