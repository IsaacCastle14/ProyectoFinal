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
import com.mycompany.proyectofinal.View.ModalUserCareerPatch;
import com.mycompany.proyectofinal.View.ModalUserPatch;
import com.mycompany.proyectofinal.View.PanelUsersCareer;
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
public class UsersCareerController implements ActionListener, MouseListener {

    MethodsApiUsers methodsApiUsers;
    ModalUserCareerPatch modalUserCareerPatch;
    PanelUsersCareer panelUsersCareer;
    MethodsApiCareers methodsApiCareers;

    public UsersCareerController(MethodsApiUsers methodsApiUsersParam, PanelUsersCareer panelUsersCareerParam, MethodsApiCareers methodsApiCareersParam) {
        methodsApiUsers = methodsApiUsersParam;
        modalUserCareerPatch = new ModalUserCareerPatch();
        panelUsersCareer = panelUsersCareerParam;
        methodsApiCareers = methodsApiCareersParam;
        panelUsersCareer.ListenMouse(this);
        panelUsersCareer.listen(this);
        modalUserCareerPatch.listen(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "PatchUser":
                System.out.println("patch");
                try {
                    String row[];
                    row = methodsApiUsers.getRowSelected();
                    modalUserCareerPatch.setTextDefault(row[0]);
                    if (!modalUserCareerPatch.txtUser.getText().isBlank()) {
                        modalUserCareerPatch.setVisible(true);

                        methodsApiCareers.getApiData("http://localhost:8080/carrera/allCarrera");
                        modalUserCareerPatch.cbxCareer.removeAllItems();
                        List<CareerModel> careerList = methodsApiCareers.getCareerList();
                        for (CareerModel careerModel : careerList) {
                            modalUserCareerPatch.cbxCareer.addItem(careerModel.getName());
                        }
                    }
                } catch (Exception error) {
                    System.out.println(error);
                    System.out.println("Seleccione una fila valida");
                }
                break;

            case "PatchDataUser":
                if (modalUserCareerPatch.isComplete()) {
                    try {

                        String mainProfileModal = modalUserCareerPatch.cbxCareer.getSelectedItem().toString();
                        UserModel updateUser = methodsApiUsers.find(modalUserCareerPatch.txtUser.getText());

                        CareerModel careerModel = methodsApiCareers.find(mainProfileModal);
                        methodsApiUsers.postApi("http://localhost:8080/usuario/" + updateUser.getId() + "/" + "carrera" + "/" + careerModel.getId());

                        System.out.print("http://localhost:8080/usuario/" + updateUser.getId() + "/" + "carrera" + "/" + careerModel.getId());
                        System.out.print(methodsApiUsers.getCodigo());

                        if (methodsApiUsers.getCodigo() >= 200 && methodsApiUsers.getCodigo() <= 299) {
                            System.out.println(" : Los datos fueron enviados satisfacotiramente");

                            methodsApiUsers.getApiData("http://localhost:8080/usuario/allUsuario");
                            panelUsersCareer.setTable(UserModel.HEADER_STUDENTS, methodsApiUsers.getMatrixWIthoutFilters());
                            methodsApiUsers.setRowSelected(null);

                            modalUserCareerPatch.clean();
                            modalUserCareerPatch.setVisible(false);
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
                modalUserCareerPatch.setVisible(false);
                modalUserCareerPatch.clean();
                break;

        }
    }

    @Override
    public void mouseClicked(MouseEvent e
    ) {
        try {
            if (panelUsersCareer.isVisible()) {
                String[] studentArray;
                studentArray = panelUsersCareer.getRow();
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
    public void mousePressed(MouseEvent e
    ) {
    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {
    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {
    }

    @Override
    public void mouseExited(MouseEvent e
    ) {
    }

}
