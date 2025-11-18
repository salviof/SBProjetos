/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.config.webPaginas;

import com.super_bits.modulosSB.SBCore.ConfigGeral.ItfConfiguracaoCoreSomenteLeitura;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Controller.ConfigPermissaoSBCoreAbstrato;
import com.super_bits.modulosSB.SBCore.modulos.ManipulaArquivo.interfaces.ItfCentralDeArquivos;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.ItfCentralMensagens;
import com.super_bits.modulosSB.SBCore.modulos.TratamentoDeErros.ErroSBCoreFW;
import com.super_bits.modulosSB.SBCore.modulos.comunicacao.ItfCentralComunicacao;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ComoFabricaAcoes;
import com.super_bits.modulosSB.SBCore.modulos.fonteDados.ItfCentralAtributosDeObjetos;
import com.super_bits.modulosSB.SBCore.modulos.localizacao.ItfCentralLocalizacao;
import com.super_bits.modulosSB.SBCore.modulos.logeventos.CentralLogEventosArqTextoGenerica;
import com.super_bits.modulosSB.SBCore.modulos.logeventos.ItfCentralEventos;
import com.super_bits.modulosSB.SBCore.modulos.sessao.Interfaces.ItfControleDeSessao;
import com.super_bits.modulosSB.SBCore.modulos.view.ItfServicoVisualizacao;
import com.super_bits.modulosSB.webPaginas.controller.sessao.ControleDeSessaoWeb;
import com.super_bits.modulosSB.webPaginas.util.CentralDeMensagensJSFAPP;

/**
 *
 * @author Salvio
 */
public class ConfigCoreDemoWP implements ItfConfiguracaoCoreSomenteLeitura {

    @Override
    public Class<? extends ItfCentralMensagens> getCentralDeMensagens() {
        return CentralDeMensagensJSFAPP.class;
    }

    @Override
    public Class<? extends ErroSBCoreFW> getClasseErro() {
        return ErroSBCoreFW.class;
    }

    @Override
    public Class<? extends ItfControleDeSessao> getControleDeSessao() {
        return ControleDeSessaoWeb.class;
    }

    @Override
    public Class<? extends ItfCentralEventos> getCentralDeEventos() {
        return CentralLogEventosArqTextoGenerica.class;
    }

    @Override
    public String getNomeProjeto() {
        return "InomeClienteI";
    }

    @Override
    public String getDiretorioBase() {
        return "/";
    }

    @Override
    public String getCliente() {
        return "InomeClienteI";
    }

    @Override
    public String getGrupoProjeto() {
        return "modulos";
    }

    @Override
    public String getNomeSocial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<? extends ConfigPermissaoSBCoreAbstrato> getConfigPermissoes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SBCore.ESTADO_APP getEstadoApp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<? extends ComoFabricaAcoes>[] getFabricaDeAcoes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUrlJira() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<? extends ItfServicoVisualizacao> getServicoVisualizacao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<? extends ItfCentralDeArquivos> getCentralDeArquivo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<? extends ItfCentralComunicacao> getCentralComunicacao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<? extends ItfCentralLocalizacao> getCentralDeLocalizacao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<? extends ItfCentralAtributosDeObjetos> getCentralAtributoDados() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
