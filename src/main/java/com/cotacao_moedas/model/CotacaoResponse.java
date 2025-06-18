package com.cotacao_moedas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CotacaoResponse {
    // @JsonProperty mapeia a chave "USDBRL" do JSON para o campo 'usdbrl' em Java
    @JsonProperty("USDBRL")
    private CotacaoMoeda usdbrl;

    // Construtor padrão
    public CotacaoResponse() {}

    // Getter e Setter
    public CotacaoMoeda getUsdbrl() {
        return usdbrl;
    }

    public void setUsdbrl(CotacaoMoeda usdbrl) {
        this.usdbrl = usdbrl;
    }

    @Override
    public String toString() {
        return "CotacaoResponse{" +
                "usdbrl=" + usdbrl +
                '}';
    }
}