package com.pixsecurity.pix_security.customer.api.dto;

import com.pixsecurity.pix_security.customer.domain.CustomerStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

// Classe que controla o que será apresentado (neste caso, via GET no POSTMAN)
@Getter
@Setter
@Data
public class CustomerResponse {

    private UUID id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private CustomerStatus status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

}

