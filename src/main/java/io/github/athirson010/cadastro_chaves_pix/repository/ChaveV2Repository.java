package io.github.athirson010.cadastro_chaves_pix.repository;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModelV2;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModelV2;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChaveV2Repository extends MongoRepository<ChaveModelV2, String> {
    Optional<ChaveModelV2> findByValorChaveAndStatus(String chave, StatusChaveEnum statusChaveEnum);

    int countByContaIdAndStatus(String contaId, StatusChaveEnum statusChaveEnum);

    Optional<ContaModelV2> findByContaId(String numeroConta, String numeroAgencia);
}
