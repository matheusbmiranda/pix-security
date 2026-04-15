package com.pixsecurity.pix_security.customer.application;

import com.pixsecurity.pix_security.customer.api.dto.CustomerRequest;
import com.pixsecurity.pix_security.customer.domain.Customer;
import com.pixsecurity.pix_security.customer.domain.CustomerStatus;
import com.pixsecurity.pix_security.customer.infrastructure.CustomerRepository;
import com.pixsecurity.pix_security.shared.exception.CpfAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


// Classe para testar a lógica de 'CustomerService' sem subir a aplicação, sem usar banco de dados real
// Essa classe existe para garantir que o 'CustomerService' se comporta corretamente nos casos de
// 1 - Criar cliente com sucesso
// 2 - Rejeitar CPF duplicado
// 3 - Rejeitar cliente inativo


// Use Mockito junto com essa classe de teste
@ExtendWith(MockitoExtension.class) // Mockito simula a aplicação para testar, criando objetos falsos sem chamar banco real
class CustomerServiceTest {

    // Cria um repository falso, simulando
    @Mock
    private CustomerRepository customerRepository;

    // Cria um CustomerService real e injeta os Mocks nela (neste caso, o Repository falso)
    @InjectMocks
    private CustomerService customerService;

    @Test // Indica que esse metodo é 1 teste
    void shouldCreateCustomerSuccessfully() { // Esse teste deverá criar um cliente novo com sucesso

        // Simula o corpo de um objeto vindo da requisição via HTTP
        CustomerRequest request = new CustomerRequest();
        request.setCpf("12345678901");
        request.setNome("Matheus");
        request.setEmail("matheus@email.com");
        request.setTelefone("11999999999");

        // Desvia a validação de caso o cliente já exista, garantindo o funcionamento do teste de criar um cliente novo com sucesso
        when(customerRepository.existsByCpf("12345678901")).thenReturn(false);

        // Simula o save e simula os dados de retorno do objeto salvo
        Customer savedCustomer = new Customer();
        savedCustomer.setId(UUID.randomUUID());
        savedCustomer.setCpf(request.getCpf());
        savedCustomer.setNome(request.getNome());
        savedCustomer.setEmail(request.getEmail());
        savedCustomer.setTelefone(request.getTelefone());
        savedCustomer.setStatus(CustomerStatus.ATIVO);

        // Garante o retorno desse savedCustomer criado sempre que for chamado um save com qualquer Customer
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // Chama o metodo real criarCliente do Service
        var response = customerService.criarCliente(request);

        // Se espera que para o teste dar certo, o resultado obrigatóriamente tem que ser assim
        assertNotNull(response); // Resposta não pode ser nula
        assertEquals("12345678901", response.getCpf()); // CPF de retorno deve ser igual ao enviado
        assertEquals("Matheus", response.getNome()); // Nome de retorno deve ser igual ao enviado
        assertEquals(CustomerStatus.ATIVO, response.getStatus()); // Status deve ser ativo

        verify(customerRepository).existsByCpf("12345678901"); // Confirmar que o service realmente chamou o metodo existsByCpf do Repository
        verify(customerRepository).save(any(Customer.class)); // Confirmar que o service realmente chamou o metodo save
    }

    @Test // Indica que esse metodo é 1 teste
    void shouldRejectDuplicateCpf() { // Esse teste deverá negar a criação se o CPF já existir

        // Simula o corpo de um objeto vindo da requisição via HTTP
        CustomerRequest request = new CustomerRequest();
        request.setCpf("12345678901");
        request.setNome("Matheus");
        request.setEmail("matheus@email.com");
        request.setTelefone("11999999999");

        // Garante que esse cliente já existe no banco, garantindo o funcionamento do teste de rejeitar um duplicado
        when(customerRepository.existsByCpf("12345678901")).thenReturn(true);

        // Se espera que para o teste dar certo, ao tentar criar esse cliente duplicado, a lógica vá cair no erro CpfAlreadyExistsException do metodo criarCliente
        assertThrows(CpfAlreadyExistsException.class, () -> customerService.criarCliente(request));

        verify(customerRepository).existsByCpf("12345678901"); // Confirmar que o service realmente chamou o metodo existsByCpf do Repository
        verify(customerRepository, never()).save(any(Customer.class)); // Confirmar que o service nunca chamou o metodo save
    }

    @Test // Indica que esse metodo é 1 teste
    void shouldRejectUpdateOfInactiveCustomer() { // Esse teste deverá rejeitar a atualização de um cliente inativo

        UUID id = UUID.randomUUID(); // Cria um ID falso para simular um cliente existente

        // Simula o corpo de um objeto vindo da requisição via HTTP
        CustomerRequest request = new CustomerRequest();
        request.setCpf("12345678901");
        request.setNome("Matheus Atualizado");
        request.setEmail("matheus@email.com");
        request.setTelefone("11999999999");

        // Cria um cliente qualquer existente mas com status INATIVO
        Customer inactiveCustomer = new Customer();
        inactiveCustomer.setId(id);
        inactiveCustomer.setCpf("12345678901");
        inactiveCustomer.setNome("Matheus");
        inactiveCustomer.setEmail("matheus@email.com");
        inactiveCustomer.setTelefone("11999999999");
        inactiveCustomer.setStatus(CustomerStatus.INATIVO);

        // Garante o retorno desse inativo sempre que chamar findById
        when(customerRepository.findById(id)).thenReturn(Optional.of(inactiveCustomer));

        // Se espera que para o teste dar certo, ao tentar atualizar esse cliente inativo, a lógica vá cair na exceção RuntimeException do metodo update
        // Além disso, guarda a mensagem do erro na variável exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> customerService.update(id, request));

        // Além de cair na exceção, para o teste dar certo se espera que o resultado obrigatóriamente tem que ser assim
        assertEquals("Cliente inativo, não pode ser editado.", exception.getMessage()); // Confirma que a mensagem do erro deve ser igual a essa

        verify(customerRepository).findById(id); // Confirmar que o service usou o metodo para buscar o cliente
        verify(customerRepository, never()).save(any(Customer.class)); // Confirmar que o service nunca chamou o metodo save
    }
}
