/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Controller;

import com.mycompany.proyectofinal.Model.CourseModel;
import com.mycompany.proyectofinal.Model.MethodsApiCourse;
import com.mycompany.proyectofinal.Model.MethodsApiStudyProgram;
import com.mycompany.proyectofinal.Model.StudyProgramModel;
import com.mycompany.proyectofinal.View.PanelPlanCourse;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;

/**
 *
 * @author Marco
 */
public class PlanCourseController implements ActionListener, MouseListener {

    PanelPlanCourse panelPlanCourse;
    MethodsApiCourse methodsApiCourse;
    MethodsApiStudyProgram methodsApiStudyProgram;

    public PlanCourseController(PanelPlanCourse panelPlanCourseParam, MethodsApiCourse methodsApiCourseParam, MethodsApiStudyProgram methodsApiStudyProgramParam) {
        panelPlanCourse = panelPlanCourseParam;
        methodsApiCourse = methodsApiCourseParam;
        methodsApiStudyProgram = methodsApiStudyProgramParam;
        panelPlanCourse.listen(this);
        panelPlanCourse.ListenMouse(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Asociar":
                if (panelPlanCourse.cbxStudyPlan.getSelectedItem() != null) {
                    String planEstudios = panelPlanCourse.cbxStudyPlan.getSelectedItem().toString();
                    StudyProgramModel studyProgramModel = methodsApiStudyProgram.find(planEstudios);
                    for (CourseModel model : methodsApiCourse.getCoursePlanList()) {
                        try {
                            methodsApiStudyProgram.postApi("http://localhost:8080/planEstudio/" + studyProgramModel.getId() + "/" + "cursos" + "/" + model.getId());
                            System.out.println("http://localhost:8080/planEstudio/" + studyProgramModel.getId() + "/" + "cursos" + "/" + model.getId());

                        } catch (Exception ex) {
                            System.out.println("error: " + ex);
                        }
                    }
                    methodsApiCourse.resetArrayList();
                    panelPlanCourse.setTableCourseSelect(CourseModel.HEADER_COURSE, methodsApiCourse.getMatrixPlanCourse());

                } else {
                    System.out.println("Seleccione una opcion valida");
                }
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            JTable table = (JTable) e.getSource();
            Point point = e.getPoint();
            int row = table.rowAtPoint(point);

            if (row >= 0 && row < table.getRowCount()) {
                if (table == panelPlanCourse.tblCourse) {
                    // Lógica para la tabla de cursos
                    String[] curso = panelPlanCourse.getRowCourse();
                    methodsApiCourse.setUserTemp(curso);
                    CourseModel course = methodsApiCourse.find(curso[1]);
                    methodsApiCourse.setSelect(course.getId());
                    System.out.println(course.getId());
                    methodsApiCourse.add(course);
                    panelPlanCourse.setTableCourseSelect(CourseModel.HEADER_COURSE, methodsApiCourse.getMatrixPlanCourse());
                } else if (table == panelPlanCourse.tblCourseSelect) {
                    System.out.println("tabla seleccionada 2");
                    String[] cursoSeleccionado = panelPlanCourse.getRowCourseSelected();
                    methodsApiCourse.setUserTemp(cursoSeleccionado);
                    CourseModel course = methodsApiCourse.find(cursoSeleccionado[1]);
                    methodsApiCourse.setSelect(course.getId());
                    methodsApiCourse.delete(course);
                    panelPlanCourse.setTableCourseSelect(CourseModel.HEADER_COURSE, methodsApiCourse.getMatrixPlanCourse());

                }
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
