package com.zand.creditcard.domain.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

/** Class for configuring OpenAPI. */
@OpenAPIDefinition(
    info =
        @Info(
            title = "API for credit card checker",
            version = "v1.0",
            description = "API for credit card checker",
            contact =
                @Contact(
                    name = "Zand Technologies",
                    url = "https://github.com/thombergs/code-examples",
                    email = "info@zandtech.com"),
            license =
                @License(
                    name = "MIT Licence",
                    url = "https://github.com/thombergs/code-examples/blob/master/LICENSE")))
@Configuration
public class OpenApiConfig {}
