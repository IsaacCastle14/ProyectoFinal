/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Controller;

import com.mycompany.proyectofinal.Model.CareerModel;
import com.mycompany.proyectofinal.Model.CourseModel;
import com.mycompany.proyectofinal.Model.MethodsApiCareers;
import com.mycompany.proyectofinal.Model.MethodsApiCourse;
import com.mycompany.proyectofinal.Model.MethodsApiStudyProgram;
import com.mycompany.proyectofinal.Model.MethodsApiUsers;
import com.mycompany.proyectofinal.Model.StudyProgramModel;
import com.mycompany.proyectofinal.Model.UserModel;
import com.mycompany.proyectofinal.View.MainGUI;
import com.mycompany.proyectofinal.View.MessageFrame;
import com.mycompany.proyectofinal.View.ModalUserPatch;
import com.mycompany.proyectofinal.View.PanelCareer;
import com.mycompany.proyectofinal.View.PanelCourse;
import com.mycompany.proyectofinal.View.PanelMain;
import com.mycompany.proyectofinal.View.PanelStudyProgram;
import com.mycompany.proyectofinal.View.PanelUsers;
import com.mycompany.proyectofinal.View.PanelUsersAdminUsers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

/**
 *
 * @author josh
 */
public class MainController implements ActionListener {

    MainGUI mainGUI;
    PanelUsers panelUsers;
    PanelStudyProgram panelStudyProgram;
    PanelCareer panelCareer;
    PanelCourse panelCourse;
    PanelMain panelMain;
    PanelUsersAdminUsers panelUsersAdminUsers;
    MethodsApiCareers methodsApiCareers;
    MethodsApiCourse methodsApiCourse;
    MethodsApiStudyProgram methodsApiStudyProgram;
    MethodsApiUsers methodsApiUsers;
    MessageFrame messageFrame;
    StudyProgramController studyProgramController;
    CoursesController coursesController;
    CareersController careersController;
    UsersController usersController;

