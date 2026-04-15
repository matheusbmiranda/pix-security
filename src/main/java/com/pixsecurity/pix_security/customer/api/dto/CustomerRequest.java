package com.pixsecurity.pix_security.customer.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// Classe para receber e dar erro logo na entrada dos dados na API (neste caso, via POST), caso algum campo não atenda aos requisitos
@Setter
@Getter
@Schema(description = "Dados necessários para criar ou atualizar um cliente")
public class CustomerRequest {

    @Schema(description = "CPF do cliente com 11 dígitos", example = "12345678901")
    @NotBlank (message = "O preenchimento do campo CPF é obrigatório")// Este campo não poderá ser vazio ou nulo
    @Size(min = 11, max = 11, message = "O campo CPF deve conter 11 dígitos") // Este campo terá obrigatóriamente 11 caracteres
    private String cpf;

    @Schema(description = "Nome completo do cliente", example = "Matheus Botelho")
    @NotBlank (message = "O preenchimento do campo nome é obrigatório")
    private String nome;

    @Schema(description = "E-mail do cliente", example = "matheus@email.com")
    @NotBlank (message = "O preenchimento do campo e-mail é obrigatório")
    @Email (message = "E-mail inválido") // Esse campo precisa ser um e-mail válido
    private String email;

    @Schema(description = "Telefone do cliente com DDD", example = "11999999999")
    @NotBlank (message = "O preenchimento do campo telefone é obrigatório")
    private String telefone;

}
