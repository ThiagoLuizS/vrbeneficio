package br.com.vrbeneficio.repository;

import br.com.vrbeneficio.models.collection.Cartao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends MongoRepository<Cartao, String> {
    Cartao findByNumeroCartao(Long numeroCartao);
}
