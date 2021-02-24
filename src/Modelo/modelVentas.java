/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controlador.conexion;
import Vistas.ventas;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class modelVentas {
    public int idagen=0;
    public ventas vis;
    private DefaultTableModel hotelTableModel;
    private String idFromRowSelected;
    
    private ResultSet fill() throws SQLException{
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String sql="SELECT idVentas,idFDestino,idFAgente,idFCliente,idFHotel FROM anfel.ventas";
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet resultado = pst.executeQuery();
        resultado.first();
        return resultado;
    }
    private DefaultTableModel getTableModel() {
        if (hotelTableModel==null) {
            hotelTableModel = new DefaultTableModel(new Object[]{"Identificación","Destino","Agente","Cliente","Hotel"}, 0);
        }
        return hotelTableModel;
    }
    public void llenarTabla() throws SQLException{
        ResultSet resultado = fill();
        int hola;
        do {                
            this.getTableModel().addRow(new Object []{resultado.getInt(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),resultado.getString(5)});
            hola=resultado.getInt(1);
        } while (resultado.next()); 
        hola+=1;
        vis.txtid.setText(String.valueOf(hola));
    }
    public void llenar(){
        vis.tblhot.setModel(getTableModel());
        vis.tblhot.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               ResultSet result=null;
               ResultSet res=null;
               ResultSet resulta=null;
               idFromRowSelected=String.valueOf(vis.tblhot.getModel().getValueAt(vis.tblhot.getSelectedRow(), 0));
                try {
                    result=encontrar(idFromRowSelected);
                    resulta=encontrar2(result.getString(1));
                    res=encontrar3(result.getString(2));
                    vis.txtid.setText(String.valueOf(vis.tblhot.getModel().getValueAt(vis.tblhot.getSelectedRow(), 0)));
                    vis.comdes.getModel().setSelectedItem(resulta.getString(1));
                    vis.txtagen.setText(res.getString(1)+res.getString(2));
                    vis.txtcli.setText(result.getString(3));
                    vis.btncance.setEnabled(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    public ResultSet search (String id) throws SQLException{
        String sql="SELECT idHotel FROM hotel where hotel.nombreHotel=?";
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        PreparedStatement pst=cn.prepareStatement(sql);
        pst.setString(1, id);
        pst.execute();
        ResultSet result = pst.getResultSet();
        result.next();
        return result;
    }
    public ResultSet encontrar3(String id) throws SQLException{
        String sql="SELECT nombreAgente, apellidoAgente FROM agenteDeViajes where agenteDeViajes.idAgente=?";
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        PreparedStatement pst=cn.prepareStatement(sql);
        pst.setString(1, id);
        pst.execute();
        ResultSet result = pst.getResultSet();
        result.next();
        return result; 
    }
    private ResultSet encontrar2(String id) throws SQLException{
        String sql="SELECT cuidadDestino FROM destino where destino.idDestino=?";
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        PreparedStatement pst=cn.prepareStatement(sql);
        pst.setString(1, id);
        pst.execute();
        ResultSet result = pst.getResultSet();
        result.next();
        return result;
    }
    private ResultSet encontrar(String id) throws SQLException{
        String sql="select idFDestino,idFAgente,idFCliente from ventas where ventas.idVentas=?";
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        PreparedStatement pst=cn.prepareStatement(sql);
        pst.setString(1, id);
        pst.execute();
        ResultSet result = pst.getResultSet();
        result.next();
        return result;
    }
    public ResultSet combo() throws SQLException{
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String sql="SELECT cuidadDestino FROM Anfel.destino";
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet resultado = pst.executeQuery();
        resultado.first();
        return resultado;
    }
    public void combo2() throws SQLException{
        ResultSet resultado=combo();
        ArrayList<String> h=new ArrayList<>();
        do {            
            h.add(resultado.getString(1));
        } while (resultado.next());
        String h2[]=new String[h.size()];
        for (int i = 0; i < h2.length; i++) {
            h2[i]=h.get(i);
        }
        vis.comdes.setModel(new javax.swing.DefaultComboBoxModel<>(h2));
    }
    private ResultSet hotel2(String id) throws SQLException{
        String sql="select nombreHotel from hotel where hotel.cuidadHotel=?";
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        PreparedStatement pst=cn.prepareStatement(sql);
        pst.setString(1, id);
        pst.execute();
        ResultSet result = pst.getResultSet();
        result.next();
        return result;
    }
    public void hotel1() throws SQLException {
        ResultSet resultado=hotel2((String) vis.comdes.getSelectedItem());
        ArrayList<String> h=new ArrayList<>();
        do {            
            h.add(resultado.getString(1));
        } while (resultado.next());
        String h2[]=new String[h.size()];
        for (int i = 0; i < h2.length; i++) {
            h2[i]=h.get(i);
        }
        vis.comho.setModel(new javax.swing.DefaultComboBoxModel<>(h2));
    }
    public void insert(){
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String id,cade,nomb,ciu,ho="",sql="";
        
        ResultSet set=null;
        try {
            set=search((String) vis.comho.getSelectedItem());
        } catch (SQLException ex) {
            Logger.getLogger(modelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ho=set.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(modelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(ho);
        id=vis.txtid.getText();
        cade=String.valueOf(vis.comdes.getSelectedIndex()+1);
        nomb=String.valueOf(idagen);
        ciu=vis.txtcli.getText();
        sql="insert into ventas (idVentas, idFDestino, idFAgente, idFCliente, idFHotel) values(?,?,?,?,?)";
        try {
            PreparedStatement pst=cn.prepareCall(sql);
            pst.setString(1, id);
            pst.setString(2, cade);
            pst.setString(3, nomb);
            pst.setString(4, ciu);
            pst.setString(5, ho);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null,"Venta registrada exitosamente");
                limpiar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Registro fallido");
        }
        try {
            DefaultTableModel modelo=(DefaultTableModel) vis.tblhot.getModel();
            int filas=vis.tblhot.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
        try {
            llenarTabla();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void actuali(String id){
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String cade,nomb,ciu,ho="",sql="";
        cade=String.valueOf(vis.comdes.getSelectedIndex()+1);
        nomb=String.valueOf(idagen);
        ciu=vis.txtcli.getText();
        ResultSet set=null;
        try {
            set=search((String) vis.comho.getSelectedItem());
            ho=set.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(modelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql="update ventas set idFDestino='"+cade+"', idFAgente='"+nomb+"', idFCliente='"+ciu+"', idFHotel='"+ho+"' where idVentas='"+id+"';";
        try {
            PreparedStatement pst=cn.prepareStatement(sql);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null, "Actualización exitosa de la Venta No. "+id+"");
                limpiar();
            }else{
               JOptionPane.showMessageDialog(null, "Venta no encontrada");
                limpiar(); 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            vaciar();
            llenar();
            llenarTabla();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo en llenado de tabla con BD"+e.getMessage());
        }
        vis.btncance.setEnabled(false);
    }
    public void cance(){
        limpiar();
        vaciar();
        llenar();
        try {
            llenarTabla();
        } catch (SQLException ex) {
            Logger.getLogger(modelHotel.class.getName()).log(Level.SEVERE, null, ex);
        }
        vis.btncance.setEnabled(false);
    }
    public void eliminar(String id){
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user, Controlador.ControladorMenu.pass);
        String sql="";
        sql="delete from ventas where "
                +"idVentas= '"+id+"';";
        try {
            PreparedStatement pst=cn.prepareStatement(sql);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null, "Venta No. "+id+" eliminada exitosamente");
                limpiar();
                
            }else{
                JOptionPane.showMessageDialog(null, "Venta no encontrada");
                limpiar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error conexion "+e.getMessage());
            limpiar();
        }
        try {
            vaciar();
            llenar();
            llenarTabla();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo en llenado de tabla con BD"+e.getMessage());
        } 
    }

    public void limpiar() {
        vis.txtid.setText("");
        vis.txtcli.setText("");
    }

    private void vaciar() {
        hotelTableModel=null;
    }
}
