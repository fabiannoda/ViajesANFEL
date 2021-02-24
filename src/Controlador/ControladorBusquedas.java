/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.modelBusqueda;
import Vistas.busqueda;
import Vistas.ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author USER
 */
public class ControladorBusquedas implements ActionListener{
    public modelBusqueda model;
    public busqueda vist;
    public ventana vis2;

    public ControladorBusquedas(modelBusqueda model, busqueda vist, ventana vis2) {
        this.model = model;
        this.vist = vist;
        this.vis2 = vis2;
        vist.setVisible(true);
        vist.setLocationRelativeTo(null);
        vist.btnbus1.addActionListener(this);
        vist.btnbus2.addActionListener(this);
        vist.btnbus3.addActionListener(this);
        vist.btnbus4.addActionListener(this);
        vist.btnbus5.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==vist.btnbus1) {
            model.tipo(1);
            vis2.setVisible(true);
        }
        if (e.getSource()==vist.btnbus2) {
            model.tipo(2);
            vis2.setVisible(true);
        }
        if (e.getSource()==vist.btnbus3) {
            model.tipo(3);
            vis2.setVisible(true);
        }
        if (e.getSource()==vist.btnbus4) {
            model.tipo(4);
            vis2.setVisible(true);
        }
        if (e.getSource()==vist.btnbus5) {
            model.tipo(5);
            vis2.setVisible(true);
        }
    }
    
}
