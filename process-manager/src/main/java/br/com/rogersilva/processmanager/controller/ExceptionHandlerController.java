package br.com.rogersilva.processmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.rogersilva.processmanager.dto.ErrorDto;
import br.com.rogersilva.processmanager.exception.NotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> notFound(NotFoundException e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("not_found", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> internalServerError(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("internal_server_error", e.getMessage()));
    }
}