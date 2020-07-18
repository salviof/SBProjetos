/*
 *  Super-Bits.com CODE CNPJ 20.019.971/0001-90

 */
package com.super_bits.sbProjetos.Model;

import com.super_bits.modulos.SBAcessosModel.model.UsuarioSB;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 *
 *
 *
 *
 * @author Salvio
 */
@Entity
public class Desenvolvedor extends UsuarioSB {

    public static final String PASTADEVELOPER = "/home/superBits/projetos";

    private String usuarioSVN;
    private Integer horasMensais;

    @ManyToMany(mappedBy = "desenvolvedores")
    private List<Projeto> projetos;

    public String getUsuarioSVN() {
        return usuarioSVN;

    }

    public void setUsuarioSVN(String usuarioSVN) {
        this.usuarioSVN = usuarioSVN;
    }

    public Integer getHorasMensais() {
        return horasMensais;
    }

    public void setHorasMensais(Integer horasMensais) {
        this.horasMensais = horasMensais;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    public List<Requisito> getRequisitosProximaVersao() {
        List<Requisito> resp = new ArrayList<>();
        for (Projeto proj : getProjetos()) {
            resp.addAll(proj.getRequisitosProximaVersao());
        }
        return resp;

    }

}
