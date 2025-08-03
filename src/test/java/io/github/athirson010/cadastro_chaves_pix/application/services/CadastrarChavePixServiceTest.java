package io.github.athirson010.cadastro_chaves_pix.application.services;

import io.github.athirson010.cadastro_chaves_pix.application.dto.CadastrarChavePixCommand;
import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ChavePixRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ContaRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.ChavePix;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.Conta;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoPessoaEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.exceptions.ChaveDuplicadaException;
import io.github.athirson010.cadastro_chaves_pix.domain.exceptions.LimiteChavesExcedidoException;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarChavePixServiceTest {

    @Mock
    private ChavePixRepositoryPort chavePixRepository;

    @Mock
    private ContaRepositoryPort contaRepository;

    private CadastrarChavePixService service;

    @BeforeEach
    void setUp() {
        service = new CadastrarChavePixService(chavePixRepository, contaRepository);
    }

    @Test
    void deveCadastrarChavePixComSucesso() {
        CadastrarChavePixCommand command = new CadastrarChavePixCommand(
                TipoChaveEnum.EMAIL,
                "test@example.com",
                TipoContaEnum.CORRENTE,
                "1234",
                "12345678",
                "João Silva",
                TipoPessoaEnum.PESSOA_FISICA
        );

        Conta conta = criarConta();
        ChavePix chavePixSalva = criarChavePix();

        when(chavePixRepository.existePorValor(any(ChavePixValue.class))).thenReturn(false);
        when(contaRepository.buscarPorAgenciaEConta(any(NumeroAgencia.class), any(NumeroConta.class)))
                .thenReturn(Optional.of(conta));
        when(chavePixRepository.contarChavesAtivasPorConta(any(ContaId.class))).thenReturn(0L);
        when(chavePixRepository.salvar(any(ChavePix.class))).thenReturn(chavePixSalva);

        ChavePix resultado = service.executar(command);

        assertNotNull(resultado);
        verify(chavePixRepository).existePorValor(any(ChavePixValue.class));
        verify(chavePixRepository).contarChavesAtivasPorConta(any(ContaId.class));
        verify(chavePixRepository).salvar(any(ChavePix.class));
    }

    @Test
    void deveLancarExcecaoQuandoChaveJaExiste() {
        CadastrarChavePixCommand command = new CadastrarChavePixCommand(
                TipoChaveEnum.EMAIL,
                "test@example.com",
                TipoContaEnum.CORRENTE,
                "1234",
                "12345678",
                "João Silva",
                TipoPessoaEnum.PESSOA_FISICA
        );

        when(chavePixRepository.existePorValor(any(ChavePixValue.class))).thenReturn(true);

        assertThrows(ChaveDuplicadaException.class, () -> service.executar(command));
        verify(chavePixRepository).existePorValor(any(ChavePixValue.class));
        verify(chavePixRepository, never()).salvar(any(ChavePix.class));
    }

    @Test
    void deveLancarExcecaoQuandoLimiteChavesExcedido() {
        CadastrarChavePixCommand command = new CadastrarChavePixCommand(
                TipoChaveEnum.EMAIL,
                "test@example.com",
                TipoContaEnum.CORRENTE,
                "1234",
                "12345678",
                "João Silva",
                TipoPessoaEnum.PESSOA_FISICA
        );

        Conta conta = criarConta();

        when(chavePixRepository.existePorValor(any(ChavePixValue.class))).thenReturn(false);
        when(contaRepository.buscarPorAgenciaEConta(any(NumeroAgencia.class), any(NumeroConta.class)))
                .thenReturn(Optional.of(conta));
        when(chavePixRepository.contarChavesAtivasPorConta(any(ContaId.class))).thenReturn(5L);

        assertThrows(LimiteChavesExcedidoException.class, () -> service.executar(command));
        verify(chavePixRepository, never()).salvar(any(ChavePix.class));
    }

    private Conta criarConta() {
        return new Conta(
                ContaId.generate(),
                TipoContaEnum.CORRENTE,
                NumeroAgencia.of("1234"),
                NumeroConta.of("12345678"),
                "João Silva",
                TipoPessoaEnum.PESSOA_FISICA
        );
    }

    private ChavePix criarChavePix() {
        return new ChavePix(
                ChavePixId.generate(),
                ContaId.generate(),
                TipoChaveEnum.EMAIL,
                ChavePixValue.of("test@example.com"),
                java.time.LocalDateTime.now()
        );
    }
}