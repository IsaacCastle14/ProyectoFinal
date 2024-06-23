/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Controller;

import com.mycompany.proyectofinal.Model.CourseModel;
import com.mycompany.proyectofinal.Model.MethodsApiCourse;
import com.mycompany.proyectofinal.View.ModalCourseAdd;
import com.mycompany.proyectofinal.View.ModalCoursePatch;
import com.mycompany.proyectofinal.View.PanelCourse;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Marco
 */
public class CoursesController implements ActionListener, MouseListener {

    ModalCourseAdd modalCourseAdd;
    ModalCoursePatch modalCoursePatch;
    MethodsApiCourse methodsApiCourse;
    PanelCourse panelCourse;

    public CoursesController(MethodsApiCourse methodsApiCourseParam, PanelCourse panelCourseParam) {
        this.panelCourse = panelCourseParam;
        modalCourseAdd = new ModalCourseAdd();
        modalCoursePatch = new ModalCoursePatch();
        methodsApiCourse = methodsApiCourseParam;

        panelCourse.listen(this);
        panelCourse.ListenMouse(this);
        modalCourseAdd.listen(this);
        modalCoursePatch.listen(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "AddCourse":
                modalCourseAdd.setVisible(true);
                break;

            case "PatchCourse":
                
                try {
                modalCoursePatch.setVisible(true);
                modalCoursePatch.setTextDefault(methodsApiCourse.getUserTemp(0),
                        methodsApiCourse.getUserTemp(1),
                        methodsApiCourse.getUserTemp(2),
                        methodsApiCourse.getUserTemp(3),
                        methodsApiCourse.getUserTemp(4),
                        methodsApiCourse.getUserTemp(5),
                        methodsApiCourse.getUserTemp(6),
                        methodsApiCourse.getUserTemp(7)
                );

            } catch (Exception error) {
                System.out.println(error);
            }

            case "AddDataCourse":
                if (modalCourseAdd.isComplete()) {
                    try {
                        methodsApiCourse.postApi("http://localhost:8080/curso",
                                modalCourseAdd.txtDescription.getText(),
                                modalCourseAdd.txtName.getText(),
                                modalCourseAdd.txtBlockBelonging.getText(),
                                modalCourseAdd.txtTeachingHours.getText(),
                                modalCourseAdd.txtModality.getText(),
                                modalCourseAdd.txtIndependentWorkHours.getText(),
                                modalCourseAdd.txtCreditQuantity.getText(),
                                modalCourseAdd.txtInitials.getText());

                        System.out.print(methodsApiCourse.getCodigo());

                        if (methodsApiCourse.getCodigo() >= 200 && methodsApiCourse.getCodigo() <= 299) {
                            System.out.println(" : Los datos fueron enviados satisfacotiramente");

                            methodsApiCourse.getApiData("http://localhost:8080/curso/allCurso");
                            panelCourse.setTable(CourseModel.HEADER_COURSE, methodsApiCourse.getMatrix());

                            modalCourseAdd.clean();
                            modalCourseAdd.setVisible(false);
                        } else {
                            ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                            System.out.println(" : Error de solicitud : " + methodsApiCourse.getCodigo());
                        }
                    } catch (Exception error) {
                        System.out.print(error);
                    }
                }
                break;

            case "PatchDataCourse":
                if (modalCoursePatch.isComplete()) {
                    try {
                        methodsApiCourse.patchApi("http://localhost:8080/curso",
                                methodsApiCourse.getSelect(),
                                modalCoursePatch.txtDescription.getText(),
                                modalCoursePatch.txtName.getText(),
                                modalCoursePatch.txtBlockBelonging.getText(),
                                modalCoursePatch.txtTeachingHours.getText(),
                                modalCoursePatch.txtModality.getText(),
                                modalCoursePatch.txtIndependentWorkHours.getText(),
                                modalCoursePatch.txtCreditQuantity.getText(),
                                modalCoursePatch.txtInitials.getText());

                        System.out.print(methodsApiCourse.getCodigo());

                        if (methodsApiCourse.getCodigo() >= 200 && methodsApiCourse.getCodigo() <= 299) {
                            System.out.println(" : Los datos fueron enviados satisfacotiramente");

                            methodsApiCourse.getApiData("http://localhost:8080/curso/allCurso");
                            panelCourse.setTable(CourseModel.HEADER_COURSE, methodsApiCourse.getMatrix());

                            modalCoursePatch.clean();
                            modalCoursePatch.setVisible(false);
                        } else {
                            ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                            System.out.println(" : Error de solicitud : " + methodsApiCourse.getCodigo());
                        }
                    } catch (Exception error) {
                        System.out.print(error);
                    }
                }
                break;

            case "DeleteCourse":
                
                try {
                methodsApiCourse.deleteApiData("http://localhost:8080/curso/" + methodsApiCourse.getSelect());

                if (methodsApiCourse.getCodigo() >= 200 && methodsApiCourse.getCodigo() <= 299) {
                    System.out.println(" : Los datos fueron enviados satisfacotiramente");

                    methodsApiCourse.getApiData("http://localhost:8080/curso/allCurso");
                    panelCourse.setTable(CourseModel.HEADER_COURSE, methodsApiCourse.getMatrix());

                    modalCourseAdd.clean();
                    modalCourseAdd.setVisible(false);
                } else {
                    ///PONER LABELS CON LOS ERRORES Y MENSAJES///
                    System.out.println(" : Error de solicitud : " + methodsApiCourse.getCodigo());
                }
            } catch (Exception error) {
                System.err.println(error);
            }
            break;

            case "Back":
                modalCoursePatch.setVisible(false);
                modalCourseAdd.setVisible(false);
                modalCourseAdd.clean();
                modalCoursePatch.clean();
                break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if (panelCourse.isVisible()) {
                String[] curso;
                curso = panelCourse.getRow();
                methodsApiCourse.setUserTemp(curso);
                CourseModel course = methodsApiCourse.find(curso[1]);
                methodsApiCourse.setSelect(course.getId());
                System.out.println(course.getId());
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
