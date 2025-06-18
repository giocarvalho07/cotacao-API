package com.cotacao_moedas.service;

import com.cotacao_moedas.model.CotacaoResponse;
import com.cotacao_moedas.model.Transacao;
import com.cotacao_moedas.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service // Indica que esta classe é um componente de serviço e contém a lógica de negócio
public class CotacaoService {

    private final WebClient webClient; // Injeta o WebClient que configuramos
    private final TransacaoRepository transacaoRepository; // Injeta o repositório para salvar transações

    // Construtor para injeção de dependências
    @Autowired
    public CotacaoService(WebClient webClient, TransacaoRepository transacaoRepository) {
        this.webClient = webClient;
        this.transacaoRepository = transacaoRepository;
    }

    /**
     * Busca a cotação atual do Dólar Americano para Real Brasileiro (USD-BRL)
     * da Awesome API.
     * @return Mono<BigDecimal> com o valor de compra (bid) do dólar.
     */
    public Mono<BigDecimal> getCotacaoDolarBRL() {
        // Faz a requisição HTTP GET usando o WebClient
        return webClient.get()
                .uri("json/last/USD-BRL") // O uri é relativo à baseUrl definida no WebClientConfig
                .retrieve() // Inicia a recuperação da resposta
                .bodyToMono(CotacaoResponse.class) // Converte o corpo da resposta para um Mono de CotacaoResponse
                .map(cotacaoResponse -> {
                    // Pega o valor 'bid' (compra) da cotação USD-BRL e converte para BigDecimal
                    String bidString = cotacaoResponse.getUsdbrl().getBid();
                    return new BigDecimal(bidString);
                })
                .log("getCotacaoDolarBRL") // Opcional: para logar o fluxo reativo
                .onErrorResume(e -> {
                    System.err.println("Erro ao obter cotação USD-BRL: " + e.getMessage());
                    return Mono.error(new RuntimeException("Não foi possível obter a cotação do dólar no momento."));
                });
    }

    /**
     * Converte um valor de BRL para USD.
     * Registra a transação no banco de dados.
     * @param valorBRL O valor em Real a ser convertido.
     * @param nomeUsuario O nome do usuário que fez a transação.
     * @return Mono<BigDecimal> com o valor convertido em Dólar.
     */
    public Mono<BigDecimal> converterBRLtoUSD(BigDecimal valorBRL, String nomeUsuario) {
        // Primeiro, obtém a cotação atual do dólar
        return getCotacaoDolarBRL()
                .flatMap(cotacaoDolar -> {
                    // Calcula o valor em USD: valorBRL / cotacaoDolar (ask - valor de venda para quem compra dólar)
                    // Usamos getAsk() para o cenário de quem está comprando dólar (vendendo Real)
                    BigDecimal valorUSD = valorBRL.divide(cotacaoDolar, 4, RoundingMode.HALF_UP);

                    // Salva a transação no banco de dados
                    Transacao transacao = new Transacao(nomeUsuario, "BRL_TO_USD");
                    // O save do JpaRepository retorna o objeto salvo. flatMap é usado aqui para garantir
                    // que a transação seja salva antes de retornar o resultado da conversão.
                    return Mono.fromCallable(() -> transacaoRepository.save(transacao))
                            .thenReturn(valorUSD); // Retorna o valorUSD após salvar
                })
                .log("converterBRLtoUSD")
                .onErrorResume(e -> {
                    System.err.println("Erro ao converter BRL para USD: " + e.getMessage());
                    return Mono.error(new RuntimeException("Erro ao converter BRL para USD: " + e.getMessage()));
                });
    }

    /**
     * Converte um valor de USD para BRL.
     * Registra a transação no banco de dados.
     * @param valorUSD O valor em Dólar a ser convertido.
     * @param nomeUsuario O nome do usuário que fez a transação.
     * @return Mono<BigDecimal> com o valor convertido em Real.
     */
    public Mono<BigDecimal> converterUSDtoBRL(BigDecimal valorUSD, String nomeUsuario) {
        // Primeiro, obtém a cotação atual do dólar
        return getCotacaoDolarBRL()
                .flatMap(cotacaoDolar -> {
                    // Calcula o valor em BRL: valorUSD * cotacaoDolar (bid - valor de compra para quem vende dólar)
                    // Usamos getBid() para o cenário de quem está vendendo dólar (comprando Real)
                    BigDecimal valorBRL = valorUSD.multiply(cotacaoDolar);

                    // Salva a transação no banco de dados
                    Transacao transacao = new Transacao(nomeUsuario, "USD_TO_BRL");
                    return Mono.fromCallable(() -> transacaoRepository.save(transacao))
                            .thenReturn(valorBRL); // Retorna o valorBRL após salvar
                })
                .log("converterUSDtoBRL")
                .onErrorResume(e -> {
                    System.err.println("Erro ao converter USD para BRL: " + e.getMessage());
                    return Mono.error(new RuntimeException("Erro ao converter USD para BRL: " + e.getMessage()));
                });
    }

    /**
     * Lista todas as transações de conversão salvas no banco de dados.
     * @return Mono<List<Transacao>> com a lista de transações.
     */
    public Mono<List<Transacao>> listarTodasTransacoes() {
        // Como JpaRepository.findAll() é bloqueante, usamos Mono.fromCallable para
        // envolvê-lo em um fluxo reativo e evitar bloqueios na thread principal do WebFlux.
        return Mono.fromCallable(() -> transacaoRepository.findAll())
                .log("listarTodasTransacoes") // Opcional: para logar o fluxo
                .onErrorResume(e -> {
                    System.err.println("Erro no serviço ao listar transações: " + e.getMessage());
                    return Mono.error(new RuntimeException("Não foi possível listar as transações no momento."));
                });
    }
}