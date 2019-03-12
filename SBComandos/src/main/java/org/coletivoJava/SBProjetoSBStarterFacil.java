/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coletivoJava;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.sbProjetos.ConfiguradorCoreSBProjetosModel;
import com.super_bits.sbProjetos.util.FabVariaveisScriptDeProjeto;
import com.super_bits.sbProjetos.util.MapaProjetos;
import com.super_bits.sbProjetos.util.UtilSBProjetosCommandLine;

/**
 *
 * @author SalvioF
 */
public class SBProjetoSBStarterFacil {

    public static void main(String[] args) {
        SBCore.configurar(new ConfiguradorCoreSBProjetosModel(), SBCore.ESTADO_APP.PRODUCAO);
        System.out.println(FabVariaveisScriptDeProjeto.SERVIDOR_GIT_RELEASE.toString());
        MapaProjetos.buildMapaByEstacao();
        UtilSBProjetosCommandLine.iniciarNavegacao();
    }
}
