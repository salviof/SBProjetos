/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbProjetos.util;

/**
 *
 * @author SalvioF
 */
public enum FabVariaveisScriptDeProjeto {

    SERVIDOR_GIT_RELEASE,
    SERVIDOR_GIT_SOURCE;

    public static String nomeArquivoCliente() {
        return "cliente.info";
    }
    
    public  String valorPadrao(){
        switch (this){
            case SERVIDOR_GIT_RELEASE:
                return "https://github.com/salviof/";
                
            case SERVIDOR_GIT_SOURCE:
                return "https://github.com/salviof/";
                
            default:
                throw new AssertionError(this.name());
            
        }
    }

}
