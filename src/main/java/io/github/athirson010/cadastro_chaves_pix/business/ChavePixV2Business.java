package io.github.athirson010.cadastro_chaves_pix.business;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
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
import io.github.athirson010.cadastro_chaves_pix.exceptions.NaoEncontradoException;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.v2.ChaveServiceV2;
import io.github.athirson010.cadastro_chaves_pix.services.v2.ContaServiceV2;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ContaResponseV2> buscarChaves(Example<ChaveModelV2> exampleChave, Example<ContaModelV2> exampleConta) {
        List<ChaveModelV2> chaves = filtrarIntervalosDatas(chaveServiceV2.buscarTudo(exampleChave), exampleChave.getProbe());
        List<ContaModelV2> contas = contaServiceV2.buscarTudo(exampleConta);

        List<ContaResponseV2> resultado = contas.stream()
                .map(contaModelV2 -> ContaV2Mapper.of(contaModelV2, chaves.stream()
                        .filter(chaveModelV2 -> chaveModelV2.getContaId().equals(contaModelV2.getId())).toList()))
                .toList();

        if (resultado.isEmpty()) {
            throw new NaoEncontradoException("Criterio(s)");
        }
        return resultado;
    }

    private List<ChaveModelV2> filtrarIntervalosDatas(List<ChaveModelV2> chaves, ChaveModelV2 filtro) {
        if (filtro.getDataInclusao() != null) {
            LocalDateTime inclusaoComeco = filtro.getDataInclusao();
            LocalDateTime inclusaoFinal = LocalDateTime.of(LocalDate.from(filtro.getDataInclusao()), LocalTime.MAX);

            return chaves.stream()
                    .filter(chave -> chave.getDataInclusao() != null &&
                            !chave.getDataInclusao().isBefore(inclusaoComeco) &&
                            !chave.getDataInclusao().isAfter(inclusaoFinal))
                    .collect(Collectors.toList());
        }
        if (filtro.getDataInativacao() != null) {
            LocalDateTime inclusaoComeco = filtro.getDataInativacao();
            LocalDateTime inclusaoFinal = LocalDateTime.of(LocalDate.from(filtro.getDataInativacao()), LocalTime.MAX);

            return chaves.stream()
                    .filter(chave -> chave.getDataInclusao() != null &&
                            !chave.getDataInativacao().isBefore(inclusaoComeco) &&
                            !chave.getDataInativacao().isAfter(inclusaoFinal))
                    .collect(Collectors.toList());
        }
        return chaves;
    }

    @Transactional
    public ContaResponseV2 atualizarChavePix(String id, AtualizarChavePixRequest body) {
        resgatarTipoValidacao(body.getTipoChave())
                .validarCaracteristicasChave(body.getValorChave())
                .validarExistenciaChave(body.getValorChave(), chaveServiceV2);

        ChaveModelV2 chave = (ChaveModelV2) chaveServiceV2.findById(id);

        if (chave.getStatus().equals(INATIVA)) {
            throw new ValidacaoException("Chave inativa");
        }

        ContaModelV2 contaModelV2 = ContaV2Mapper.of(body);

        chave = ChaveV2Mapper.of(body);

        contaServiceV2.update(chave.getContaId(), contaModelV2);
        chave = (ChaveModelV2) chaveServiceV2.update(chave.getId(), chave);

        return ContaV2Mapper.of(contaModelV2, List.of(chave));
    }
}
