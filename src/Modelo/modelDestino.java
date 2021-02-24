/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controlador.conexion;
import Vistas.destino;
import Vistas.hotel;
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
public class modelDestino {
    public destino vis;
    private DefaultTableModel hotelTableModel;
    private String idFromRowSelected;
    
    private ResultSet fill() throws SQLException{
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String sql="SELECT idDestino, idAerolinea , paisDestino, cuidadDestino FROM Anfel.destino";
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet resultado = pst.executeQuery();
        resultado.first();
        return resultado;
    }
    private DefaultTableModel getTableModel() {
        if (hotelTableModel==null) {
            hotelTableModel = new DefaultTableModel(new Object[]{"Identificación","Aerolinea","Pais","Ciudad"}, 0);
        }
        return hotelTableModel;
    }
    public void llenarTabla() throws SQLException{
        ResultSet resultado = fill();
        int hola;
        do {                
            this.getTableModel().addRow(new Object []{resultado.getInt(1),resultado.getString(2),resultado.getString(3),resultado.getString(4)});
            hola=resultado.getInt(1);
        } while (resultado.next()); 
        hola+=1;
        vis.txtid.setText(String.valueOf(hola));
    }
    public void llenar(){
        vis.tbldes.setModel(getTableModel());
        vis.tbldes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               ResultSet result=null;
               ResultSet resulta=null;
               idFromRowSelected=String.valueOf(vis.tbldes.getModel().getValueAt(vis.tbldes.getSelectedRow(), 0));
                try {
                    result=encontrar(idFromRowSelected);
                    resulta=encontrar2(result.getString(1));
                    vis.txtid.setText(String.valueOf(vis.tbldes.getModel().getValueAt(vis.tbldes.getSelectedRow(), 0)));
                    vis.comaero.getModel().setSelectedItem(resulta.getString(1));
                    vis.compai.getModel().setSelectedItem(result.getString(2));
                    vis.txtciu.setText(result.getString(3));
                    vis.btncance.setEnabled(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    private ResultSet encontrar2(String id) throws SQLException{
        String sql="SELECT nombreAerolinea FROM aerolinea where aerolinea.idAerolinea=?";
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
        String sql="select idAerolinea, paisDestino, cuidadDestino from destino where destino.idDestino=?";
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
        String sql="SELECT nombreAerolinea FROM Anfel.aerolinea";
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
        vis.comaero.setModel(new javax.swing.DefaultComboBoxModel<>(h2));
    }
    public void insert(){
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String id,cade,pais,ciu,sql="";
        id=vis.txtid.getText();
        cade=String.valueOf(vis.comaero.getSelectedIndex()+1);
        pais=(String) vis.compai.getSelectedItem();
        ciu=vis.txtciu.getText();
        sql="call insertarDestino(?,?,?,?)";
        try {
            PreparedStatement pst=cn.prepareCall(sql);
            pst.setString(1, id);
            pst.setString(2, cade);
            pst.setString(3, pais);
            pst.setString(4, ciu);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null,"Destino registrado exitosamente");
                limpiar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Registro fallido");
        }
        try {
            DefaultTableModel modelo=(DefaultTableModel) vis.tbldes.getModel();
            int filas=vis.tbldes.getRowCount();
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
        String cade,pais,ciu,sql="";
        cade=String.valueOf(vis.comaero.getSelectedIndex()+1);
        pais=(String) vis.compai.getSelectedItem();
        ciu=vis.txtciu.getText();
        sql="update destino set idAerolinea ='"+cade+"', paisDestino='"+pais+"', cuidadDestino='"+ciu+"' where idDestino='"+id+"';";
        try {
            PreparedStatement pst=cn.prepareStatement(sql);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null, "Actualización exitosa del destino No. "+id+"");
                limpiar();
            }else{
               JOptionPane.showMessageDialog(null, "Destino no encontrado");
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
        sql="delete from destino where "
                +"idDestino= '"+id+"';";
        try {
            PreparedStatement pst=cn.prepareStatement(sql);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null, "Destino No. "+id+" eliminado exitosamente");
                limpiar();
                
            }else{
                JOptionPane.showMessageDialog(null, "Destino no encontrado");
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
        vis.txtciu.setText("");
    }

    private void vaciar() {
        hotelTableModel=null;
    }
    
}
