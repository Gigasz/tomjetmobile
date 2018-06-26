package com.arthurvalle.tomjet.model;

public class Aviao {
    private int capacidade, id;
    private String modelo, prefixo;

    public Aviao(int capacidade, int id, String modelo, String prefixo) {
        this.capacidade = capacidade;
        this.id = id;
        this.modelo = modelo;
        this.prefixo = prefixo;
    }

    public Aviao() {
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }
}
