package io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix;

import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidacaoChaveExistenteTest {
    @Test
    void testValidarChaveNaoExistente() {
        String valorChave = "12345678909";
        ChaveService chaveService = mock(ChaveService.class);
        when(chaveService.buscarChavePorValorChave(valorChave)).thenReturn(Optional.empty());

        ValidacaoChaveExistente validacaoChaveExistente = new ValidacaoChaveExistente(chaveService);
        assertDoesNotThrow(() -> validacaoChaveExistente.validar(valorChave));
    }

    @Test
    void testValidarChaveExistente() {
        String valorChave = "12345678909";
        ChaveService chaveService = mock(ChaveService.class);
        when(chaveService.buscarChavePorValorChave(valorChave)).thenReturn(Optional.of(new ChaveModel()));

        ValidacaoChaveExistente validacaoChaveExistente = new ValidacaoChaveExistente(chaveService);
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> validacaoChaveExistente.validar(valorChave));
        assertEquals("422 UNPROCESSABLE_ENTITY", exception.getMessage());
    }
}