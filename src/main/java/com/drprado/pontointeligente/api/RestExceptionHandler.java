package com.drprado.pontointeligente.api;

import com.drprado.pontointeligente.domain.exceptions.MyCustomException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Com uma classe com essas notations podemos ter uma classe geral para gerenciar exceções de todos os controllers
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // Trata todos os erros relacionados a desserialização da REQUEST
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Object[]{"Ocorreu um erro na desserialização da sua requisição", ex.getMessage()});
    }

    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<Object> handleUnexpectedExceptions(MyCustomException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Object[]{"Ocorreu o meu CUSTOM ERRO", ex.getMessage()});
    }

    @ExceptionHandler(Error.class)
    public ResponseEntity<Object> handleUnexpectedExceptions(Error ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Object[]{"Ocorreu um erro INESPERADO", ex.getMessage()});
    }
}
