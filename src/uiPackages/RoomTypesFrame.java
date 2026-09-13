package uiPackages;

import java.sql.*;
import ProjectSources.*;
import java.math.BigDecimal;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class RoomTypesFrame extends javax.swing.JFrame {

    private Integer selectedRoomTypeId = -1;

    public RoomTypesFrame() {
        initComponents();

        Clear();
        LoadTable();
    }

    public void Clear() {
        selectedRoomTypeId = -1;
        txtRoomTypeName.setText("");
        txtDescription.setText("");
        txtMaxGuests.setText("");
        txtPricePerNight.setText("");
        tblRoomTypes.clearSelection();
    }

    public void LoadTable() {
        try (Connection connection = JDBC.con()) {

            String sql = "SELECT RoomTypeId, RoomTypeName, Description, "
                    + "PricePerNight, MaxGuests "
                    + "FROM RoomTypes";

            PreparedStatement preparedStatement
                    = connection.prepareStatement(sql);

            ResultSet resultSet
                    = preparedStatement.executeQuery();

            DefaultTableModel model
                    = (DefaultTableModel) tblRoomTypes.getModel();

            model.setRowCount(0);

            while (resultSet.next()) {

                Object[] row = {
                    resultSet.getInt("RoomTypeId"),
                    resultSet.getString("RoomTypeName"),
                    resultSet.getString("Description"),
                    resultSet.getBigDecimal("PricePerNight"),
                    resultSet.getInt("MaxGuests")
                };

                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading room types: " + e.getMessage()
            );
        }
    }

    private boolean validateRoomType() {

        String roomType = txtRoomTypeName.getText().trim();
        String description = txtDescription.getText().trim();
        String price = txtPricePerNight.getText().trim();
        String guests = txtMaxGuests.getText().trim();

        // Empty validation
        if (roomType.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Room Type Name is required.");
            txtRoomTypeName.requestFocus();
            return false;
        }

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Description is required.");
            txtDescription.requestFocus();
            return false;
        }

        if (price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Price Per Night is required.");
            txtPricePerNight.requestFocus();
            return false;
        }

        if (guests.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Max Guests is required.");
            txtMaxGuests.requestFocus();
            return false;
        }

        // Room Type Name validation
        if (!roomType.matches("^[A-Za-z ]{2,50}$")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Room Type Name should contain only letters and spaces."
            );
            txtRoomTypeName.requestFocus();
            return false;
        }

        // Description validation
        if (description.length() < 5 || description.length() > 255) {
            JOptionPane.showMessageDialog(
                    this,
                    "Description must be between 5 and 255 characters."
            );
            txtDescription.requestFocus();
            return false;
        }

        // Price validation
        if (!price.matches("^\\d+(\\.\\d{1,2})?$")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Price must be a valid number. Example: 2500 or 2500.50"
            );
            txtPricePerNight.requestFocus();
            return false;
        }

        double priceValue = Double.parseDouble(price);

        if (priceValue <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Price must be greater than 0."
            );
            txtPricePerNight.requestFocus();
            return false;
        }

        // Guests validation
        if (!guests.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Max Guests must contain numbers only."
            );
            txtMaxGuests.requestFocus();
            return false;
        }

        int guestValue = Integer.parseInt(guests);

        if (guestValue <= 0 || guestValue > 20) {
            JOptionPane.showMessageDialog(
                    this,
                    "Max Guests must be between 1 and 20."
            );
            txtMaxGuests.requestFocus();
            return false;
        }

        return true;
    }

    private void updateRoomType(
            int roomTypeId,
            String roomTypeName,
            String description,
            BigDecimal pricePerNight,
            int maxGuests) {

        String sql = "UPDATE RoomTypes "
                + "SET RoomTypeName = ?, "
                + "Description = ?, "
                + "PricePerNight = ?, "
                + "MaxGuests = ? "
                + "WHERE RoomTypeId = ?";

        try (Connection connection = JDBC.con(); PreparedStatement preparedStatement
                = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, roomTypeName);
            preparedStatement.setString(2, description);
            preparedStatement.setBigDecimal(3, pricePerNight);
            preparedStatement.setInt(4, maxGuests);
            preparedStatement.setInt(5, roomTypeId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Room type updated successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Refresh table
                LoadTable();

                // Clear fields
                Clear();

                // Reset selected ID
                selectedRoomTypeId = -1;

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Room type not found.",
                        "Update Failed",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
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
        lblRoomTypeName = new javax.swing.JLabel();
        txtRoomTypeName = new javax.swing.JTextField();
        lblDescription = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        lblPricePerNight = new javax.swing.JLabel();
        txtPricePerNight = new javax.swing.JTextField();
        lblMaxGuests = new javax.swing.JLabel();
        txtMaxGuests = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnGetAll = new javax.swing.JButton();
        btnGetById = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRoomTypes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Room Types");
        setMaximumSize(new java.awt.Dimension(1100, 600));
        setMinimumSize(new java.awt.Dimension(1100, 600));
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(27, 42, 80));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DAIMOND HOTEL BOOKING");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Room Types");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(280, 280, 280)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(448, Short.MAX_VALUE))
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

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 50));

        jPanel3.setBackground(new java.awt.Color(15, 30, 61));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFormTitle.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lblFormTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblFormTitle.setText("Room Type Details");
        jPanel3.add(lblFormTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 300, -1));

        lblRoomTypeName.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblRoomTypeName.setForeground(new java.awt.Color(255, 255, 255));
        lblRoomTypeName.setText("ROOM TYPE NAME");
        jPanel3.add(lblRoomTypeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 300, -1));

        txtRoomTypeName.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtRoomTypeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 300, 28));

        lblDescription.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblDescription.setForeground(new java.awt.Color(255, 255, 255));
        lblDescription.setText("DESCRIPTION");
        jPanel3.add(lblDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 300, -1));

        txtDescription.setColumns(20);
        txtDescription.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        txtDescription.setLineWrap(true);
        txtDescription.setRows(3);
        txtDescription.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtDescription);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 300, 80));

        lblPricePerNight.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblPricePerNight.setForeground(new java.awt.Color(255, 255, 255));
        lblPricePerNight.setText("PRICE PER NIGHT");
        jPanel3.add(lblPricePerNight, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 300, -1));

        txtPricePerNight.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtPricePerNight, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 300, 28));

        lblMaxGuests.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblMaxGuests.setForeground(new java.awt.Color(255, 255, 255));
        lblMaxGuests.setText("MAX GUESTS");
        jPanel3.add(lblMaxGuests, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 300, -1));

        txtMaxGuests.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtMaxGuests, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 300, 28));

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
        jPanel3.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 145, 32));

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
        jPanel3.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 350, 145, 32));

        btnDelete.setBackground(new java.awt.Color(220, 38, 38));
        btnDelete.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.setFocusPainted(false);
        jPanel3.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 145, 32));

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
        jPanel3.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 390, 145, 32));

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
        jPanel3.add(btnGetAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 145, 32));

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
        jPanel3.add(btnGetById, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 430, 145, 32));

        btnClear.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnClear.setText("Clear");
        btnClear.setFocusPainted(false);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel3.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 300, 32));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 340, 550));

        tblRoomTypes.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        tblRoomTypes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ROOM ID", "ROOM TYPE", "DESCRIPTION", "PRICE PER NIGHT", "MAX GUESTS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRoomTypes.setRowHeight(32);
        tblRoomTypes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRoomTypesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblRoomTypes);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, 760, 550));

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
        // TODO add your handling code here:
        Clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!validateRoomType()) {
            return;
        }

        String roomType = txtRoomTypeName.getText().trim();
        String description = txtDescription.getText().trim();
        BigDecimal price = new BigDecimal(txtPricePerNight.getText().trim());
        int guests = Integer.parseInt(txtMaxGuests.getText().trim());

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_RoomType_Insert(?,?,?,?,?)}");
            callableStatement.setString(1, roomType);
            callableStatement.setString(2, description);
            callableStatement.setBigDecimal(3, price);
            callableStatement.setInt(4, guests);
            callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
            callableStatement.execute();

            String result = callableStatement.getString(5);
            if (result != null && result.toLowerCase().contains("exist")) {
                JOptionPane.showMessageDialog(this, result, "Add Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, result == null ? "Room type created successfully!" : result);
            Clear();
            LoadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnGetAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetAllActionPerformed
        LoadTable();
    }//GEN-LAST:event_btnGetAllActionPerformed

    private void tblRoomTypesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRoomTypesMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblRoomTypes.getSelectedRow();

        if (selectedRow != -1) {

            // Get Room Type ID
            selectedRoomTypeId = Integer.parseInt(
                    tblRoomTypes.getValueAt(selectedRow, 0).toString()
            );

            // Get values from selected row
            String roomTypeName
                    = tblRoomTypes.getValueAt(selectedRow, 1).toString();

            String description
                    = tblRoomTypes.getValueAt(selectedRow, 2).toString();

            String pricePerNight
                    = tblRoomTypes.getValueAt(selectedRow, 3).toString();

            String maxGuests
                    = tblRoomTypes.getValueAt(selectedRow, 4).toString();

            // Set values into fields
            txtRoomTypeName.setText(roomTypeName);
            txtDescription.setText(description);
            txtPricePerNight.setText(pricePerNight);
            txtMaxGuests.setText(maxGuests);
        }
    }//GEN-LAST:event_tblRoomTypesMouseClicked

    private void btnGetByIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetByIdActionPerformed
        // TODO add your handling code here:
        String input = JOptionPane.showInputDialog(this, "Enter room Id", "Get Room Type:", JOptionPane.QUESTION_MESSAGE);

        if (input == null) {
            return;
        }
    }//GEN-LAST:event_btnGetByIdActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (selectedRoomTypeId == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a room type from the table first.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String roomTypeName = txtRoomTypeName.getText().trim();
        String description = txtDescription.getText().trim();
        String priceText = txtPricePerNight.getText().trim();
        String maxGuestsText = txtMaxGuests.getText().trim();

        if (roomTypeName.isEmpty()
                || description.isEmpty()
                || priceText.isEmpty()
                || maxGuestsText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {

            BigDecimal pricePerNight = new BigDecimal(priceText);
            int maxGuests = Integer.parseInt(maxGuestsText);

            updateRoomType(
                    selectedRoomTypeId,
                    roomTypeName,
                    description,
                    pricePerNight,
                    maxGuests
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price and Max Guests must contain valid numbers.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String searchText = JOptionPane.showInputDialog(
                this,
                "Enter Room Type Name:",
                "Search Room Type",
                JOptionPane.QUESTION_MESSAGE
        );

        if (searchText == null) {
            return;
        }

        searchText = searchText.trim();

        // Empty search
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a room type name.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String sql = "SELECT RoomTypeId, RoomTypeName, Description, "
                + "PricePerNight, MaxGuests "
                + "FROM RoomTypes "
                + "WHERE RoomTypeName LIKE ?";

        try (Connection connection = JDBC.con(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + searchText + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                DefaultTableModel model
                        = (DefaultTableModel) tblRoomTypes.getModel();

                model.setRowCount(0);

                boolean found = false;

                while (resultSet.next()) {

                    found = true;

                    Object[] row = {
                        resultSet.getInt("RoomTypeId"),
                        resultSet.getString("RoomTypeName"),
                        resultSet.getString("Description"),
                        resultSet.getBigDecimal("PricePerNight"),
                        resultSet.getInt("MaxGuests")
                    };

                    model.addRow(row);
                }

                if (!found) {
                    JOptionPane.showMessageDialog(
                            this,
                            "No room type found for: " + searchText,
                            "Search Result",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    // Reload all records
                    LoadTable();
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    public static void main(String args[]) {
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(RoomTypesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(RoomTypesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(RoomTypesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(RoomTypesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RoomTypesFrame().setVisible(true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblFormTitle;
    private javax.swing.JLabel lblMaxGuests;
    private javax.swing.JLabel lblPricePerNight;
    private javax.swing.JLabel lblRoomTypeName;
    private javax.swing.JTable tblRoomTypes;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtMaxGuests;
    private javax.swing.JTextField txtPricePerNight;
    private javax.swing.JTextField txtRoomTypeName;
    // End of variables declaration//GEN-END:variables
}
