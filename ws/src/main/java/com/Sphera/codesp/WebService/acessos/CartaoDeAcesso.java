/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Sphera.codesp.WebService.acessos;

/**
 *
 * @author Salvio
 */
public class CartaoDeAcesso {

    private String codigoMifar;
    private String nome;
    private String urlFoto;
    private String observacao;
    private boolean acessoPermitido;

    public String getCodigoMifar() {
        return codigoMifar.toUpperCase();
    }

    public void setCodigoMifar(String codigoMifar) {
        this.codigoMifar = codigoMifar.toUpperCase();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public boolean isAcessoPermitido() {
        return acessoPermitido;
    }

    public void setAcessoPermitido(boolean acessoPermitido) {
        this.acessoPermitido = acessoPermitido;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
