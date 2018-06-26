package com.arthurvalle.tomjet.model;

public class Assento {
    private int assento;
    private int ocupado;
    private String origem, destino, dataVoo, aviao;
    private double valorPassagem;

    public Assento(int assento, int ocupado, String origem, String destino, String dataVoo, String aviao, double valorPassagem) {
        this.assento = assento;
        this.ocupado = ocupado;
        this.origem = origem;
        this.destino = destino;
        this.dataVoo = dataVoo;
        this.aviao = aviao;
        this.valorPassagem = valorPassagem;
    }

    public Assento() {
    }

    public int getAssento() {
        return assento;
    }

    public void setAssento(int assento) {
        this.assento = assento;
    }

  //  public boolean isOcupado() {
  //      return ocupado;
  //  }

   // public void setOcupado(boolean ocupado) {
   //     this.ocupado = ocupado;

    public int getOcupado() {
        return ocupado;
    }

    public void setOcupado(int ocupado) {
        this.ocupado = ocupado;
    }
    // }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDataVoo() {
        return dataVoo;
    }

    public void setDataVoo(String dataVoo) {
        this.dataVoo = dataVoo;
    }

    public String getAviao() {
        return aviao;
    }

    public void setAviao(String aviao) {
        this.aviao = aviao;
    }

    public double getValorPassagem() {
        return valorPassagem;
    }

    public void setValorPassagem(double valorPassagem) {
        this.valorPassagem = valorPassagem;
    }
}
