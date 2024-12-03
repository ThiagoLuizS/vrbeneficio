package br.com.vrbeneficio.models.mapper;

import br.com.vrbeneficio.models.collection.Cartao;
import br.com.vrbeneficio.models.dto.form.CartaoForm;
import br.com.vrbeneficio.models.dto.view.CartaoView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = MapStructMapper.class)
public interface CartaoMapper extends MapStructMapper<Cartao, CartaoView, CartaoForm>{
}
