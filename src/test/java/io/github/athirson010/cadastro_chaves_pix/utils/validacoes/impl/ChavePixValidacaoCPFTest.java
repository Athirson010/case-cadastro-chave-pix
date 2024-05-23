package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.impl;

import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChavePixValidacaoCPFTest {
    private final ChavePixValidacaoCPF chavePixValidacaoCPF = new ChavePixValidacaoCPF();

    @Test
    public void testCPFValidoComPontuacao() {
        String cpfValido = "123.456.789-09";
        assertDoesNotThrow(() -> chavePixValidacaoCPF.validarCaracteristicas(cpfValido));
    }

    @Test
    public void testCPFValidoSemPontuacao() {
        String cpfValido = "12345678909";
        assertDoesNotThrow(() -> chavePixValidacaoCPF.validarCaracteristicas(cpfValido));
    }

    @Test
    public void testCPFInvalidoComPontuacaoErrada() {
        String cpfInvalido = "123.456.789/09";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCPF.validarCaracteristicas(cpfInvalido));
    }

    @Test
    public void testCPFInvalidoMenosDe11Digitos() {
        String cpfInvalido = "1234567890";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCPF.validarCaracteristicas(cpfInvalido));
    }

    @Test
    public void testCPFInvalidoMaisDe11Digitos() {
        String cpfInvalido = "123456789009";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCPF.validarCaracteristicas(cpfInvalido));
    }

    @Test
    public void testCPFInvalidoComLetras() {
        String cpfInvalido = "123.456.789-0A";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCPF.validarCaracteristicas(cpfInvalido));
    }

    @Test
    public void testCPFComDigitosRepetidos() {
        String cpfRepetido = "111.111.111-11";
        assertThrows(ValidacaoException.class, () -> chavePixValidacaoCPF.validarCaracteristicas(cpfRepetido));
    }
}