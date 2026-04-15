package com.pixsecurity.pix_security.risk.application;

import com.pixsecurity.pix_security.pixlimit.domain.PixLimit;
import com.pixsecurity.pix_security.risk.api.RiskValidationRequest;
import com.pixsecurity.pix_security.risk.domain.RiskLevel;
import com.pixsecurity.pix_security.risk.infrastructure.SecurityApiClient;
import com.pixsecurity.pix_security.trustedbinding.domain.TrustedBinding;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;



// Classe para testar a lógica de 'PixRiskOrchestratorService' sem subir a aplicação, sem usar banco de dados real
// Essa classe existe para garantir que o 'PixRiskOrchestatrorService' se comporta corretamente nos casos de
// 1 - Risco baixo para transação abaixo do limite
// 2 - Risco alto para transação acima do limite e sem validações


// Use Mockito junto com essa classe de teste
@ExtendWith(MockitoExtension.class) // Mockito simula a aplicação para testar, criando objetos falsos sem chamar banco real
class PixRiskOrchestratorServiceTest {

    // Cria um SecurityApiClient (classe que busca os vinculos e o limite do cliente) falso, simulando
    @Mock
    private SecurityApiClient securityApiClient;

    // Cria um PixRiskOrchestratorService real e injeta os Mocks nela (neste caso, o SecurityApiClient falso)
    @InjectMocks
    private PixRiskOrchestratorService pixRiskOrchestratorService;

    @Test // Indica que esse metodo é 1 teste
        // Esse teste deverá retornar risco BAIXO quando a transação for dentro do limite registrado
    void shouldReturnLowWhenTransactionIsWithinLimit() {

        // Simula o corpo de um objeto vindo da requisição via HTTP
        RiskValidationRequest request = new RiskValidationRequest();
        request.setClientId("client-1");
        request.setAmount(30000L);
        request.setType("PIX");

        // Cria um cliente qualquer existente com limite maior que o requisitado
        PixLimit pixLimit = new PixLimit();
        pixLimit.setClientId("client-1");
        pixLimit.setLimitValue(50000L);

        // Garante o retorno desse PixLimit sempre que essa classe chamar getPixLimit
        when(securityApiClient.getPixLimit("client-1")).thenReturn(pixLimit);

        // Chama o metodo real validate do Service
        var response = pixRiskOrchestratorService.validate(request);

        // Se espera que para o teste dar certo, o resultado obrigatóriamente tem que ser assim
        assertEquals(RiskLevel.LOW, response.getRiskLevel()); // Confirma que o nivel de risco retornado foi LOW
        assertEquals("Transação dentro do limite PIX", response.getMessage()); // Confirma que a mensagem retorno veio igual
    }

    @Test // Indica que esse metodo é 1 teste
        // Esse teste deverá retornar risco BLOQUEADO quando a transação for acima do limite registrado e não houver validações
    void shouldReturnBlockedWhenTransactionIsAboveLimitAndHasNoValidations() {

        // Simula o corpo de um objeto vindo da requisição via HTTP
        RiskValidationRequest request = new RiskValidationRequest();
        request.setClientId("client-2");
        request.setAmount(90000L);
        request.setType("PIX");

        // Cria um cliente qualquer existente com limite menor que o requisitado
        PixLimit pixLimit = new PixLimit();
        pixLimit.setClientId("client-2");
        pixLimit.setLimitValue(50000L);

        // Garante o retorno desse PixLimit sempre que essa classe chamar getPixLimit
        when(securityApiClient.getPixLimit("client-2")).thenReturn(pixLimit);

        // Garante o retorno de 0 TrustedBindings sempre que essa classe chamar getTrustedBindings (nenhuma validação)
        when(securityApiClient.getTrustedBindings("client-2")).thenReturn(new TrustedBinding[0]);

        // Chama o metodo real validate do Service
        var response = pixRiskOrchestratorService.validate(request);

        // Se espera que para o teste dar certo, o resultado obrigatóriamente tem que ser assim
        assertEquals(RiskLevel.BLOCKED, response.getRiskLevel()); // Confirma que o nivel de risco retornado foi BLOCKED
        assertEquals("Transação bloqueada por ausência de validações", response.getMessage()); // Confirma que a mensagem retorno veio igual
    }
}
