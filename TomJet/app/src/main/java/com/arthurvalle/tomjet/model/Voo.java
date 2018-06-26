package com.arthurvalle.tomjet.model;

import java.util.Date;

public class Voo {
    private int id;
    private Aviao aviao;
    private Aeroporto origem, destino;
    private String dataVoo;
    private double valorPassagem;

    public Voo(int id, Aviao aviao, Aeroporto origem, Aeroporto destino, String dataVoo, double valorPassagem) {
        this.id = id;
        this.aviao = aviao;
        this.origem = origem;
        this.destino = destino;
        this.dataVoo = dataVoo;
        this.valorPassagem = valorPassagem;
    }

    public Voo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Aviao getAviao() {
        return aviao;
    }

    public void setAviao(Aviao aviao) {
        this.aviao = aviao;
    }

    public Aeroporto getOrigem() {
        return origem;
    }

    public void setOrigem(Aeroporto origem) {
        this.origem = origem;
    }

    public Aeroporto getDestino() {
        return destino;
    }

    public void setDestino(Aeroporto destino) {
        this.destino = destino;
    }

    public String getDataVoo() {
        return dataVoo;
    }

    public void setDataVoo(String dataVoo) {
        this.dataVoo = dataVoo;
    }

    public double getValorPassagem() {
        return valorPassagem;
    }

    public void setValorPassagem(double valorPassagem) {
        this.valorPassagem = valorPassagem;
    }
}
