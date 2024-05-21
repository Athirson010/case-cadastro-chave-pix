package io.github.athirson010.cadastro_chaves_pix.business;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.exceptions.NaoEncontradoException;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.ChavePixValidacao;
import io.github.athirson010.cadastro_chaves_pix.utils.validacoes.chave_pix.impl.*;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum.INATIVA;

@Service
public class ChavePixBusiness {
    private final ChaveService chaveService;

    public ChavePixBusiness(ChaveService chaveService) {
        this.chaveService = chaveService;
    }

    @Transactional
    public CadastroChavePixResponse criarChaveComConta(CadastroChavePixRequest body) {
        resgatarTipoValidacao(body.getTipoChave())
                .validarCaracteristicasChave(body.getValorChave())
                .validarExistenciaChave(body.getValorChave(), chaveService);

        resgatarQuantidadeChavePorConta(body.getNumeroConta(), body.getNumeroAgencia(), body.getTipoPessoa());

        ChaveModel chave = ChaveMapper.of(body);

        return new CadastroChavePixResponse(chaveService.save(chave).getId());
    }

    private void resgatarQuantidadeChavePorConta(String numeroConta, String numeroAgencia, TipoPessoaEnum tipoPessoa) {
        if (chaveService.buscarQuantidadeChavesAtivasPorNumeroContaENumeroAgencia(numeroConta, numeroAgencia, tipoPessoa) >= tipoPessoa.getLimitePermitido()) {
            throw new ValidacaoException("Limite de chaves atingido");
        }
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

    public List<ChavePixResponse> buscarChaves(Example<ChaveModel> example) {
        List<ChaveModel> chaves = filtrarIntervalosDatas(chaveService.buscarTudo(example), example.getProbe());

        if (chaves.isEmpty()) {
            throw new NaoEncontradoException("Chaves");
        }

        return chaves.stream().map(ChaveMapper::of).toList();
    }

    private List<ChaveModel> filtrarIntervalosDatas(List<ChaveModel> chaves, ChaveModel filtro) {

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

    public ChaveModel atualizar(String id, AtualizarChavePixRequest request) {
        return (ChaveModel) chaveService.update(id, ChaveMapper.of(request));
    }
}
