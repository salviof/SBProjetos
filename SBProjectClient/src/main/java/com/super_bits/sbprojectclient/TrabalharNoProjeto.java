/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbprojectclient;

import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import com.super_bits.modulosSB.SBCore.ConfigGeral.SBCore;
import com.super_bits.modulosSB.SBCore.TratamentoDeErros.ErroSB;
import com.super_bits.sbProjetos.Model.Desenvolvedor;
import com.super_bits.sbProjetos.Model.Projeto;
import com.super_bits.sbProjetos.Model.Requisito;
import com.super_bits.sbProjetos.Model.Trabalho;
import com.super_bits.sbProjetos.controller.getaoProjeto.ModuloGestaoProjeto;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Salvio
 */
public class TrabalharNoProjeto extends javax.swing.JFrame {

    public Requisito requisitoAtual;
    private Map<String, Requisito> requisitoByNome;
    public Trabalho trabalhoAtual;
    public Desenvolvedor desenvolvedorLogado;
    public Date ultimoHistorico;

    public void atualizarTela() {

        if (desenvolvedorLogado != null) {
            jLabel1.setText("Bom trabalho, " + desenvolvedorLogado.getNome());
            trabalhoAtual = ModuloGestaoProjeto.trabalhoAtivoPorDesenvolvedor(desenvolvedorLogado);
            if (trabalhoAtual != null) {
                requisitoAtual = trabalhoAtual.getRequisito();
            }
            String[] reqStrings = getListaDeRequisitos();
            DefaultListModel listaReq = new DefaultListModel();
            int i = 0;
            for (String req : reqStrings) {
                listaReq.addElement(req);
                i++;
            }
            //  jListRequisitos = new JList(listaReq);
            jListRequisitos.setModel(listaReq);

        }
        if (trabalhoAtual != null) {
            if (ultimoHistorico == null) {
                ultimoHistorico = trabalhoAtual.getInicio();
            }
            jScrollRequisitos.setVisible(false);
            jButunTrabalhar.setVisible(false);
            jTextHistorico.setText(trabalhoAtual.getHistorico());
            jLabel2.setSize(400, 20);
            jLabel2.setMaximumSize(new Dimension(400, 100));
            jLabel2.setText("Você está trabalhando em " + trabalhoAtual.getRequisito().getNome());
            jTextHistorico.setVisible(true);
            jLabel2.setVisible(true);
            jScrollPaneDetalhesDesenvolvimento.setVisible(true);
            jScrollPaneDetalhesDesenvolvimento.setVisible(true);
            jScrollPaneDetalhesDesenvolvimento.setSize(100, 400);
            jLabelTempoTrabalhado.setAlignmentY(200);
            jButtonNovoHistorico.setVisible(true);
            jButtonEncerrar.setVisible(true);

        } else {
            jButtonNovoHistorico.setVisible(false);
            jButtonEncerrar.setVisible(false);
            jLabel2.setVisible(false);
            jScrollRequisitos.setVisible(true);
            jListRequisitos.setVisible(true);
            jButunTrabalhar.setVisible(true);
            jTextHistorico.setVisible(false);
            jScrollPaneDetalhesDesenvolvimento.setVisible(false);
        }

    }

    public void Logar() {
        while (desenvolvedorLogado == null) {
            Object registro = UtilSBPersistencia.getRegistroByJPQL("SELECT d from Desenvolvedor d where email='" + javax.swing.JOptionPane.showInputDialog("Digite seu e-mail") + "' ", Desenvolvedor.class, SBProjectClient.getEMServidor());
            if (registro != null) {
                desenvolvedorLogado = (Desenvolvedor) registro;
                trabalhoAtual = ModuloGestaoProjeto.trabalhoAtivoPorDesenvolvedor(desenvolvedorLogado);
            }
        }
        atualizarTela();
    }

