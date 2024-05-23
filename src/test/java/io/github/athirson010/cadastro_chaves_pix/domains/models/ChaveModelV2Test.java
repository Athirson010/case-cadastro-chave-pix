package io.github.athirson010.cadastro_chaves_pix.domains.models;

import io.github.athirson010.cadastro_chaves_pix.dados.ChaveMassa;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveV2Mapper;
import io.github.athirson010.cadastro_chaves_pix.utils.AbstractModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ChaveModelV2Test {
    @Test
    public void testFiltrarPorDataInclusao() {
        ChaveModelV2 chave = ChaveV2Mapper.of(ChaveMassa.cadastroChavePixRequest(),
                new AbstractModel("1", LocalDate.now()));

        List<ChaveModelV2> chaves = Collections.singletonList(chave);


        ChaveModelV2 filtro = ChaveModelV2.builder()
                .dataInclusao(LocalDateTime.now())
                .build();

        List<ChaveModelV2> resultado = ChaveModelV2.filtrarIntervalosDatas(chaves, filtro);

        assertThat(resultado).containsExactly(chave);
    }

    @Test
    public void testFiltrarPorDataInativacao() {
        ChaveModelV2 chave = ChaveV2Mapper.of(ChaveMassa.cadastroChavePixRequest(),
                new AbstractModel("1", LocalDate.now()));
        chave.setDataInativacao(LocalDateTime.now());

        List<ChaveModelV2> chaves = List.of(chave);

        ChaveModelV2 filtro = ChaveModelV2.builder()
                .dataInativacao(LocalDateTime.now())
                .build();

        List<ChaveModelV2> resultado = ChaveModelV2.filtrarIntervalosDatas(chaves, filtro);

        assertThat(resultado).containsExactly(chave);
    }

    @Test
   public void testSemFiltro() {

        ChaveModelV2 chave = new ChaveModelV2();

        List<ChaveModelV2> chaves = List.of(chave);

        ChaveModelV2 filtro = new ChaveModelV2();

        List<ChaveModelV2> resultado = ChaveModelV2.filtrarIntervalosDatas(chaves, filtro);

        assertThat(resultado).containsExactly(chave);
    }

}