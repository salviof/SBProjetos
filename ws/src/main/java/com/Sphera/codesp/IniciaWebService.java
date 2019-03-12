/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Sphera.codesp;

import com.Sphera.codesp.WebService.acessos.WSAcessosMifare;
import com.Sphera.codesp.WebService.acessos.WSAcessosMifareIO;
import com.super_bits.modulosSB.SBCore.ConfigGeral.ConfigCorePadrao;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 *
 * @author Salvio
 */
public class IniciaWebService {

    public static void main(String[] args) {
        SBCore.configurar(new ConfigCorePadrao());
        //     SBPersistencia.configuraJPA(new ConfigPersistenciaSegurancaSphera());
        WSAcessosMifareIO wsAcesso = new WSAcessosMifareIO();
        wsAcesso.ativaServidor(WSAcessosMifare.class);

        boolean encerrarAplicacao = false;
        while (encerrarAplicacao == false) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(IniciaWebService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
