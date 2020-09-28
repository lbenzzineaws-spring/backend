package net.lbenzzine.backend.swagger;


import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Configuration
@EnableSwagger2
public class SwageerConfig {

    public static final Contact DEFAULT_CONTACT = new Contact(
            "Latif Benzzinee", "http://theplay2learn.com", "lbenzzine@gmail.com");

    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Rewards calculation API", "API to calculate rewards points earned per customer per transaction. " + "\n" +
            "The API also offers endpoints to calculate rewards points earned for a given Month, as weell as the total rewards points earned per customer."
            , "1.0",
            "urn:tos", DEFAULT_CONTACT,
            "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Arrays.asList());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<String>(Arrays.asList("application/json",
                    "application/xml"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)

                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("net.lbenzzine.backend"))
                .build()
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);

    }

    @SwaggerDefinition(
            info = @Info(
                    title = "Rewards calculation API", version = "1.0", description = "Awesome Resources", license = @License(
                    name = "Apache 2.0",
                    url = "http://www.apache.org/licenses/LICENSE-2.0"
            )),
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"},
            schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
            externalDocs = @ExternalDocs(value = "Read This For Sure", url = "http://theplay2learn.com")
    )
    public interface ApiDocumentationConfig {

    }
}
