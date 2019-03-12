/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.Model;

import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;

import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.CampoMapValores;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 *
 *
 * @author Salvio
 */
@Entity
public class GerenteProjeto extends UsuarioSB {

    @ManyToOne
    private Cliente cliente;

    public CampoMapValores getCamposEsperados() {
        return camposEsperados;
    }

    public void setCamposEsperados(CampoMapValores camposEsperados) {
        this.camposEsperados = camposEsperados;
    }

}
