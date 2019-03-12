/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.webpaginas.paginas;

import com.super_bits.config.webPaginas.SiteMapSBProject;
import com.super_bits.modulosSB.webPaginas.JSFManagedBeans.formularios.MB_PaginaAtual;
import com.super_bits.modulosSB.webPaginas.JSFManagedBeans.siteMap.MB_SiteMapa;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Salvio
 */
@ConversationScoped
@Named
public class PaginaAtual extends MB_PaginaAtual {

    @Inject
    private SiteMapSBProject mapa;

    @Override
    protected MB_SiteMapa getSiteMap() {
        return mapa;
    }

}
