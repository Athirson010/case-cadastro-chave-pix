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
import io.github.athirson010.cadastro_chaves_pix.exceptions.NaoEncontradoException;
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public List<ChavePixResponse> buscarChaves(FiltroChavePixRequest request) {
        List<Criteria> criteriosConta = new ArrayList<>();
        List<Criteria> criteriosChaves = new ArrayList<>();

        if (request.getAgencia() != null && !request.getAgencia().isBlank()) {
            criteriosConta.add(Criteria.where("numeroAgencia").is(request.getAgencia()));
        }
        if (request.getConta() != null && !request.getConta().isBlank()) {
            criteriosConta.add(Criteria.where("numeroConta").is(request.getConta()));
        }
        if (request.getNomeCorrentista() != null && !request.getNomeCorrentista().isBlank()) {
            criteriosConta.add(Criteria.where("nomeCorrentista").regex(Pattern.quote(request.getNomeCorrentista()), "i"));
        }

        if (request.getTipoChave() != null) {
            criteriosChaves.add(Criteria.where("tipoChave").is(request.getTipoChave()));
        }

        if (request.getDataInclusao() != null) {
            LocalDateTime inclusaoComeco = request.getDataInclusao().atStartOfDay();
            LocalDateTime inclusaoFinal = LocalDateTime.of(request.getDataInclusao(), LocalTime.MAX);
            criteriosChaves.add(Criteria.where("dataInclusao")
                    .gte(inclusaoComeco)
                    .lte(inclusaoFinal));
        }

        if (request.getDataInativacao() != null) {
            LocalDateTime inativacaoComeco = request.getDataInativacao().atStartOfDay();
            LocalDateTime inativacaoFinal = LocalDateTime.of(request.getDataInativacao(), LocalTime.MAX);
            criteriosChaves.add(Criteria.where("dataInativacao")
                    .gte(inativacaoComeco)
                    .lte(inativacaoFinal));
        }

        if (!criteriosConta.isEmpty()) {
            List<ContaModel> contas = contaService.findByCriteria(criteriosConta);
            List<String> contaIds = contas.stream().map(ContaModel::getId).collect(Collectors.toList());

            criteriosChaves.add(Criteria.where("conta.$id").in(contaIds));
        }

        List<ChaveModel> chaves;
        if (!criteriosChaves.isEmpty()) {
            chaves = chaveService.findByCriteria(criteriosChaves);
        } else {
            chaves = chaveService.findAll();
        }
        if (chaves.isEmpty()) {
            throw new NaoEncontradoException("Chaves");
        }
        return chaves.stream().map(ChaveMapper::of).toList();
    }
}
