/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.modelAgente;
import Vistas.agente;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class ControladorAgente implements ActionListener {

    public agente cli;
    public modelAgente model;

    public ControladorAgente(agente clien, modelAgente modelo) {
        this.cli = clien;
        this.model = modelo;
        model.client = cli;
        model.llenar();
        try {
            model.llenarTabla();
        } catch (Exception e) {
        }
        cli.setVisible(true);
        cli.btncance.setEnabled(false);
        cli.setLocationRelativeTo(null);
        cli.btnInsert.setBackground(new Color(184, 240, 238));
        cli.btncance.setBackground(new Color(184, 240, 238));
        cli.btnvol.setBackground(new Color(184, 240, 238));
        cli.btnactual.setBackground(new Color(184, 240, 238));
        cli.btnelimi.setBackground(new Color(184, 240, 238));
        cli.btnInsert.addActionListener(this);
        cli.btnactual.addActionListener(this);
        cli.txtape.addActionListener(this);
        cli.txtcorr.addActionListener(this);
        cli.txtdire.addActionListener(this);
        cli.txteda.addActionListener(this);
        cli.txtnac.addActionListener(this);
        cli.txtid.addActionListener(this);
        cli.txtnom.addActionListener(this);
        cli.txttel.addActionListener(this);
        cli.btnvol.addActionListener(this);
        cli.btncance.addActionListener(this);
        cli.btnelimi.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cli.txtid) {
            if (cli.txtid.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este parámetro no puede estar vacío");
            } else {
                if (model.isNumeric(cli.txtid.getText())) {
                    cli.txtnom.setEditable(true);
                    cli.txtid.transferFocus();
                    cli.btnelimi.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Este parámetro debe ser numérico");
                }
            }
        }
        if (e.getSource() == cli.txtnom) {
            if (cli.txtnom.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este parámetro no puede estar vacío");
            } else {
                if (model.isNumeric(cli.txtnom.getText())) {
                    JOptionPane.showMessageDialog(null, "Este parámetro no puede ser numérico");
                } else {
                    cli.txtape.setEditable(true);
                    cli.txtnom.transferFocus();
                }
            }
        }
        if (e.getSource() == cli.txtape) {
            if (cli.txtape.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este parámetro no puede estar vacío");
            } else {
                if (model.isNumeric(cli.txtape.getText())) {
                    JOptionPane.showMessageDialog(null, "Este parámetro no puede ser numérico");
                } else {
                    cli.txtnac.setEditable(true);
                    cli.txtape.transferFocus();
                }
            }
        }
        if (e.getSource() == cli.txtnac) {
            if (cli.txtnac.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este parámetro no puede estar vacío");
            } else {
                if (model.isNumeric(cli.txtnac.getText())) {
                    JOptionPane.showMessageDialog(null, "Este parámetro no puede ser numérico");
                } else {
                    cli.txteda.setEditable(true);
                    cli.txtnac.transferFocus();
                }
            }
        }
        if (e.getSource() == cli.txteda) {
            if (cli.txteda.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este parámetro no puede estar vacío");
            } else {
                if (model.isNumeric(cli.txteda.getText())) {
                    cli.txtdire.setEditable(true);
                    cli.txteda.transferFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Este parámetro debe ser numérico");
                }
            }
        }
        if (e.getSource() == cli.txtdire) {
            if (cli.txtdire.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este parámetro no puede estar vacío");
            } else {
                if (model.isNumeric(cli.txtdire.getText())) {
                    JOptionPane.showMessageDialog(null, "Este parámetro no puede ser numérico");
                } else {
                    cli.txtcorr.setEditable(true);
                    cli.txtdire.transferFocus();
                }
            }
        }
        if (e.getSource() == cli.txtcorr) {
            if (cli.txtcorr.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este parámetro no puede estar vacío");
            } else {
                if (model.isNumeric(cli.txtcorr.getText())) {
                    JOptionPane.showMessageDialog(null, "Este parámetro no puede ser numérico");
                } else {
                    cli.txttel.setEditable(true);
                    cli.txtcorr.transferFocus();

                }
            }
        }
        if (e.getSource() == cli.txttel) {
            if (cli.txttel.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este parámetro no puede estar vacío");
            } else {
                cli.btnInsert.setEnabled(true);

            }
        }
        if (e.getSource() == cli.btnInsert) {
            model.insertar();
            model.limpiar();
            model.bloquear();
        }
        if (e.getSource() == cli.btncance) {
            model.limpiar();
            model.bloquear();
        }
        if (e.getSource() == cli.btnactual) {
            String ident = cli.txtid.getText();
            model.actualizar(ident);
        }
        if (e.getSource() == cli.btnelimi) {
            String ident = cli.txtid.getText();
            model.eliminar(ident);
        }
        if (e.getSource() == cli.btnvol) {
            cli.dispose();
        }
    }
}
