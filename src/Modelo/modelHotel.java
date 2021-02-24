/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controlador.conexion;
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
public class modelHotel {
    public hotel vis;
    private DefaultTableModel hotelTableModel;
    private String idFromRowSelected;
    
    private ResultSet fill() throws SQLException{
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String sql="SELECT idHotel,idCadena,nombreHotel,paisHotel,cuidadHotel,calificacionHotel,descripcionHotel FROM Anfel.hotel";
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet resultado = pst.executeQuery();
        resultado.first();
        return resultado;
    }
    private DefaultTableModel getTableModel() {
        if (hotelTableModel==null) {
            hotelTableModel = new DefaultTableModel(new Object[]{"Identificación","Cadena","Nombre","País","Ciudad","Calificación","Descripción"}, 0);
        }
        return hotelTableModel;
    }
    public void llenarTabla() throws SQLException{
        ResultSet resultado = fill();
        int hola;
        do {                
            this.getTableModel().addRow(new Object []{resultado.getInt(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),resultado.getString(5),resultado.getString(6),resultado.getString(7)});
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
               ResultSet resulta=null;
               idFromRowSelected=String.valueOf(vis.tblhot.getModel().getValueAt(vis.tblhot.getSelectedRow(), 0));
                try {
                    result=encontrar(idFromRowSelected);
                    resulta=encontrar2(result.getString(1));
                    vis.txtid.setText(String.valueOf(vis.tblhot.getModel().getValueAt(vis.tblhot.getSelectedRow(), 0)));
                    vis.comcade.getModel().setSelectedItem(resulta.getString(1));
                    vis.txtnomb.setText(result.getString(2));
                    vis.compais.getModel().setSelectedItem(result.getString(3));
                    vis.txtciu.setText(result.getString(4));
                    vis.comcali.getModel().setSelectedItem(result.getString(5));
                    vis.txtdesc.setText(result.getString(6));
                    vis.btncance.setEnabled(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    private ResultSet encontrar2(String id) throws SQLException{
        String sql="SELECT nombreCadena FROM cadena where cadena.idCadena=?";
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
        String sql="select idCadena,nombreHotel,paisHotel,cuidadHotel,calificacionHotel,descripcionHotel from hotel where hotel.idHotel=?";
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
        String sql="SELECT nombreCadena FROM Anfel.cadena";
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
        vis.comcade.setModel(new javax.swing.DefaultComboBoxModel<>(h2));
    }
    public void insert(){
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String id,cade,nomb,pais,ciu,cali,des,sql="";
        id=vis.txtid.getText();
        cade=String.valueOf(vis.comcade.getSelectedIndex()+1);
        nomb=vis.txtnomb.getText();
        pais=(String) vis.compais.getSelectedItem();
        ciu=vis.txtciu.getText();
        cali=(String) vis.comcali.getSelectedItem();
        des=vis.txtdesc.getText();
        sql="call insertarHotel(?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst=cn.prepareCall(sql);
            pst.setString(1, id);
            pst.setString(2, cade);
            pst.setString(3, nomb);
            pst.setString(4, pais);
            pst.setString(5, ciu);
            pst.setString(6, cali);
            pst.setString(7, des);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null,"Hotel registrado exitosamente");
                limpiar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Registro fallido");
            System.out.println(e.getMessage());
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
        String cade,nomb,pais,ciu,cali,des,sql="";
        cade=String.valueOf(vis.comcade.getSelectedIndex()+1);
        nomb=vis.txtnomb.getText();
        pais=(String) vis.compais.getSelectedItem();
        ciu=vis.txtciu.getText();
        cali=(String) vis.comcali.getSelectedItem();
        des=vis.txtdesc.getText();
        sql="update hotel set idCadena='"+cade+"', nombreHotel='"+nomb+"', paisHotel='"+pais+"', cuidadHotel='"+ciu+"', calificacionHotel='"+cali+"', descripcionHotel='"+des+"' where idHotel='"+id+"';";
        try {
            PreparedStatement pst=cn.prepareStatement(sql);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null, "Actualización exitosa del hotel No. "+id+"");
                limpiar();
            }else{
               JOptionPane.showMessageDialog(null, "Hotel no encontrado");
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
        sql="delete from hotel where "
                +"idHotel= '"+id+"';";
        try {
            PreparedStatement pst=cn.prepareStatement(sql);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null, "Hotel No. "+id+" eliminado exitosamente");
                limpiar();
                
            }else{
                JOptionPane.showMessageDialog(null, "Hotel no encontrado");
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
        vis.txtdesc.setText("");
        vis.txtid.setText("");
        vis.txtnomb.setText("");
    }

    private void vaciar() {
        hotelTableModel=null;
    }
}
