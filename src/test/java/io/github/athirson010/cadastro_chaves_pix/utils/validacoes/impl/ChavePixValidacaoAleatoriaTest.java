package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.impl;

import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ChavePixValidacaoAleatoriaTest {
    private final ChavePixValidacaoAleatoria chavePixValidacaoAleatoria = new ChavePixValidacaoAleatoria();

    @Test
    public void testChaveValida() {
        String chaveValida = "zYxWvUtSrQpOnMlKjIhGfEdCbA1234567890";
        Assertions.assertDoesNotThrow(() -> chavePixValidacaoAleatoria.validarCaracteristicas(chaveValida));
    }

    @Test
    public void testChaveInvalidaCaracteresEspeciais() {
        String chaveInvalida = "zYxWvUtSrQpOnMlKjIhGfEdCbA123456!@#";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoAleatoria.validarCaracteristicas(chaveInvalida));
    }

    @Test
    public void testChaveInvalidaMenosDe36Caracteres() {
        String chaveInvalida = "zYxWvUtSrQpOnMlKjIhGfEdCbA12345";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoAleatoria.validarCaracteristicas(chaveInvalida));
    }

    @Test
    public void testChaveInvalidaMaisDe36Caracteres() {
        String chaveInvalida = "zYxWvUtSrQpOnMlKjIhGfEdCbA1234567890123456";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoAleatoria.validarCaracteristicas(chaveInvalida));
    }

    @Test
    public void testChaveVazia() {
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoAleatoria.validarCaracteristicas(""));
    }
}