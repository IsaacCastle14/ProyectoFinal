/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Model;

import java.util.List;
import lombok.*;

/**
 *
 * @author Marco
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserModel {

    public static final String[] HEADER_STUDENTS = {"User", "First Name", "Last Name", "Email", "Phone", "Carne"};
    public static final String[] HEADER_Reports = {"User", "Perfil", "perfil Secundario", "Carrera"};

    private Long id;
    private String user;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String carne;
    //private CareerModel carrera;
    private List<PerfilModel> listaPerfil;
    
    

    public String getData(int colum) {
        switch (colum) {
            case 0 -> {
                return this.getUser();
            }
            case 1 -> {
                return this.getFirstName();
            }
            case 2 -> {
                return this.getLastName();
            }
            case 3 -> {
                return this.getEmail();
            }
            case 4 -> {
                return this.getPhone();
            }
            case 5 -> {
                return this.getCarne();
            }
        }
        return "";
    }

    public static String verficarPerfiles(UserModel userParameter) {
        boolean isAdmin = false;
        boolean isProfessor = false;
        boolean isStudent = false;

        for (PerfilModel perfil : userParameter.getListaPerfil()) {
            switch (perfil.getTypeUser()) {
                case "Administrador" ->
                    isAdmin = true;
                case "Profesor" ->
                    isProfessor = true;
                case "Estudiante" ->
                    isStudent = true;
            }
        }

        if (isAdmin) {
            return "Administrador";
        } else if (isProfessor) {
            return "Profesor";
        } else if (isStudent) {
            return "Estudiante";
        } else {
            return "";
        }
    }
}
