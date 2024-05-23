package io.github.athirson010.cadastro_chaves_pix.services.v2;

import io.github.athirson010.cadastro_chaves_pix.dados.ChaveMassa;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ContaV2Mapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModelV2;
import io.github.athirson010.cadastro_chaves_pix.repository.ContaV2Repository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContaServiceV2Test {

    private final ContaV2Repository repository = mock(ContaV2Repository.class);
    private final ContaServiceV2 service = new ContaServiceV2(repository);
    ContaModelV2 conta = ContaV2Mapper.of(ChaveMassa.cadastroChavePixRequest());

    @Test
    void buscarContaPorNumeroContaENumeroAgencia() {
        when(repository.findByNumeroContaAndNumeroAgencia(any(), any()))
                .thenReturn(Optional.of(conta));

        Optional<ContaModelV2> result = service.buscarContaPorNumeroContaENumeroAgencia(conta.getNumeroConta(), conta.getNumeroAgencia());
        assertEquals(Optional.of(conta), result);
    }

    @Test
    void buscarTudo() {
        when(repository.findAll(any(Example.class)))
                .thenReturn(List.of(conta));

        Example<ContaModelV2> example = Example.of(conta);

        List<ContaModelV2> result = service.buscarTudo(example);

        assertEquals(List.of(conta), result);
    }
}