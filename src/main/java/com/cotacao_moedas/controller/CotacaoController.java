package com.cotacao_moedas.controller;

import com.cotacao_moedas.model.Transacao;
import com.cotacao_moedas.service.CotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // Indica que esta classe é um controlador REST e seus métodos retornarão dados JSON/XML
@RequestMapping("/api/v1/cotacao") // Define o prefixo de URL para todos os endpoints neste controlador
public class CotacaoController {

    private final CotacaoService cotacaoService; // Injeta o serviço que criamos

    // Construtor para injeção de dependências
    @Autowired
    public CotacaoController(CotacaoService cotacaoService) {
        this.cotacaoService = cotacaoService;
    }

    /**
     * Endpoint para obter a cotação atual do Dólar Americano para Real Brasileiro.
     * GET /api/v1/cotacao/dolar-brl
     */
    @GetMapping("/dolar-brl")
    public Mono<ResponseEntity<Map<String, String>>> getCotacaoDolarBRL() {
        return cotacaoService.getCotacaoDolarBRL()
                .map(cotacao -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("moeda", "USD-BRL");
                    response.put("valor_compra", cotacao.setScale(4, BigDecimal.ROUND_HALF_UP).toPlainString()); // Formata para 4 casas decimais
                    return ResponseEntity.ok(response); // Retorna 200 OK com a cotação
                })
                .defaultIfEmpty(ResponseEntity.notFound().build()) // Retorna 404 Not Found se o Mono estiver vazio
                .onErrorResume(e -> {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("erro", e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)); // Retorna 500 Internal Server Error
                });
    }

    /**
     * Endpoint para converter um valor de Real (BRL) para Dólar (USD).
     * Requer o nome do usuário e o valor em BRL.
     * POST /api/v1/cotacao/converter-brl-para-usd
     * Corpo da requisição (JSON):
     * {
     * "valor": 10.00,
     * "nomeUsuario": "João Silva"
     * }
     */
    @PostMapping("/converter-brl-para-usd")
    public Mono<ResponseEntity<Map<String, String>>> converterBRLparaUSD(
            @RequestBody Map<String, Object> requestBody) {

        try {
            BigDecimal valorBRL = new BigDecimal(requestBody.get("valor").toString());
            String nomeUsuario = requestBody.get("nomeUsuario").toString();

            return cotacaoService.converterBRLtoUSD(valorBRL, nomeUsuario)
                    .map(valorUSD -> {
                        Map<String, String> response = new HashMap<>();
                        response.put("origem", "BRL");
                        response.put("destino", "USD");
                        response.put("valor_original_BRL", valorBRL.toPlainString());
                        response.put("valor_convertido_USD", valorUSD.setScale(4, BigDecimal.ROUND_HALF_UP).toPlainString()); // Formata
                        response.put("usuario", nomeUsuario);
                        return ResponseEntity.ok(response); // Retorna 200 OK com o resultado
                    })
                    .onErrorResume(e -> {
                        Map<String, String> errorResponse = new HashMap<>();
                        errorResponse.put("erro", "Erro ao converter BRL para USD: " + e.getMessage());
                        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
                    });
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("erro", "Parâmetros inválidos: " + e.getMessage());
            return Mono.just(ResponseEntity.badRequest().body(errorResponse)); // Retorna 400 Bad Request
        }
    }

    /**
     * Endpoint para converter um valor de Dólar (USD) para Real (BRL).
     * Requer o nome do usuário e o valor em USD.
     * POST /api/v1/cotacao/converter-usd-para-brl
     * Corpo da requisição (JSON):
     * {
     * "valor": 2.50,
     * "nomeUsuario": "Maria Souza"
     * }
     */
    @PostMapping("/converter-usd-para-brl")
    public Mono<ResponseEntity<Map<String, String>>> converterUSDparaBRL(
            @RequestBody Map<String, Object> requestBody) {

        try {
            BigDecimal valorUSD = new BigDecimal(requestBody.get("valor").toString());
            String nomeUsuario = requestBody.get("nomeUsuario").toString();

            return cotacaoService.converterUSDtoBRL(valorUSD, nomeUsuario)
                    .map(valorBRL -> {
                        Map<String, String> response = new HashMap<>();
                        response.put("origem", "USD");
                        response.put("destino", "BRL");
                        response.put("valor_original_USD", valorUSD.toPlainString());
                        response.put("valor_convertido_BRL", valorBRL.setScale(4, BigDecimal.ROUND_HALF_UP).toPlainString()); // Formata
                        response.put("usuario", nomeUsuario);
                        return ResponseEntity.ok(response); // Retorna 200 OK com o resultado
                    })
                    .onErrorResume(e -> {
                        Map<String, String> errorResponse = new HashMap<>();
                        errorResponse.put("erro", "Erro ao converter USD para BRL: " + e.getMessage());
                        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
                    });
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("erro", "Parâmetros inválidos: " + e.getMessage());
            return Mono.just(ResponseEntity.badRequest().body(errorResponse));
        }
    }


    @GetMapping("/transacoes")
    public Mono<ResponseEntity<List<Transacao>>> listarTransacoes() {
        // Chama o serviço para buscar todas as transações.
        // O findAll() do JpaRepository retorna um List, mas como estamos no contexto reativo do WebFlux
        // e o Controller retorna Mono/Flux, precisamos converter o List síncrono para um Mono<List>.
        return cotacaoService.listarTodasTransacoes()
                .map(ResponseEntity::ok) // Mapeia a lista de transações para um ResponseEntity 200 OK
                .defaultIfEmpty(ResponseEntity.notFound().build()) // Se não houver transações, retorna 404 Not Found
                .onErrorResume(e -> {
                    System.err.println("Erro ao listar transações: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }
}