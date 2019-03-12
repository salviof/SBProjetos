/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.config.webPaginas;

import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.webPaginas.ConfigGeral.ItfInicioFimAppWP;
import com.super_bits.modulosSB.webPaginas.ConfigGeral.SBWebPaginas;
import com.super_bits.sbProjetos.Model.ConfigPersistenciaSBProject;

/**
 *
 * @author Salvio
 */
public class InicioFimContextoWebPagina implements ItfInicioFimAppWP {

    @Override
    public void inicio() {
        SBCore.configurar(Configu);
        SBPersistencia.configuraJPA(new ConfigPersistenciaSBProject());
        SBWebPaginas.configurar(new ConfigWebPaginasSBProject());
    }

    @Override
    public void fim() {
        System.out.println("Fim do contexto");
    }

}