    public void comecarTrabalho() {

        try {
            requisitoAtual = requisitoByNome.get(jListRequisitos.getSelectedValue());
            if (desenvolvedorLogado == null) {
                Logar();
            }
            trabalhoAtual = ModuloGestaoProjeto.iniciarTrabalho(desenvolvedorLogado, requisitoAtual);
            String diretorio = trabalhoAtual.getRequisito().getProjeto().getPastaDoProjetoSource();
        } catch (Exception e) {
            SBCore.RelatarErro(ErroSB.TIPO_ERRO.ALERTA_PROGRAMADOR, "Erro inicio de trabalho", e);
        }
        atualizarTela();
    }

    public void adcionarHistorico() {
        if (trabalhoAtual == null) {
            JOptionPane.showMessageDialog(null, " Você não iniciou nenhu trabalho");

            return;
        }
        trabalhoAtual = ModuloGestaoProjeto.AtualizarHistorioDeTrabalho(trabalhoAtual, javax.swing.JOptionPane.showInputDialog("Descreva o histórico"));
        ultimoHistorico = new Date();
        atualizarTela();
    }

    public void comitar() {
        Runtime rt = Runtime.getRuntime();
        try {

            rt.exec("TortoiseProc.exe /command:commit /path:\"" + requisitoAtual.getProjeto().getPastaDoProjetoSource() + "\" /notempfile /logmsg:\"" + jTextHistorico.getText() + "\" /closeonend:3");
            //     UtilSBSVN.commitaTudo("http://sbbh.ddns.info:8000/usvn/svn/SBProject/trunk","/home/projetosSB/projetos/SBProject","SBAdmin"  ,"123321@aA","Commit teste");
        } catch (IOException ex) {
            Logger.getLogger(TrabalharNoProjeto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void encerrarTrabalho() {
        if (trabalhoAtual == null) {
            JOptionPane.showMessageDialog(null, " Você não iniciou nenhu trabalho");

            return;
        }
        ModuloGestaoProjeto.finalizarTrabalho(trabalhoAtual);

        trabalhoAtual = null;
        comitar();
        atualizarTela();
    }

    /**
     * Creates new form TrabalharNoProjeto
     */
    public TrabalharNoProjeto() {
        initComponents();
    }

    public String[] getListaDeRequisitos() {
        if (desenvolvedorLogado == null) {
            return new String[0];
        }
        List<Projeto> projetos = desenvolvedorLogado.getProjetos();
        List<Requisito> lista = new ArrayList<>();
        for (Projeto proj : projetos) {
            List<Requisito> reqs = proj.getRequisitosProximaVersao();
            for (Requisito req : reqs) {
                System.out.println("req disponivel:" + req.getNome());
                lista.add(req);
            }
        }

        String[] resposta = new String[lista.size()];
        int i = 0;

        requisitoByNome = new HashMap<>();

        for (Requisito rec : lista) {
            requisitoByNome.put(rec.getNome(), rec);
            resposta[i] = rec.getNome();
            i++;
        }
        return resposta;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButunTrabalhar = new javax.swing.JButton();
        jButtonCommit = new javax.swing.JButton();
        jScrollRequisitos = new javax.swing.JScrollPane();
        jListRequisitos = new javax.swing.JList();
        jScrollPaneDetalhesDesenvolvimento = new javax.swing.JScrollPane();
        jTextHistorico = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButtonNovoHistorico = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButtonEncerrar = new javax.swing.JButton();
        jLabelTempoTrabalhado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("SBProject"); // NOI18N
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jButunTrabalhar.setText("TRABALHAR NESTE REQUISITO");
        jButunTrabalhar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButunTrabalharActionPerformed(evt);
            }
        });

        jButtonCommit.setText("REALIZAR COMMIT");
        jButtonCommit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCommitActionPerformed(evt);
            }
        });

        jListRequisitos.setName(""); // NOI18N
        jScrollRequisitos.setViewportView(jListRequisitos);
        jListRequisitos.getAccessibleContext().setAccessibleName("");

        jTextHistorico.setEditable(false);
        jTextHistorico.setColumns(20);
        jTextHistorico.setRows(5);
        jScrollPaneDetalhesDesenvolvimento.setViewportView(jTextHistorico);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 204, 0));
        jLabel1.setText("..");

        jButtonNovoHistorico.setText("NOVO HISTÓRICO DE TRABALHO");
        jButtonNovoHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoHistoricoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("..");

        jButtonEncerrar.setText("Terminar Tarefa e Realizar Commit");
        jButtonEncerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEncerrarActionPerformed(evt);
            }
        });

        jLabelTempoTrabalhado.setFont(new java.awt.Font("Courier New", 1, 48)); // NOI18N
        jLabelTempoTrabalhado.setForeground(new java.awt.Color(0, 153, 0));
        jLabelTempoTrabalhado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTempoTrabalhado.setText("..");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButunTrabalhar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollRequisitos)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPaneDetalhesDesenvolvimento, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jButtonNovoHistorico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                                                .addComponent(jButtonCommit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(jButtonEncerrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 2, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(jLabelTempoTrabalhado, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTempoTrabalhado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollRequisitos, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButunTrabalhar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPaneDetalhesDesenvolvimento, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonNovoHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonCommit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonEncerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        setSize(new java.awt.Dimension(845, 526));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCommitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCommitActionPerformed

        comitar();
    }//GEN-LAST:event_jButtonCommitActionPerformed

    private void jButunTrabalharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButunTrabalharActionPerformed

        try {
            if (jListRequisitos.getSelectedValue() == null) {

                System.out.println("Nenhum item foi selecionado");
                atualizarTela();
                return;

            }
            requisitoAtual = requisitoByNome.get(jListRequisitos.getSelectedValue());
            System.out.println("iniciando trabalho em " + requisitoAtual.getNome());
            trabalhoAtual = ModuloGestaoProjeto.iniciarTrabalho(desenvolvedorLogado, requisitoAtual);
            System.out.println("trabalho=" + trabalhoAtual.getHistorico());
            jTextHistorico.setText(trabalhoAtual.getHistorico());
        } catch (Exception e) {
            SBCore.RelatarErro(ErroSB.TIPO_ERRO.ALERTA_PROGRAMADOR, "Erro iniciando novo trabalho", e);
        }

        atualizarTela();

    }//GEN-LAST:event_jButunTrabalharActionPerformed

    private void jButtonNovoHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoHistoricoActionPerformed

        adcionarHistorico();
    }//GEN-LAST:event_jButtonNovoHistoricoActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        System.out.println("nada");
        this.setVisible(false);
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        //   System.out.println("CONFIGURANDO LOOKFELL");
        //    UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        //    for (UIManager.LookAndFeelInfo info : infos) {
        //        System.out.println(info.getName());
        //        if (info.getName().equals("Windows")) {
        //             System.out.println(info.getName());
        //            try {
        //                 UIManager.setLookAndFeel(info.getClassName());
        //             } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        //                Logger.getLogger("Errro setando" + TrabalharNoProjeto.class.getName()).log(Level.SEVERE, null, ex);
        //             }
        //              break;
        //         }
        //       }

    }//GEN-LAST:event_formWindowOpened

    private void jButtonEncerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEncerrarActionPerformed
        encerrarTrabalho();
    }//GEN-LAST:event_jButtonEncerrarActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCommit;
    private javax.swing.JButton jButtonEncerrar;
    private javax.swing.JButton jButtonNovoHistorico;
    public javax.swing.JButton jButunTrabalhar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabelTempoTrabalhado;
    private javax.swing.JList jListRequisitos;
    private javax.swing.JScrollPane jScrollPaneDetalhesDesenvolvimento;
    private javax.swing.JScrollPane jScrollRequisitos;
    private javax.swing.JTextArea jTextHistorico;
    // End of variables declaration//GEN-END:variables
}
