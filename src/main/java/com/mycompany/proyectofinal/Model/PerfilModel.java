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
public class PerfilModel {

    public PerfilModel(String description, String typeUser) {
        this.description = description;
        this.typeUser = typeUser;
    }
    
    
    private Long id;
    private String description;
    private String typeUser;
}