    public MainController(MethodsApiUsers methodsApiUsersInstance) {
        mainGUI = new MainGUI();
        mainGUI.setVisible(true);
        panelUsers = mainGUI.getPanelUsers();
        panelUsersAdminUsers = mainGUI.getPanelUsersAdminUsers();
        methodsApiCareers = new MethodsApiCareers();
        methodsApiCourse = new MethodsApiCourse();
        methodsApiUsers = methodsApiUsersInstance;
        methodsApiStudyProgram = new MethodsApiStudyProgram();
        panelStudyProgram = mainGUI.getPanelStudyProgram();
        panelCareer = mainGUI.getPanelCareer();
        panelCourse = mainGUI.getPanelCourse();
        panelMain = mainGUI.getPanelMain();
        messageFrame = new MessageFrame();
        mainGUI.listen(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Menu":
                mainGUI.tbPanel.setSelectedIndex(4);
                break;
            //Module Users
            case "Users":
                if (usersController == null) {
                    usersController = new UsersController(panelUsers, methodsApiUsers, panelUsersAdminUsers, mainGUI, panelCareer, methodsApiCareers);
                }
                if (methodsApiUsers.getUserTemp().getListaPerfil().isEmpty()) {
                    mainGUI.tbPanel.setSelectedIndex(0);
                    panelUsers.lbUser.setText(methodsApiUsers.getUserTemp().getUser());

                } else if (UserModel.verficarPerfiles(methodsApiUsers.getUserTemp()).equals("Profesor")
                        || UserModel.verficarPerfiles(methodsApiUsers.getUserTemp()).equals("Administrador")) {
                    mainGUI.tbPanel.setSelectedIndex(5);
                    panelUsersAdminUsers.lbUser.setText(methodsApiUsers.getUserTemp().getUser());

                    try {
                        methodsApiUsers.getApiData("http://localhost:8080/usuario/allUsuario");
                        panelUsersAdminUsers.setTable(UserModel.HEADER_STUDENTS, methodsApiUsers.getMatrix());
                    } catch (Exception error) {
                        System.out.print(error);
                    }
                } else {
                    mainGUI.tbPanel.setSelectedIndex(4);
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
            //End Module Users//

            //ModuleStudyProgram//
            case "Study Program":
                if (studyProgramController == null) {
                    studyProgramController = new StudyProgramController(methodsApiStudyProgram, panelStudyProgram);
                }
                if (!methodsApiUsers.getUserTemp().getListaPerfil().isEmpty()) {
                    if (UserModel.verficarPerfiles(methodsApiUsers.getUserTemp()).equals("Profesor")
                            || UserModel.verficarPerfiles(methodsApiUsers.getUserTemp()).equals("Administrador")) {
                        mainGUI.tbPanel.setSelectedIndex(1);
                        try {
                            methodsApiStudyProgram.getApiData("http://localhost:8080/planEstudio/allPlanEstudio");
                            panelStudyProgram.setTable(StudyProgramModel.HEADER_CURRICULUM, methodsApiStudyProgram.getMatrix());

                        } catch (Exception error) {
                            System.out.print(error);
                        }
                    } else {
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
                } else {
                    mainGUI.tbPanel.setSelectedIndex(0);
                    panelUsers.lbError.setText("Asocia primero a tu perfil");
                    Timer timer = new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            panelUsers.lbError.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                    System.out.println("Asocia tu usuario a un perfil");
                }
                break;
            //END ModuleStudyProgram//

            //ModuleCareer//
            case "Careers":
                if (careersController == null) {
                    careersController = new CareersController(methodsApiCareers, panelCareer);
                }
                if (!methodsApiUsers.getUserTemp().getListaPerfil().isEmpty()) {
                    if (UserModel.verficarPerfiles(methodsApiUsers.getUserTemp()).equals("Profesor")
                            || UserModel.verficarPerfiles(methodsApiUsers.getUserTemp()).equals("Administrador")) {
                        mainGUI.tbPanel.setSelectedIndex(2);

                        try {
                            methodsApiCareers.getApiData("http://localhost:8080/carrera/allCarrera");
                            panelCareer.setTable(CareerModel.HEADER_CAREER, methodsApiCareers.getMatrix());

                        } catch (Exception error) {
                            System.out.print(error);
                        }
                    } else {
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
                } else {
                    mainGUI.tbPanel.setSelectedIndex(0);
                    panelUsers.lbError.setText("Asocia primero a tu perfil");
                    Timer timer = new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            panelUsers.lbError.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                    System.out.println("Asocia tu usuario a un perfil");
                }
                break;
            //END ModuleCareer//

            //Module Course//
            case "Courses":
                if (coursesController == null) {
                    coursesController = new CoursesController(methodsApiCourse, panelCourse);
                }
                if (!methodsApiUsers.getUserTemp().getListaPerfil().isEmpty()) {
                    if (UserModel.verficarPerfiles(methodsApiUsers.getUserTemp()).equals("Profesor")
                            || UserModel.verficarPerfiles(methodsApiUsers.getUserTemp()).equals("Administrador")) {
                        mainGUI.tbPanel.setSelectedIndex(3);

                        try {
                            methodsApiCourse.getApiData("http://localhost:8080/curso/allCurso");
                            panelCourse.setTable(CourseModel.HEADER_COURSE, methodsApiCourse.getMatrix());

                        } catch (Exception error) {
                            System.out.print(error);
                        }
                    } else {
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
                } else {
                    mainGUI.tbPanel.setSelectedIndex(0);
                    panelUsers.lbError.setText("Asocia primero a tu perfil");
                    Timer timer = new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            panelUsers.lbError.setText("");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                    System.out.println("Asocia tu usuario a un perfil");
                }
                break;
            //END Module Curse//        
        }
    }
}
