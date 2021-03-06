/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.modelDestino;
import Vistas.destino;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class ControladorDestino implements ActionListener{
    public modelDestino model;
    public destino vista;

    public ControladorDestino(modelDestino model, destino vis) {
        this.model = model;
        this.vista = vis;  
        model.vis=vista;
        model.llenar();
        try {
            model.llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            model.combo2();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
        vis.setVisible(true);
        vis.setLocationRelativeTo(null);
        vis.btnactu.addActionListener(this);
        vis.btncance.addActionListener(this);
        vis.btneli.addActionListener(this);
        vis.btninser.addActionListener(this);
        vis.btnvol.addActionListener(this);
        vis.txtid.setEnabled(false);
        vis.btncance.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==vista.btninser) {
            model.insert();
        }
        if (e.getSource()==vista.btnactu) {
            model.actuali(vista.txtid.getText());
        }
        if (e.getSource()==vista.btncance) {
            model.cance();
        }
        if (e.getSource()==vista.btnvol) {
            vista.dispose();
        }
        if (e.getSource()==vista.btneli) {
            model.eliminar(vista.txtid.getText());
        }
    }
    
}
