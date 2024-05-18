package io.github.athirson010.cadastro_chaves_pix.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GerenciadorException {
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleValidationExceptions(
            MethodArgumentNotValidException ex) {
    }

    @ExceptionHandler(RequisicaoInvalidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Erro> tratarRequisicaoInvalida(RequisicaoInvalidaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erro(e.getMessage(), null));
    }
    @ExceptionHandler(ValidacaoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity tratarValidacao(ValidacaoException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity tratarValidacaoEnum(HttpMessageNotReadableException e) {
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

}
