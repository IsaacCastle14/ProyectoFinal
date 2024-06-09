/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Controller;

import com.mycompany.proyectofinal.Model.CareerModel;
import com.mycompany.proyectofinal.Model.MethodsApiCareers;
import com.mycompany.proyectofinal.View.MainGUI;
import com.mycompany.proyectofinal.View.ModalCareerAdd;
import com.mycompany.proyectofinal.View.ModalCareerPatch;
import com.mycompany.proyectofinal.View.PanelCareer;
import com.mycompany.proyectofinal.View.PanelCourse;
import com.mycompany.proyectofinal.View.PanelStudyProgram;
import com.mycompany.proyectofinal.View.PanelUsers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author josh
 */
public class MainController implements ActionListener, MouseListener {

    MainGUI mainGUI;
    PanelUsers panelUsers;
    PanelStudyProgram panelStudyProgram;
    PanelCareer panelCareer;
    PanelCourse panelCourse;
    MethodsApiCareers methodsApiCareers;
    ModalCareerAdd modalCareerAdd;
    ModalCareerPatch modalCareerPatch;

    public MainController() {
        mainGUI = new MainGUI();
        mainGUI.setVisible(true);
        panelUsers = mainGUI.getPanelUsers();
        modalCareerAdd = new ModalCareerAdd();
        modalCareerPatch = new ModalCareerPatch();
        panelStudyProgram = mainGUI.getPanelStudyProgram();
        methodsApiCareers = new MethodsApiCareers();
        panelCareer = mainGUI.getPanelCareer();
        panelCourse = mainGUI.getPanelCourse();
        mainGUI.listen(this);
        panelCareer.listen(this);
        modalCareerAdd.listen(this);
        modalCareerPatch.listen(this);
        panelCareer.ListenMouse(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Users":
                mainGUI.tbPanel.setSelectedIndex(0);
                break;

            case "Study Program":
                mainGUI.tbPanel.setSelectedIndex(1);
                break;
            //ModuleCareer//
            case "Careers":
                mainGUI.tbPanel.setSelectedIndex(2);

                try {
                    methodsApiCareers.getApiData("http://localhost:8080/carrera/allCarrera");
                    panelCareer.setTable(CareerModel.HEADER_CAREER, methodsApiCareers.getMatrix());

                } catch (Exception error) {
                    System.out.print(error);
                }
                break;

            case "Add":
                modalCareerAdd.setVisible(true);
                break;

            case "Patch":
                
                try {                  
                modalCareerPatch.setVisible(true);
                modalCareerPatch.setTextDefault(methodsApiCareers.getUserTemp(0),
                        methodsApiCareers.getUserTemp(1),
                        methodsApiCareers.getUserTemp(2),
                        methodsApiCareers.getUserTemp(3),
                        methodsApiCareers.getUserTemp(4));

            } catch (Exception error) {
                System.out.println(error);
            }

            case "AddCareer":
                if (modalCareerAdd.isComplete()) {
                    try {
                        methodsApiCareers.postApi("http://localhost:8080/carrera",
                                modalCareerAdd.txtCareerCode.getText(),
                                modalCareerAdd.txtDescription.getText(),
                                modalCareerAdd.txtWorkingMarket.getText(),
                                modalCareerAdd.txtName.getText(),
                                modalCareerAdd.txtProfessionalProfile.getText());

                        System.out.print(methodsApiCareers.getCodigo());

                        if (methodsApiCareers.getCodigo() >= 200 && methodsApiCareers.getCodigo() <= 299) {
                            System.out.println(" : Los datos fueron enviados satisfacotiramente");

                            methodsApiCareers.getApiData("http://localhost:8080/carrera/allCarrera");
                            panelCareer.setTable(CareerModel.HEADER_CAREER, methodsApiCareers.getMatrix());

                            modalCareerAdd.clean();
                            modalCareerAdd.setVisible(false);
                        } else {
                            ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                            System.out.println(" : Error de solicitud : " + methodsApiCareers.getCodigo());
                        }
                    } catch (Exception error) {
                        System.out.print(error);
                    }
                }
                break;

            case "PatchCareer":
                if (modalCareerPatch.isComplete()) {
                    try {
                        methodsApiCareers.patchApi("http://localhost:8080/carrera",
                                methodsApiCareers.getSelect(),
                                modalCareerPatch.txtCareerCode.getText(),
                                modalCareerPatch.txtDescription.getText(),
                                modalCareerPatch.txtWorkingMarket.getText(),
                                modalCareerPatch.txtName.getText(),
                                modalCareerPatch.txtProfessionalProfile.getText());

                        System.out.print(methodsApiCareers.getCodigo());

                        if (methodsApiCareers.getCodigo() >= 200 && methodsApiCareers.getCodigo() <= 299) {
                            System.out.println(" : Los datos fueron enviados satisfacotiramente");

                            methodsApiCareers.getApiData("http://localhost:8080/carrera/allCarrera");
                            panelCareer.setTable(CareerModel.HEADER_CAREER, methodsApiCareers.getMatrix());

                            modalCareerPatch.clean();
                            modalCareerPatch.setVisible(false);
                        } else {
                            ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                            System.out.println(" : Error de solicitud : " + methodsApiCareers.getCodigo());
                        }
                    } catch (Exception error) {
                        System.out.print(error);
                    }
                }
                break;

            case "BackCareer":
                modalCareerPatch.setVisible(false);
                modalCareerAdd.setVisible(false);
                modalCareerAdd.clean();
                modalCareerPatch.clean();
                break;

            case "DeleteCareer":
                
                try {
                methodsApiCareers.deleteApiData("http://localhost:8080/carrera/" + methodsApiCareers.getSelect());

                if (methodsApiCareers.getCodigo() >= 200 && methodsApiCareers.getCodigo() <= 299) {
                    System.out.println(" : Los datos fueron enviados satisfacotiramente");

                    methodsApiCareers.getApiData("http://localhost:8080/carrera/allCarrera");
                    panelCareer.setTable(CareerModel.HEADER_CAREER, methodsApiCareers.getMatrix());

                    modalCareerAdd.clean();
                    modalCareerAdd.setVisible(false);
                } else {
                    ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                    System.out.println(" : Error de solicitud : " + methodsApiCareers.getCodigo());
                }
            } catch (Exception error) {
                System.err.println(error);
            }
            break;

            //END ModuleCareer//
            case "Courses":
                mainGUI.tbPanel.setSelectedIndex(3);
                break;

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {

            String[] carrera;
            carrera = panelCareer.getRow();
            methodsApiCareers.setUserTemp(carrera);
            CareerModel career = methodsApiCareers.find(carrera[3]);
            methodsApiCareers.setSelect(career.getId());
            System.out.println(career.getId());

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
