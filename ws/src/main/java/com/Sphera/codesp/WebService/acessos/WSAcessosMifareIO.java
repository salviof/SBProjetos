/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Sphera.codesp.WebService.acessos;

import com.super_bits.modulosSB.webPaginas.controller.soap.SBWS_IO;

/**
 *
 *
 * @author Salvio
 */
public class WSAcessosMifareIO extends SBWS_IO<ItfWSAcessosMifare> {

    @Override
    public Class getInterface() {
        return ItfWSAcessosMifare.class;
    }

    @Override
    public String getDnsName() {
        return "172.100.150.124";
    }

    @Override
    public Integer getporta() {
        return 8081;
    }

}
