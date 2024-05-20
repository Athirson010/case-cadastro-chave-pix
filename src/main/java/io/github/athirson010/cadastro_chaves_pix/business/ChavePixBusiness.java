package io.github.athirson010.cadastro_chaves_pix.business;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.entity.ChaveEntity;
import io.github.athirson010.cadastro_chaves_pix.domains.entity.ContaEntity;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ContaMapper;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;
import io.github.athirson010.cadastro_chaves_pix.services.ContaService;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.ChavePixValidacao;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.impl.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum.INATIVA;

@Service
public class ChavePixBusiness {
    private final ChaveService chaveService;

    private final ContaService contaService;

    public ChavePixBusiness(ChaveService chaveService, ContaService contaService) {
        this.chaveService = chaveService;
        this.contaService = contaService;
    }

    @Transactional
    public CadastroChavePixResponse criarChaveComConta(CadastroChavePixRequest body) {
        resgatarTipoValidacao(body.getTipoChave())
                .validarCaracteristicasChave(body.getValorChave())
                .validarExistenciaChave(body.getValorChave(), chaveService);

        ContaEntity conta = verificarConta(body);

        resgatarQuantidadeChavePorConta(conta.getId(), body.getTipoPessoa());

        ChaveEntity chave = ChaveMapper.of(body, conta);
        chave.setId(UUID.randomUUID().toString());

        return new CadastroChavePixResponse(chaveService.save(chave).getId());
    }

    private void resgatarQuantidadeChavePorConta(String id, TipoPessoaEnum tipoPessoa) {
        if (chaveService.buscarQuantidadeChavesAtivasPorConta(id) >= tipoPessoa.getLimitePermitido()) {
            throw new ValidacaoException("Limite de chaves atingido");
        }
    }

    private ContaEntity verificarConta(CadastroChavePixRequest body) {
        return contaService.buscarContaPorNumeroEAgencia(body.getNumeroConta(), body.getNumeroAgencia())
                .orElseGet(() -> (ContaEntity) contaService.save(ContaMapper.of(body)));
    }

    private ChavePixValidacao resgatarTipoValidacao(TipoChaveEnum tipoChave) {
        return switch (tipoChave) {
            case CELULAR -> new ChavePixValidacaoCelular();
            case EMAIL -> new ChavePixValidacaoEmail();
            case CPF -> new ChavePixValidacaoCPF();
            case CNPJ -> new ChavePixValidacaoCNPJ();
            case ALEATORIA -> new ChavePixValidacaoAleatoria();
        };
    }

    public ChavePixResponse inativarChavePix(String id) {
        ChaveEntity chave = (ChaveEntity) chaveService
                .findById(id);

        if (chave.getStatus().equals(INATIVA)) {
            throw new ValidacaoException("Chave ja inativa");
        }
        chave.setDataInativacao(LocalDateTime.now());
        chave.setStatus(INATIVA);
        return ChaveMapper.of((ChaveEntity) chaveService.update(id, chave));
    }

    public ChavePixResponse buscarPorId(String id) {
        return ChaveMapper.of((ChaveEntity) chaveService.findById(id));
    }
}
