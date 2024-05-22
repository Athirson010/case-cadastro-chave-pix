package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.impl;

import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChavePixValidacaoEmailTest {
    private final ChavePixValidacaoEmail chavePixValidacaoEmail = new ChavePixValidacaoEmail();

    @Test
    public void testEmailValidoSimples() {
        String emailValido = "test@example.com";
        assertDoesNotThrow(() -> chavePixValidacaoEmail.validarCaracteristicasChave(emailValido));
    }

    @Test
    public void testEmailValidoComNumeros() {
        String emailValido = "test123@example.com";
        assertDoesNotThrow(() -> chavePixValidacaoEmail.validarCaracteristicasChave(emailValido));
    }

    @Test
    public void testEmailValidoComPontos() {
        String emailValido = "first.last@example.com";
        assertDoesNotThrow(() -> chavePixValidacaoEmail.validarCaracteristicasChave(emailValido));
    }

    @Test
    public void testEmailValidoComSinais() {
        String emailValido = "user+mailbox/department=shipping@example.com";
        assertDoesNotThrow(() -> chavePixValidacaoEmail.validarCaracteristicasChave(emailValido));
    }

    @Test
    public void testEmailInvalido() {
        String emailInvalido = "test.example.com";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoEmail.validarCaracteristicasChave(emailInvalido));
    }

    @Test
    public void testEmailInvalidoSemDominio() {
        String emailInvalido = "test@.com";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoEmail.validarCaracteristicasChave(emailInvalido));
    }

    @Test
    public void testEmailInvalidoComEspacos() {
        String emailInvalido = "test @example.com";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoEmail.validarCaracteristicasChave(emailInvalido));
    }

}