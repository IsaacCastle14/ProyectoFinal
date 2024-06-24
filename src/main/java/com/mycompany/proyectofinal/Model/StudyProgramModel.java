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
public class StudyProgramModel {

    public static final String[] HEADER_CURRICULUM = {"Name", "Description", "Number of Credits", "Effective Date", "Approval Date"};

    private Long id;
    private String name;
    private String description;
    private String numberCredits;
    private String effectiveDate;
    private String approvalDate;
    private List<CourseModel> cursos;

    public String getData(int colum) {
        switch (colum) {
            case 0 -> {
                return this.getName();
            }
            case 1 -> {
                return this.getDescription();
            }
            case 2 -> {
                return this.getNumberCredits();
            }
            case 3 -> {
                return this.getEffectiveDate();
            }
            case 4 -> {
                return this.getApprovalDate();
            }
        }
        return "";
    }
}
