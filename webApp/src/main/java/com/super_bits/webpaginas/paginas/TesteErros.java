/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.webpaginas.paginas;

import com.super_bits.sbProjetos.Model.Projeto;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

/**
 *
 * ATENÇÃO A DOCUMENTAÇÃO DA CLASSE É OBRIGATÓRIA O JAVADOC DOS METODOS PUBLICOS
 * SÓ SÃO OBRIGATÓRIOS QUANDO NÃO EXISTIR UMA INTERFACE DOCUMENTADA, DESCREVA DE
 * FORMA OBJETIVA E EFICIENTE, NÃO ESQUEÇA QUE VOCÊ FAZ PARTE DE UMA EQUIPE.
 *
 *
 * @author <a href="mailto:salviof@gmail.com">Salvio Furbino</a>
 * @since 12/01/2015
 * @version 1.0
 */
@Named
@RequestScoped
public class TesteErros {

    Projeto proj;

    public void nulo() {
        proj.getCliente();
    }

    public void naoImplementado() {
        throw new UnsupportedOperationException("Nao foi implementado");
    }

}
