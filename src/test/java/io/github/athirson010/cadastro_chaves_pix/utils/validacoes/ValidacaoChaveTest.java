package io.github.athirson010.cadastro_chaves_pix.utils.validacoes;

import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidacaoChaveTest {
    @Test
    void testValidarChaveNaoExistente() {
        String valorChave = "12345678909";
        ChaveService chaveService = mock(ChaveService.class);
        when(chaveService.buscarChavePorValorChave(valorChave)).thenReturn(Optional.empty());

        ValidacaoChave validacaoChave = new ValidacaoChave(chaveService);
        assertDoesNotThrow(() -> validacaoChave.validarExistencia(valorChave));
    }

    @Test
    void testValidarChaveExistente() {
        String valorChave = "12345678909";
        ChaveService chaveService = mock(ChaveService.class);
        when(chaveService.buscarChavePorValorChave(valorChave)).thenReturn(Optional.of(new ChaveModel()));

        ValidacaoChave validacaoChave = new ValidacaoChave(chaveService);
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> validacaoChave.validarExistencia(valorChave));
        assertEquals("422 UNPROCESSABLE_ENTITY", exception.getMessage());
    }
}