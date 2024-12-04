package br.com.vrbeneficio.models.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartaoForm {
    @JsonIgnore
    private String id;
    @NotNull(message = "Numero do cartão obrigatório")
    private Long numeroCartao;
    @NotEmpty(message = "Senha do cartão obrigatório")
    private String senha;
    @JsonIgnore
    private BigDecimal saldo = BigDecimal.valueOf(500);
}
