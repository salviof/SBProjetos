/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos;

import com.super_bits.modulosSB.SBCore.ConfigGeral.ConfiguradorCoreDeProjetoJarAbstrato;
import com.super_bits.modulosSB.SBCore.ConfigGeral.ItfConfiguracaoCoreCustomizavel;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.CentralMensagemArqTexto;

/**
 *
 * @author salvioF
 */
public class ConfiguradorCoreSBProjetosModel extends ConfiguradorCoreDeProjetoJarAbstrato {

    public ConfiguradorCoreSBProjetosModel() {
        setIgnorarConfiguracaoAcoesDoSistema(true);
        setIgnorarConfiguracaoPermissoes(true);
    }

    @Override
    public void defineFabricasDeACao(ItfConfiguracaoCoreCustomizavel pConfig) {
        pConfig.setCentralMEnsagens(CentralMensagemArqTexto.class);
        pConfig.setFabricaDeAcoes(new Class[]{});

    }

}
