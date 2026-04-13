package com.pixsecurity.pix_security.customer.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// Classe para receber e dar erro logo na entrada dos dados na API (neste caso, via POST), caso algum campo não atenda aos requisitos
@Setter
@Getter
@Data
public class CustomerRequest {

    @NotBlank (message = "O preenchimento do campo CPF é obrigatório")// Este campo não poderá ser vazio ou nulo
    @Size(min = 11, max = 11, message = "O campo CPF deve conter 11 dígitos") // Este campo terá obrigatóriamente 11 caracteres
    private String cpf;

    @NotBlank (message = "O preenchimento do campo nome é obrigatório")
    private String nome;

    @NotBlank (message = "O preenchimento do campo e-mail é obrigatório")
    @Email (message = "E-mail inválido") // Esse campo precisa ser um e-mail válido
    private String email;

    @NotBlank (message = "O preenchimento do campo telefone é obrigatório")
    private String telefone;

}
