package com.pixsecurity.pix_security.customer.application;

import com.pixsecurity.pix_security.customer.api.dto.CustomerRequest;
import com.pixsecurity.pix_security.customer.api.dto.CustomerResponse;
import com.pixsecurity.pix_security.customer.domain.Customer;
import com.pixsecurity.pix_security.customer.domain.CustomerStatus;
import com.pixsecurity.pix_security.customer.infrastructure.CustomerRepository;
import com.pixsecurity.pix_security.shared.exception.CpfAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service // Transforma a classe em um objeto gerenciado diretamente pelo Spring
public class CustomerService {

    // Atributo (guardar o Repository), indica que essa classe CustomerService PRECISA de um CustomerRepository para funcionar
    private final CustomerRepository customerRepository;

    // Construtor (receber o repository e guardar dentro da classe)
    public CustomerService(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    public CustomerResponse criarCliente(CustomerRequest request) { // Função que recebe um objeto request e retorna um Customer salvo
        if (customerRepository.existsByCpf(request.getCpf())) { // Verifica no banco se já existe alguém com esse cpf
            throw new CpfAlreadyExistsException("CPF já cadastrado"); // Se sim, mostra mensagem erro
        }

        Customer customer = new Customer();

        customer.setCpf(request.getCpf());
        customer.setNome(request.getNome());
        customer.setEmail(request.getEmail());
        customer.setTelefone(request.getTelefone());

        customer.setStatus(CustomerStatus.ATIVO);
        customer.setDataCriacao(LocalDateTime.now());
        customer.setDataAtualizacao(LocalDateTime.now());

        Customer customerSalvo = customerRepository.save(customer);
        return toResponse(customerSalvo);
    }


    // Buscar um cliente por UUID, se existir, retorna os dados deste, se não existir, mostra erro.
    public CustomerResponse buscarPorId(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow();

        return toResponse(customer);
    }


    // Transforma um Customer (entidade do banco de dados) em um Response (saída da API)
    private CustomerResponse toResponse(Customer customer) {

        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getId());
        response.setCpf(customer.getCpf());
        response.setNome(customer.getNome());
        response.setEmail(customer.getEmail());
        response.setTelefone(customer.getTelefone());
        response.setStatus(customer.getStatus());
        response.setDataCriacao(customer.getDataCriacao());
        response.setDataAtualizacao(customer.getDataAtualizacao());

        return response;
    }

    // Metodo para o Service buscar e retornar um Customer (entidade do banco) pelo ID, se existir mostra se não existir da erro, para ser utilizado pelo update abaixo
    private Customer findCustomerById(UUID id) {
        return customerRepository.findById(id).orElseThrow();
    }

    // Metodo que: busca cliente por ID, se não existir ou se status inativo = erro, atualiza dados possiveis, atualiza dataAtualizaçao, salva, converte para CustomerResponse
    // CustomerResponse = tipo de retorno que o metodo vai devolver
    // update = nome do metodo
    // UUID id = identificador do cliente que queremos editar
    // CustomerRequest request = objeto com novos dados substitutos, os novos dados vem dentro do request
    public CustomerResponse update(UUID id, CustomerRequest request) {
        Customer customer = findCustomerById(id);
        if (customer.getStatus() != CustomerStatus.ATIVO) { // Verifica se o customer encontrado está ativo
            throw new RuntimeException("Cliente inativo, não pode ser editado."); // Se inativo, interrompe o metodo e mostra o erro
        }
        // Pega as novas informações do request e coloca no customer encontrado no banco
        customer.setNome(request.getNome());
        customer.setEmail(request.getEmail());
        customer.setTelefone(request.getTelefone());
        customer.setDataAtualizacao(LocalDateTime.now());

        Customer customerAtualizado = customerRepository.save(customer); // Salva as mudanças no banco

        return toResponse(customerAtualizado); // Pega a entidade customer transforma em Response antes de devolver (saída da API)

    }

    // Metodo para mudar status do cliente para INATIVO
    public CustomerResponse deactivate(UUID id) {
        Customer customer = findCustomerById(id); // Busca o customer
        customer.setStatus(CustomerStatus.INATIVO); // Inativa-o
        customer.setDataAtualizacao(LocalDateTime.now()); // Atualiza a data
        Customer customerInativado = customerRepository.save(customer); // Salva no MySQL
        return toResponse(customerInativado); // Converte para Response e retorna para a API

    }

}
