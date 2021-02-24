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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class ControladorMenu implements ActionListener{
    private menu menu;
    public static String user="";
    public static String pass="";

    public ControladorMenu(menu menu){
        this.menu = menu;
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
        //-----------------------------------------------------MENU---------------------------------------------------------------
        menu.getContentPane().setBackground(Color.white);
        menu.btnagen.setBackground(new Color(220, 232, 217));
        menu.btnaero.setBackground(new Color(220, 232, 217));
        menu.btndes.setBackground(new Color(220, 232, 217));
        menu.btnclien.setBackground(new Color(220, 232, 217));
        menu.btnhotel.setBackground(new Color(220, 232, 217));
        menu.btncade.setBackground(new Color(220, 232, 217));
        menu.busqu.setBackground(new Color(220, 232, 217));
        menu.btnventas.setBackground(new Color(102, 179, 90));
        menu.btnclien.addActionListener((ActionListener) this);
        menu.btnhotel.addActionListener(this);
        menu.btncade.addActionListener(this);
        menu.btnagen.addActionListener(this);
        menu.btnaero.addActionListener(this);
        menu.btndes.addActionListener(this);
        menu.btnventas.addActionListener(this);
        menu.busqu.addActionListener(this);
        menu.busqu.addActionListener(this);
        menu.btnok.addActionListener(this);
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //--------------------------------------------MENU------------------------------------------------------------------
        if (e.getSource()==menu.btnclien) {
            cliente vistacli=new cliente();
            modelCliente modelocli=new modelCliente();
            controllerCliente control=new controllerCliente(vistacli, modelocli);
            control.cli=vistacli;
            control.model=modelocli;
            
        }
        if (e.getSource()==menu.btnhotel) {
            hotel vis=new hotel();
            modelHotel model=new modelHotel();
            ControladorHotel con=new ControladorHotel(model, vis);
            con.model=model;
            con.vista=vis;
            con.model.vis=vis;
        }
        if (e.getSource()==menu.btncade) {
            cadena vis=new cadena();
            modelCadena mod=new modelCadena();
            ControladorCadena cont=new ControladorCadena(mod, vis);
            cont.modelo=mod;
            cont.vis=vis;
            cont.modelo.vista=vis;
        }
        if (e.getSource()==menu.btnagen) {
            agente vis=new agente();
            modelAgente mod=new modelAgente();
            ControladorAgente cont=new ControladorAgente(vis, mod);
            cont.cli=vis;
            cont.model=mod;
            cont.model.client=vis;
        }
        if (e.getSource()==menu.btnaero) {
            aerolinea vis=new aerolinea();
            modelAerolinea mod=new modelAerolinea();
            ControladorAerolinea cont=new ControladorAerolinea(mod, vis);
            cont.modelo=mod;
            cont.vis=vis;
            cont.modelo.vista=vis;
        }
        if (e.getSource()==menu.btndes) {
            destino vis=new destino();
            modelDestino model=new modelDestino();
            ControladorDestino con=new ControladorDestino(model, vis);
            con.model=model;
            con.vista=vis;
            con.model.vis=vis;
        }
        if (e.getSource()==menu.btnventas) {
            ventas vis=new ventas();
            modelVentas model=new modelVentas();
            ControladorVentas con=new ControladorVentas(model, vis);
            con.model=model;
            con.vista=vis;
            con.model.vis=vis;
        }
        if (e.getSource()==menu.busqu) {
            busqueda vis=new busqueda();
            modelBusqueda mode=new modelBusqueda();
            ventana vist=new ventana();
            ControladorBusquedas cont=new ControladorBusquedas(mode, vis, vist);
            mode.vis=vist;
        }
        if (e.getSource()==menu.btnok) {
            try {
                conexion cc=new conexion();
                Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
                int h=Integer.parseInt((String) menu.comboh.getSelectedItem());
                String sql="select calificacion("+h+");";
                PreparedStatement pst = cn.prepareStatement(sql);
                ResultSet resultado = pst.executeQuery();
                resultado.first();
                JOptionPane.showMessageDialog(null, "Muchas Gracias, nuestro promedio de calificación es de: "+resultado.getString(1));
            } catch (SQLException ex) {
                Logger.getLogger(ControladorMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
