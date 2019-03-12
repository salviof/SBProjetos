/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.config.webPaginas.forms;

import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.ItfCentralMensagens;
import com.super_bits.modulosSB.webPaginas.JSFBeans.SB.form.B_Formulario;
import com.super_bits.modulosSB.webPaginas.JSFBeans.SB.form.InfoForm;
import com.super_bits.sbProjetos.Model.Projeto;
import com.super_bits.sbProjetos.controller.getaoProjeto.ModuloGestaoProjeto;
import com.super_bits.webpaginas.paginas.PgVisaoGeral;
import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Salvio
 */
@ConversationScoped
@Named
@InfoForm(recurso = "/site/formulario/cadastro/requisito", textoAcao = "Cadastrar Requisito")
public class FrmNovoRequisito extends B_Formulario implements Serializable {

    private String nomeNovoRequisito;

    private String descricao;

    private String motivacao;

    private Projeto projeto;
    @Inject
    private Conversation conversation;

    @Inject
    private PgVisaoGeral visaoGerao;

    public String getNomeNovoRequisito() {
        return nomeNovoRequisito;
    }

    public void setNomeNovoRequisito(String nomeNovoRequisito) {
        this.nomeNovoRequisito = nomeNovoRequisito;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;

    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    protected boolean acaoForm() {

        if (ModuloGestaoProjeto.criarRequisito(descricao, motivacao, nomeNovoRequisito, projeto)) {
            SBCore.getCentralDeMensagens().enviaMensagemUsuario("Requisito cadastrado", ItfCentralMensagens.TP_MENSAGEM.AVISO);
            return true;
        } else {

            SBCore.getCentralDeMensagens().enviaMensagemUsuario("Não foi possível realizar o cadastro", ItfCentralMensagens.TP_MENSAGEM.ERRO);
            return false;
        }
    }
}
