package com.arthurvalle.tomjet.model;

public class Aeroporto {
    private int id;
    private String cidade, aeroporto;

    public Aeroporto(int id, String cidade, String aeroporto) {
        this.id = id;
        this.cidade = cidade;
        this.aeroporto = aeroporto;
    }

    public Aeroporto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getAeroporto() {
        return aeroporto;
    }

    public void setAeroporto(String aeroporto) {
        this.aeroporto = aeroporto;
    }
}
