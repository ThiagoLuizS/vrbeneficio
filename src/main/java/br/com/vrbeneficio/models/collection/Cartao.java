package br.com.vrbeneficio.models.collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cartoes")
public class Cartao {
    @Id
    private String id;
    private Long numeroCartao;
    private String senha;
    private BigDecimal saldo;
}
