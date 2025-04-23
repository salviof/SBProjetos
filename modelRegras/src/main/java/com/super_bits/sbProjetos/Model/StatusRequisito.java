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

    public static StatusRequisito sujestao = new StatusRequisito(1l, "Sujestão de requisito");
    public static StatusRequisito futuro = new StatusRequisito(2l, "Implementação futura");
    public static StatusRequisito proximaVersao = new StatusRequisito(3l, "Proxima Versão");
    public static StatusRequisito finalizado = new StatusRequisito(4l, "Finalizado");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;

    public StatusRequisito() {

    }

    public StatusRequisito(Long pId, String pDescricao) {
        id = pId;
        descricao = pDescricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
