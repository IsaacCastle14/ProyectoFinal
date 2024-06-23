/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Controller;

import com.mycompany.proyectofinal.Model.CareerModel;
import com.mycompany.proyectofinal.Model.MethodsApiCareers;
import com.mycompany.proyectofinal.Model.MethodsApiUsers;
import com.mycompany.proyectofinal.Model.PerfilModel;
import com.mycompany.proyectofinal.Model.UserModel;
import com.mycompany.proyectofinal.View.MainGUI;
import com.mycompany.proyectofinal.View.ModalUserPatch;
import com.mycompany.proyectofinal.View.PanelCareer;
import com.mycompany.proyectofinal.View.PanelUsers;
import com.mycompany.proyectofinal.View.PanelUsersAdminUsers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marco
 */
public class UsersController implements ActionListener, MouseListener {

    PanelUsers panelUsers;
    MethodsApiUsers methodsApiUsers;
    PanelUsersAdminUsers panelUsersAdminUsers;
    MainGUI mainGUI;
    PanelCareer panelCareer;
    MethodsApiCareers methodsApiCareers;
    ModalUserPatch modalUserPatch;

    public UsersController(PanelUsers panelUsersParam, MethodsApiUsers methodsApiUsersParam, PanelUsersAdminUsers panelUsersAdminUsersParam, MainGUI mainGUIParam, PanelCareer panelCareerParam, MethodsApiCareers methodsApiCareersParam) {
        panelUsers = panelUsersParam;
        methodsApiUsers = methodsApiUsersParam;
        panelUsersAdminUsers = panelUsersAdminUsersParam;
        mainGUI = mainGUIParam;
        panelCareer = panelCareerParam;
        methodsApiCareers = methodsApiCareersParam;
        modalUserPatch = new ModalUserPatch();

        mainGUI.listen(this);
        panelCareer.listen(this);
        panelUsers.listen(this);
        panelCareer.ListenMouse(this);
        modalUserPatch.listen(this);
        panelUsersAdminUsers.listen(this);
        panelUsersAdminUsers.ListenMouse(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Asociar":

                String mainProfile = panelUsers.cbPp.getSelectedItem().toString();
                String secondaryProfile = panelUsers.cbPs.getSelectedItem().toString();

                try {
                    List<PerfilModel> listPerfil = new ArrayList<>();
                    if (!mainProfile.equalsIgnoreCase("Ninguno") && !secondaryProfile.equalsIgnoreCase("Ninguno")) {
                        listPerfil = new ArrayList<>();
                        listPerfil.add(new PerfilModel(mainProfile, mainProfile));
                        listPerfil.add(new PerfilModel(secondaryProfile, secondaryProfile));
                        System.out.println("1: " + listPerfil.get(0) + "\n" + "2: " + listPerfil.get(1));
                    } else if (!mainProfile.equalsIgnoreCase("Ninguno")) {
                        listPerfil = new ArrayList<>();
                        listPerfil.add(new PerfilModel(mainProfile, mainProfile));
                    } else if (!secondaryProfile.equalsIgnoreCase("Ninguno")) {
                        listPerfil = new ArrayList<>();
                        listPerfil.add(new PerfilModel(secondaryProfile, secondaryProfile));
                    }

                    methodsApiUsers.patchApi("http://localhost:8080/usuario",
                            methodsApiUsers.getUserTemp().getId(),
                            methodsApiUsers.getUserTemp().getUser(),
                            methodsApiUsers.getUserTemp().getFirstName(),
                            methodsApiUsers.getUserTemp().getLastName(),
                            methodsApiUsers.getUserTemp().getEmail(),
                            methodsApiUsers.getUserTemp().getPassword(),
                            methodsApiUsers.getUserTemp().getPhone(),
                            methodsApiUsers.getUserTemp().getCarne(),
                            listPerfil);

                    System.out.print(methodsApiUsers.getCodigo());

                    if (methodsApiUsers.getCodigo() >= 200 && methodsApiUsers.getCodigo() <= 299) {
                        methodsApiUsers.getApiData("http://localhost:8080/usuario/allUsuario");
                        panelUsersAdminUsers.setTable(UserModel.HEADER_STUDENTS, methodsApiUsers.getMatrix());
                        methodsApiUsers.setUserTemp(new UserModel(methodsApiUsers.getUserTemp().getId(),
                                methodsApiUsers.getUserTemp().getUser(),
                                methodsApiUsers.getUserTemp().getFirstName(),
                                methodsApiUsers.getUserTemp().getLastName(),
                                methodsApiUsers.getUserTemp().getEmail(),
                                methodsApiUsers.getUserTemp().getPassword(),
                                methodsApiUsers.getUserTemp().getPhone(),
                                methodsApiUsers.getUserTemp().getCarne(),
                                listPerfil));

                        mainGUI.tbPanel.setSelectedIndex(4);
                        System.out.println(" : Los datos fueron enviados satisfacotiramente");
                    } else {
                        ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                        System.out.println(" : Error de solicitud : " + methodsApiUsers.getCodigo());
                    }
                } catch (Exception error) {
                    System.out.print(error);
                }
                break;

            case "PatchUser":
                System.out.println("patch");
                try {
                    String row[];
                    row = methodsApiUsers.getRowSelected();
                    modalUserPatch.setTextDefault(row[0]);
                    if (!modalUserPatch.txtUser.getText().isBlank()) {
                        modalUserPatch.setVisible(true);
                    }
                } catch (Exception error) {
                    System.out.println(error);
                    System.out.println("Seleccione una fila valida");
                }
                break;

            case "PatchDataUser":
                if (modalUserPatch.isComplete()) {

                    String mainProfileModal = modalUserPatch.cbPp.getSelectedItem().toString();
                    String secondaryProfileModal = modalUserPatch.cbPs.getSelectedItem().toString();

                    try {
                        List<PerfilModel> listPerfil = new ArrayList<>();
                        if (!mainProfileModal.equalsIgnoreCase("Ninguno") && !secondaryProfileModal.equalsIgnoreCase("Ninguno")) {
                            listPerfil = new ArrayList<>();
                            listPerfil.add(new PerfilModel(mainProfileModal, mainProfileModal));
                            listPerfil.add(new PerfilModel(secondaryProfileModal, secondaryProfileModal));
                            System.out.println("1: " + listPerfil.get(0) + "\n" + "2: " + listPerfil.get(1));
                        } else if (!mainProfileModal.equalsIgnoreCase("Ninguno")) {
                            listPerfil = new ArrayList<>();
                            listPerfil.add(new PerfilModel(mainProfileModal, mainProfileModal));
                        } else if (!secondaryProfileModal.equalsIgnoreCase("Ninguno")) {
                            listPerfil = new ArrayList<>();
                            listPerfil.add(new PerfilModel(secondaryProfileModal, secondaryProfileModal));
                        }

                        UserModel updateUser = methodsApiUsers.find(modalUserPatch.txtUser.getText());

                        methodsApiUsers.patchApi("http://localhost:8080/usuario",
                                updateUser.getId(),
                                updateUser.getUser(),
                                updateUser.getFirstName(),
                                updateUser.getLastName(),
                                updateUser.getEmail(),
                                updateUser.getPassword(),
                                updateUser.getPhone(),
                                updateUser.getCarne(),
                                listPerfil);

                        System.out.print(methodsApiUsers.getCodigo());

                        if (methodsApiUsers.getCodigo() >= 200 && methodsApiUsers.getCodigo() <= 299) {
                            System.out.println(" : Los datos fueron enviados satisfacotiramente");

                            methodsApiUsers.getApiData("http://localhost:8080/usuario/allUsuario");
                            panelUsersAdminUsers.setTable(UserModel.HEADER_STUDENTS, methodsApiUsers.getMatrix());
                            methodsApiUsers.setRowSelected(null);

                            modalUserPatch.clean();
                            modalUserPatch.setVisible(false);
                        } else {
                            ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                            System.out.println(" : Error de solicitud : " + methodsApiUsers.getCodigo());
                        }
                    } catch (Exception error) {
                        System.out.print(error);
                    }
                }
                break;

            case "Back":
                modalUserPatch.setVisible(false);
                modalUserPatch.clean();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if (panelUsersAdminUsers.isVisible()) {
                String[] studentArray;
                studentArray = panelUsersAdminUsers.getRow();
                methodsApiUsers.setRowSelected(studentArray);
                UserModel student = methodsApiUsers.find(studentArray[0]);
                methodsApiUsers.setSelect(student.getId());
                System.out.println(student.getId());
            }
        } catch (Exception error) {
            System.err.println(error);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
