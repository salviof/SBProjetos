/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.configSBFW.acessos;

import com.super_bits.modulos.SBAcessosModel.ConfigPermissoesAcessoModelAbstrato;
import com.super_bits.modulos.SBAcessosModel.model.GrupoUsuarioSB;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ItfGrupoUsuario;

import com.super_bits.sbProjetos.Model.Cliente;
import com.super_bits.sbProjetos.Model.Desenvolvedor;
import com.super_bits.sbProjetos.Model.GerenteProjeto;
import com.super_bits.sbProjetos.Model.Projeto;
import com.super_bits.sbProjetos.Model.StatusRequisito;
import com.super_bits.sbProjetos.controller.getaoProjeto.ModuloGestaoProjeto;
import com.super_bits.modulosSB.SBCore.modulos.view.menu.MenusDaSessao;

/**
 *
 *
 *
 *
 * @author Salvio
 */
public class ConfigAcessos extends ConfigPermissoesAcessoModelAbstrato {

    private static Class[] getClasses() {
        Class[] classes = {ModuloGestaoProjeto.class};
        return classes;
    }

    public ConfigAcessos() {
        super(getClasses());
    }

    public void configuraAcessosOld() {

        boolean criatudo = false;

        if (criatudo) {

            UtilSBPersistencia.mergeRegistro(StatusRequisito.futuro);
            UtilSBPersistencia.mergeRegistro(StatusRequisito.proximaVersao);
            UtilSBPersistencia.mergeRegistro(StatusRequisito.sujestao);
            UtilSBPersistencia.mergeRegistro(StatusRequisito.finalizado);

            Cliente superBits = new Cliente();
            superBits.setNome("Super Bits");
            superBits.setId(1);

            Cliente sphera = new Cliente();

            sphera.setNome("Sphera Security");
            sphera.setId(2);
            System.out.println("CLIENTE CADASTRADO");
            UtilSBPersistencia.mergeRegistro(superBits);
            UtilSBPersistencia.mergeRegistro(sphera);

            Cliente teste = new Cliente();
            teste.loadByID(2);

            GerenteProjeto sergioOku = new GerenteProjeto();

            GerenteProjeto julio = new GerenteProjeto();
            GerenteProjeto joaquim = new GerenteProjeto();
            Desenvolvedor alline = new Desenvolvedor();
            Desenvolvedor eduardo = new Desenvolvedor();
            Desenvolvedor marcos = new Desenvolvedor();
            Desenvolvedor salvio = new Desenvolvedor();

            GrupoUsuarioSB gerenteDeProjeto = new GrupoUsuarioSB();
            gerenteDeProjeto.setId(1);
            gerenteDeProjeto.setNome("Gerente de Pronjeto");
            gerenteDeProjeto.setDescricao("Usuarios com função de gerenciar projeto");
            gerenteDeProjeto.adcionaUsuario(sergioOku);

            UtilSBPersistencia.mergeRegistro(gerenteDeProjeto);

            GrupoUsuarioSB desenvolvedor = new GrupoUsuarioSB();
            desenvolvedor.setNome("Desenvolvedor");

            UtilSBPersistencia.mergeRegistro(desenvolvedor);

            sergioOku.setNome("Sergio Oku");
            sergioOku.setEmail("sergio.oku@spherasecurity.com");
            sergioOku.setId(1);
            UtilSBPersistencia.mergeRegistro(sergioOku);

            julio.setNome("José Júlio");
            julio.setEmail("julio@spherasecurity.com");
            julio.setId(2);
            UtilSBPersistencia.mergeRegistro(julio);

            joaquim.setNome("Joaquim Marques");
            joaquim.setEmail("jmarques@spherasecurity.com");
            joaquim.setId(3);
            UtilSBPersistencia.mergeRegistro(joaquim);

            alline.setNome("Alline Basile");
            alline.setEmail("alline@spherasecurity.com");
            alline.setId(4);
            UtilSBPersistencia.mergeRegistro(alline);

            marcos.setNome("Marcos Vinicius");
            marcos.setEmail("mvrcorreia@outlook.com");
            marcos.setId(5);
            UtilSBPersistencia.mergeRegistro(marcos);

            eduardo.setNome("Eduardo Lima");
            eduardo.setEmail("eduarddollima@yahoo.com.br");
            eduardo.setId(6);
            UtilSBPersistencia.mergeRegistro(eduardo);

            salvio.setNome("Salvio Furbino");
            salvio.setEmail("salviof@gmail.com");
            salvio.setId(7);
            Projeto proj = new Projeto();
            proj.loadByID(1);

            salvio.getProjetos().add(proj);
            UtilSBPersistencia.mergeRegistro(salvio);

            int i = 1;

        }

    }

    @Override
    public MenusDaSessao definirMenu(ItfGrupoUsuario pGrupo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
