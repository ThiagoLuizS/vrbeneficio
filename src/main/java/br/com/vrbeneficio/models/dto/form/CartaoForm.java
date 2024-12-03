package br.com.vrbeneficio.models.dto.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CartaoForm {
    @NotNull(message = "Numero do cartão obrigatório")
    private Long numeroCartao;
    @NotEmpty(message = "Senha do cartão obrigatório")
    private String senha;
}
