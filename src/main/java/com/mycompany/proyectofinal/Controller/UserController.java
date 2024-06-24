/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Controller;

import com.mycompany.proyectofinal.Model.MethodsApiCareers;
import com.mycompany.proyectofinal.Model.MethodsApiCourse;
import com.mycompany.proyectofinal.Model.MethodsApiStudyProgram;
import com.mycompany.proyectofinal.Model.MethodsApiUsers;
import com.mycompany.proyectofinal.Model.PerfilModel;
import com.mycompany.proyectofinal.Model.UserModel;
import com.mycompany.proyectofinal.View.MainGUI;
import com.mycompany.proyectofinal.View.MessageFrame;
import com.mycompany.proyectofinal.View.ModalCareerAdd;
import com.mycompany.proyectofinal.View.ModalCareerPatch;
import com.mycompany.proyectofinal.View.ModalCourseAdd;
import com.mycompany.proyectofinal.View.ModalCoursePatch;
import com.mycompany.proyectofinal.View.ModalStudyProgramAdd;
import com.mycompany.proyectofinal.View.ModalStudyProgramPatch;
import com.mycompany.proyectofinal.View.ModalUserPatch;
import com.mycompany.proyectofinal.View.PanelCareer;
import com.mycompany.proyectofinal.View.PanelCourse;
import com.mycompany.proyectofinal.View.PanelMain;
import com.mycompany.proyectofinal.View.PanelStudyProgram;
import com.mycompany.proyectofinal.View.PanelUsers;
import com.mycompany.proyectofinal.View.PanelUsersAdminUsers;
import com.mycompany.proyectofinal.View.UserGUI;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josh
 */
public class UserController implements ActionListener, MouseListener {

    UserGUI userGUI;
    PanelUsers panelUsers;
    PanelUsersAdminUsers panelUsersAdminUsers;
    MethodsApiUsers methodsApiUsers;
    ModalUserPatch modalUserPatch;
    MessageFrame messageFrame;

    public UserController(PanelUsers panelUsers, PanelUsersAdminUsers panelUsersAdminUsers,
            MethodsApiUsers methodsApiUsers, ModalUserPatch modalUserPatch, MessageFrame messageFrame) {
        
        this.panelUsers = panelUsers;
        this.panelUsersAdminUsers = panelUsersAdminUsers;
        this.methodsApiUsers = methodsApiUsers;
        this.modalUserPatch = modalUserPatch;
        this.messageFrame = messageFrame;
        panelUsers.listen(this);
        this.panelUsersAdminUsers.listen(this);
        this.modalUserPatch.listen(this);
        this.panelUsersAdminUsers.ListenMouse(this);
        userGUI = new UserGUI();
        userGUI.setVisible(true);
        panelUsers = userGUI.getPanelUsers();
        panelUsersAdminUsers = userGUI.getPanelUsersAdminUsers();
        panelUsersAdminUsers.ListenMouse(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Users":
                if (methodsApiUsers.getUserTemp().getListaPerfil().isEmpty()) {
                    userGUI.tbPanel.setSelectedIndex(0);
                    panelUsers.lbUser.setText(methodsApiUsers.getUserTemp().getUser());

                } else if (UserModel.verficarPerfiles(methodsApiUsers.getUserTemp()).equals("Profesor")
                        || UserModel.verficarPerfiles(methodsApiUsers.getUserTemp()).equals("Administrador")) {
                    userGUI.tbPanel.setSelectedIndex(5);
                    panelUsersAdminUsers.lbUser.setText(methodsApiUsers.getUserTemp().getUser());

                    try {
                        methodsApiUsers.getApiData("http://localhost:8080/usuario/allUsuario");
                        panelUsersAdminUsers.setTable(UserModel.HEADER_STUDENTS, methodsApiUsers.getMatrix());
                    } catch (Exception error) {
                        System.out.print(error);
                    }
                } else {
                    userGUI.tbPanel.setSelectedIndex(4);
                    messageFrame.setVisible(true);
                    Timer timer = new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            messageFrame.setVisible(false);
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
                break;

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

                        userGUI.tbPanel.setSelectedIndex(4);
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

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
