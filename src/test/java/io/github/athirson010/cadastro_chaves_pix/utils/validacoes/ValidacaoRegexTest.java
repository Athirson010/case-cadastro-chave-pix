package io.github.athirson010.cadastro_chaves_pix.utils.validacoes;

import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import org.junit.jupiter.api.Test;

import static io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoChave.validarRegex;
import static org.junit.jupiter.api.Assertions.*;

class ValidacaoRegexTest {
    @Test
    public void testValidarComRegexValido() {
        String regex = "^[a-zA-Z0-9]+$";
        String valor = "Teste123";
        assertDoesNotThrow(() -> validarRegex(regex, valor));
    }

    @Test
    public void testValidarComRegexInvalido() {
        String regex = "^[a-zA-Z0-9]+$";
        String valor = "Teste 123";

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            validarRegex(regex, valor);
        });

        assertEquals("422 UNPROCESSABLE_ENTITY", exception.getMessage());
    }
}