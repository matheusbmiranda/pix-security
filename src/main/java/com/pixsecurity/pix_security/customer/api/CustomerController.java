package com.pixsecurity.pix_security.customer.api;

import com.pixsecurity.pix_security.customer.api.dto.CustomerRequest;
import com.pixsecurity.pix_security.customer.api.dto.CustomerResponse;
import com.pixsecurity.pix_security.customer.application.CustomerService;
import com.pixsecurity.pix_security.customer.domain.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController // Essa classe vai receber requisições HTTP e devolver JSON
@RequestMapping("/api/v1/customers") // Todas as rotas dessa classe começam com /api/v1/customers
@Tag(name = "Customers", description = "Endpoints para gerenciamento de clientes") // Organiza e descreve um grupo de endpoints
public class CustomerController { // É a classe que permite receber requisições via POST
    private final CustomerService customerService; // Precisa do Service para salvar no banco

    public CustomerController(CustomerService customerService) { // Construtor para inserir o Service no Controller

        this.customerService = customerService;
    }

    // Recebe um JSON válido para criar cliente, manda o service criar e salvar, e devolve uma resposta HTTP 201 com o cliente criado no formato de API
    @PostMapping // Esse metodo recebe requisições HTTP, pega os dados enviados, manda para o Service e devolve a resposta
    @Operation(summary = "Criar cliente", description = "Cria um novo cliente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "CPF já cadastrado")
    })
    public ResponseEntity<CustomerResponse> criarCliente(@Valid @RequestBody CustomerRequest request) { // Pegar o corpo da requisição HTTP, validar e devolver resposta HTTP completa
        CustomerResponse response = customerService.criarCliente(request); // Pega o request, envia para CustomerService criar Customer, salva no banco, converte e devovle CustomerResponse
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Passa a resposta HTTP completa, status 201 (criado com sucesso) e o corpo da resposta é CustomerResponse
    }

    // Metodo para pegar dados de um cliente através de seu ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente pelo UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public CustomerResponse buscarPorId(@PathVariable UUID id) { // Vai pegar o que vier na URL no lugar de {id} e colocar na variável id

        return customerService.buscarPorId(id);
    }

    // Metodo para atualizar dados de um ID valido atraves do JSON (PUT)
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza nome, email e telefone de um cliente ativo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou cliente inativo"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public CustomerResponse update(@PathVariable UUID id, @Valid @RequestBody CustomerRequest request) {
        return customerService.update(id, request); // Passa para o service qual cliente será atualizado (id) e os dados novos que vieram (request)
    }

    // Metodo para desativar um ID
    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Inativar cliente", description = "Inativa logicamente um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public CustomerResponse deactivate(@PathVariable UUID id) {
        return customerService.deactivate(id);
    }
}


