package uiPackages;

import javax.swing.*;
import ProjectSources.*;
import java.awt.Image;
import java.sql.*;

public class SignupFrame extends javax.swing.JFrame {

    public SignupFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        Clear();
        ImageResize();
    }

    public void Clear(){
       txt_userName.setText(" ");
       txt_userEmail.setText(" ");
       txt_userPassword.setText(" ");
       txt_userPassword.setText(" ");
    }
    public void ImageResize(){
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/DiamondHotelLogo.jpg")); 
        Image image = imageIcon.getImage().getScaledInstance(this.logo.getWidth(),this.logo.getHeight(),Image.SCALE_SMOOTH); 
        this.logo.setIcon(new ImageIcon(image)); 
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        mainpanel = new javax.swing.JPanel();
        titlecard = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_userName = new javax.swing.JTextField();
        txt_userEmail = new javax.swing.JTextField();
        txt_userPassword = new javax.swing.JPasswordField();
        showPassword = new javax.swing.JCheckBox();
        btnSignup = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txt_userPhoneNumber = new javax.swing.JTextField();
        logo = new javax.swing.JLabel();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainpanel.setBackground(new java.awt.Color(15, 30, 61));

        titlecard.setBackground(new java.awt.Color(27, 42, 80));

        title.setBackground(new java.awt.Color(255, 255, 255));
        title.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Daimond Hotel");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Booking Management System");

        javax.swing.GroupLayout titlecardLayout = new javax.swing.GroupLayout(titlecard);
        titlecard.setLayout(titlecardLayout);
        titlecardLayout.setHorizontalGroup(
            titlecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titlecardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
            .addGroup(titlecardLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        titlecardLayout.setVerticalGroup(
            titlecardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlecardLayout.createSequentialGroup()
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("Sign Up");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("USER NAME");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("E-MAIL");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("PASSWORD");

        txt_userName.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        txt_userEmail.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        txt_userPassword.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        showPassword.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        showPassword.setText("Show Password");
        showPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPasswordActionPerformed(evt);
            }
        });

        btnSignup.setBackground(new java.awt.Color(22, 163, 74));
        btnSignup.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSignup.setForeground(new java.awt.Color(255, 255, 255));
        btnSignup.setText("Sign Up");
        btnSignup.setFocusPainted(false);
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });

        btnLogin.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnLogin.setText("Already have an account? Login");
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("PHONE NUMBER");

        txt_userPhoneNumber.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(145, 145, 145))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txt_userName)
                    .addComponent(txt_userEmail)
                    .addComponent(txt_userPassword)
                    .addComponent(showPassword)
                    .addComponent(btnSignup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                    .addComponent(txt_userPhoneNumber))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_userName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_userEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_userPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(showPassword)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_userPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        logo.setBackground(new java.awt.Color(27, 42, 80));
        logo.setText("Logo");

        javax.swing.GroupLayout mainpanelLayout = new javax.swing.GroupLayout(mainpanel);
        mainpanel.setLayout(mainpanelLayout);
        mainpanelLayout.setHorizontalGroup(
            mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainpanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(titlecard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(mainpanelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainpanelLayout.setVerticalGroup(
            mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainpanelLayout.createSequentialGroup()
                .addGroup(mainpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainpanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titlecard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainpanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPasswordActionPerformed
        if (showPassword.isSelected()) {
            txt_userPassword.setEchoChar((char) 0);
        } else {
            txt_userPassword.setEchoChar('*');
        }
    }//GEN-LAST:event_showPasswordActionPerformed

    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignupActionPerformed

        String UserName = this.txt_userName.getText().trim();
        String Email = this.txt_userEmail.getText().trim();
        String Password = new String(this.txt_userPassword.getPassword()).trim();
        String PhoeNumber = this.txt_userPhoneNumber.getText().trim();
        
        String EncryptPassword = AES.encrypt(Password);
        
        if(UserName.isEmpty() || Email.isEmpty() || Password.isEmpty() || PhoeNumber.isEmpty()){
            JOptionPane.showMessageDialog(this, "Enter All User Details.");
            return;
        }
        
        if(!REGEX.is_userName_valid(UserName)){
            JOptionPane.showMessageDialog(this, "Username must start with a capital letter (e.g. John Doe)");
            return;
        }
        
        if(!REGEX.is_email_valid(Email)){
            JOptionPane.showMessageDialog(this, "Invalid email format (e.g. user@email.com)");
            return;
        }
        
        if(!REGEX.is_password_valid(Password)){
            JOptionPane.showMessageDialog(this, "Password must have uppercase, lowercase, digit and special char");
            return;
        }
        
        if(!REGEX.is_phone_valid(PhoeNumber)){
            JOptionPane.showMessageDialog(this, "Phone number must be a valid 10-digit number");
            return;
        }
        
        String Roles;
        
        if(Email.endsWith("@diamondhotel.com")){
            Roles = "Admin";
        }else if(Email.endsWith("@staff.diamondhotel.com")){
            Roles = "Staff";
        }else{
            Roles = "Customer";
        }
        
        try(Connection connection = JDBC.con()) {
            
            CallableStatement cs = connection.prepareCall("{call sp_User_Insert(?,?,?,?,?,?)}");
            cs.setString(1, UserName);
            cs.setString(2, Email);
            cs.setString(3, EncryptPassword);
            cs.setString(4, PhoeNumber);
            cs.setString(5, Roles);
            
            cs.registerOutParameter(6, java.sql.Types.VARCHAR);
            
            cs.execute();
            
            String result = cs.getString(6);
            
            if (result != null && result.toLowerCase().contains("exist")) {
                 JOptionPane.showMessageDialog(this, result, "Sign Up Failed", JOptionPane.WARNING_MESSAGE);
                 return;
            }
            
            JOptionPane.showMessageDialog(this, "Account created successfully!");
            dispose();
            new LoginFrame().setVisible(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSignupActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        dispose();
        new LoginFrame().setVisible(true);
    }//GEN-LAST:event_btnLoginActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignupFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSignup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JCheckBox showPassword;
    private javax.swing.JLabel title;
    private javax.swing.JPanel titlecard;
    private javax.swing.JTextField txt_userEmail;
    private javax.swing.JTextField txt_userName;
    private javax.swing.JPasswordField txt_userPassword;
    private javax.swing.JTextField txt_userPhoneNumber;
    // End of variables declaration//GEN-END:variables
}
