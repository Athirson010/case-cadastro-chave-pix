package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.impl;

import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChavePixValidacaoCNPJTest {

    private final ChavePixValidacaoCNPJ chavePixValidacaoCNPJ = new ChavePixValidacaoCNPJ();

    @Test
    public void testCNPJValidoComPontuacao() {
        String cnpjValido = "12.345.678/0001-99";
        assertDoesNotThrow(() -> chavePixValidacaoCNPJ.validarCaracteristicasChave(cnpjValido));
    }

    @Test
    public void testCNPJValidoSemPontuacao() {
        String cnpjValido = "12345678000199";
        assertDoesNotThrow(() -> chavePixValidacaoCNPJ.validarCaracteristicasChave(cnpjValido));
    }

    @Test
    public void testCNPJInvalidoComPontuacaoErrada() {
        String cnpjInvalido = "12.345.678/0001.99";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCNPJ.validarCaracteristicasChave(cnpjInvalido));
    }

    @Test
    public void testCNPJInvalidoMenosDe14Digitos() {
        String cnpjInvalido = "1234567800019";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCNPJ.validarCaracteristicasChave(cnpjInvalido));
    }

    @Test
    public void testCNPJInvalidoMaisDe14Digitos() {
        String cnpjInvalido = "123456780001999";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCNPJ.validarCaracteristicasChave(cnpjInvalido));
    }

    @Test
    public void testCNPJInvalidoComLetras() {
        String cnpjInvalido = "12.345.678/0001-9A";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCNPJ.validarCaracteristicasChave(cnpjInvalido));
    }
}