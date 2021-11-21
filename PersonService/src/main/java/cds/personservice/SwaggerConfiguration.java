package cds.personservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * Standard API declaration to help Swagger generate its documentation
     */
    @Bean
    public Docket api() throws Exception {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        return docket
                // API description
                .apiInfo(apiInfo())
                // Security schemes that facilitate the use of JWT with Swagger
                .securityContexts(newArrayList(securityContext()))
                .securitySchemes(newArrayList(apiKey()))
                .select()
                // Generate the documentation only for the class annotate with @RestController
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build();
    }

    /**
     * API Description
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger Implementation For the Car Fleet Project")
                .description("This Swagger page documents and allows testing of GSO Car Fleet Project's APIs")
                .build();
    }

    /**
     * Define what kind of auth we want to use in Swagger
     * Here the API key is called 'Authorization' and stored in the 'header'
     * (its our JWT)
     */
    private ApiKey apiKey() {
        return new ApiKey("AUTHORIZATION", "Authorization", "header");
    }

    /**
     * Scoping access with AUTHORIZATION api key
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        newArrayList(
                                new SecurityReference(
                                        "AUTHORIZATION",
                                        new AuthorizationScope[] {
                                                new AuthorizationScope("global", "accessEverything")
                                        })
                        )
                )
                .build();
    }
}
