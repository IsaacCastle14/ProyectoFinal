/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Controller;

import com.mycompany.proyectofinal.View.LoginGUI;
import com.mycompany.proyectofinal.View.PanelDatos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author josei
 */
public class MainController implements ActionListener
{
    private LoginGUI loginGUI; 
    private PanelDatos panelDatos; 


    public MainController(){
        loginGUI = new LoginGUI(); 
        panelDatos = loginGUI.getPanelDatos(); 
        panelDatos.listen(this);
        loginGUI.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        switch(e.getActionCommand())
        {
            case"Login":
                System.out.println("Logiando");
                break; 
                
            case"":
                break; 
                
        }
    }
    
}
