package io.github.athirson010.cadastro_chaves_pix.business;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.exceptions.NaoEncontradoException;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum.I;
import static io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel.filtrarIntervalosDatas;
import static io.github.athirson010.cadastro_chaves_pix.utils.validacoes.ValidacaoChave.resgatarTipoValidacao;

@Service
public class ChavePixBusiness {
    private final ChaveService chaveService;

    public ChavePixBusiness(ChaveService chaveService) {
        this.chaveService = chaveService;
    }

    @Transactional
    public CadastroChavePixResponse criarChaveComConta(CadastroChavePixRequest body) {
        resgatarTipoValidacao(body.getTipoChave())
                .validarCaracteristicas(body.getValorChave())
                .validarDadosConta(body.getNumeroAgencia(), body.getNumeroConta())
                .validarExistenciaChave(body.getValorChave(), chaveService);

        resgatarQuantidadeChavePorConta(body.getNumeroConta(), body.getNumeroAgencia(), body.getTipoPessoa());

        ChaveModel chave = ChaveMapper.of(body);

        return new CadastroChavePixResponse(chaveService.save(chave).getId());
    }

    private void resgatarQuantidadeChavePorConta(String numeroConta, String numeroAgencia, TipoPessoaEnum tipoPessoa) {
        if (chaveService.buscarQuantidadeChavesAtivasPorNumeroContaENumeroAgencia(numeroConta, numeroAgencia) >= tipoPessoa.getLimitePermitido()) {
            throw new ValidacaoException("Limite de chaves atingido");
        }
    }

    public ChavePixResponse inativarChavePix(String id) {
        ChaveModel chave = (ChaveModel) chaveService
                .findById(id);

        if (chave.getStatus().equals(I)) {
            throw new ValidacaoException("Chave ja inativa");
        }
        chave.setDataInativacao(LocalDateTime.now());
        chave.setStatus(I);
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

    public ChaveModel atualizar(String id, AtualizarChavePixRequest request) {
        ChaveModel chave = (ChaveModel) chaveService.findById(id);
        if (chave.getStatus().equals(I)) {
            throw new ValidacaoException("Não é permitido atualizar chaves inativas");
        }
        return (ChaveModel) chaveService.update(id, ChaveMapper.of(request));
    }
}
