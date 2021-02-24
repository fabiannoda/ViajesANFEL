/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import Controlador.*;
import java.sql.Connection;

/**
 *
 * @author USER
 */
public class modelIngres {
    public int probar(String user, String pass){
        int registro;
        conexion cc=new conexion();
        Connection cn=cc.conex(user, pass);
        if (cn==null) {
            System.out.println("Conexion fallida");
            registro=0;
        }else{
            System.out.println("Conexion exitosa");
            registro=1;
        }
        return registro;
    }
}
