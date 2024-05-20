package io.github.athirson010.cadastro_chaves_pix.services;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.repository.ChaveRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChaveService extends AbstractService {
    private final ChaveRepository repository;

    public ChaveService(ChaveRepository repository) {
        super(ChaveModel.class, repository);
        this.repository = repository;
    }

    public Optional<ChaveModel> buscarChavePorValorChave(String chave) {
        return repository.findByValorChaveAndStatus(chave, StatusChaveEnum.ATIVA);
    }

    public List<ChaveModel> buscarTudo(Example<ChaveModel> example) {
        return repository.findAll(example);
    }

    public int buscarQuantidadeChavesAtivasPorNumeroContaENumeroAgencia(String numeroConta, String numeroAgencia, TipoPessoaEnum tipoPessoa) {
        return repository.countByNumeroContaAndNumeroAgenciaAndStatus(numeroConta, numeroAgencia, StatusChaveEnum.ATIVA);
    }
}
