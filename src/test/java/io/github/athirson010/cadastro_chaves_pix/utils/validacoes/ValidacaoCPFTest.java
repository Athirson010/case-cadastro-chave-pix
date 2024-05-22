package io.github.athirson010.cadastro_chaves_pix.utils.validacoes;

import org.junit.jupiter.api.Test;

import static io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoChave.isCPF;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidacaoCPFTest {
    @Test
    public void testCPFValido() {
        assertTrue(isCPF("123.456.789-09"));
        assertTrue(isCPF("111.444.777-35"));
    }

    @Test
    public void testCPFInvalido() {
        assertFalse(isCPF("123.456.789-00"));
        assertFalse(isCPF("111.222.333-44"));
        assertFalse(isCPF("000.000.000-00"));
        assertFalse(isCPF("111.111.111-11"));
    }

    @Test
    public void testCPFNulo() {
        assertFalse(isCPF(null));
    }

    @Test
    public void testCPFComCaracteresInvalidos() {
        assertFalse(isCPF("123.456.789-0X"));
        assertFalse(isCPF("111-222-333-AB"));
    }

    @Test
    public void testCPFComTamanhoInvalido() {
        assertFalse(isCPF("123.456-78"));
        assertFalse(isCPF("123.456.78900-9"));
        assertFalse(isCPF("123456789")); // Menos de 11 dígitos
        assertFalse(isCPF("123456789012")); // Mais de 11 dígitos
    }
}