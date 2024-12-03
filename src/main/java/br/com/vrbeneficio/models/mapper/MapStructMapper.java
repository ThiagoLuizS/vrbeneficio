package br.com.vrbeneficio.models.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MapStructMapper<T, View, Form> {
    View entityToView(T t);
    T formToEntity(Form form);
}
