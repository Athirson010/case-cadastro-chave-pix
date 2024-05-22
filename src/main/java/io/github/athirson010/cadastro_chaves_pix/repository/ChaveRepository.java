package io.github.athirson010.cadastro_chaves_pix.repository;


import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChaveRepository extends MongoRepository<ChaveModel, String> {
    Optional<ChaveModel> findByValorChaveAndStatus(String chave, StatusChaveEnum status);

    int countByNumeroContaAndNumeroAgenciaAndStatus(String numeroConta, String numeroAgencia, StatusChaveEnum statusChaveEnum);

    Optional<ChaveModel> findByNumeroContaAndNumeroAgencia(String numeroConta, String numeroAgencia);
}
