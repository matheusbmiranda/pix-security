package com.pixsecurity.pix_security.risk.application;

import com.pixsecurity.pix_security.pixlimit.domain.PixLimit;
import com.pixsecurity.pix_security.risk.api.RiskValidationRequest;
import com.pixsecurity.pix_security.risk.api.RiskValidationResponse;
import com.pixsecurity.pix_security.risk.domain.RiskLevel;
import com.pixsecurity.pix_security.risk.infrastructure.SecurityApiClient;
import com.pixsecurity.pix_security.trustedbinding.domain.TrustedBinding;
import org.springframework.stereotype.Service;

@Service // Transforma a classe em um objeto gerenciado diretamente pelo Spring
public class PixRiskOrchestratorService {

    // Atributo para guardar o SecurityApiClient nessa classe
    private final SecurityApiClient securityApiClient; // Traz o SecurityApiCli

    // Construtor (receber o SecurityApiClient e injetar dentro da classe)
    public PixRiskOrchestratorService(SecurityApiClient securityApiClient) {
        this.securityApiClient = securityApiClient;
    }

    // Metodo para validar risco do PIX
    // Recebe um objeto RiskValidationRequest e retorna um objeto RiskValidationResponse
    public RiskValidationResponse validate(RiskValidationRequest request) {

        PixLimit pixLimit = securityApiClient.getPixLimit(request.getClientId()); // Pega o clientId do request, busca e guarda o objeto nessa variavel

        RiskValidationResponse response = new RiskValidationResponse(); // Cria um objeto novo para a resposta

        if (request.getAmount() <= pixLimit.getLimitValue()) { // Se o valor da transação for menor ou igual ao limite PIX do cliente, já para aqui
            response.setRiskLevel(RiskLevel.LOW); // Define o risco dessa transação como baixo
            response.setMessage("Transação dentro do limite PIX"); // Mensagem explicativa do risco
            return response;
        }

        TrustedBinding[] bindings = securityApiClient.getTrustedBindings(request.getClientId()); // Pega o clientId do request, busca e guarda o objeto nessa variavel

        // Declaração de variaveis
        boolean hasDevice = false;
        boolean hasWifi = false;
        boolean hasPixContact = false;

        for (TrustedBinding binding : bindings) { // Para cada binding dentro do array bindings
            switch (binding.getType()) {
                case DEVICE: // Se tiver DEVICE marca como true
                    hasDevice = true;
                    break;
                case WIFI: // Se tiver WIFI marca como true
                    hasWifi = true;
                    break;
                case PIX_CONTACT: // Se tiver PIX_CONTACT marca como true
                    hasPixContact = true;
                    break;
            }
        }

        // Contagem de vinculos para definir risco de PIX
        int validationsOk = 0;

        if (hasDevice) {
            validationsOk++;
        }

        if (hasWifi) {
            validationsOk++;
        }

        if (hasPixContact) {
            validationsOk++;
        }

        if (validationsOk >= 2) { // Se houver 2 ou mais vinculos
            response.setRiskLevel(RiskLevel.MEDIUM); // Define o risco dessa transação como médio
            response.setMessage("Transação acima do limite, mas com validações suficientes"); // Mensagem explicativa do risco
            return response;
        }

        if (validationsOk == 1) { // Se houver 1 vinculo
            response.setRiskLevel(RiskLevel.HIGH); // Define o risco dessa transação como alto
            response.setMessage("Transação acima do limite com validação parcial"); // Mensagem explicativa do risco
            return response;
        }

        response.setRiskLevel(RiskLevel.BLOCKED); // Se não houver vinculo, defico o risco como bloqueado
        response.setMessage("Transação bloqueada por ausência de validações"); // Mensagem explicativa do risco
        return response;
    }
}



