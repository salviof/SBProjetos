/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.Model;

import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeSimples;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 *
 * @author Salvio
 */
@Entity
public class StatusRequisito extends EntidadeSimples implements Serializable {

    public static StatusRequisito sujestao = new StatusRequisito(1,"Sujestão de requisito");
    public static StatusRequisito futuro = new StatusRequisito(2,"Implementação futura");
    public static StatusRequisito proximaVersao = new StatusRequisito(3,"Proxima Versão");
    public static StatusRequisito finalizado = new StatusRequisito(4,"Finalizado");
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descricao;

    public StatusRequisito() {
        
    }

    public StatusRequisito(int pId, String pDescricao) {
        id = pId;
        descricao = pDescricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
