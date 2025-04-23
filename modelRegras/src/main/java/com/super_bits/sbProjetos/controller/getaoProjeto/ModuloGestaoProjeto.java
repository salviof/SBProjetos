/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.controller.getaoProjeto;

import com.super_bits.modulosSB.Persistencia.dao.ControllerAbstratoSBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;

import com.super_bits.sbProjetos.Model.Cliente;
import com.super_bits.sbProjetos.Model.Desenvolvedor;
import com.super_bits.sbProjetos.Model.Projeto;
import com.super_bits.sbProjetos.Model.Projeto_Desenvolvedor;
import com.super_bits.sbProjetos.Model.Requisito;
import com.super_bits.sbProjetos.Model.StatusRequisito;
import java.util.Date;
import java.util.List;
import org.coletivojava.fw.api.tratamentoErros.FabErro;

/**
 *
 * Funções estaticas de acesso ao banco de dados da camada Controladora
 *
 * @author Salvio
 */
public class ModuloGestaoProjeto extends ControllerAbstratoSBPersistencia {

    @InfoAcaoGestaoProjeto(acao = FabAcaoGestaoProjetos.REQUISITO_CTR_PROMOVER_REQUISITO)
    public static ItfResposta promoverRequisito(Requisito req) {

        ItfResposta resp = getNovaRespostaAutorizaChecaNulo(req);

        if (!resp.isSucesso()) {
            return resp.dispararMensagens();
        }
        if (req.getStatusRequisito().getId() == StatusRequisito.finalizado.getId()) {
            resp.addErro("O Requisito já foi promovido");

        }

        if (req.getHorasEstimadas() < 1) {
            resp.addErro("As horas estimadas não foram definidas");

        }

        if (req.getStatusRequisito().getId() == StatusRequisito.proximaVersao.getId()) {
            finalizarRequisito(req);

        }

        if (!resp.isSucesso()) {
            return resp.dispararMensagens();
        }

        try {
            StatusRequisito novoStatus = new StatusRequisito();
            System.out.println("Setando novo status" + novoStatus.getDescricao());
            novoStatus.loadByID(req.getStatusRequisito().getId() + 1);
            if (novoStatus.getId() != null && novoStatus.getId() > 0) {
                req.setStatusRequisito(novoStatus);
                UtilSBPersistencia.mergeRegistro(req);
                System.out.println("promovido");

            }
            return resp.dispararMensagens();
        } catch (Exception e) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "erro promovendo requisito", e);
            resp.addErro("Erro não previsto,  tentando promover o Requisito");

