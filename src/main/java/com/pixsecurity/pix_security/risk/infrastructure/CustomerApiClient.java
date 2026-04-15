package com.pixsecurity.pix_security.risk.infrastructure;


import com.pixsecurity.pix_security.customer.api.dto.CustomerResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component // Significa que essa classe pode ser injetada em outras classes
public class CustomerApiClient {

    private final RestTemplate restTemplate; // Implementando RestTemplate nessa classe

    // Construtor (receber o RestTemplate e guardar dentro da classe)
    public CustomerApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Metodo para fazer requisições HTTP
    public CustomerResponse getCustomerById(String clientId) { // Recebe um clientId e retorna um CustomerResponse (dados de um cliente)
        String url = "http://localhost:8080/api/v1/customers/" + clientId; // Endpoint (igual a requisições via Postman)

        return restTemplate.getForObject(url, CustomerResponse.class); // Simula o modo GET, converte o JSON no tipo de objeto CustomerResponse e retorna
    }
}
