/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import Controlador.conexion;
import Vistas.*;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author USER
 */
public class modelCliente {
    public cliente client;
    private DefaultTableModel clienteTableModel;
    private String idFromRowSlected="";
    public void bloquear(){
        client.txtape.setEditable(false);
        client.txtcorr.setEditable(false);
        client.txtdire.setEditable(false);
        client.txteda.setEditable(false);
        client.txtnac.setEditable(false);
        client.txtnom.setEditable(false);
        client.txttel.setEditable(false);
        client.btnInsert.setEnabled(false);
        client.btnactual.setEnabled(false);
        client.btnelimi.setEnabled(false);
        client.btncance.setEnabled(false);
        client.txtid.setEditable(true);
    }
    public void limpiar(){
        client.txtape.setText("");
        client.txtcorr.setText("");
        client.txtdire.setText("");
        client.txteda.setText("");
        client.txtid.setText("");
        client.txtnac.setText("");
        client.txtnom.setText("");
        client.txttel.setText("");
    }
    public void llenar(){
        limpiar();
        bloquear();
        client.tblclie.setModel(getClienteDefaultTableModel());
        client.tblclie.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ResultSet resultCliente = null;
                limpiar();
                idFromRowSlected = String.valueOf(client.tblclie.getModel().getValueAt(client.tblclie.getSelectedRow(), 0));
                client.txtape.setEditable(true);
                client.txtcorr.setEditable(true);
                client.txtdire.setEditable(true);
                client.txteda.setEditable(true);
                client.txtnac.setEditable(true);
                client.txtnom.setEditable(true);
                client.txttel.setEditable(true);
                client.txtid.setEditable(false);
                client.btnInsert.setEnabled(false);
                client.btnactual.setEnabled(true);
                client.btnelimi.setEnabled(true);
                try {
                    resultCliente=EncontrarCliente(idFromRowSlected);
                    client.txtid.setText(String.valueOf(client.tblclie.getModel().getValueAt(client.tblclie.getSelectedRow(), 0)));
                    client.txtnom.setText(resultCliente.getString(1));
                    client.txtape.setText(resultCliente.getString(2));
                    client.txtnac.setText(resultCliente.getString(3));
                    client.txteda.setText(resultCliente.getString(4));
                    client.txtdire.setText(resultCliente.getString(5));
                    client.txtcorr.setText(resultCliente.getString(6));
                    client.txttel.setText(resultCliente.getString(7));
                    client.btncance.setEnabled(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }  
        });
    }
    private ResultSet EncontrarCliente(String idRegistro) throws SQLException{
        String sql="select nombreCliente,apellidoCliente,fechaNacimientoCliente,edadCliente,direccionCliente,correoCliente,telefonoCliente from clientes where clientes.idCliente=?";
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        PreparedStatement pst=cn.prepareStatement(sql);
        pst.setString(1, idRegistro);
        pst.execute();
        ResultSet result = pst.getResultSet();
        result.next();
        return result;
    }
    private DefaultTableModel getClienteDefaultTableModel() {
        if (clienteTableModel==null) {
            clienteTableModel = new DefaultTableModel(new Object[]{"Identificación","Nombre","Apellido","Fecha de Nac.","Edad","Direccion","Correo","Teléfono"}, 0);
        }
        return clienteTableModel;
    }

    public void llenarTabla() throws SQLException{
        ResultSet resultado = fill();
        do {                
            this.getClienteDefaultTableModel().addRow(new Object []{resultado.getInt(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),resultado.getInt(5),resultado.getString(6),resultado.getString(7),resultado.getString(8)});
        } while (resultado.next());
        limpiar();
    }
    private ResultSet fill() throws SQLException{
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String sql="SELECT idCliente,nombreCliente,apellidoCliente,fechaNacimientoCliente,edadCliente,direccionCliente,correoCliente,telefonoCliente FROM Anfel.clientes";
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet resultado = pst.executeQuery();
        resultado.first();
        return resultado;
    }
    public void vaciar(){
        clienteTableModel=null;
    }
    //----------------------------------------------------INSERTAR---------------------------------------------------------------------
    public void insertar(){
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String id,nom,ape,fecha,edad,dire,corre,tel,sql="";
        id=client.txtid.getText();
        nom=client.txtnom.getText();
        ape=client.txtape.getText();
        fecha=client.txtnac.getText();
        edad=client.txteda.getText();
        dire=client.txtdire.getText();
        corre=client.txtcorr.getText();
        tel=client.txttel.getText();
        sql="insert into clientes (idCliente,nombreCliente,apellidoCliente,fechaNacimientoCliente,edadCliente,direccionCliente,correoCliente,telefonoCliente) values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst=cn.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, nom);
            pst.setString(3, ape);
            pst.setString(4, fecha);
            pst.setString(5, edad);
            pst.setString(6, dire);
            pst.setString(7, corre);
            pst.setString(8, tel);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null,"Cliente registrado exitosamente");
                limpiar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Registro fallido");
        }
        try {
            DefaultTableModel modelo=(DefaultTableModel) client.tblclie.getModel();
            int filas=client.tblclie.getRowCount();
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
    public void CancelarActu(){
        vaciar();
        try {
            llenarTabla();
            client.btncance.setEnabled(false);
        } catch (SQLException ex) {
            Logger.getLogger(modelCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean isNumeric (String cadena){
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado=true;
        } catch (NumberFormatException e) {
            resultado=false;
        }
        return resultado;
    }
    public void actualizar(String ident){
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user,Controlador.ControladorMenu.pass);
        String nom,ape,fecha,edad,dire,corre,tel,sql="";
        nom=client.txtnom.getText();
        ape=client.txtape.getText();
        fecha=client.txtnac.getText();
        edad=client.txteda.getText();
        dire=client.txtdire.getText();
        corre=client.txtcorr.getText();
        tel=client.txttel.getText();
        sql="update clientes set nombreCliente='"+nom+"', apellidoCliente='"+ape+"', fechaNacimientoCliente='"+fecha+"', edadCliente='"+edad+"', direccionCliente='"+dire+"', correoCliente='"+corre+"',telefonoCliente='"+tel+"' where idCliente='"+ident+"';";
        try {
            PreparedStatement pst=cn.prepareStatement(sql);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null, "Actualización exitosa del cliente No. "+ident+"");
                limpiar();
                bloquear();
            }else{
               JOptionPane.showMessageDialog(null, "Cliente no encontrado");
                limpiar();
                bloquear(); 
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo en comunicacion con BD"+e.getMessage());
        }
        try {
            vaciar();
            llenar();
            llenarTabla();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo en llenado de tabla con BD"+e.getMessage());
        }
    }
    public void eliminar(String ident){
        conexion cc=new conexion();
        Connection cn=cc.conex(Controlador.ControladorMenu.user, Controlador.ControladorMenu.pass);
        String sql="";
        sql="delete from clientes where "
                +"idCliente= '"+ident+"'";
        try {
            PreparedStatement pst=cn.prepareStatement(sql);
            int registro=pst.executeUpdate();
            if (registro>0) {
                JOptionPane.showMessageDialog(null, "Cliente No. "+ident+" eliminado exitosamente");
                limpiar();
                bloquear();
            }else{
                JOptionPane.showMessageDialog(null, "Cliente no encontrado");
                limpiar();
                bloquear();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error conexion "+e.getMessage());
            limpiar();
            bloquear();
        }
        try {
            vaciar();
            llenar();
            llenarTabla();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo en llenado de tabla con BD"+e.getMessage());
        }
                
    }
}

