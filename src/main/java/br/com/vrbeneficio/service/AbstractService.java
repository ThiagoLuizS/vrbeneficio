package br.com.vrbeneficio.service;

import br.com.vrbeneficio.exception.GlobalException;
import br.com.vrbeneficio.models.mapper.MapStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.MongoRepository;

@Slf4j
public abstract class AbstractService<T, View, Form> {

    protected abstract MongoRepository<T, String> getRepository();
    protected abstract MapStructMapper<T, View, Form> getMapper();

    public View saveToView(Form form) {
        try {
            log.info(">> SALVAR [form={}] ", form);
            T t = getMapper().formToEntity(form);
            t = getRepository().save(t);
            log.info("<< SALVAR [t={}] ", t);
            View view =  getMapper().entityToView(t);
            log.info("<< SALVAR [view={}] ", view);
            return view;
        } catch (Exception e) {
            log.error("<< SALVAR [error={}]", e.getMessage(), e);
            throw new GlobalException(e.getMessage());
        }
    }

    public T saveToEntity(T entity) {
        try {
            log.info(">> SALVAR [entity={}] ", entity);
            entity = getRepository().save(entity);
            log.info("<< SALVAR [entity={}] ", entity);
            return entity;
        } catch (Exception e) {
            log.error("<< SALVAR [error={}]", e.getMessage(), e);
            throw new GlobalException(e.getMessage());
        }
    }
}
