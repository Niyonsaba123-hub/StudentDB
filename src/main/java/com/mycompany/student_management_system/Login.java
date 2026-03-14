package com.mycompany.student_management_system;

import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());

    public Login() {
        initComponents();
        DBConnection.getConnection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        textusername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        passwordd = new javax.swing.JPasswordField();
        resetbutton = new java.awt.Button();
        loginbutton = new java.awt.Button();
        jLabel3 = new javax.swing.JLabel();
        logoutbutton = new java.awt.Button();
        remember = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Username");

        textusername.setText("");
        textusername.addActionListener(this::textusernameActionPerformed);

        jLabel2.setText("Password");

        passwordd.addActionListener(this::passworddActionPerformed);

        resetbutton.setLabel("Reset");
        resetbutton.addActionListener(this::resetbuttonActionPerformed);

        loginbutton.setBackground(new java.awt.Color(24, 240, 0));
        loginbutton.setLabel("Login");
        loginbutton.addActionListener(this::loginbuttonActionPerformed);

        jLabel3.setText("Login Page");

        logoutbutton.setBackground(new java.awt.Color(28, 128, 128));
        logoutbutton.setForeground(new java.awt.Color(255, 255, 255));
        logoutbutton.setLabel("Logout");
        logoutbutton.addActionListener(this::logoutbuttonActionPerformed);

        remember.setText("Remember Me");
        remember.addActionListener(this::rememberActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addComponent(logoutbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(textusername, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))

                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(passwordd, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))

                    .addGroup(layout.createSequentialGroup()
                        .addComponent(remember)
                        .addGap(20,20,20)
                        .addComponent(loginbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10,10,10)
                        .addComponent(resetbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                )
                .addContainerGap(40, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

            .addGroup(layout.createSequentialGroup()
                .addContainerGap()

                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(logoutbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))

                .addGap(15)

                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textusername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))

                .addGap(10)

                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(passwordd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))

                .addGap(10)

                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(remember)
                    .addComponent(loginbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))

                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }//GEN-END:initComponents
    // </editor-fold>

    private void passworddActionPerformed(java.awt.event.ActionEvent evt) {
        loginbuttonActionPerformed(evt);
    }

    private void resetbuttonActionPerformed(java.awt.event.ActionEvent evt) {
        textusername.setText("");
        passwordd.setText("");
    }

    private void loginbuttonActionPerformed(java.awt.event.ActionEvent evt) {

        String username = textusername.getText();
        String password = new String(passwordd.getPassword());

        if ("Jean".equals(username) && "1234".equals(password)) {

            java.awt.EventQueue.invokeLater(() -> new Main_Page().setVisible(true));
            this.dispose();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void textusernameActionPerformed(java.awt.event.ActionEvent evt) {
        loginbuttonActionPerformed(evt);
    }

    private void rememberActionPerformed(java.awt.event.ActionEvent evt) {}

    private void logoutbuttonActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    public static void main(String args[]) {

        try {

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {

                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private java.awt.Button loginbutton;
    private java.awt.Button logoutbutton;
    private javax.swing.JPasswordField passwordd;
    private javax.swing.JCheckBox remember;
    private java.awt.Button resetbutton;
    private javax.swing.JTextField textusername;
    // End of variables declaration//GEN-END:variables
}