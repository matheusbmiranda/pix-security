package com.pixsecurity.pix_security.pixlimit.api;


import com.pixsecurity.pix_security.pixlimit.application.PixLimitService;
import com.pixsecurity.pix_security.pixlimit.domain.PixLimit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// Classe controller = interface com o mundo (HTTP), apenas receber e devolver os dados

@RestController // Essa classe vai receber requisições HTTP
@RequestMapping("/api/v1/customers/{clientId}/pix-limit") // Rota base HTTP
@Tag(name = "Pix Limit", description = "Gerenciamento de limite PIX do cliente") // Organiza e descreve um grupo de endpoints no Swagger
public class PixLimitController {

    private final PixLimitService pixLimitService; // Variavel para guardar o service

    public PixLimitController(PixLimitService pixLimitService) { // Traz e guarda o service
        this.pixLimitService = pixLimitService;
    }

    // Metodo para atualizar dados de um ID valido atraves do JSON (PUT)
    @PutMapping
    @Operation(summary = "Definir limite PIX", description = "Cria ou atualiza o limite PIX do cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Limite atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor inválido para limite")
    })
    // PathVariable recebe o ID através da URL, RequestBody recebe o corpo da requisição JSON
    // Vai devolver um objeto do tipo PixLimit
    // ResponseEntity controla o status que vai retornar no HTTP
    public ResponseEntity<PixLimit> setLimit(@PathVariable String clientId, @RequestBody PixLimitRequest request) {
        PixLimit response = pixLimitService.setLimit(clientId, request.getLimitValue()); // Chama o metodo setLimit do Service e passa o valor vindo da requisição
        return ResponseEntity.ok(response); // Status que será retornado no HTTP (200 OK, neste caso)
    }

    // Metodo GET do HTTP
    @GetMapping
    @Operation(summary = "Buscar limite PIX",description = "Retorna o limite PIX configurado para o cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Limite encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Limite não encontrado")
    })
    // PathVariable recebe o ID através da URL
    // Vai devolver um objeto do tipo PixLimit
    // ResponseEntity controla o status que vai retornar no HTTP
    public ResponseEntity<PixLimit> getLimit(@PathVariable String clientId) {
        PixLimit response = pixLimitService.getByClientId(clientId); // Chama o metodo getLimit do Service que retorna o limite registrado deste cliente
        return ResponseEntity.ok(response); // Status que será retornado no HTTP (200 OK, neste caso)
    }
}