            return resp.dispararMensagens();
        }

    }

    @InfoAcaoGestaoProjeto(acao = FabAcaoGestaoProjetos.REQUISITO_CTR_APROVAR_REQUISITO)
    public static ItfResposta aprovarRequisito(Requisito req) {
        ItfResposta resp = getNovaRespostaAutorizaChecaNulo(req);

        if (!resp.isSucesso()) {
            return resp.dispararMensagens();
        }
        req.setStatusRequisito(StatusRequisito.futuro);
        UtilSBPersistencia.mergeRegistro(req);
        return resp.setRetornoDisparaERetorna(resp);
    }

    @InfoAcaoGestaoProjeto(acao = FabAcaoGestaoProjetos.REQUISITO_CTR_REBAIXAR_REQUISITO)
    public static ItfResposta rebaixarRequisito(Requisito req) {
        ItfResposta resp = getNovaRespostaAutorizaChecaNulo(req);

        if (req.getStatusRequisito().getId() == StatusRequisito.sujestao.getId()) {
            resp.addErro("Requisito já se encontra como sujestão");

        }

        if (!resp.isSucesso()) {
            return resp.dispararMensagens();
        }

        StatusRequisito novoStatus = new StatusRequisito();

        novoStatus.loadByID(req.getStatusRequisito().getId() - 1);
        System.out.println("Setando novo status" + novoStatus.getDescricao());
        if (novoStatus.getId() != null && novoStatus.getId() > 0) {

            req.setStatusRequisito(novoStatus);
            UtilSBPersistencia.mergeRegistro(req);
            System.out.println("Rebaixado");
        }

        return resp.setRetornoDisparaERetorna(resp);

    }

    @InfoAcaoGestaoProjeto(acao = FabAcaoGestaoProjetos.REQUISITO_CTR_CRIAR_REQUISITO)
    public static ItfResposta criarRequisito(String descricao, String motivacao, String pNome, Projeto projeto) {
        ItfResposta resp = getNovaRespostaAutorizaChecaNulo(projeto);

        if (descricao == null || motivacao == null || projeto == null) {
            resp.addErro("Preencha todos os parametros");

        }

        if (!resp.isSucesso()) {
            return resp.dispararMensagens();
        }

        Requisito novoRequisito = new Requisito();
        novoRequisito.setDescricao(descricao);
        novoRequisito.setMotivacao(motivacao);
        novoRequisito.setNome(pNome);
        novoRequisito.setProjeto(projeto);
        novoRequisito.setStatusRequisito(StatusRequisito.sujestao);

        if (UtilSBPersistencia.persistirRegistro(novoRequisito)) {

            return resp.addMensagemAvisoDisparaERetorna("Requisito cadastrado com sucessso!");
        } else {
            return resp.addMensagemErroDisparaERetorna("Ouve um erro ao tentar salvar a informção sobre o requisito");

        }
    }

    @InfoAcaoGestaoProjeto(acao = FabAcaoGestaoProjetos.PROJETO_CTR_ADICIONAR_DESENVOLVEDOR_AO_PROJETO)
    public static ItfResposta criarProjeto(String pnome, String pDescricao, Cliente pCliente) {

        ItfResposta resp = getNovaRespostaAutorizaChecaNulo(pCliente);
        if (!resp.isSucesso()) {
            return resp.dispararMensagens();
        }

        Projeto novoprojeto = new Projeto();
        novoprojeto.setDescricao(pDescricao);
        novoprojeto.setCliente(pCliente);
        novoprojeto.setNomeProjeto(pnome);

        return criarProjeto(novoprojeto);

    }

    @InfoAcaoGestaoProjeto(acao = FabAcaoGestaoProjetos.PROJETO_CTR_CRIAR_PROJETO)
    public static ItfResposta criarProjeto(Projeto pProjeto) {

        ItfResposta resp = getNovaRespostaAutorizaChecaNulo(pProjeto);
        if (!resp.isSucesso()) {
            return resp.dispararMensagens();
        }

        if (pProjeto.getCliente() == null) {
            return resp.addMensagemErroDisparaERetorna("Não foi possível criar o projeto, Cliente não selecionado");

        }
        if (pProjeto.getDescricao() == null) {
            return resp.addMensagemErroDisparaERetorna("Não foi possível criar o projeto, a descrição não foi preenchida ");

        }
        if (pProjeto.getNomeProjeto() == null) {
            return resp.addMensagemErroDisparaERetorna("Não foi possível criar o projeto, o nome não foi configurado");

        }

        pProjeto.setDataCriacao(new Date());
        //   pProjeto.setPastaDoProjeto("c:\\home\\projetos\\source\\" + pProjeto.getCliente().getNomeCurto() + "\\" + pProjeto.getNomeCurto());

        boolean gravou = UtilSBPersistencia.persistirRegistro(pProjeto);
        if (gravou) {
            return resp.addMensagemErroDisparaERetorna("Projeto " + pProjeto.getNomeProjeto() + " cadastrado com sucesso");

        } else {
            return resp.addMensagemErroDisparaERetorna("Ocorreu um erro ao tentar gravar o novo projeto");

        }

    }

    @InfoAcaoGestaoProjeto(acao = FabAcaoGestaoProjetos.REUISITIO_CTR_FINALIZAR_REQUISITO)
    public static void finalizarRequisito(Requisito pRequisito) {

        pRequisito.setStatusRequisito(StatusRequisito.finalizado);
        UtilSBPersistencia.mergeRegistro(pRequisito);
    }

    public static ItfResposta adcionarDesenvolvedorAoProjeto(Projeto pProjeto, Desenvolvedor pDesenvolvedor, int horasSemanais) {

        ItfResposta resp = getNovaRespostaAutorizaChecaNulo(pProjeto);
        if (!resp.isSucesso()) {
            return resp.dispararMensagens();
        }

        if (pProjeto == null || pDesenvolvedor == null || horasSemanais == 0) {
            return resp.addMensagemErroDisparaERetorna("Os parametros projeto, desenvolvedor e horas semanais são obrigatórios");

        }

        List<Desenvolvedor> desenvolvedores = pProjeto.getDesenvolvedores();
        for (Desenvolvedor dev : desenvolvedores) {
            if (dev.getId() == pDesenvolvedor.getId()) {
                System.out.println("usuario já cadastrado");
                return resp.addMensagemErroDisparaERetorna("Usuário já cadastrado");

            }
        }

        Projeto_Desenvolvedor projDev = new Projeto_Desenvolvedor();
        projDev.setDesenvolvedor(pDesenvolvedor);
        projDev.setProjeto(pProjeto);
        projDev.setHorasDia(horasSemanais);

        pProjeto.getDesenvolvedoresInfoCompleta().add(projDev);

        if (UtilSBPersistencia.persistirRegistro(projDev)) {
            resp.addMensagemAvisoDisparaERetorna("Desenvolvedor: " + pDesenvolvedor.getNome() + " adcionado com sucesso ao projeto" + pProjeto.getNomeCurto());

        } else {
            return resp.addMensagemErroDisparaERetorna("Erro inesperado salvando alterações");
        }
        return resp.dispararMensagens();

    }

    private static ItfResposta alteraHorasSemanaisDesenvolvedor(Projeto pProjeto, Desenvolvedor pDesenvolvedor, int alteracao) {
        ItfResposta resp = getNovaRespostaAutorizaChecaNulo(pProjeto);
        if (!resp.isSucesso()) {
            return resp.dispararMensagens();
        }

        if (pProjeto == null) {

            return resp.addMensagemErroDisparaERetorna("Parametro projeto é obrigatório");
        }
        if (pDesenvolvedor == null) {
            return resp.addMensagemErroDisparaERetorna("Parametro desenvolvedor é pbrigatorio");
        }

        Projeto_Desenvolvedor novohoraRioSemanal = (Projeto_Desenvolvedor) UtilSBPersistencia.getRegistroByJPQL(" SELECT pd  from Projeto_Desenvolvedor pd where projeto_id=" + pProjeto.getId() + " and desenvolvedor_id=" + pDesenvolvedor.getId(), Projeto_Desenvolvedor.class);
        if (novohoraRioSemanal == null) {
            return resp.addMensagemErroDisparaERetorna("Usuário Não trabalha neste projeto");

        }

        novohoraRioSemanal.setHorasDia(novohoraRioSemanal.getHorasDia() + alteracao);
        UtilSBPersistencia.mergeRegistro(novohoraRioSemanal);
        return resp.addMensagemErroDisparaERetorna("");
    }

    public static ItfResposta incrementarHorasSemanaisDesenvolvedor(Projeto pProjeto, Desenvolvedor pDesenvolvedor) {
        ItfResposta resp = getNovaRespostaAutorizaChecaNulo(pProjeto);
        if (!resp.isSucesso()) {
            return resp.dispararMensagens();
        }
        return resp;
    }

    public static ItfResposta decrementarHorasSemanaisDesenvolvedor(Projeto pProjeto, Desenvolvedor pDesenvolvedor) {
        ItfResposta resp = getNovaRespostaAutorizaChecaNulo(pProjeto);
        if (!resp.isSucesso()) {
            return resp.dispararMensagens();
        }
        return resp;
    }

}
