package com.cotacao_moedas.model;

public class CotacaoMoeda {

    private String code;
    private String codein;
    private String name;
    private String high;
    private String low;
    private String varBid;
    private String pctChange;
    private String bid; // Valor de compra
    private String ask; // Valor de venda
    private String timestamp;
    private String create_date;

    // Construtor padrão (necessário para o Jackson, que é usado pelo WebClient)
    public CotacaoMoeda() {}

    // Getters e Setters (pode gerá-los automaticamente na sua IDE)
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getCodein() { return codein; }
    public void setCodein(String codein) { this.codein = codein; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getHigh() { return high; }
    public void setHigh(String high) { this.high = high; }
    public String getLow() { return low; }
    public void setLow(String low) { this.low = low; }
    public String getVarBid() { return varBid; }
    public void setVarBid(String varBid) { this.varBid = varBid; }
    public String getPctChange() { return pctChange; }
    public void setPctChange(String pctChange) { this.pctChange = pctChange; }
    public String getBid() { return bid; }
    public void setBid(String bid) { this.bid = bid; }
    public String getAsk() { return ask; }
    public void setAsk(String ask) { this.ask = ask; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getCreate_date() { return create_date; }
    public void setCreate_date(String create_date) { this.create_date = create_date; }

    @Override
    public String toString() {
        return "CotacaoMoeda{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", bid='" + bid + '\'' +
                ", ask='" + ask + '\'' +
                ", create_date='" + create_date + '\'' +
                '}';
    }
}
