package uiPackages;

import java.sql.*;
import ProjectSources.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GuestsFrame extends javax.swing.JFrame {

    private Integer selectedGuestId = -1;

    public GuestsFrame() {
        initComponents();
        Clear();
        LoadTable();
    }

    public void Clear() {
        selectedGuestId = -1;
        txtFirstName.setText("");
        txtLastName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtIdProofNumber.setText("");
        if (cmbIdProofType.getItemCount() > 0) {
            cmbIdProofType.setSelectedIndex(0);
        }
        tblGuests.clearSelection();
    }

    public void LoadTable() {
        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Guest_GetAll}");
            ResultSet resultSet = callableStatement.executeQuery();
            fillTable(resultSet);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading guests: " + e.getMessage()
            );
        }
    }

    private void fillTable(ResultSet resultSet) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tblGuests.getModel();
        model.setRowCount(0);

        while (resultSet.next()) {
            model.addRow(new Object[]{
                resultSet.getInt("GuestId"),
                resultSet.getString("FirstName"),
                resultSet.getString("LastName"),
                resultSet.getString("Phone"),
                resultSet.getString("Email"),
                resultSet.getString("Address"),
                resultSet.getString("IdProofType"),
                resultSet.getString("IdProofNumber")
            });
        }
    }

    private void fillForm(ResultSet resultSet) throws SQLException {
        selectedGuestId = resultSet.getInt("GuestId");
        txtFirstName.setText(resultSet.getString("FirstName"));
        txtLastName.setText(resultSet.getString("LastName"));
        txtPhone.setText(resultSet.getString("Phone"));
        txtEmail.setText(resultSet.getString("Email"));
        txtAddress.setText(resultSet.getString("Address"));
        cmbIdProofType.setSelectedItem(resultSet.getString("IdProofType"));
        txtIdProofNumber.setText(resultSet.getString("IdProofNumber"));
    }

    private boolean validateGuest() {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();
        String idProofType = cmbIdProofType.getSelectedItem() == null
                ? ""
                : cmbIdProofType.getSelectedItem().toString();
        String idProofNumber = txtIdProofNumber.getText().trim();

        if (firstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First Name is required.");
            txtFirstName.requestFocus();
            return false;
        }

        if (!firstName.matches("^[A-Za-z ]{2,50}$")) {
            JOptionPane.showMessageDialog(this, "First Name should contain only letters and spaces.");
            txtFirstName.requestFocus();
            return false;
        }

        if (lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Last Name is required.");
            txtLastName.requestFocus();
            return false;
        }

        if (!lastName.matches("^[A-Za-z ]{2,50}$")) {
            JOptionPane.showMessageDialog(this, "Last Name should contain only letters and spaces.");
            txtLastName.requestFocus();
            return false;
        }

        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phone is required.");
            txtPhone.requestFocus();
            return false;
        }

        if (!REGEX.is_phone_valid(phone)) {
            JOptionPane.showMessageDialog(this, "Phone number must be a valid 10-digit number.");
            txtPhone.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email is required.");
            txtEmail.requestFocus();
            return false;
        }

        if (!REGEX.is_email_valid(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            txtEmail.requestFocus();
            return false;
        }

        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Address is required.");
            txtAddress.requestFocus();
            return false;
        }

        if (address.length() < 5 || address.length() > 255) {
            JOptionPane.showMessageDialog(this, "Address must be between 5 and 255 characters.");
            txtAddress.requestFocus();
            return false;
        }

        if (idProofType.isEmpty() || idProofType.equals("Select ID Proof")) {
            JOptionPane.showMessageDialog(this, "Please select an ID Proof Type.");
            cmbIdProofType.requestFocus();
            return false;
        }

        if (idProofNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Proof Number is required.");
            txtIdProofNumber.requestFocus();
            return false;
        }

        if (!idProofNumber.matches("^[A-Za-z0-9]{4,20}$")) {
            JOptionPane.showMessageDialog(this, "ID Proof Number should be 4 to 20 letters or numbers.");
            txtIdProofNumber.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isFailedResult(String result) {
        if (result == null) {
            return false;
        }
        String lower = result.toLowerCase();
        return lower.contains("exist") || lower.contains("not found");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblFormTitle = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        lblIdProofType = new javax.swing.JLabel();
        cmbIdProofType = new javax.swing.JComboBox<>();
        lblIdProofNumber = new javax.swing.JLabel();
        txtIdProofNumber = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnGetAll = new javax.swing.JButton();
        btnGetById = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGuests = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Guest Details");
        setMaximumSize(new java.awt.Dimension(1200, 700));
        setMinimumSize(new java.awt.Dimension(1200, 700));
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(27, 42, 80));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DAIMOND HOTEL BOOKING");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Guest Details");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(280, 280, 280)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(548, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 50));

        jPanel3.setBackground(new java.awt.Color(15, 30, 61));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFormTitle.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lblFormTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblFormTitle.setText("Guest Details");
        jPanel3.add(lblFormTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 12, 320, -1));

        lblFirstName.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblFirstName.setForeground(new java.awt.Color(255, 255, 255));
        lblFirstName.setText("FIRST NAME");
        jPanel3.add(lblFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 320, -1));

        txtFirstName.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 58, 320, 26));

        lblLastName.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblLastName.setForeground(new java.awt.Color(255, 255, 255));
        lblLastName.setText("LAST NAME");
        jPanel3.add(lblLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 320, -1));

        txtLastName.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 108, 320, 26));

        lblPhone.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblPhone.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone.setText("PHONE");
        jPanel3.add(lblPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 320, -1));

        txtPhone.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 158, 320, 26));

        lblEmail.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblEmail.setText("EMAIL");
        jPanel3.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 320, -1));

        txtEmail.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 208, 320, 26));

        lblAddress.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblAddress.setForeground(new java.awt.Color(255, 255, 255));
        lblAddress.setText("ADDRESS");
        jPanel3.add(lblAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 320, -1));

        txtAddress.setColumns(20);
        txtAddress.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        txtAddress.setLineWrap(true);
        txtAddress.setRows(3);
        txtAddress.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtAddress);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 258, 320, 55));

        lblIdProofType.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblIdProofType.setForeground(new java.awt.Color(255, 255, 255));
        lblIdProofType.setText("ID PROOF TYPE");
        jPanel3.add(lblIdProofType, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 320, -1));

        cmbIdProofType.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmbIdProofType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select ID Proof", "Aadhaar", "Passport", "Driving License", "PAN Card", "Voter ID" }));
        jPanel3.add(cmbIdProofType, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 338, 320, 26));

        lblIdProofNumber.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblIdProofNumber.setForeground(new java.awt.Color(255, 255, 255));
        lblIdProofNumber.setText("ID PROOF NUMBER");
        jPanel3.add(lblIdProofNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 320, -1));

        txtIdProofNumber.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtIdProofNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 388, 320, 26));

        btnAdd.setBackground(new java.awt.Color(22, 163, 74));
        btnAdd.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel3.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 155, 32));

        btnUpdate.setBackground(new java.awt.Color(37, 99, 235));
        btnUpdate.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel3.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 430, 155, 32));

        btnDelete.setBackground(new java.awt.Color(220, 38, 38));
        btnDelete.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel3.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 155, 32));

        btnSearch.setBackground(new java.awt.Color(217, 119, 6));
        btnSearch.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Search");
        btnSearch.setFocusPainted(false);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel3.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 470, 155, 32));

        btnGetAll.setBackground(new java.awt.Color(71, 85, 105));
        btnGetAll.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnGetAll.setForeground(new java.awt.Color(255, 255, 255));
        btnGetAll.setText("Get All");
        btnGetAll.setFocusPainted(false);
        btnGetAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetAllActionPerformed(evt);
            }
        });
        jPanel3.add(btnGetAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 155, 32));

        btnGetById.setBackground(new java.awt.Color(124, 58, 237));
        btnGetById.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnGetById.setForeground(new java.awt.Color(255, 255, 255));
        btnGetById.setText("Get By ID");
        btnGetById.setFocusPainted(false);
        btnGetById.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetByIdActionPerformed(evt);
            }
        });
        jPanel3.add(btnGetById, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 510, 155, 32));

        btnClear.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnClear.setText("Clear");
        btnClear.setFocusPainted(false);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel3.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 320, 32));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 360, 650));

        tblGuests.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblGuests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "GUEST ID", "FIRST NAME", "LAST NAME", "PHONE", "EMAIL", "ADDRESS", "ID TYPE", "ID NUMBER"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGuests.setRowHeight(30);
        tblGuests.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGuestsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGuests);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 840, 650));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        Clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!validateGuest()) {
            return;
        }

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Guest_Insert(?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, txtFirstName.getText().trim());
            callableStatement.setString(2, txtLastName.getText().trim());
            callableStatement.setString(3, txtPhone.getText().trim());
            callableStatement.setString(4, txtEmail.getText().trim());
            callableStatement.setString(5, txtAddress.getText().trim());
            callableStatement.setString(6, cmbIdProofType.getSelectedItem().toString());
            callableStatement.setString(7, txtIdProofNumber.getText().trim());
            callableStatement.registerOutParameter(8, java.sql.Types.VARCHAR);
            callableStatement.execute();

            String result = callableStatement.getString(8);
            if (isFailedResult(result)) {
                JOptionPane.showMessageDialog(this, result, "Add Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, result == null ? "Guest inserted successfully" : result);
            Clear();
            LoadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedGuestId == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a guest from the table first.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!validateGuest()) {
            return;
        }

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Guest_Update(?,?,?,?,?,?,?,?,?)}");
            callableStatement.setInt(1, selectedGuestId);
            callableStatement.setString(2, txtFirstName.getText().trim());
            callableStatement.setString(3, txtLastName.getText().trim());
            callableStatement.setString(4, txtPhone.getText().trim());
            callableStatement.setString(5, txtEmail.getText().trim());
            callableStatement.setString(6, txtAddress.getText().trim());
            callableStatement.setString(7, cmbIdProofType.getSelectedItem().toString());
            callableStatement.setString(8, txtIdProofNumber.getText().trim());
            callableStatement.registerOutParameter(9, java.sql.Types.VARCHAR);
            callableStatement.execute();

            String result = callableStatement.getString(9);
            if (isFailedResult(result)) {
                JOptionPane.showMessageDialog(this, result, "Update Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, result == null ? "Guest updated successfully" : result);
            Clear();
            LoadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedGuestId == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a guest from the table first.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this guest?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Guest_Delete(?,?)}");
            callableStatement.setInt(1, selectedGuestId);
            callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
            callableStatement.execute();

            String result = callableStatement.getString(2);
            JOptionPane.showMessageDialog(this, result == null ? "Guest deleted successfully" : result);
            Clear();
            LoadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String searchText = JOptionPane.showInputDialog(
                this,
                "Enter name, phone, email or ID proof number:",
                "Search Guest",
                JOptionPane.QUESTION_MESSAGE
        );

        if (searchText == null) {
            return;
        }

        searchText = searchText.trim();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a search value.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Guest_Search(?)}");
            callableStatement.setString(1, searchText);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) tblGuests.getModel();
                model.setRowCount(0);

                boolean found = false;
                while (resultSet.next()) {
                    found = true;
                    model.addRow(new Object[]{
                        resultSet.getInt("GuestId"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email"),
                        resultSet.getString("Address"),
                        resultSet.getString("IdProofType"),
                        resultSet.getString("IdProofNumber")
                    });
                }

                if (!found) {
                    JOptionPane.showMessageDialog(
                            this,
                            "No guest found for: " + searchText,
                            "Search Result",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    LoadTable();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnGetAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetAllActionPerformed
        LoadTable();
    }//GEN-LAST:event_btnGetAllActionPerformed

    private void btnGetByIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetByIdActionPerformed
        String input = JOptionPane.showInputDialog(this, "Enter Guest Id", "Get Guest", JOptionPane.QUESTION_MESSAGE);

        if (input == null) {
            return;
        }

        input = input.trim();
        if (!input.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Guest Id.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Guest_GetById(?)}");
            callableStatement.setInt(1, Integer.parseInt(input));

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) tblGuests.getModel();
                model.setRowCount(0);

                if (resultSet.next()) {
                    fillForm(resultSet);
                    model.addRow(new Object[]{
                        resultSet.getInt("GuestId"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Email"),
                        resultSet.getString("Address"),
                        resultSet.getString("IdProofType"),
                        resultSet.getString("IdProofNumber")
                    });
                    tblGuests.setRowSelectionInterval(0, 0);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "No guest found for Id: " + input,
                            "Get By ID",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    LoadTable();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGetByIdActionPerformed

    private void tblGuestsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGuestsMouseClicked
        int selectedRow = tblGuests.getSelectedRow();

        if (selectedRow != -1) {
            selectedGuestId = Integer.parseInt(tblGuests.getValueAt(selectedRow, 0).toString());
            txtFirstName.setText(tblGuests.getValueAt(selectedRow, 1).toString());
            txtLastName.setText(tblGuests.getValueAt(selectedRow, 2).toString());
            txtPhone.setText(tblGuests.getValueAt(selectedRow, 3).toString());
            txtEmail.setText(tblGuests.getValueAt(selectedRow, 4).toString());
            txtAddress.setText(tblGuests.getValueAt(selectedRow, 5).toString());
            cmbIdProofType.setSelectedItem(tblGuests.getValueAt(selectedRow, 6).toString());
            txtIdProofNumber.setText(tblGuests.getValueAt(selectedRow, 7).toString());
        }
    }//GEN-LAST:event_tblGuestsMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuestsFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnGetAll;
    private javax.swing.JButton btnGetById;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbIdProofType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblFormTitle;
    private javax.swing.JLabel lblIdProofNumber;
    private javax.swing.JLabel lblIdProofType;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JTable tblGuests;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtIdProofNumber;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
