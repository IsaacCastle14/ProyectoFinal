/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Marco
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareerModel {

    public static final String[] HEADER_CAREER = {"Career Code", "Description", "Working Market", "Name", "Professional Profile"};

    private Long id;
    private String careerCode;
    private String description;
    private String workingMarket;
    private String name;
    private String professionalProfile;
    private List<UserModel> usuarios;      

    public String getData(int colum) {
        switch (colum) {
            case 0 -> {
                return this.getCareerCode();
            }
            case 1 -> {
                return this.getDescription();
            }
            case 2 -> {
                return this.getWorkingMarket();
            }
            case 3 -> {
                return this.getName();
            }
            case 4 -> {
                return this.getProfessionalProfile();
            }
        }
        return "";
    }
}
