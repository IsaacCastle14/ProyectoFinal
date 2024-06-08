/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectofinal;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.mycompany.proyectofinal.Controller.LoginController;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author josei
 */


public class ProyectoFinal {

    public static void main(String[] args) {
         try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new LoginController(); 
    }
 
}
