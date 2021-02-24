/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;
import Modelo.*;
import Controlador.*;
import Vistas.*;
/**
 *
 * @author Angel Nodarse
 * @serial 1032507704
 * @author Felipe Uribe
 * @serial 1000970791
 * @since 07/11/2019
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ingreso ingres=new ingreso();
        modelIngres model=new modelIngres();
        ControladorTotal control=new ControladorTotal(ingres,model);
    }
    
}
