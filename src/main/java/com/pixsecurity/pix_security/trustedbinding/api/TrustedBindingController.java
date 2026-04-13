package com.pixsecurity.pix_security.trustedbinding.api;

import com.pixsecurity.pix_security.trustedbinding.application.TrustedBindingService;
import com.pixsecurity.pix_security.trustedbinding.domain.TrustedBinding;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// Classe controller = interface com o mundo (HTTP), apenas receber e devolver os dados

@RestController // Essa classe vai receber requisições HTTP
@RequestMapping("/api/v1/customers/{clientId}/trusted-bindings") // Rota base HTTP
@Tag(name = "Trusted Bindings", description = "Gerenciamento de vínculos confiáveis do cliente") // Organiza e descreve um grupo de endpoints
public class TrustedBindingController {

    private final TrustedBindingService trustedBindingService; // Variavel para guardar o service

    public TrustedBindingController(TrustedBindingService trustedBindingService) { // Traz e guarda o service
        this.trustedBindingService = trustedBindingService;
    }

    @PostMapping // Metodo POST do HTTP
    @Operation(summary = "Criar vínculo confiável", description = "Cria um vínculo confiável para um cliente") // Documentação, Swagger neste caso
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou vínculo duplicado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    // PathVariable recebe o ID através da URL, RequestBody recebe o corpo da requisição
    // Vai devolver um objeto do tipo TrustedBinding
    public TrustedBinding bind(@PathVariable String clientId, @RequestBody TrustedBindingRequest request) {
        return trustedBindingService.bind(clientId, request); // Chama e executa o metodo Service e depois retorna o resultado

    }

    @DeleteMapping("/{bindingId}") // Metodo DELET do HTTP
    @Operation(summary = "Remover vínculo confiável", description = "Remove um vínculo confiável de um cliente") // Documentação, Swagger neste caso
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculo removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado para este cliente")
    })
    public void unbind(@PathVariable String clientId, // Pega o clientId pela URL
                       @PathVariable String bindingId) { // Pega o bindingId pela URL
        trustedBindingService.unbind(clientId, bindingId); // Usa o metodo criado no Service e deleta
    }

    @GetMapping // Metodo GET do HTTP
    @Operation(summary = "Listar vínculos do cliente", description = "Retorna os vínculos confiáveis de um cliente") // Documentação, Swagger neste caso
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculos retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public List<TrustedBinding> findByClientId(@PathVariable String clientId) { // Chama esse metodo do Service que retorna a lista TrustedBingind
        return trustedBindingService.findByClientId(clientId);
    }
}
