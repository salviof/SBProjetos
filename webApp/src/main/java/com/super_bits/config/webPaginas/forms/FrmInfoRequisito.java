/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.config.webPaginas.forms;

import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Mensagens.ItfCentralMensagens;
import com.super_bits.modulosSB.webPaginas.JSFBeans.SB.form.B_Formulario;
import com.super_bits.modulosSB.webPaginas.JSFBeans.SB.form.InfoForm;
import com.super_bits.sbProjetos.Model.Requisito;
import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

/**
 *
 * @author Salvio
 */
@ConversationScoped
@Named
@InfoForm(recurso = "/site/formulario/info/requisito")
public class FrmInfoRequisito extends B_Formulario implements Serializable {

    private Requisito requisito;

    public Requisito getRequisito() {
        return requisito;
    }

    public void setRequisito(Requisito requisito) {
        this.requisito = requisito;
    }

    @Override
    protected boolean acaoForm() {
        return true;
    }

    public void salvarAlteracoes() {
        if (!SBCore.getControleDeSessao().getSessaoAtual().getUsuario().getEmail().equals("salviof@gmail.com")) {
            SBCore.getCentralDeMensagens().enviaMensagemUsuario("Você não tem permissão para editar", ItfCentralMensagens.TP_MENSAGEM.ALERTA);
            return;
        }

        if (UtilSBPersistencia.mergeRegistro(requisito) != null) {
            SBCore.getCentralDeMensagens().enviaMensagemUsuario("Alteração salva com sucesso", ItfCentralMensagens.TP_MENSAGEM.AVISO);
        } else {
            SBCore.getCentralDeMensagens().enviaMensagemUsuario("Erro ao salvar alterações", ItfCentralMensagens.TP_MENSAGEM.ERRO);
        }
    }

}
