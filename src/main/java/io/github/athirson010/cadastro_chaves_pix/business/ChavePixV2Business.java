package io.github.athirson010.cadastro_chaves_pix.business;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.v2.ContaResponseV2;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveV2Mapper;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ContaV2Mapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModelV2;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModelV2;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.v2.ChaveServiceV2;
import io.github.athirson010.cadastro_chaves_pix.services.v2.ContaServiceV2;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum.INATIVA;
import static io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoChave.resgatarTipoValidacao;

@Service
public class ChavePixV2Business {

    private final ChaveServiceV2 chaveServiceV2;

    private final ContaServiceV2 contaServiceV2;

    public ChavePixV2Business(ChaveServiceV2 chaveServiceV2, ContaServiceV2 contaServiceV2) {
        this.chaveServiceV2 = chaveServiceV2;
        this.contaServiceV2 = contaServiceV2;
    }

    @Transactional
    public CadastroChavePixResponse criarChaveComConta(CadastroChavePixRequest body) {
        resgatarTipoValidacao(body.getTipoChave())
                .validarCaracteristicasChave(body.getValorChave())
                .validarExistenciaChave(body.getValorChave(), chaveServiceV2);

        resgatarQuantidadeChavePorConta(body.getNumeroConta(), body.getNumeroAgencia(), body.getTipoPessoa());

        ContaModelV2 conta = validarRegistroConta(body);

        ChaveModelV2 chave = ChaveV2Mapper.of(body, conta);

        return new CadastroChavePixResponse(chaveServiceV2.save(chave).getId());
    }

    private ContaModelV2 validarRegistroConta(CadastroChavePixRequest body) {
        return contaServiceV2.buscarContaPorNumeroContaENumeroAgencia(body.getNumeroConta(), body.getNumeroAgencia())
                .orElseGet(() -> (ContaModelV2) contaServiceV2.save(ContaV2Mapper.of(body)));
    }

    private void resgatarQuantidadeChavePorConta(String numeroConta, String numeroAgencia, TipoPessoaEnum tipoPessoa) {
        Optional<ContaModelV2> contaModelV2 = contaServiceV2.buscarContaPorNumeroContaENumeroAgencia(numeroConta, numeroAgencia);

        if (contaModelV2.isPresent()) {
            if (chaveServiceV2.buscarQuantidadeChavesAtivasPorConta(contaModelV2.get().getId()) >= tipoPessoa.getLimitePermitido()) {
                throw new ValidacaoException("Limite de chaves atingido");
            }
        }
    }

    public ChavePixResponse inativarChavePix(String id) {
        ChaveModelV2 chave = (ChaveModelV2) chaveServiceV2
                .findById(id);

        if (chave.getStatus().equals(INATIVA)) {
            throw new ValidacaoException("Chave ja inativa");
        }
        chave.setDataInativacao(LocalDateTime.now());
        chave.setStatus(INATIVA);

        ContaModelV2 conta = (ContaModelV2) contaServiceV2.findById(chave.getContaId());

        return ChaveMapper.of((ChaveModelV2) chaveServiceV2.update(id, chave), conta);
    }

    public List<ContaResponseV2> buscarChaves(Example<ChaveModelV2> example) {
        List<ChaveModelV2> chaves = chaveServiceV2.buscarTudo(example);

        List<String> contas = chaves
                .stream()
                .map(ChaveModelV2::getContaId)
                .distinct()
                .toList();

        return contaServiceV2.buscarContasPorIds(contas)
                .stream()
                .map(contaModelV2 ->
                        ContaV2Mapper
                                .of(contaModelV2, chaves.stream()
                                        .filter(chaveModelV2 ->
                                                chaveModelV2.getContaId().equals(contaModelV2.getId()))
                                        .toList()))
                .toList();
    }
}
