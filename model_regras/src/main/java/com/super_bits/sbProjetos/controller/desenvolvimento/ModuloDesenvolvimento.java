/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.controller.desenvolvimento;

import com.super_bits.modulosSB.SBCore.modulos.Controller.Interfaces.ItfResposta;
import com.super_bits.modulosSB.Persistencia.dao.ControllerAbstratoSBPersistencia;
import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.modulos.TratamentoDeErros.FabErro;
import com.super_bits.sbProjetos.Model.Desenvolvedor;
import com.super_bits.sbProjetos.Model.Requisito;
import com.super_bits.sbProjetos.Model.Trabalho;
import com.super_bits.sbProjetos.controller.getaoProjeto.FabAcaoGestaoProjetos;
import com.super_bits.sbProjetos.controller.getaoProjeto.InfoAcaoGestaoProjeto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author salvioF
 */
public class ModuloDesenvolvimento extends ControllerAbstratoSBPersistencia {

    @InfoAcaoGestaoProjeto(acao = FabAcaoGestaoProjetos.PROJETO_CTR_ADICIONAR_DESENVOLVEDOR_AO_PROJETO)
    public static ItfResposta iniciarTrabalho(Desenvolvedor pDesenvolvedor, Requisito requisito) {

        ItfResposta resp = getNovaRespostaAutorizaChecaNulo(requisito);
        try {
            Trabalho trabalhoAtual = trabalhoAtivoPorDesenvolvedor(pDesenvolvedor);

            if (trabalhoAtual == null) {

                trabalhoAtual = new Trabalho(requisito, pDesenvolvedor);
                trabalhoAtual = (Trabalho) UtilSBPersistencia.mergeRegistro(trabalhoAtual);
            }
            return resp.setRetornoDisparaERetorna(trabalhoAtual);
        } catch (Exception e) {

            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro controler iniciando trabalho", e);
            return null;
        }

    }

    public static Trabalho finalizarTrabalho(Trabalho ptrabalhoAtual) {
        ptrabalhoAtual.setFim(new Date());
        EntityManager em = UtilSBPersistencia.getNovoEM();
        try {
            em.getTransaction().begin();
            Requisito req = ptrabalhoAtual.getRequisito();
            req.atualizarHorasTrabalhadadas();
            UtilSBPersistencia.mergeRegistro(req, em);
            return (Trabalho) UtilSBPersistencia.mergeRegistro(ptrabalhoAtual, em);

        } catch (Exception e) {
            SBCore.RelatarErro(FabErro.SOLICITAR_REPARO, "Erro Finalizando trabalho", e);
            if (em != null) {
                em.getTransaction().rollback();
                em.close();
                em = null;
            }
        } finally {
            if (em != null) {
                em.getTransaction().commit();
                em.close();

            }
        }
        return ptrabalhoAtual;
    }

    public static Trabalho AtualizarHistorioDeTrabalho(Trabalho pTrabalho, String novoHistorico) {

        pTrabalho.setHistorico(pTrabalho.getHistorico() + "\n" + novoHistorico);
        return (Trabalho) UtilSBPersistencia.mergeRegistro(pTrabalho);
    }

    public static Trabalho trabalhoAtivoPorDesenvolvedor(Desenvolvedor pDev) {
        EntityManager em = null;
        try {
            em = UtilSBPersistencia.getNovoEM();
            Object trabalhoAtivo = UtilSBPersistencia.getRegistroByJPQL("from Trabalho   where desenvolvedor.id=" + pDev.getId() + " and fim is null ", Trabalho.class, em);
            if (trabalhoAtivo == null) {
                System.out.println("Nenhum trabalho ativo encontrado");
                return null;
            }
            System.out.println("Trabalho Ativo=" + trabalhoAtivo);

            return (Trabalho) trabalhoAtivo;
        } finally {
            if (em != null) {
                //  em.close();
            }
        }
    }

    public static List<Requisito> getRequisitosDisponiveis(Desenvolvedor pDesenvolvedor) {
        return new ArrayList<>();
    }
}
