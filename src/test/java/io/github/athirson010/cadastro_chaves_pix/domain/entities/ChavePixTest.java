package io.github.athirson010.cadastro_chaves_pix.domain.entities;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixValue;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ContaId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ChavePixTest {

    @Test
    void devecriarChavePixComSucesso() {
        ChavePixId id = ChavePixId.generate();
        ContaId contaId = ContaId.generate();
        TipoChaveEnum tipoChave = TipoChaveEnum.EMAIL;
        ChavePixValue valorChave = ChavePixValue.of("test@example.com");
        LocalDateTime dataInclusao = LocalDateTime.now();

        ChavePix chavePix = new ChavePix(id, contaId, tipoChave, valorChave, dataInclusao);

        assertEquals(id, chavePix.getId());
        assertEquals(contaId, chavePix.getContaId());
        assertEquals(tipoChave, chavePix.getTipoChave());
        assertEquals(valorChave, chavePix.getValorChave());
        assertEquals(dataInclusao, chavePix.getDataInclusao());
        assertEquals(StatusChaveEnum.ATIVA, chavePix.getStatus());
        assertTrue(chavePix.isAtiva());
        assertNull(chavePix.getDataInativacao());
    }

    @Test
    void deveInativarChaveComSucesso() {
        ChavePix chavePix = criarChavePixValida();

        chavePix.inativar();

        assertEquals(StatusChaveEnum.INATIVA, chavePix.getStatus());
        assertFalse(chavePix.isAtiva());
        assertNotNull(chavePix.getDataInativacao());
    }

    @Test
    void deveLancarExcecaoAoInativarChaveJaInativa() {
        ChavePix chavePix = criarChavePixValida();
        chavePix.inativar();

        assertThrows(IllegalStateException.class, chavePix::inativar);
    }

    @Test
    void deveAtivarChaveInativaComSucesso() {
        ChavePix chavePix = criarChavePixValida();
        chavePix.inativar();

        chavePix.ativar();

        assertEquals(StatusChaveEnum.ATIVA, chavePix.getStatus());
        assertTrue(chavePix.isAtiva());
        assertNull(chavePix.getDataInativacao());
    }

    @Test
    void deveLancarExcecaoAoAtivarChaveJaAtiva() {
        ChavePix chavePix = criarChavePixValida();

        assertThrows(IllegalStateException.class, chavePix::ativar);
    }

    @Test
    void deveLancarExcecaoComValorChaveInvalido() {
        assertThrows(IllegalArgumentException.class, () ->
                new ChavePix(
                        ChavePixId.generate(),
                        ContaId.generate(),
                        TipoChaveEnum.EMAIL,
                        ChavePixValue.of("email-invalido"),
                        LocalDateTime.now()
                )
        );
    }

    private ChavePix criarChavePixValida() {
        return new ChavePix(
                ChavePixId.generate(),
                ContaId.generate(),
                TipoChaveEnum.EMAIL,
                ChavePixValue.of("test@example.com"),
                LocalDateTime.now()
        );
    }
}