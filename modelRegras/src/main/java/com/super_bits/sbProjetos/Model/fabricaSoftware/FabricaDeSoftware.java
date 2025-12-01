/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.super_bits.sbProjetos.Model.fabricaSoftware;

import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import com.super_bits.modulosSB.Persistencia.registro.persistidos.EntidadeORMNormal;
import com.super_bits.sbProjetos.Model.Cliente;
import java.util.List;

/**
 *
 * @author salvio
 */
public class FabricaDeSoftware extends EntidadeORMNormal {

    private Long id;
    private String nome;
    private String razaoSocial;
    private String documento;
    private UsuarioDesenvolvedor responsavel;

    private List<UsuarioDesenvolvedor> desenvolvedores;

    private List<Cliente> clientes;

}
