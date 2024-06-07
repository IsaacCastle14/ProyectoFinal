/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Controller;

import com.mycompany.proyectofinal.Model.MethodsApi;
import com.mycompany.proyectofinal.View.LoginGUI;
import com.mycompany.proyectofinal.View.PanelDatos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author josei
 */
public class LoginController implements ActionListener {

    private LoginGUI loginGUI;
    private PanelDatos panelDatos;
    private MethodsApi methodsApi;

    public LoginController() {
        loginGUI = new LoginGUI();
        methodsApi = new MethodsApi();
        panelDatos = loginGUI.getPanelDatos();
        panelDatos.listen(this);
        loginGUI.listen(this);
        loginGUI.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Login":
                
                try {
                methodsApi.getApiData("http://localhost:8080/login/allUsers");
                if (methodsApi.searchUser(panelDatos.txtUser.getText(), panelDatos.txtPassword.getText())) {
                    System.out.println("login Correcto");
                } else {
                    System.out.println("Usuario o contraseñass Incorrectos");
                }
            } catch (Exception error) {
                System.out.print(error);
                //label error concexion api
            }

            break;

            case "Exit":
                System.exit(0);
                break;

        }
    }

}
