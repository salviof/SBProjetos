/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbprojectclient;

import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.ConfigCorePadrao;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.sbProjetos.Model.ConfigPersistenciaSBProject;
import javax.persistence.EntityManager;

/**
 *
 *
 *
 * @author Salvio
 */
public class SBProjectClient extends Thread {

    public SBProjectClient() {

    }

    private static EntityManager emAplicacao;

    public static EntityManager getEMServidor() {
        if (emAplicacao == null) {
            emAplicacao = UtilSBPersistencia.getNovoEM();
        }
        return emAplicacao;
    }

    public static void main(String[] args) {

        SBPersistencia.configuraJPA(new ConfigPersistenciaSBProject());
        SBCore.configurar(new ConfigCorePadrao());
        Monitor monitor = new Monitor();
        monitor.start();
        monitor.aguardarInicializacao();
        monitor.mostraTray();

        //   monitor.abreJanela();
        monitor.aguardarFechar();
        System.out.println("Fechando");
        monitor = null;
        System.exit(0);

    }

}
