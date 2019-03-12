/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.webpaginas.paginas;

import com.super_bits.sbProjetos.Model.Projeto;
import com.super_bits.sbProjetos.controller.getaoProjeto.ModuloGestaoProjeto;

/**
 *
 * @author sfurbino
 */
public class PgCriarProjeto {

    private Projeto novoProjeto;

    public void salvarNovoProjeto() {
        ModuloGestaoProjeto.criarProjeto(novoProjeto);
    }

}
