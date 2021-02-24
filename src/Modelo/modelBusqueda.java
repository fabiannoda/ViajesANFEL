/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controlador.conexion;
import Vistas.ventana;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author USER
 */
public class modelBusqueda {

    private DefaultTableModel hotelTableModel;
    public ventana vis;

    public void tipo(int i) {
        hotelTableModel = null;
        switch (i) {
            case 1:
                vis.tblcli.setModel(getClienteDefaultTableModel1());
                try {
                    llenarTabla1();
                } catch (SQLException ex) {
                    Logger.getLogger(modelBusqueda.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 2:
                vis.tblcli.setModel(getClienteDefaultTableModel2());
                try {
                    llenarTabla2();
                } catch (SQLException ex) {
                    Logger.getLogger(modelBusqueda.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 3:
                vis.tblcli.setModel(getClienteDefaultTableModel3());
                try {
                    llenarTabla3();
                } catch (SQLException ex) {
                    Logger.getLogger(modelBusqueda.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 4:
                vis.tblcli.setModel(getClienteDefaultTableModel4());
                try {
                    llenarTabla4();
                } catch (SQLException ex) {
                    Logger.getLogger(modelBusqueda.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 5:
                vis.tblcli.setModel(getClienteDefaultTableModel5());
                try {
                    llenarTabla5();
                } catch (SQLException ex) {
                    Logger.getLogger(modelBusqueda.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    private DefaultTableModel getClienteDefaultTableModel1() {
        if (hotelTableModel == null) {
            hotelTableModel = new DefaultTableModel(new Object[]{"Identificación", "Nombre", "Apellido", "Fecha de Nac.", "Edad", "Direccion", "Correo", "Teléfono"}, 0);
        }
        return hotelTableModel;
    }

    public void llenarTabla1() throws SQLException {
        ResultSet resultado = fill1();
        do {
            this.getClienteDefaultTableModel1().addRow(new Object[]{resultado.getInt(1), resultado.getString(2), resultado.getString(3), resultado.getString(4), resultado.getInt(5), resultado.getString(6), resultado.getString(7), resultado.getString(8)});
        } while (resultado.next());
    }

    private ResultSet fill1() throws SQLException {
        conexion cc = new conexion();
        Connection cn = cc.conex(Controlador.ControladorMenu.user, Controlador.ControladorMenu.pass);
        String sql = "select * from clientes where edadCliente<18;";
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet resultado = pst.executeQuery();
        resultado.first();
        return resultado;
    }

    private void llenarTabla2() throws SQLException {
        ResultSet resultado = fill2();
        do {
            this.getClienteDefaultTableModel1().addRow(new Object[]{resultado.getString(1), resultado.getString(2)});
        } while (resultado.next());
    }

    private TableModel getClienteDefaultTableModel2() {
        if (hotelTableModel == null) {
            hotelTableModel = new DefaultTableModel(new Object[]{"Pais", "Total"}, 0);
        }
        return hotelTableModel;
    }

    private ResultSet fill2() throws SQLException {
        conexion cc = new conexion();
        Connection cn = cc.conex(Controlador.ControladorMenu.user, Controlador.ControladorMenu.pass);
        String sql = "select paisDestino, count(idFDestino) as total\n"
                + "from ventas, destino \n"
                + "where destino.idDestino=ventas.idFDestino group by ventas.idFDestino\n"
                + "order by total desc\n"
                + "limit 1;";
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet resultado = pst.executeQuery();
        resultado.first();
        return resultado;
    }

    private void llenarTabla3() throws SQLException {
        ResultSet resultado = fill3();
        do {
            this.getClienteDefaultTableModel1().addRow(new Object[]{resultado.getString(1), resultado.getString(2)});
        } while (resultado.next());
    }

    private TableModel getClienteDefaultTableModel3() {
        if (hotelTableModel == null) {
            hotelTableModel = new DefaultTableModel(new Object[]{"Nombre", "Total"}, 0);
        }
        return hotelTableModel;
    }

    private ResultSet fill3() throws SQLException {
        conexion cc = new conexion();
        Connection cn = cc.conex(Controlador.ControladorMenu.user, Controlador.ControladorMenu.pass);
        String sql = "select nombreCliente, count(idFCliente) as total\n"
                + "from ventas, clientes \n"
                + "where clientes.idCliente=ventas.idFCliente group by ventas.idFCliente\n"
                + "order by total desc\n"
                + "limit 1;";
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet resultado = pst.executeQuery();
        resultado.first();
        return resultado;
    }

    private void llenarTabla4() throws SQLException {
        ResultSet resultado = fill4();
        do {
            this.getClienteDefaultTableModel1().addRow(new Object[]{resultado.getString(1), resultado.getString(2)});
        } while (resultado.next());
    }

    private TableModel getClienteDefaultTableModel4() {
        if (hotelTableModel == null) {
            hotelTableModel = new DefaultTableModel(new Object[]{"Nombre", "Total"}, 0);
        }
        return hotelTableModel;
    }

    private ResultSet fill4() throws SQLException {
        conexion cc = new conexion();
        Connection cn = cc.conex(Controlador.ControladorMenu.user, Controlador.ControladorMenu.pass);
        String sql = "select nombreAgente, count(idFAgente) as total\n"
                + "from ventas, agenteDeViajes\n"
                + "where agenteDeViajes.idAgente=ventas.idFAgente group by ventas.idFAgente\n"
                + "order by total desc\n"
                + "limit 1";
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet resultado = pst.executeQuery();
        resultado.first();
        return resultado;
    }

    private void llenarTabla5() throws SQLException {
        ResultSet resultado = fill5();
        do {
            this.getClienteDefaultTableModel1().addRow(new Object[]{resultado.getString(1), resultado.getString(2),resultado.getInt(3)});
        } while (resultado.next());
    }

    private TableModel getClienteDefaultTableModel5() {
        if (hotelTableModel == null) {
            hotelTableModel = new DefaultTableModel(new Object[]{"Nombre", "Edad","Total"}, 0);
        }
        return hotelTableModel;
    }

    private ResultSet fill5() throws SQLException {
        conexion cc = new conexion();
        Connection cn = cc.conex(Controlador.ControladorMenu.user, Controlador.ControladorMenu.pass);
        String sql = "select nombreCliente, edadCliente, count(idFCliente) as total\n"
                + "from ventas, clientes \n"
                + "where clientes.idCliente=ventas.idFCliente and clientes.edadCliente>19 group by ventas.idFCliente\n"
                + "order by total desc;";
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet resultado = pst.executeQuery();
        resultado.first();
        return resultado;
    }
}
