package com.example.LearnAuthentication.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
////package com.example.LearnAuthentication.config;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
////import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
////import springfox.documentation.builders.PathSelectors;
////import springfox.documentation.builders.RequestHandlerSelectors;
////import springfox.documentation.service.ApiInfo;
////import springfox.documentation.service.Contact;
////import springfox.documentation.spi.DocumentationType;
////import springfox.documentation.spring.web.plugins.Docket;
////import springfox.documentation.swagger2.annotations.EnableSwagger2;
////
////import java.util.Collections;
////
////@Configuration
////@EnableSwagger2
////public class SwaggerConfig extends WebMvcConfigurationSupport {
////
////	@Bean
////	public Docket api() {
////		return new Docket(DocumentationType.SWAGGER_2)
////				.select().apis(RequestHandlerSelectors.basePackage("com.mbusa.eai.aftersales.dw"))
////				.paths(PathSelectors.any())
////				.build().apiInfo(apiInfo());
////		}
////
////
////private ApiInfo apiInfo() {
////     return new ApiInfo(
////       "AfterSales DW Services",
////       "Various AfterSales DW Services",
////       "1.0",
////       "Terms of service",
////       new Contact("Enterprise Application Integration", "www.mbusa.com", "stareai@mbusa.com"),
////       "License of API", "API license URL", Collections.emptyList());
////}
////
////protected void addResourceHandlers(ResourceHandlerRegistry registry) {
////    registry.addResourceHandler("swagger-ui.html")
////            .addResourceLocations("classpath:/META-INF/resources/");
////
////    registry.addResourceHandler("/webjars/**")
////            .addResourceLocations("classpath:/META-INF/resources/webjars/");
////}
////
////}
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.LearnAuthentication"))
//                .paths(PathSelectors.any())
//                .build().apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        ApiInfo apiInfo = new ApiInfo(
//                "StudentSearchApplication",
//                "An application to search Student from a Student repository by studentId",
//                "StudentSearchApplication v1",
//                "Terms of service",
//                "hendisantika@gmail.com",
//                "License of API",
//                "https://swagger.io/docs/");
//        return apiInfo;
//    }
//}

/* approch 2
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "Authorization";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Basic API Information
                .info(new Info()
                        .title("LearnAuthentication")
                        .version("v1.0.0")
                        .description("Comprehensive API documentation for managing authentication and other services.")
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                                .name("Mohd Talib")
                                .email("talibjamia@gmail.com")
                                //.url("https://example.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                // Global Security Scheme Definition
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_SCHEME_NAME)) // Attach the security scheme globally
                .components(new Components()
                                .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                                );

        /*
        .addList("Authorization-Token")) // Attach the customized security scheme globally
            .components(new Components()
                    .addSecuritySchemes("Authorization-Token", new SecurityScheme()
                            .name("Authorization") // Name of the header
                            .type(SecurityScheme.Type.APIKEY) // Use API Key type for custom header behavior
                            .in(SecurityScheme.In.HEADER) // Specify that the token will be in the header
                            .description("JWT token without Bearer prefix"))); // Custom description
         */

//    }
//}


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "LearnAuthentication Spring Boot 3 Auth APIs", version = "1.0",
        description = "Auth application documentation", contact = @Contact(name = "Mohd Talib")),
        security = {@SecurityRequirement(name = "bearerToken"), @SecurityRequirement(name = "cookie")})
@SecuritySchemes({
        @SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT"),
        @SecurityScheme(name = "cookie", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, paramName = "cookie")
})
public class SwaggerConfig {
}

