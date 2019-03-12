/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.config.webPaginas;

import com.super_bits.modulosSB.Persistencia.ConfigGeral.SBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.webPaginas.ConfigGeral.SBWebPaginas;
import com.super_bits.sbProjetos.Model.ConfigPersistenciaSBProject;

/**
 *
 * @author Salvio
 */
public abstract class SBProject {

    public static void ConfiguraSBProject() {
        SBCore.configurar(new Con, SBCore.ESTADO_APP.DESENVOLVIMENTO);
        SBPersistencia.configuraJPA(new ConfigPersistenciaSBProject());
        SBWebPaginas.configurar(new ConfigWebPaginasSBProject());
    }

}
