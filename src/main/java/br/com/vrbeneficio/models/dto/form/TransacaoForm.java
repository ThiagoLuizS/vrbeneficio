package br.com.vrbeneficio.models.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransacaoForm {
    @JsonIgnore
    private String id;
    @NotNull(message = "Numero do cartão obrigatório")
    private Long numeroCartao;
    @NotEmpty(message = "Senha do cartão obrigatório")
    private String senha;
    @NotNull(message = "Informe o valor")
    private BigDecimal valor;
}
