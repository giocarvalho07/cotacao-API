package com.cotacao_moedas.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity // Indica que esta classe é uma entidade JPA e será mapeada para uma tabela no banco de dados
@Table(name = "transacoes") // Define o nome da tabela no banco de dados
public class Transacao {

    @Id // Marca o campo como a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a geração automática de ID pelo banco de dados
    private Long id;

    @Column(name = "nome_usuario", nullable = false) // Mapeia para uma coluna no DB e define como não nula
    private String nomeUsuario;

    @Column(name = "tipo_transacao", nullable = false)
    private String tipoTransacao; // Ex: "BRL_TO_USD" ou "USD_TO_BRL"

    @Column(name = "data_transacao", nullable = false)
    private LocalDateTime dataTransacao; // Para registrar a data e hora da transação

    // Construtor padrão (necessário para JPA/Hibernate)
    public Transacao() {
    }

    // Construtor com parâmetros para facilitar a criação de objetos
    public Transacao(String nomeUsuario, String tipoTransacao) {
        this.nomeUsuario = nomeUsuario;
        this.tipoTransacao = tipoTransacao;
        this.dataTransacao = LocalDateTime.now(); // Define a data e hora atual ao criar a transação
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public LocalDateTime getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", tipoTransacao='" + tipoTransacao + '\'' +
                ", dataTransacao=" + dataTransacao +
                '}';
    }
}