package io.github.athirson010.cadastro_chaves_pix.domains.models;

import io.github.athirson010.cadastro_chaves_pix.dados.ChaveMassa;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveV2Mapper;
import io.github.athirson010.cadastro_chaves_pix.utils.AbstractModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class ChaveModelTest {
    @Test
    public void testFiltrarPorDataInclusao() {
        ChaveModel chave = ChaveMapper.of(ChaveMassa.cadastroChavePixRequest());

        List<ChaveModel> chaves = Collections.singletonList(chave);

        ChaveModel filtro = ChaveModel.builder()
                .dataInclusao(LocalDateTime.now())
                .build();

        List<ChaveModel> resultado = ChaveModel.filtrarIntervalosDatas(chaves, filtro);

        assertThat(resultado).containsExactly(chave);
    }

    @Test
    public void testFiltrarPorDataInativacao() {
        ChaveModel chave = ChaveMapper.of(ChaveMassa.cadastroChavePixRequest());
        chave.setDataInativacao(LocalDateTime.now());

        List<ChaveModel> chaves = List.of(chave);

        ChaveModel filtro = ChaveModel.builder()
                .dataInativacao(LocalDateTime.now())
                .build();

        List<ChaveModel> resultado = ChaveModel.filtrarIntervalosDatas(chaves, filtro);

        assertThat(resultado).containsExactly(chave);
    }

    @Test
    public void testSemFiltro() {

        ChaveModel chave = new ChaveModel();

        List<ChaveModel> chaves = List.of(chave);

        ChaveModel filtro = new ChaveModel();

        List<ChaveModel> resultado = ChaveModel.filtrarIntervalosDatas(chaves, filtro);

        assertThat(resultado).containsExactly(chave);
    }

}