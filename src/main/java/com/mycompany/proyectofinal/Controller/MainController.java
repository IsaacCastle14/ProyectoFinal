/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectofinal.Controller;

import com.mycompany.proyectofinal.View.MainGUI;
import com.mycompany.proyectofinal.View.PanelCareer;
import com.mycompany.proyectofinal.View.PanelCourse;
import com.mycompany.proyectofinal.View.PanelStudyProgram;
import com.mycompany.proyectofinal.View.PanelUsers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public MainController() {
        mainGUI = new MainGUI();
        mainGUI.setVisible(true);
        panelUsers = mainGUI.getPanelUsers();
        panelStudyProgram = mainGUI.getPanelStudyProgram();
        panelCareer = mainGUI.getPanelCareer();
        panelCourse = mainGUI.getPanelCourse();
        mainGUI.listen(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Users":
                mainGUI.tbPanel.setSelectedIndex(0);
                break;

            case "Study Program":
                mainGUI.tbPanel.setSelectedIndex(1);
                break;

            case "Careers":
                mainGUI.tbPanel.setSelectedIndex(2);
                break;

            case "Courses":
                mainGUI.tbPanel.setSelectedIndex(3);
                break;

        }
    }

}
