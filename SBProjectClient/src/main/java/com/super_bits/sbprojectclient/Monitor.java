/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.super_bits.sbprojectclient;

import com.super_bits.modulosSB.Persistencia.dao.UtilSBPersistencia;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Salvio
 */
public class Monitor extends Thread {

    public enum AcaoDeTray {

        ABRIRMONITOR, HISTORICO, FECHARMONITOR, COMMIT, ENCERRAR_TRABALHO, MOSTRAR_DICAS
    }

    public enum EstadoTela {

        NAOIDENTIFICADO, AGUARDANDO_TRABALHO, TRABALHANDO
    }

    public TrabalharNoProjeto tela = new TrabalharNoProjeto();
    public boolean sistemaAberto = true;
    public boolean sistemaIniciando = true;
    public Timer relogio;
    public TrayIcon trayIcon;
    private int segundos = 0;

    class AtualizaHorarioTask extends TimerTask {

        @Override
        public void run() {
            segundos++;
            if (segundos > 60) {
                segundos = 0;
            }
            if ((segundos % 10) == 0) {
                bancoUP();
            }
            atualizarRelogio();

        }

    }

    public synchronized void aguardarInicializacao() {
        while (sistemaIniciando) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void bancoUP() {
        UtilSBPersistencia.getRegistroBySQL("select now()");
    }

    public void atualizarRelogio() {

        if (tela.trabalhoAtual != null) {
            long diferenca = (new Date().getTime() - tela.trabalhoAtual.getInicio().getTime());
            long tempoUltimoHistorico = (new Date().getTime() - tela.ultimoHistorico.getTime());
            Date dtDif = new Date(diferenca);

            long horasUltimoHistorico = tempoUltimoHistorico / (60 * 60 * 1000) % 24;

            long diffSeconds = diferenca / 1000 % 60;
            long diffMinutes = diferenca / (60 * 1000) % 60;
            long diffHours = diferenca / (60 * 60 * 1000) % 24;
            long diffDays = diferenca / (24 * 60 * 60 * 1000);

            SimpleDateFormat formato = new SimpleDateFormat(diffHours + " : " + diffMinutes + " : " + diffSeconds);
            tela.jLabelTempoTrabalhado.setText(formato.format(dtDif));
            if (horasUltimoHistorico > 0 && diffSeconds % 10 == 0) {
                trayIcon.displayMessage("Uma hora sem relatar o Historico", " Por favor, relate agora com uma breve descrição, oq foi desenvolvido na ultima hora", TrayIcon.MessageType.INFO);
            }
        } else {
            tela.jLabelTempoTrabalhado.setText("");
        }

    }

    public MouseListener getMouseTray() {
        return new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    System.out.println(e.getSource().toString());
                    abreJanela();

                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    public ActionListener getAcaoTray(AcaoDeTray pacao) {

        switch (pacao) {
            case ABRIRMONITOR:
                return new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        abreJanela();
                    }
                };

            case FECHARMONITOR:
                return new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        fecharSistema();
                    }
                };

            case HISTORICO:
                return new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        tela.adcionarHistorico();
                    }
                };

            case COMMIT:
                return new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        tela.comitar();
                    }
                };

            case ENCERRAR_TRABALHO:
                return new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tela.encerrarTrabalho();
                    }
                };
            case MOSTRAR_DICAS:
                return new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Dicas mostraDicas = new Dicas();

                    }
                };
        }
        return null;
    }

    public void abreJanela() {
        if (tela == null) {
            tela = new TrabalharNoProjeto();
        }
        tela.setVisible(true);
    }

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        tela = new TrabalharNoProjeto();
        sistemaIniciando = false;
    }

    public Image getImagemTray() {
        try {
            Image icone = ImageIO.read(ClassLoader.getSystemResourceAsStream("timer.png"));
            return icone;
        } catch (IOException ex) {

            System.out.println("Erro Lendo Imagem");
            Logger.getLogger(SBProjectClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void mostraTray() {
        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "System Tray não é suportado neste sistema");
            return;
        }

        final PopupMenu popup = new PopupMenu();

        trayIcon = new TrayIcon(getImagemTray());
        final SystemTray tray = SystemTray.getSystemTray();

        // Create a pop-up menu components
        MenuItem dicas = new MenuItem("Atalhos");
        dicas.addActionListener(getAcaoTray(AcaoDeTray.MOSTRAR_DICAS));
        MenuItem abrirMonitor = new MenuItem("Abrir Monitor (2 Cliques no Icone)");
        abrirMonitor.addActionListener(getAcaoTray(AcaoDeTray.ABRIRMONITOR));
        MenuItem novoHistorico = new MenuItem("Adcionar Historico");
        novoHistorico.addActionListener(getAcaoTray(AcaoDeTray.HISTORICO));
        MenuItem exitItem = new MenuItem("Sair");
        exitItem.addActionListener(getAcaoTray(AcaoDeTray.FECHARMONITOR));
        MenuItem commit = new MenuItem("Salvar Codigo");
        commit.addActionListener(getAcaoTray(AcaoDeTray.COMMIT));
        MenuItem fecharEComitar = new MenuItem("Encerrar trabalho e Salvar Codigo");
        fecharEComitar.addActionListener(getAcaoTray(AcaoDeTray.ENCERRAR_TRABALHO));
        popup.add(dicas);
        popup.add(abrirMonitor);
        popup.addSeparator();
        popup.add(novoHistorico);
        popup.add(commit);
        popup.add(fecharEComitar);
        popup.addSeparator();

        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);
        trayIcon.addMouseListener(getMouseTray());

        tela.Logar();
        relogio = new Timer();
        relogio.scheduleAtFixedRate(new AtualizaHorarioTask(), 1000, 1000);

        try {

            tray.add(trayIcon);

            //   trayIcon.getPopupMenu().show(tela, segundos, segundos);
            trayIcon.displayMessage("teste", "teste", TrayIcon.MessageType.INFO);
            //  abreJanela();
        } catch (AWTException e) {
            System.out.println(".Erro adcionando Tray" + e.getMessage());
        }
    }

    public synchronized void aguardarFechar() {

        while (sistemaAberto) {
            try {
                //  abreJanela();
                System.out.println("Abriu Janela");
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(SBProjectClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public synchronized void fecharSistema() {
        System.out.println("fechar");
        sistemaAberto = false;
        notifyAll();
    }

}
