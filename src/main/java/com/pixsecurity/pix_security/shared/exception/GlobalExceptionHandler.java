package com.pixsecurity.pix_security.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Tratamento de erro dos controller
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class) // Quando for usado uma RuntimeException, usar este metodo
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) { // Mensagens do retorno throw usada em outras classes
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage())); // JSON (corpo) da resposta
    }

    @ExceptionHandler(CpfAlreadyExistsException.class) // Quando CPF já existir no POST, vai utilizar este metodo de erro
    public ResponseEntity<Map<String, String>> handleCpfAlreadyExists(CpfAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Quando der erro em algum campo @Valid, vai utilizar este metodo de erro
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(BindingAlreadyExistsException.class) // Quando Binding já existir no POST, vai utilizar este metodo de erro
    public ResponseEntity<Map<String, String>> handleBindingAlreadyExists(BindingAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }
}

