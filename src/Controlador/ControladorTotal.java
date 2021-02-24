/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Vistas.*;
import Modelo.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


/**
 *
 * @author USER
 */
public class ControladorTotal implements ActionListener{
    public ingreso ingres;
    public modelIngres model;

    public ControladorTotal(ingreso ingres,modelIngres model ) {
        this.ingres = ingres;
        this.model = model;
        ingres.setLocationRelativeTo(null);
        ingres.setVisible(true);
        ingres.setBackground(Color.white);
        ingres.ingresa.setBackground(new Color(220, 232, 217));
        ingres.ingresa.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==ingres.ingresa) {    
           String user=ingres.txtuser.getText();
           String pass=ingres.txtcontra.getText();
           int regis=model.probar(user, pass);
           if (regis==1) {
               ControladorMenu.pass=pass;
               ControladorMenu.user=user;
                menu men=new menu();
                ControladorMenu control=new ControladorMenu(men);
                ingres.dispose();
           }else{
               JOptionPane.showMessageDialog(null, "Usuario y contraseña incorrectos");
               ingres.txtcontra.setText("");
               ingres.txtuser.setText("");
           }
           
        }
        
    }
    
    
    
}
