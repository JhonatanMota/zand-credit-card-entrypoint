package com.zand.creditcard.domain.openapi;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;

@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
    schema = @Schema(implementation = ErrorResponse.class),
    mediaType = MediaType.APPLICATION_JSON_VALUE
))
@ApiResponse(responseCode = "500", description = "Internal error", content = @Content(
    schema = @Schema(implementation = ErrorResponse.class),
    mediaType = MediaType.APPLICATION_JSON_VALUE
))
@ApiResponse(
    responseCode = "404",
    description = "Not found",
    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface StandardErrorApiResponses {

}
