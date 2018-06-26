package com.arthurvalle.tomjet.model;

public class Usuario {
    private int id;
    private String email, login, nome, senha;

    public Usuario(int id, String email, String login, String nome, String senha) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.nome = nome;
        this.senha = senha;
    }

    public Usuario(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
