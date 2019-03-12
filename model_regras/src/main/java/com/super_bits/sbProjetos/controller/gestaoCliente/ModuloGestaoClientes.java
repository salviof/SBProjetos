/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.controller.gestaoCliente;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.Persistencia.dao.ControllerAbstratoSBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.sbProjetos.Model.Cliente;

/**
 *
 * @author salvioF
 */
public class ModuloGestaoClientes extends ControllerAbstratoSBPersistencia {

    public static ItfResposta criarCliente(Cliente pCliente) {
        ItfResposta resp = getNovaRespostaAutorizaChecaNulo(pCliente);
        if (!resp.isSucesso()) {
            return resp.dispararMensagens();
        }

        if (UtilSBPersistencia.persistirRegistro(pCliente)) {
            resp.addMensagemAvisoDisparaERetorna("Cliente cadastrado com sucesso");

        } else {
            return resp.addMensagemErroDisparaERetorna("Desculpe, aconteceu um problema ao tentar cadastrar o Cliente, você preencheu todos os campos obrigatórios?");

        }
        return resp;
    }
}
