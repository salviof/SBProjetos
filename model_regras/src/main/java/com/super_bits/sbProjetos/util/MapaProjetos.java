/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.util;

import com.google.common.collect.Lists;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivoTexto;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.UtilSBCoreArquivos;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;
import com.super_bits.sbProjetos.Model.Cliente;
import com.super_bits.sbProjetos.Model.Projeto;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

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

    
    public static Properties getPropriedadesDoClientePastaSource(Cliente pCliente){
        try {
        String arquivoSource=pCliente.getCaminhoPastaClinteSource() + "/" + FabVariaveisScriptDeProjeto.nomeArquivoCliente();
        if (UtilSBCoreArquivos.isArquivoExiste(arquivoSource)){
            
        }else {
            List<String> conteudoPadraoArquivo=Lists.newArrayList(FabVariaveisScriptDeProjeto.SERVIDOR_GIT_SOURCE.toString()+"="+FabVariaveisScriptDeProjeto.SERVIDOR_GIT_SOURCE.valorPadrao());
            UtilSBCoreArquivoTexto.escreveLinhasEmNovoArquivo(arquivoSource, conteudoPadraoArquivo);
            System.out.println("NÃO ENCONTRAMOS O ARQUIVO DE CONFIGURAÇÃO DO CLIENTE, CONTENDO O SERVIDOR DO REPOSITÓRIO, o sistema irá criar um arquivo padrão..");
        }
          Properties prop = UtilSBCoreArquivoTexto.getPropriedadesNoArquivo(pCliente.getCaminhoPastaClinteSource() + "/" + FabVariaveisScriptDeProjeto.nomeArquivoCliente());
        return prop;
        }catch(Throwable t){
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Deveria haver na pasta do projeto "+pCliente.getNome()+" um arquivo de proriedades, informando o servidor de repositório cliente do cliente, para os arquivos source e parar os arquivos release", t);
            return null;
        }
    }
    
     public static Properties getPropriedadesDoClientePastaRelease(Cliente pCliente){
        try {
        String arquivoRelease=pCliente.getCaminhoPastaClinteRelease() + "/" + FabVariaveisScriptDeProjeto.nomeArquivoCliente();
        if (UtilSBCoreArquivos.isArquivoExiste(arquivoRelease)){
            
        }else {
            List<String> conteudoPadraoArquivo=Lists.newArrayList(FabVariaveisScriptDeProjeto.SERVIDOR_GIT_RELEASE.toString()+"="+FabVariaveisScriptDeProjeto.SERVIDOR_GIT_RELEASE.valorPadrao());
            UtilSBCoreArquivoTexto.escreveLinhasEmNovoArquivo(arquivoRelease, conteudoPadraoArquivo);
            System.out.println("NÃO ENCONTRAMOS O ARQUIVO DE CONFIGURAÇÃO DO CLIENTE, CONTENDO O SERVIDOR DO REPOSITÓRIO, o sistema irá criar um arquivo padrão..");
        }
          Properties prop = UtilSBCoreArquivoTexto.getPropriedadesNoArquivo(pCliente.getCaminhoPastaClinteSource() + "/" + FabVariaveisScriptDeProjeto.nomeArquivoCliente());
        return prop;
        }catch(Throwable t){
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Deveria haver na pasta do projeto "+pCliente.getNome()+" um arquivo de proriedades, informando o servidor de repositório cliente do cliente, para os arquivos source e parar os arquivos release", t);
            return null;
        }
    }
    
    
    public static void buildMapaByEstacao() {

        File pastaProjetos = new File("/home/superBits/projetos");

        for (File pasta : pastaProjetos.listFiles()) {

            if (pasta.isDirectory()) {
                Cliente novoCliente = new Cliente();
                try {
                    novoCliente.setNome(pasta.getName());
                    
                    Properties prop = getPropriedadesDoClientePastaSource(novoCliente);
                    if (prop==null){
                        continue;
                    }
                    String caminhoServidorSource = prop.getProperty(FabVariaveisScriptDeProjeto.SERVIDOR_GIT_SOURCE.toString());
                    novoCliente.setServidorGitCodigoFonte(caminhoServidorSource);
                    Properties propRelease = getPropriedadesDoClientePastaRelease(novoCliente);
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
