/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.Model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Salvio
 */
@Entity
public class Trabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Desenvolvedor desenvolvedor;
    @ManyToOne
    private Requisito requisito;

    private Date inicio;
    private Date fim;
    private String historico;
    private int versao;

    public Trabalho() {
        Date inicio = new Date();
    }

    public Trabalho(Requisito pReq,Desenvolvedor pDesenvolvedor) {
        setRequisito(pReq);
        setDesenvolvedor(pDesenvolvedor);
        setInicio( new Date());
        historico = "Atualização de codigo \n"
                + " |Requisito|  [" + requisito.getNome() + "] \n"
                + " |Desenvolvedor| [" + desenvolvedor.getNome()+"] \n"
                + " |inicio| " + getInicio() + " \n"
                + " |HISTORICO:|";

    }

    public int getId() {
        return id;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Desenvolvedor getDesenvolvedor() {
        return desenvolvedor;
    }

    public void setDesenvolvedor(Desenvolvedor desenvolvedor) {
        this.desenvolvedor = desenvolvedor;
    }

    public Requisito getRequisito() {
        return requisito;
    }

    public void setRequisito(Requisito requisito) {
        this.requisito = requisito;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }

}
