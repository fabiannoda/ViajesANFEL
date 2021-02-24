/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.modelAerolinea;
import Vistas.aerolinea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class ControladorAerolinea implements ActionListener{
    public modelAerolinea modelo;
    public aerolinea vis;

    public ControladorAerolinea(modelAerolinea modelo, aerolinea vista) {
        this.modelo = modelo;
        this.vis = vista;
        modelo.vista=vista;
        vis.setVisible(true);
        vis.setLocationRelativeTo(null);
        vis.txtid.setEnabled(false);
        modelo.llenar();
        try {
            modelo.llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCadena.class.getName()).log(Level.SEVERE, null, ex);
        }
        vista.btnactu.addActionListener(this);
        vista.btncance.addActionListener(this);
        vista.btneli.addActionListener(this);
        vista.btninsert.addActionListener(this);
        vista.btnvol.addActionListener(this);
        vista.btncance.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==vis.btninsert) {
            modelo.insert();
        }
        if (e.getSource()==vis.btnactu) {
            modelo.actuali(vis.txtid.getText());
        }
        if (e.getSource()==vis.btncance) {
            modelo.cance();
        }
        if (e.getSource()==vis.btneli) {
            modelo.eliminar(vis.txtid.getText());
        }
        if (e.getSource()==vis.btnvol) {
            vis.dispose();
        }
    }
}
