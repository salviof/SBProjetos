/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.sbProjetos.Model;

import com.super_bits.modulosSB.Persistencia.ConfigGeral.ItfConfigSBPersistencia;
import com.super_bits.modulosSB.SBCore.modulos.fabrica.ItfFabrica;
import com.super_bits.sbProjetos.controller.getaoProjeto.FabAcaoGestaoProjetos;

/**
 *
 * @author Salvio
 */
public class ConfigPersistenciaSBProject implements ItfConfigSBPersistencia {

    @Override
    public String bancoPrincipal() {
        return "SBProject";
    }

    @Override
    public String[] bancosExtra() {
        return new String[0];
    }

    @Override
    public String formatoDataBanco() {
        return "yyy-MM-dd";
    }

    @Override
    public String formatoDataUsuario() {
        return "dd/MM/yy";
    }

    @Override
    public String pastaImagensJPA() {
        return "/resources/img";
    }

    @Override
    public void criarBancoInicial() {
        return;
    }

    @Override
    public Class<? extends ItfFabrica>[] fabricasRegistrosIniciais() {
        return new Class[]{FabAcaoGestaoProjetos.class};
    }

}
