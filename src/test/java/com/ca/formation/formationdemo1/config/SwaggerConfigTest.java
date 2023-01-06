package com.ca.formation.formationdemo1.config;

import com.ca.formation.formationdemo1.config.SwaggerConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
class SwaggerConfigTest {
    @Autowired
    private SwaggerConfig swaggerConfig;

    @Test
    @DisplayName("Should return an openapi object with the correct title")
    void springShopOpenAPIWhenReturnOpenAPIWithCorrectTitle() {
        assertEquals("Formtion API", swaggerConfig.springShopOpenAPI().getInfo().getTitle());
    }

    @Test
    @DisplayName("Should return an openapi object with the correct license url")
    void springShopOpenAPiWhenReturnOpenApiWithCorrectLicenseUrl() {
        assertEquals(
                "http://springdoc.org",
                swaggerConfig.springShopOpenAPI().getInfo().getLicense().getUrl());
    }

    @Test
    @DisplayName("Should return an openapi object with the correct description")
    void springShopOpenAPIWhenReturnOpenAPIWithCorrectDescription() {
        assertEquals(
                "Formation sample application",
                swaggerConfig.springShopOpenAPI().getInfo().getDescription());
    }

    @Test
    @DisplayName("Should return an openapi object with the correct license name")
    void springShopOpenAPiWhenReturnOpenApiWithCorrectLicenseName() {
        assertEquals(
                "Apache 2.0", swaggerConfig.springShopOpenAPI().getInfo().getLicense().getName());
    }

    @Test
    @DisplayName("Should return an openapi object with the correct version")
    void springShopOpenAPIWhenReturnOpenAPIWithCorrectVersion() {
        assertEquals("1.0.0", swaggerConfig.springShopOpenAPI().getInfo().getVersion());
    }

    @Test
    @DisplayName("Should return groupedopenapi")
    void publicApiShouldReturnGroupedOpenApi() {
        assertNotNull(swaggerConfig.publicApi());
    }



}