package io.github.athirson010.cadastro_chaves_pix.business;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.FiltroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ContaMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModel;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;
import io.github.athirson010.cadastro_chaves_pix.services.ContaService;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.ChavePixValidacao;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.impl.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

        ContaModel conta = verificarConta(body);

        resgatarQuantidadeChavePorConta(conta.getId(), body.getTipoPessoa());

        ChaveModel chave = ChaveMapper.of(body, conta);

        return new CadastroChavePixResponse(chaveService.save(chave).getId());
    }

    private void resgatarQuantidadeChavePorConta(String id, TipoPessoaEnum tipoPessoa) {
        if (chaveService.buscarQuantidadeChavesAtivasPorConta(id) >= tipoPessoa.getLimitePermitido()) {
            throw new ValidacaoException("Limite de chaves atingido");
        }
    }

    private ContaModel verificarConta(CadastroChavePixRequest body) {
        return contaService.buscarContaPorNumeroEAgencia(body.getNumeroConta(), body.getNumeroAgencia())
                .orElseGet(() -> (ContaModel) contaService.save(ContaMapper.of(body)));
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
        ChaveModel chave = (ChaveModel) chaveService
                .findById(id);

        if (chave.getStatus().equals(INATIVA)) {
            throw new ValidacaoException("Chave ja inativa");
        }
        chave.setDataInativacao(LocalDateTime.now());
        chave.setStatus(INATIVA);
        return ChaveMapper.of((ChaveModel) chaveService.update(id, chave));
    }

    public ChavePixResponse buscarPorId(String id) {
        return ChaveMapper.of((ChaveModel) chaveService.findById(id));
    }


    private List<Criteria> resgatarCriteriosChave(FiltroChavePixRequest request) {
        List<Criteria> criteriosChave = new ArrayList<>();

        if (request.getId() != null) {
            criteriosChave.add(Criteria.where("id").is(request.getId()));
        }
        if (request.getTipoChave() != null) {
            criteriosChave.add(Criteria.where("tipoChave").is(request.getTipoChave()));
        }


        return criteriosChave;
    }

    public List<ChavePixResponse> buscarChaves(FiltroChavePixRequest request) {
        List<Criteria> criterios = new ArrayList<>();


        if (request.getAgencia() != null) {
            criterios.add(Criteria.where("conta.numeroAgencia").is(request.getAgencia()));
        }
        if (request.getConta() != null) {
            criterios.add(Criteria.where("conta.numeroConta").is(request.getConta()));
        }
        if (request.getNomeCorrentista() != null) {
            criterios.add(Criteria.where("conta.nomeCorrentista").is(request.getNomeCorrentista()));
        }

        if (request.getTipoChave() != null) {
            criterios.add(Criteria.where("tipoChave").is(request.getTipoChave()));
        }

        if (request.getDataInclusao() != null) {
            LocalDateTime inclusaoComeco = request.getDataInclusao().atStartOfDay();
            LocalDateTime inclusaoFinal = LocalDateTime.of(request.getDataInclusao(), LocalTime.MAX);
            criterios.add(Criteria.where("dataInclusao")
                    .gte(inclusaoComeco)
                    .lte(inclusaoFinal));
        }

        if (request.getDataInativacao() != null) {
            LocalDateTime inativacaoComeco = request.getDataInativacao().atStartOfDay();
            LocalDateTime inativacaoFinal = LocalDateTime.of(request.getDataInativacao(), LocalTime.MAX);
            criterios.add(Criteria.where("dataInativacao")
                    .gte(inativacaoComeco)
                    .lte(inativacaoFinal));
        }

        return ((List<ChaveModel>) chaveService.findByCriteria(criterios)).stream().map(ChaveMapper::of).toList();
    }
}
