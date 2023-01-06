package com.ca.formation.formationdemo1.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;


    @Test
    @DisplayName("Should return a response entity with status code 404")
    void resourceNotFoundExceptionShouldReturnResponseEntityWithStatusCode404() {
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("Request description");
        ResourceNotFoundException resourceNotFoundException =
                new ResourceNotFoundException("Resource not found");

        ResponseEntity<?> responseEntity =
                globalExceptionHandler.resourceNotFoundException(
                        resourceNotFoundException, request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should return a response entity with error response body")
    void resourceNotFoundExceptionShouldReturnResponseEntityWithErrorResponseBody() {
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("description");
        ResourceNotFoundException resourceNotFoundException =
                new ResourceNotFoundException("message");

        ResponseEntity<?> responseEntity =
                globalExceptionHandler.resourceNotFoundException(
                        resourceNotFoundException, request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}