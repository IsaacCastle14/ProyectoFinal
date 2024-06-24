/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Controller;

import com.mycompany.proyectofinal.Model.DateModel;
import com.mycompany.proyectofinal.Model.MethodsApiCourse;
import com.mycompany.proyectofinal.Model.MethodsApiStudyProgram;
import com.mycompany.proyectofinal.Model.MethodsApiUsers;
import com.mycompany.proyectofinal.Model.StudyProgramModel;
import com.mycompany.proyectofinal.Model.UserModel;
import com.mycompany.proyectofinal.View.MainGUI;
import com.mycompany.proyectofinal.View.ModalStudyProgramAdd;
import com.mycompany.proyectofinal.View.ModalStudyProgramPatch;
import com.mycompany.proyectofinal.View.PanelStudyProgram;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Timer;

/**
 *
 * @author Marco
 */
public class StudyProgramController implements ActionListener, MouseListener {

    ModalStudyProgramAdd modalStudyProgramAdd;
    ModalStudyProgramPatch modalStudyProgramPatch;
    MethodsApiStudyProgram methodsApiStudyProgram;
    PanelStudyProgram panelStudyProgram;
    MethodsApiCourse methodsApiCourse;

    public StudyProgramController(MethodsApiStudyProgram methodsApiStudyProgramParam, PanelStudyProgram panelStudyProgramParam, MethodsApiCourse methodsApiCourseParam) {
        this.panelStudyProgram = panelStudyProgramParam;
        modalStudyProgramAdd = new ModalStudyProgramAdd();
        modalStudyProgramPatch = new ModalStudyProgramPatch();
        methodsApiStudyProgram = methodsApiStudyProgramParam;
        methodsApiCourse = methodsApiCourseParam;
        panelStudyProgram.listen(this);
        panelStudyProgram.ListenMouse(this);
        modalStudyProgramAdd.listen(this);
        modalStudyProgramPatch.listen(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "AddStudyProgram":
                System.out.println("ESCUCHA");
                modalStudyProgramAdd.setVisible(true);
                break;

            case "PatchStudyProgram":
                System.out.println("ESCUCHA");
                try {
                    modalStudyProgramPatch.setVisible(true);
                    modalStudyProgramPatch.setTextDefault(methodsApiStudyProgram.getUserTemp(0),
                            methodsApiStudyProgram.getUserTemp(1),
                            methodsApiStudyProgram.getUserTemp(2)
                    );

                } catch (Exception error) {
                    System.out.println(error);
                }

            case "AddDataStudyProgram":
                if (modalStudyProgramAdd.isComplete()) {
                    try {
                        methodsApiStudyProgram.postApi("http://localhost:8080/planEstudio",
                                modalStudyProgramAdd.txtName.getText(),
                                modalStudyProgramAdd.txtDescription.getText(),
                                modalStudyProgramAdd.txtNumberCredits.getText(),
                                DateModel.obtenerFechaHoraHoyFormateada(),
                                DateModel.obtenerFechaHoraHoyFormateada());

                        System.out.print(methodsApiStudyProgram.getCodigo());

                        if (methodsApiStudyProgram.getCodigo() >= 200 && methodsApiStudyProgram.getCodigo() <= 299) {
                            System.out.println(" : Los datos fueron enviados satisfacotiramente");

                            methodsApiStudyProgram.getApiData("http://localhost:8080/planEstudio/allPlanEstudio");
                            panelStudyProgram.setTable(StudyProgramModel.HEADER_CURRICULUM, methodsApiStudyProgram.getMatrix());

                            modalStudyProgramAdd.clean();
                            modalStudyProgramAdd.setVisible(false);
                        } else {
                            ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                            System.out.println(" : Error de solicitud : " + methodsApiStudyProgram.getCodigo());
                        }
                    } catch (Exception error) {
                        System.out.print(error);
                    }
                }
                break;

            case "PatchDataStudyProgram":
                if (modalStudyProgramPatch.isComplete()) {
                      methodsApiCourse.getApiData("http://localhost:8080/curso/allCurso");
                    try {
                        StudyProgramModel model = methodsApiStudyProgram.find(methodsApiStudyProgram.getUserTemp(0));
                        methodsApiStudyProgram.patchApi("http://localhost:8080/planEstudio",
                                methodsApiStudyProgram.getSelect(),
                                modalStudyProgramPatch.txtName.getText(),
                                modalStudyProgramPatch.txtDescription.getText(),
                                modalStudyProgramPatch.txtNumberCredits.getText(),
                                DateModel.obtenerFechaHoraHoyFormateada(),
                                DateModel.obtenerFechaHoraHoyFormateada(),
                                model.getCursos());

                        System.out.print(methodsApiStudyProgram.getCodigo());

                        if (methodsApiStudyProgram.getCodigo() >= 200 && methodsApiStudyProgram.getCodigo() <= 299) {
                            System.out.println(" : Los datos fueron enviados satisfacotiramente");

                            methodsApiStudyProgram.getApiData("http://localhost:8080/planEstudio/allPlanEstudio");
                            panelStudyProgram.setTable(StudyProgramModel.HEADER_CURRICULUM, methodsApiStudyProgram.getMatrix());

                            modalStudyProgramPatch.clean();
                            modalStudyProgramPatch.setVisible(false);
                        } else {
                            ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                            System.out.println(" : Error de solicitud : " + methodsApiStudyProgram.getCodigo());
                        }
                    } catch (Exception error) {
                        System.out.print(error);
                    }
                }
                break;

            case "DeleteStudyProgram":
                
                try {
                methodsApiStudyProgram.deleteApiData("http://localhost:8080/planEstudio/" + methodsApiStudyProgram.getSelect());

                if (methodsApiStudyProgram.getCodigo() >= 200 && methodsApiStudyProgram.getCodigo() <= 299) {
                    System.out.println(" : Los datos fueron enviados satisfacotiramente");

                    methodsApiStudyProgram.getApiData("http://localhost:8080/planEstudio/allPlanEstudio");
                    panelStudyProgram.setTable(StudyProgramModel.HEADER_CURRICULUM, methodsApiStudyProgram.getMatrix());

                    modalStudyProgramAdd.clean();
                    modalStudyProgramAdd.setVisible(false);
                } else {
                    ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                    System.out.println(" : Error de solicitud : " + methodsApiStudyProgram.getCodigo());
                }
            } catch (Exception error) {
                System.err.println(error);
            }
            break;

            case "Back":
                modalStudyProgramPatch.setVisible(false);
                modalStudyProgramAdd.setVisible(false);
                modalStudyProgramAdd.clean();
                modalStudyProgramPatch.clean();
                break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if (panelStudyProgram.isVisible()) {
                String[] StudyProgram;
                StudyProgram = panelStudyProgram.getRow();
                methodsApiStudyProgram.setUserTemp(StudyProgram);
                StudyProgramModel studyProgram = methodsApiStudyProgram.find(StudyProgram[0]);
                methodsApiStudyProgram.setSelect(studyProgram.getId());
                System.out.println(studyProgram.getId());
            }
        } catch (Exception ex) {
            System.out.println("error: " + ex);
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
