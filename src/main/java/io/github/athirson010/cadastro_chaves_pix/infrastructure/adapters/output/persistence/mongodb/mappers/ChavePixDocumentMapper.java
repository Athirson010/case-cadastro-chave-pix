package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.mappers;

import io.github.athirson010.cadastro_chaves_pix.domain.entities.ChavePix;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixValue;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ContaId;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.entities.ChavePixDocument;

public final class ChavePixDocumentMapper {

    private ChavePixDocumentMapper() {
    }

    public static ChavePixDocument toDocument(ChavePix chavePix) {
        return new ChavePixDocument(
                chavePix.getId().getValue(),
                chavePix.getContaId().getValue(),
                chavePix.getTipoChave(),
                chavePix.getValorChave().getValue(),
                chavePix.getDataInclusao(),
                chavePix.getDataInativacao(),
                chavePix.getStatus()
        );
    }

    public static ChavePix toDomain(ChavePixDocument document) {
        return new ChavePix(
                ChavePixId.of(document.getId()),
                ContaId.of(document.getContaId()),
                document.getTipoChave(),
                ChavePixValue.of(document.getValorChave()),
                document.getDataInclusao(),
                document.getDataInativacao(),
                document.getStatus()
        );
    }
}