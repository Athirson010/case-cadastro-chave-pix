package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.exception;

import io.github.athirson010.cadastro_chaves_pix.domain.exceptions.ChaveDuplicadaException;
import io.github.athirson010.cadastro_chaves_pix.domain.exceptions.ChavePixNaoEncontradaException;
import io.github.athirson010.cadastro_chaves_pix.domain.exceptions.LimiteChavesExcedidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ChaveDuplicadaException.class)
    public ResponseEntity<ErrorResponse> handleChaveDuplicada(ChaveDuplicadaException ex) {
        ErrorResponse error = new ErrorResponse(
                "CHAVE_DUPLICADA",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(LimiteChavesExcedidoException.class)
    public ResponseEntity<ErrorResponse> handleLimiteExcedido(LimiteChavesExcedidoException ex) {
        ErrorResponse error = new ErrorResponse(
                "LIMITE_CHAVES_EXCEDIDO",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ChavePixNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleChaveNaoEncontrada(ChavePixNaoEncontradaException ex) {
        ErrorResponse error = new ErrorResponse(
                "CHAVE_NAO_ENCONTRADA",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                "DADOS_INVALIDOS",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException ex) {
        ErrorResponse error = new ErrorResponse(
                "ESTADO_INVALIDO",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                "DADOS_INVALIDOS",
                "Dados de entrada inválidos",
                errors,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "ERRO_INTERNO",
                "Erro interno do servidor",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record ErrorResponse(String codigo, String mensagem, LocalDateTime timestamp) {}

    public record ValidationErrorResponse(String codigo, String mensagem, Map<String, String> erros, LocalDateTime timestamp) {}
}