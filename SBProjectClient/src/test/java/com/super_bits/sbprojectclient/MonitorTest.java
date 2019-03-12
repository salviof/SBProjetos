/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbprojectclient;

import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.ConfigCoreDeveloper;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.sbProjetos.Model.Cliente;
import com.super_bits.sbProjetos.Model.ConfigPersistenciaSBProject;
import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author Salvio
 */
public class MonitorTest extends TestCase {

    public MonitorTest(String testName) {
        super(testName);
    }

    @Test
    public void testeLoadClasseGenerica() {
        SBCore.configurar(new ConfigCoreDeveloper());
        SBPersistencia.configuraJPA(new ConfigPersistenciaSBProject());
        Cliente teste = new Cliente();
        teste.loadByID(1);
        System.out.println(teste.getNomeCurto());
        teste.loadByID(2);
        System.out.println(teste.getNomeCurto());
    }

}
