package io.github.athirson010.cadastro_chaves_pix.business;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
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

        return new CadastroChavePixResponse(chaveService.save(ChaveMapper.of(body, conta)).getId());
    }

    private void resgatarQuantidadeChavePorConta(String id, TipoPessoaEnum tipoPessoa) {
        if (chaveService.buscarQuantidadeChavesAtivasPorConta(id) >= tipoPessoa.getLimitePermitido()) {
            throw new ValidacaoException();
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
}
