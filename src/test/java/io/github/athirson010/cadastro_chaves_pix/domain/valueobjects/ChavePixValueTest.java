package io.github.athirson010.cadastro_chaves_pix.domain.valueobjects;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChavePixValueTest {

    @Test
    void deveValidarEmailCorretamente() {
        ChavePixValue email = ChavePixValue.of("test@example.com");
        
        assertTrue(email.isValidForType(TipoChaveEnum.EMAIL));
        assertFalse(email.isValidForType(TipoChaveEnum.CPF));
    }

    @Test
    void deveValidarCelularCorretamente() {
        ChavePixValue celular = ChavePixValue.of("+5511999999999");
        
        assertTrue(celular.isValidForType(TipoChaveEnum.CELULAR));
        assertFalse(celular.isValidForType(TipoChaveEnum.EMAIL));
    }

    @Test
    void deveValidarCpfCorretamente() {
        ChavePixValue cpf = ChavePixValue.of("11144477735");
        
        assertTrue(cpf.isValidForType(TipoChaveEnum.CPF));
        assertFalse(cpf.isValidForType(TipoChaveEnum.EMAIL));
    }

    @Test
    void deveValidarCnpjCorretamente() {
        ChavePixValue cnpj = ChavePixValue.of("11222333000181");
        
        assertTrue(cnpj.isValidForType(TipoChaveEnum.CNPJ));
        assertFalse(cnpj.isValidForType(TipoChaveEnum.EMAIL));
    }

    @Test
    void deveValidarChaveAleatoriaCorretamente() {
        ChavePixValue aleatoria = ChavePixValue.of("123e4567-e89b-12d3-a456-426614174000");
        
        assertTrue(aleatoria.isValidForType(TipoChaveEnum.ALEATORIA));
        assertFalse(aleatoria.isValidForType(TipoChaveEnum.EMAIL));
    }

    @Test
    void deveLancarExcecaoComValorNulo() {
        assertThrows(NullPointerException.class, () -> ChavePixValue.of(null));
    }

    @Test
    void deveLancarExcecaoComValorVazio() {
        assertThrows(IllegalArgumentException.class, () -> ChavePixValue.of(""));
        assertThrows(IllegalArgumentException.class, () -> ChavePixValue.of("   "));
    }
}