/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Model;

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
public class CourseModel {

    public static final String[] HEADER_COURSE= {"Description", "Name", "Block Belonging", "Teaching Hours", "Modality", "Independent Work Hours", "Credit Quantity", "Initials"};

    private Long id;
    private String description;
    private String name;
    private String blockBelonging;
    private String teachingHours;
    private String modality;
    private String independentWorkHours;
    private String creditQuantity;
    private String initials;

    public String getData(int colum) {
        switch (colum) {
            case 0 -> {
                return this.getDescription();
            }
            case 1 -> {
                return this.getName();
            }
            case 2 -> {
                return this.getBlockBelonging();
            }
            case 3 -> {
                return this.getTeachingHours();
            }
            case 4 -> {
                return this.getModality();
            }
            case 5 -> {
                return this.getIndependentWorkHours();
            }
            case 6 -> {
                return this.getCreditQuantity();
            }
            case 7 -> {
                return this.getInitials();
            }
        }
        return "";
    }
}
