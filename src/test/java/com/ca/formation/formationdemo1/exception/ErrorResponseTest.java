package com.ca.formation.formationdemo1.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseTest {

    @Test
    @DisplayName("Should set the details")
    void setDetailsShouldSetTheDetails() {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), "404", "Not found", "Details");
        errorResponse.setDetails("New details");
        assertEquals("New details", errorResponse.getDetails());
    }

    @Test
    @DisplayName("Should return the details")
    void getDetailsShouldReturnTheDetails() {
        ErrorResponse errorResponse =
                new ErrorResponse(
                        new Date(),
                        "404",
                        "Not found",
                        "The resource you are looking for is not found");
        String details = errorResponse.getDetails();
        assertEquals("The resource you are looking for is not found", details);
    }

    @Test
    @DisplayName("Should set the message")
    void setMessageShouldSetTheMessage() {
        ErrorResponse errorResponse =
                new ErrorResponse(new Date(), "404", "Not found", "Not found");
        String expectedMessage = "Not found";

        errorResponse.setMessage("Not found");

        assertEquals(expectedMessage, errorResponse.getMessage());
    }

    @Test
    @DisplayName("Should return the message")
    void getMessageShouldReturnTheMessage() {
        ErrorResponse errorResponse =
                new ErrorResponse(new Date(), "404", "Not found", "Not found");
        String message = errorResponse.getMessage();
        assertEquals("Not found", message);
    }

    @Test
    @DisplayName("Should set the status")
    void setStatusShouldSetTheStatus() {
        ErrorResponse errorResponse =
                new ErrorResponse(new Date(), "404", "Not Found", "https://www.google.com");
        errorResponse.setStatus("200");
        assertEquals("200", errorResponse.getStatus());
    }

    @Test
    @DisplayName("Should return the status")
    void getStatusShouldReturnTheStatus() {
        ErrorResponse errorResponse =
                new ErrorResponse(
                        new Date(),
                        "404",
                        "Not Found",
                        "The resource you are looking for is not found");
        String status = errorResponse.getStatus();
        assertEquals("404", status);
    }

    @Test
    @DisplayName("Should set the timestamp")
    void setTimestampShouldSetTheTimestamp() {
        ErrorResponse errorResponse =
                new ErrorResponse(new Date(), "404", "Not found", "Not found");
        Date date = new Date();

        errorResponse.setTimestamp(date);

        assertEquals(date, errorResponse.getTimestamp());
    }

    @Test
    @DisplayName("Should return the timestamp")
    void getTimestampShouldReturnTheTimestamp() {
        Date timestamp = new Date();
        ErrorResponse errorResponse =
                new ErrorResponse(
                        timestamp, "404", "Not found", "The requested resource was not found");

        Date result = errorResponse.getTimestamp();

        assertEquals(timestamp, result);
    }






}