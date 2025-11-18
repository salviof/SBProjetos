/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.webpaginas.paginas;

import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.modulos.objetos.registro.Interfaces.basico.ComoEntidadeSimples;
import com.super_bits.modulosSB.webPaginas.JSFManagedBeans.formularios.MB_PaginaConversation;
import com.super_bits.modulosSB.webPaginas.JSFManagedBeans.formularios.reflexao.anotacoes.InfoPagina;
import com.super_bits.sbProjetos.Model.Desenvolvedor;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Salvio
 */
@InfoPagina(nomeCurto = "CD", tags = {"cadastroDeveloper"})
@ViewScoped
@Named
public class PgCadastroDesenvolvedor extends MB_PaginaConversation {

    private List<Desenvolvedor> desenvolvedores;
    private Desenvolvedor desenvolvedorSelecionado;

    @Override
    public void abrePagina() {
        super.abrePagina(); //To change body of generated methods, choose Tools | Templates.
        desenvolvedores = (List<Desenvolvedor>) UtilSBPersistencia.getListaTodos(Desenvolvedor.class);
    }

    @Override
    public String defineTitulo() {
        return "Cadastro desenvolvedor";
    }

    @Override
    public String defineNomeLink() {
        return "cadastro desenvolvedor";
    }

    @Override
    public String defineDescricao() {
        return "pagina para cadastro de desenvolvedores";
    }

    public List<Desenvolvedor> getDesenvolvedores() {
        return desenvolvedores;
    }

    public void setDesenvolvedores(List<Desenvolvedor> desenvolvedores) {
        this.desenvolvedores = desenvolvedores;
    }

    public Desenvolvedor getDesenvolvedorSelecionado() {
        return desenvolvedorSelecionado;
    }

    public void setDesenvolvedorSelecionado(Desenvolvedor desenvolvedorSelecionado) {
        this.desenvolvedorSelecionado = desenvolvedorSelecionado;
    }

    @Override
    public Long getId() {
        return 2;
    }

    @Override
    public ComoEntidadeSimples getBeanSelecionado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setBeanSelecionado(ComoEntidadeSimples pBeanSimples) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
