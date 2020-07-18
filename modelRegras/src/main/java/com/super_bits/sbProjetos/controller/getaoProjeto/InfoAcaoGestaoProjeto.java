/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.controller.getaoProjeto;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author salvioF
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface InfoAcaoGestaoProjeto {

    public boolean padraoBloqueado() default true;

    /**
     *
     * Ao criar uma anotação InfoAcaoNomeDoModulo é importante:<br>
     * 1 - que o método chame acao <br>
     * 2 - Que o enum implemente ItfFabricaDeAcoes
     *
     * @return A ação vinculada ao Método
     */
    public FabAcaoGestaoProjetos acao();

}
