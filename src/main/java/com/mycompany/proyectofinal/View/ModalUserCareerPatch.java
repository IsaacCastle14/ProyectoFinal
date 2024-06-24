/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyectofinal.View;

import java.awt.event.ActionListener;

/**
 *
 * @author Marco
 */
public class ModalUserCareerPatch extends javax.swing.JFrame {

    /**
     * Creates new form ModalCarrer
     */
    public ModalUserCareerPatch() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public void listen(ActionListener controller) {
        this.btnPatchCareer.addActionListener(controller);
        this.btnBackModal.addActionListener(controller);
    }

    public boolean isComplete() {
        boolean complete = false;
        if (!txtUser.getText().isBlank()) {
            if (cbxCareer.getSelectedIndex() != 0) {
                complete = true;
            }
        }
        return complete;
    }

    public void clean() {
        cbxCareer.setSelectedIndex(0);
        txtUser.setText("");

    }

    public void setTextDefault(String t1) {
        txtUser.setText(t1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnPatchCareer = new javax.swing.JButton();
        btnBackModal = new javax.swing.JButton();
        txtUser = new javax.swing.JTextField();
        cbxCareer = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Patch Course");

        jLabel1.setText("User");

        btnPatchCareer.setBackground(new java.awt.Color(0, 76, 142));
        btnPatchCareer.setForeground(new java.awt.Color(204, 255, 255));
        btnPatchCareer.setText("Patch");
        btnPatchCareer.setActionCommand("PatchDataUser");

        btnBackModal.setBackground(new java.awt.Color(150, 186, 224));
        btnBackModal.setText("Back");

        txtUser.setFocusable(false);

        cbxCareer.setBackground(new java.awt.Color(48, 115, 183));
        cbxCareer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguno", "Profesor", "Estudiante", "Administrador" }));

        jLabel2.setText("Carrera");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxCareer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnPatchCareer)
                        .addGap(27, 27, 27)
                        .addComponent(btnBackModal))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel6)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxCareer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBackModal)
                    .addComponent(btnPatchCareer))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBackModal;
    public javax.swing.JButton btnPatchCareer;
    public javax.swing.JComboBox<String> cbxCareer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    public javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
