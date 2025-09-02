package com.exporum.client.configuration.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 20.
 * @description :
 */


@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "World of Coffee Client API Service",
                description = """
      World of Coffee Service를 위한 사용자 API version 1.
      이 문서는 (주)엑스포럼이 저작권을 가지고 있다.
      이 문서에 기록된 모든 정보는 통보 없이 변경될 수 있으며 허가 없이 임의 수정, 배포 및 사용을 금지한다.
      """,
                version = "v1." + "${git.commit.time:UNKNOWN}",
                //  contact = @Contact(name = "hslee", email = "hslee@exporum.com"),
                license = @License(name = "Copyright (c) 2024 exporum.", url = "https://www.exporum.com/"),
                extensions = {
                        @Extension(name = "Git", properties = {
                                @ExtensionProperty(name = "Commit Time", value = "${git.commit.time:UNKNOWN}"),
                                @ExtensionProperty(name = "Commit Id", value = "${git.commit.id.abbrev:UNKNOWN}")
                        })
                }
        )
//        servers = {
//                @Server(description = "local host", url = "http://localhost:8080"),
//                @Server(description = "dev host", url = "http://192.168.0.252:8080"),
//                @Server(description = "dev host", url = "http://192.168.0.252/"),
//        }
)
@Configuration
public class SwaggerConfiguration {


    /**
     * World of Coffee API.
     * @return GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi serviceApi() {
        String[] paths = {"/api/**"};
        return GroupedOpenApi.builder().group("World of Coffee API").pathsToMatch(paths)
//                .addOpenApiCustomizer(openApi -> {
//                    Components components = openApi.getComponents();
//                    for (Schema<?> schema : buildCustomSchemas()) {
//                        components.addSchemas(schema.getName(), schema);
//                    }
//
//                    Map<String,Schema> schemas = components.getSchemas();
//                    components.setSchemas(new TreeMap<>(schemas));
//                })
                .build();
    }

//    private static List<Schema<?>> buildCustomSchemas() {
//        ArrayList<Schema<?>> result = new ArrayList<>();
//
//        Schema<?> countMap = new Schema<Map<String, Long>>()
//                .name("Count")
//                .type("object")
//                .addProperty("value", new StringSchema().example("1"))
//                .addProperty("count", new IntegerSchema().example(7L))
//                .example(getCountMapExample());
//        result.add(countMap);
//
//        Schema<?> sumMap = new Schema<Map<String, List<? extends Number>>>()
//                .name("Sum")
//                .type("object")
//                .addProperty("value", new StringSchema().example("1"))
//                .addProperty("sums", new ArraySchema().type("Number"))
//                .example(getSumMapExample());
//        result.add(sumMap);
//
//        return result;
//    }
//
//    private static Map<String, Long> getCountMapExample() {
//        Map<String, Long> example = new HashMap<>();
//        example.put("1", 7L);
//        example.put("6", 3L);
//        return example;
//    }
//
//    private static Map<String, List<? extends Number>> getSumMapExample() {
//        Map<String, List<? extends Number>> example = new HashMap<>();
//        example.put("1", List.of(7L, 2L));
//        example.put("6", List.of(4L, 1L));
//        return example;
//    }


}
