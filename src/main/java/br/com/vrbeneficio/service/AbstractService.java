package br.com.vrbeneficio.service;

import br.com.vrbeneficio.exception.GlobalException;
import br.com.vrbeneficio.models.mapper.MapStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.MongoRepository;

@Slf4j
public abstract class AbstractService<T, View, Form> {

    protected abstract MongoRepository<T, View> getRepository();
    protected abstract MapStructMapper<T, View, Form> getMapper();

    public View saveToView(T entity) {
        try {
            log.debug(">> save [entity={}] ", entity);
            T t = getRepository().save(entity);
            log.debug("<< save [t={}] ", t);
            View view =  getMapper().entityToView(t);
            log.debug("<< save [view={}] ", view);
            return view;
        } catch (Exception e) {
            log.error("<< save [error={}]", e.getMessage(), e);
            throw new GlobalException(e.getMessage());
        }
    }
}
