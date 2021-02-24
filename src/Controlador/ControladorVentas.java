/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.modelVentas;
import Vistas.ventas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class ControladorVentas implements ActionListener{
    public modelVentas model;
    public ventas vista;

    public ControladorVentas(modelVentas model, ventas vis) {
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
        String id=JOptionPane.showInputDialog(null, "Señor Agente, ingrese su numero de identificación");
        ResultSet res=null;
        model.idagen=Integer.valueOf(id);
        try {
            res= model.encontrar3(id);
            vis.txtagen.setEditable(false);
            vis.txtagen.setText(res.getString(1));
            vis.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Identificacion no encontrada, intente de nuevo");
        }        
        vis.setLocationRelativeTo(null);
        vis.btnactu.addActionListener(this);
        vis.btncance.addActionListener(this);
        vis.btneli.addActionListener(this);
        vis.btninsert.addActionListener(this);
        vis.btnvol.addActionListener(this);
        vis.busca.addActionListener(this);
        vis.txtid.setEnabled(false);
        vis.btncance.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==vista.btninsert) {
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
        if (e.getSource()==vista.busca) {
            try {
                model.hotel1();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
