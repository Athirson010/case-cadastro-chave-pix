package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.impl;

import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChavePixValidacaoCelularTest {
    private final ChavePixValidacaoCelular chavePixValidacaoCelular = new ChavePixValidacaoCelular();

    @Test
    public void testCelularValido() {
        String celular1 = "+55011912345678";
        assertDoesNotThrow(() -> chavePixValidacaoCelular.validarCaracteristicas(celular1));
    }

    @Test
    public void testCelularInvalido() {
        String celular1 = "+55abc12345678";
        String celular2 = "123456789";
        String celular3 = "1234567890123456789";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCelular.validarCaracteristicas(celular1));
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCelular.validarCaracteristicas(celular2));
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCelular.validarCaracteristicas(celular3));
    }

    @Test
    public void testCelularVazio() {
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCelular.validarCaracteristicas(""));
    }
}