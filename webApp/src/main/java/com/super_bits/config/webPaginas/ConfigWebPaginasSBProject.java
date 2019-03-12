/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.config.webPaginas;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.permissoes.ItfAcaoFormulario;
import com.super_bits.modulosSB.webPaginas.ConfigGeral.ItfConfigWebPagina;
import com.super_bits.modulosSB.webPaginas.ConfigGeral.TIPO_ESTRUTURA_LOCAL_XHTML_PADRAO;
import com.super_bits.modulosSB.webPaginas.JSFManagedBeans.siteMap.parametrosURL.ParametroURL;
import com.super_bits.modulosSB.webPaginas.controller.paginasDoSistema.FabAcaoPaginasDoSistema;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Salvio
 */
public class ConfigWebPaginasSBProject implements ItfConfigWebPagina {

    @Override
    public String SITE_HOST() {
        return "http://localhost:8084";
    }

    @Override
    public String pastaImagens() {
        return "/resources/img";
    }

    @Override
    public String nomePacoteProjeto() {
        return "SBProjetos";
    }

    @Override
    public String TituloAppWeb() {
        return "Projetos Guiase Contagem Developer";
    }

    @Override
    public String URLBASE() {
        return SITE_HOST() + "/" + nomePacoteProjeto();
    }

    @Override
    public Class mapaSite() {
        return SiteMapSBProject.class;

    }

    @Override
    public boolean parametroDeAplicacaoEmSubDominio() {
        return false;
    }

    @Override
    public ItfAcaoFormulario getAcaoPaginaInicial() {
        return FabAcaoPaginasDoSistema.PAGINA_MB_HOME.getRegistro().getComoFormulario();
    }

    @Override
    public TIPO_ESTRUTURA_LOCAL_XHTML_PADRAO getTipoEstruturaPadrao() {
        return TIPO_ESTRUTURA_LOCAL_XHTML_PADRAO.MUDULO_NOME_ENTIDADE;
    }

    @Override
    public List<ParametroURL> parametrosDeAplicacao() {
        return new ArrayList<>();
    }

}
