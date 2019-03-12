/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Sphera.codesp.WebService.acessos;

import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.ConfigCorePadrao;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import java.util.List;
import java.util.Random;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Salvio
 */
public class WSAcessosMifareTest extends WSAcessosMifare {

    WSAcessosMifare acessosWSteste;

    @Test
    public void testeAcessoLiberado() {
        SBCore.configurar(new ConfigCorePadrao());
//        SBPersistencia.configuraJPA(new ConfigPersistenciaSegurancaSphera());
        WSAcessosMifare ws = new WSAcessosMifare();
        //    assertTrue("Cartão de aceso Alline retornou false", ws.acessoPermitido("B6D1D67A", 12));
        //    assertTrue("Cartão de aceso Aleatorio Retornou True", !ws.acessoPermitido("D9F5EA6B", 12));
    }

    /**
     * Test of getListaAcessos method, of class AcessosService.
     */
    public void testGetListaAcessos() {
        SBCore.configurar(new ConfigCorePadrao());
        //    SBPersistencia.configuraJPA(new ConfigPersistenciaSegurancaSphera());
        WSAcessosMifare ws = new WSAcessosMifare();
        List<CartaoDeAcesso> lista = ws.getAcessosPermitidosPorGate(12);

        assertTrue("Lista Retornou menos de 100 resultado isto está certo?", (lista.size() > 100));
    }

    @Test
    public void testeListaBanco() {
        SBCore.configurar(new ConfigCorePadrao());
        //      SBPersistencia.configuraJPA(new ConfigPersistenciaSegurancaSphera());
        List<CartaoDeAcesso> lista = getCartoesPorBanco(12);
        for (CartaoDeAcesso cartao : lista) {
            System.out.println(cartao.getNome());
        }
        //  boolean resposta = acessoPermitido("B6D1D67A", 12);
        //   System.out.println("Retornou " + resposta + " cartoes");
    }

    /**
     * Test of acessoPermitido method, of class AcessosService.
     */
    public void testAcessoPermitido() {

        //teste.ativaServidor(AcessosMifareService.class);
        SBCore.configurar(new ConfigCorePadrao());
        //     SBPersistencia.configuraJPA(new ConfigPersistenciaSegurancaSphera());
        WSAcessosMifareIO teste = new WSAcessosMifareIO();
        ItfWSAcessosMifare cliente = teste.getServico();

        cliente.getAcessosPermitidosPorGate(12);
        List<CartaoDeAcesso> resp = cliente.getAcessosPermitidosPorGate(12);

        System.out.println(resp.get(0).getNome());
        System.out.println(resp.get(0).getCodigoMifar());

    }

    public void testeSelect() {
        SBCore.configurar(new ConfigCorePadrao());
  //      SBPersistencia.configuraJPA(new ConfigPersistenciaSegurancaSphera());

        //  List<TbPessoa> pessoa = (List<TbPessoa>) UtilSBPersistencia.getListaRegistrosByHQL(" from Tb_Pessoa ", 10);
        //   System.out.println("selecionadas " + pessoa.size());
        List<Object[]> resposta = (List<Object[]>) UtilSBPersistencia.getListaRegistrosBySQL("SELECT ID_PESSOA, NOME,NOME_MAE from TB_PESSOA where ID_PESSOA>?", 100, 8000);
        for (Object[] registro : resposta) {
            for (Object campo : registro) {
                System.out.println(campo);
                System.out.println(campo.getClass().getSimpleName());
            }
        }
    }

    public void testeConexãoComBanco() {
        SBCore.configurar(new ConfigCorePadrao());
 //       SBPersistencia.configuraJPA(new ConfigPersistenciaSegurancaSphera());

        //      List<TbPessoa> pessoa = (List<TbPessoa>) UtilSBPersistencia.getListaRegistrosByHQL(" from TbPessoa ", 10);
//        System.out.println("selecionadas " + pessoa.size());
        List<Object[]> resposta = (List<Object[]>) UtilSBPersistencia.getListaRegistrosBySQL("SELECT ID_PESSOA, NOME,NOME_MAE from TB_PESSOA where ID_PESSOA>?", 100, 8000);
        for (Object[] registro : resposta) {
            for (Object campo : registro) {
                System.out.println(campo);
                System.out.println(campo.getClass().getSimpleName());
            }
        }
    }

    @Test
    public void geraTestes() {
        Random radom = new Random();
        int numeroTmp = 0;
        String numStr = "";
        for (int i = 0; i < 10; i++) {
            numeroTmp = radom.nextInt(10);
            System.out.println(">" + numeroTmp);
            numStr = numStr + numeroTmp;
        }

        Integer.toHexString(Integer.parseInt(numStr));

    }
}
