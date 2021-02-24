/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controlador.conexion;
import Vistas.cadena;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class modelCadena {
    public cadena vista;
    private DefaultTableModel cadenaTableModel;
    private String idFromRowSelected;
    private ResultSet fill() throws SQLException{
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String sql="SELECT idCadena,nombreCadena FROM Anfel.cadena";
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet resultado = pst.executeQuery();
        resultado.first();
        return resultado;
    }
    private DefaultTableModel getTableModel() {
        if (cadenaTableModel==null) {
            cadenaTableModel = new DefaultTableModel(new Object[]{"Id","Nombre"}, 0);
        }
        return cadenaTableModel;
    }
    public void llenarTabla() throws SQLException{
        ResultSet resultado = fill();
        int hola;
        do {                
            this.getTableModel().addRow(new Object []{resultado.getInt(1),resultado.getString(2)});
            hola=resultado.getInt(1);
        } while (resultado.next()); 
        hola+=1;
        vista.txtid.setText(String.valueOf(hola));
    }
    public void llenar(){
        vista.tblcade.setModel(getTableModel());
        vista.tblcade.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               ResultSet result=null;
               idFromRowSelected=String.valueOf(vista.tblcade.getModel().getValueAt(vista.tblcade.getSelectedRow(), 0));
                try {
                    result=encontrar(idFromRowSelected);
                    vista.txtid.setText(String.valueOf(vista.tblcade.getModel().getValueAt(vista.tblcade.getSelectedRow(), 0)));
                    vista.txtnom.setText(result.getString(1));
                    vista.btncance.setEnabled(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
     private ResultSet encontrar(String id) throws SQLException{
        String sql="select nombreCadena from cadena where cadena.idCadena=?";
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        PreparedStatement pst=cn.prepareStatement(sql);
        pst.setString(1, id);
        pst.execute();
        ResultSet result = pst.getResultSet();
        result.next();
        return result;
    }
    public void insert(){
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String id,cade,sql="";
        id=vista.txtid.getText();
        cade=vista.txtnom.getText();
        sql="call insertarCadena(?,?)";
        try {
            PreparedStatement pst=cn.prepareCall(sql);
            pst.setString(1, id);
            pst.setString(2, cade);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null,"Cadena registrada exitosamente");
                limpiar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Registro fallido");
        }
        try {
            DefaultTableModel modelo=(DefaultTableModel) vista.tblcade.getModel();
            int filas=vista.tblcade.getRowCount();
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
        String cade,sql="";
        cade=vista.txtnom.getText();
        sql="update cadena set nombreCadena='"+cade+"' where idCadena='"+id+"';";
        try {
            PreparedStatement pst=cn.prepareStatement(sql);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null, "Actualización exitosa de la cadena No. "+id+"");
                limpiar();
            }else{
               JOptionPane.showMessageDialog(null, "Cadena no encontrada");
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
        vista.btncance.setEnabled(false);
    }
    public void cance(){
        limpiar();
        vaciar();
        llenar();
        try {
            llenarTabla();
        } catch (SQLException ex) {
        }
        vista.btncance.setEnabled(false);
    }
    public void eliminar(String id){
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user, Controlador.ControladorMenu.pass);
        String sql="";
        sql="delete from cadena where "
                +"idCadena= '"+id+"';";
        try {
            PreparedStatement pst=cn.prepareStatement(sql);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null, "Cadena No. "+id+" eliminado exitosamente");
                limpiar();
                
            }else{
                JOptionPane.showMessageDialog(null, "Cadena no encontrado");
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
    private void limpiar() {
        vista.txtnom.setText("");
    }
    private void vaciar(){
        cadenaTableModel=null;
    }
}