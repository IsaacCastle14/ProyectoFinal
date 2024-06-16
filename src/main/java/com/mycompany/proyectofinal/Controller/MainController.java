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
import com.mycompany.proyectofinal.Model.PerfilModel;
import com.mycompany.proyectofinal.Model.StudyProgramModel;
import com.mycompany.proyectofinal.Model.UserModel;
import com.mycompany.proyectofinal.View.MainGUI;
import com.mycompany.proyectofinal.View.MessageFrame;
import com.mycompany.proyectofinal.View.ModalCareerAdd;
import com.mycompany.proyectofinal.View.ModalCareerPatch;
import com.mycompany.proyectofinal.View.ModalCourseAdd;
import com.mycompany.proyectofinal.View.ModalCoursePatch;
import com.mycompany.proyectofinal.View.ModalStudyProgramAdd;
import com.mycompany.proyectofinal.View.ModalStudyProgramPatch;
import com.mycompany.proyectofinal.View.PanelCareer;
import com.mycompany.proyectofinal.View.PanelCourse;
import com.mycompany.proyectofinal.View.PanelMain;
import com.mycompany.proyectofinal.View.PanelStudyProgram;
import com.mycompany.proyectofinal.View.PanelUsers;
import com.mycompany.proyectofinal.View.PanelUsersAdminUsers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

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
    PanelMain panelMain;
    PanelUsersAdminUsers panelUsersAdminUsers;
    MethodsApiCareers methodsApiCareers;
    MethodsApiCourse methodsApiCourse;
    MethodsApiStudyProgram methodsApiStudyProgram;
    MethodsApiUsers methodsApiUsers;
    ModalCareerAdd modalCareerAdd;
    ModalCareerPatch modalCareerPatch;
    ModalCourseAdd modalCourseAdd;
    ModalCoursePatch modalCoursePatch;
    ModalStudyProgramAdd modalStudyProgramAdd;
    ModalStudyProgramPatch modalStudyProgramPatch;
    MessageFrame messageFrame;

    public MainController(MethodsApiUsers methodsApiUsersInstance) {
        mainGUI = new MainGUI();
        mainGUI.setVisible(true);
        panelUsers = mainGUI.getPanelUsers();
        panelUsersAdminUsers = mainGUI.getPanelUsersAdminUsers();
        modalCareerAdd = new ModalCareerAdd();
        modalCareerPatch = new ModalCareerPatch();
        modalCourseAdd = new ModalCourseAdd();
        modalCoursePatch = new ModalCoursePatch();
        modalStudyProgramAdd = new ModalStudyProgramAdd();
        modalStudyProgramPatch = new ModalStudyProgramPatch();
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
        panelCareer.listen(this);
        panelUsers.listen(this);
        modalCareerAdd.listen(this);
        modalCareerPatch.listen(this);
        panelCareer.ListenMouse(this);
        panelCourse.listen(this);
        modalCourseAdd.listen(this);
        modalCoursePatch.listen(this);
        panelCourse.ListenMouse(this);
        panelStudyProgram.listen(this);
        modalStudyProgramAdd.listen(this);
        modalStudyProgramPatch.listen(this);
        panelStudyProgram.ListenMouse(this);
        panelUsersAdminUsers.listen(this);
        panelUsersAdminUsers.ListenMouse(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Menu":
                mainGUI.tbPanel.setSelectedIndex(4);
                break;

            case "Users":

                if (methodsApiUsers.getUserTemp().getListaPerfil().isEmpty()) {
                    mainGUI.tbPanel.setSelectedIndex(0);
                    panelUsers.lbUser.setText(methodsApiUsers.getUserTemp().getUser());
                } else if (UserModel.verficarPerfiles(methodsApiUsers.getUserTemp()).equals("Profesor")
                        || UserModel.verficarPerfiles(methodsApiUsers.getUserTemp()).equals("Administrador")) {
                    mainGUI.tbPanel.setSelectedIndex(5);
                    panelUsers.lbUser.setText(methodsApiUsers.getUserTemp().getUser());

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

            //ModuleStudyProgram//
            case "Study Program":
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

            case "AddStudyProgram":
                modalStudyProgramAdd.setVisible(true);
                break;

            case "PatchStudyProgram":
                
                try {
                modalStudyProgramPatch.setVisible(true);
                modalStudyProgramPatch.setTextDefault(methodsApiStudyProgram.getUserTemp(0),
                        methodsApiStudyProgram.getUserTemp(1),
                        methodsApiStudyProgram.getUserTemp(2),
                        methodsApiStudyProgram.getUserTemp(3),
                        methodsApiStudyProgram.getUserTemp(4));

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
                                modalStudyProgramAdd.txtEffectiveDate.getText(),
                                modalStudyProgramAdd.txtApprovalDate.getText());

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
                    try {
                        methodsApiStudyProgram.patchApi("http://localhost:8080/planEstudio",
                                methodsApiStudyProgram.getSelect(),
                                modalStudyProgramPatch.txtName.getText(),
                                modalStudyProgramPatch.txtDescription.getText(),
                                modalStudyProgramPatch.txtNumberCredits.getText(),
                                modalStudyProgramPatch.txtEffectiveDate.getText(),
                                modalStudyProgramPatch.txtApprovalDate.getText());

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
            //END ModuleStudyProgram//

            //ModuleCareer//
            case "Careers":
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

            case "AddCareer":
                modalCareerAdd.setVisible(true);
                break;

            case "PatchCareer":
                
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

            case "AddDataCareer":
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

            case "PatchDataCareer":
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

            case "Back":
                modalCareerPatch.setVisible(false);
                modalCareerAdd.setVisible(false);
                modalCareerAdd.clean();
                modalCareerPatch.clean();

                modalCoursePatch.setVisible(false);
                modalCourseAdd.setVisible(false);
                modalCourseAdd.clean();
                modalCoursePatch.clean();

                modalStudyProgramPatch.setVisible(false);
                modalStudyProgramAdd.setVisible(false);
                modalStudyProgramAdd.clean();
                modalStudyProgramPatch.clean();
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

            //Module Course//
            case "Courses":
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
            //END Module Curse//
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if (panelCareer.isVisible()) {
                String[] carrera;
                carrera = panelCareer.getRow();
                methodsApiCareers.setUserTemp(carrera);
                CareerModel career = methodsApiCareers.find(carrera[3]);
                methodsApiCareers.setSelect(career.getId());
                System.out.println(career.getId());

            } else if (panelCourse.isVisible()) {
                String[] curso;
                curso = panelCourse.getRow();
                methodsApiCourse.setUserTemp(curso);
                CourseModel course = methodsApiCourse.find(curso[1]);
                methodsApiCourse.setSelect(course.getId());
                System.out.println(course.getId());

            } else if (panelStudyProgram.isVisible()) {
                String[] StudyProgram;
                StudyProgram = panelStudyProgram.getRow();
                methodsApiStudyProgram.setUserTemp(StudyProgram);
                StudyProgramModel studyProgram = methodsApiStudyProgram.find(StudyProgram[0]);
                methodsApiStudyProgram.setSelect(studyProgram.getId());
                System.out.println(studyProgram.getId());

            } else if (panelUsersAdminUsers.isVisible()) {
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
