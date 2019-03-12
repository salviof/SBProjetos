/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.config.webPaginas;

import com.super_bits.modulosSB.SBCore.modulos.view.menu.ItfFabricaMenu;
import com.super_bits.modulosSB.webPaginas.JSFManagedBeans.siteMap.MB_SiteMapa;
import com.super_bits.webpaginas.paginas.PgAdmin;
import com.super_bits.webpaginas.paginas.PgCadastroDesenvolvedor;
import com.super_bits.webpaginas.paginas.PgVisaoGeral;
import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author Salvio
 */
@ApplicationScoped
public class SiteMapSBProject extends MB_SiteMapa implements Serializable {

    @Inject
    private PgVisaoGeral vg;
    @Inject
    private PgCadastroDesenvolvedor cadastro;
    @Inject
    private PgAdmin admin;

    @Override
    public Class<? extends ItfFabricaMenu> getFabricaMenu() {
        return null;
    }

}
