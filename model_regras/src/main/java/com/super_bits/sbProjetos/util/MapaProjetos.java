/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.util;

import com.google.common.collect.Lists;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivoTexto;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;
import com.super_bits.sbProjetos.Model.Cliente;
import com.super_bits.sbProjetos.Model.Projeto;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author SalvioF
 */
public class MapaProjetos {

    private static final Map<String, Projeto> MAPA_PROJETOS = new HashMap<>();
    private static final Map<String, Cliente> MAPA_CLIENTES = new HashMap<>();

    public static void buildMapaByFabrica(Class<? extends ItfFabrica> pFabricaProjetos) {
        for (ItfFabrica projeto : pFabricaProjetos.getEnumConstants()) {
            if (projeto.getRegistro() != null) {

                Projeto pr = (Projeto) projeto.getRegistro();
                if (pr.getCliente() != null) {
                    adicionarProjeto(pr);

                }
            }
        }
    }

    public static void adicionarCliente(Cliente pCliente) {
        if (MAPA_CLIENTES.get(pCliente.getNome()) == null) {
            MAPA_CLIENTES.put(pCliente.getNome(), pCliente);

        }
    }

    public static void adicionarProjeto(Projeto pProj) {

        MAPA_PROJETOS.put(pProj.getNome(), pProj);
        if (MAPA_CLIENTES.get(pProj.getCliente().getNome()) != null) {
            MAPA_CLIENTES.get(pProj.getCliente().getNome()).getProjetos().add(pProj);
        } else {
            MAPA_CLIENTES.put(pProj.getCliente().getNome(), pProj.getCliente());
            MAPA_CLIENTES.get(pProj.getCliente().getNome()).getProjetos().add(pProj);
        }
    }

    public static void buildMapaByEstacao() {

        File pastaProjetos = new File("/home/superBits/projetos");

        for (File pasta : pastaProjetos.listFiles()) {

            if (pasta.isDirectory()) {
                Cliente novoCliente = new Cliente();
                try {
                    novoCliente.setNome(pasta.getName());
                    Properties prop = UtilSBCoreArquivoTexto.getPropriedadesNoArquivo(novoCliente.getCaminhoPastaClinteSource() + "/" + FabVariaveisScriptDeProjeto.nomeArquivoCliente());
                    String caminhoServidorSource = prop.getProperty(FabVariaveisScriptDeProjeto.SERVIDOR_GIT_SOURCE.toString());
                    novoCliente.setServidorGitCodigoFonte(caminhoServidorSource);
                    Properties propRelease = UtilSBCoreArquivoTexto.getPropriedadesNoArquivo(novoCliente.getCaminhoPastaClinteRelease() + "/" + FabVariaveisScriptDeProjeto.nomeArquivoCliente());
                    String caminhoServidorRelease = propRelease.getProperty(FabVariaveisScriptDeProjeto.SERVIDOR_GIT_RELEASE.name());
                    novoCliente.setServicorGitRelease(caminhoServidorRelease);

                    MapaProjetos.adicionarCliente(novoCliente);
                } catch (Throwable t) {
                    System.out.println("Erro identificando cliente na pasta:" + pasta);
                }

                for (File pastaProjeto : new File(novoCliente.getCaminhoPastaClinteSource()).listFiles()) {
                    try {
                        Projeto novoProjeto = new Projeto();
                        novoProjeto.setCliente(novoCliente);
                        novoProjeto.setNomeProjeto(pastaProjeto.getName());
                        novoProjeto.setNome(pastaProjeto.getName());
                        MapaProjetos.adicionarProjeto(novoProjeto);
                    } catch (Throwable t) {
                        System.out.println("Erro adicionando projeto " + t.getMessage());
                    }
                }

            }

        }

    }

    public static List<Cliente> getTodosClientes() {
        return Lists.newArrayList(MAPA_CLIENTES.values());
    }

    public static List<Projeto> getTodosProjetos() {
        return Lists.newArrayList(MAPA_PROJETOS.values());
    }

    public void buildDireotorio(Cliente pCliente) {

    }

}
