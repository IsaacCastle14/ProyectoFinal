/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Controller;

import com.mycompany.proyectofinal.Model.MethodsApi;
import com.mycompany.proyectofinal.View.LoginGUI;
import com.mycompany.proyectofinal.View.PanelDatos;
import com.mycompany.proyectofinal.View.RegisterGUI;
import com.mycompany.proyectofinal.View.RegisterPanel;
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
    private RegisterGUI registerGUI;
    private RegisterPanel registerPanel;

    public LoginController() {
        loginGUI = new LoginGUI();
        methodsApi = new MethodsApi();
        registerGUI = new RegisterGUI();
        registerPanel = registerGUI.getRegisterPanel();
        panelDatos = loginGUI.getPanelDatos();
        panelDatos.listen(this);
        loginGUI.listen(this);
        registerPanel.listen(this);
        loginGUI.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Login":
                
                try {
                methodsApi.getApiData("http://localhost:8080/usuario/allUsuario");
                if (methodsApi.searchUser(panelDatos.txtUser.getText(), panelDatos.txtPassword.getText())) {
                    new MainController();
                    loginGUI.dispose();
                } else {
                     ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                    System.out.println("Usuario o contraseñass Incorrectos");
                }
            } catch (Exception error) {
                System.out.print(error);
            }
            break;

            case "Register":
                registerGUI.setVisible(true);
                break;

            case "Back":
                registerGUI.setVisible(false);
                break;

            case "RegisterUser":
                if (registerPanel.isComplete()) {
                    if (String.valueOf(registerPanel.txtPassword.getPassword()).equals(String.valueOf(registerPanel.txtConfirmPassword.getPassword()))) {
                        if (registerPanel.txtEmail.getText().endsWith("@gmail.com")) {
                            try {
                                methodsApi.postApi("http://localhost:8080/usuario",
                                        registerPanel.txtUser.getText(),
                                        registerPanel.txtFirstName.getText(),
                                        registerPanel.txtLastName.getText(),
                                        registerPanel.txtEmail.getText(),
                                        String.valueOf(registerPanel.txtPassword.getPassword()),
                                        registerPanel.txtPhone.getText(),
                                        registerPanel.txtCarne.getText());
                                System.out.print(methodsApi.getCodigo());

                                if (methodsApi.getCodigo() >= 200 && methodsApi.getCodigo() <= 299) {
                                    System.out.println(" : Los datos fueron enviados satisfacotiramente");
                                    registerPanel.clean();
                                    registerGUI.setVisible(false);
                                } else {
                                    ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                                    System.out.println(" : Error de solicitud : " + methodsApi.getCodigo());
                                }
                            } catch (Exception error) {
                                System.out.println("error");
                            }
                        } else {
                            System.out.println("Formato de correo invalido");
                        }
                    } else {
                        System.out.println("Las contraseñas no coinciden");
                    }
                } else {
                    System.out.println(" : Datos incompletos");
                }
                break;

            case "Exit":
                System.exit(0);
                break;

        }
    }

}
