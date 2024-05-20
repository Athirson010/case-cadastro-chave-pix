package io.github.athirson010.cadastro_chaves_pix.repository;


import io.github.athirson010.cadastro_chaves_pix.domains.entity.ChaveEntity;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChaveRepository extends MongoRepository<ChaveEntity, String> {
    Optional<ChaveEntity> findByValorChaveAndStatus(String chave, StatusChaveEnum status);

    int countByContaIdAndStatus(String idConta, StatusChaveEnum status);
}
