package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.impl;

import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChavePixValidacaoCelularTest {
    private final ChavePixValidacaoCelular chavePixValidacaoCelular = new ChavePixValidacaoCelular();

    @Test
    public void testCelularValido() {
        String celular1 = "+55011912345678";
        assertDoesNotThrow(() -> chavePixValidacaoCelular.validarCaracteristicasChave(celular1));
    }

    @Test
    public void testCelularInvalido() {
        String celular1 = "+55abc12345678"; // contém caracteres não numéricos
        String celular2 = "123456789"; // menos de 9 dígitos
        String celular3 = "1234567890123456789"; // mais de 15 dígitos
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCelular.validarCaracteristicasChave(celular1));
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCelular.validarCaracteristicasChave(celular2));
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCelular.validarCaracteristicasChave(celular3));
    }

    @Test
    public void testCelularVazio() {
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCelular.validarCaracteristicasChave(""));
    }
}