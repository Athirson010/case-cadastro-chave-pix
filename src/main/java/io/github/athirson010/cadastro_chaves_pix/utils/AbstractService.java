package io.github.athirson010.cadastro_chaves_pix.utils;


import io.github.athirson010.cadastro_chaves_pix.exceptions.NaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public abstract class AbstractService<Model extends AbstractModel, Repository extends MongoRepository<Model, String>> {
    protected Repository repository;
    @Autowired
    protected MongoTemplate mongoTemplate;
    protected Class<Model> beanClass;
    protected String CONTEXTO;
    @Value("${findall.max.results:999}")
    private Integer findAllMaxResults;
    private final String ACCENT_STRINGS = "àáâãäåßòóôõöøèéêëðçÐìíîïùúûüñšÿýž";
    private final String NO_ACCENT_STRINGS = "aaaaaabooooooeeeeecdiiiiuuuunsyyz";
    private Model modelGenerico;

    public AbstractService(Class<Model> beanClass, Repository repository) {
        this.beanClass = beanClass;
        this.repository = repository;
        this.CONTEXTO = buscarContexto(beanClass.getName());
    }

    public Model save(Model model) {
        model.setId(UUID.randomUUID().toString());
        return repository.save(model);
    }

    public Model update(String id, Model model) {
        this.findById(id);
        model.setId(id);
        modelGenerico = repository.save(model);
        return modelGenerico;
    }

    public Model findById(String id) {
        return repository.findById(id).orElseThrow(() -> new NaoEncontradoException(CONTEXTO));
    }


    private String buscarContexto(String pacote) {
        List<String> pacoteList = List.of(pacote.split("\\."));
        return pacoteList.get(pacoteList.size() - 1).replace("Model", "");
    }

}
