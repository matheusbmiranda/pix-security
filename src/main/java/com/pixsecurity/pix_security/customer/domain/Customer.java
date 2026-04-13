package com.pixsecurity.pix_security.customer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity // Com esse código, a classe Customer será uma entidade e terá uma tabela de mesmo nome no banco de dados.
// Objetos do tipo Customer terão os seus dados armazenados nessa tabela.
@Getter
@Setter
@Data
public class Customer {
    @Id // Diz para o JPA (API do Java para salvar objetos no banco de dados) que este campo é a chave primária
    @GeneratedValue // Gerador de ID aleatório automaticamente no banco de dados
    private UUID id;

    @Column(length = 11, nullable = false) // Significa que esse campo deverá virar uma coluna própria no banco, com máximo de 11 caracteres e não pode ficar vazio
    private String cpf;

    @Column(length = 150, nullable = false)
    private String nome;

    @Column(length = 200, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String telefone;

    @Enumerated(EnumType.STRING) // Salvar o nome do Enum como texto no banco, sem isso JPA salva em ordem ordinal (0, 1, 2, 3, etc...)
    private CustomerStatus status;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

}

