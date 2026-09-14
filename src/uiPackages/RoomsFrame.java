package uiPackages;

import java.sql.*;
import java.util.HashMap;
import ProjectSources.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class RoomsFrame extends javax.swing.JFrame {

    private Integer selectedRoomId = -1;
    private HashMap<String, Integer> roomTypeIds = new HashMap<>();

    public RoomsFrame() {
        initComponents();

        LoadRoomTypes();
        Clear();
        LoadTable();
    }

    public void Clear() {
        selectedRoomId = -1;
        txtRoomNumber.setText("");
        txtFloorNumber.setText("");
        if (cmbRoomType.getItemCount() > 0) {
            cmbRoomType.setSelectedIndex(0);
        }
        if (cmbStatus.getItemCount() > 0) {
            cmbStatus.setSelectedIndex(0);
        }
        tblRooms.clearSelection();
    }

    public void LoadRoomTypes() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Select Room Type");
        roomTypeIds.clear();

        try (Connection connection = JDBC.con()) {
            String sql = "SELECT RoomTypeId, RoomTypeName FROM RoomTypes ORDER BY RoomTypeName";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String roomTypeName = resultSet.getString("RoomTypeName");
                roomTypeIds.put(roomTypeName, resultSet.getInt("RoomTypeId"));
                model.addElement(roomTypeName);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading room types: " + e.getMessage()
            );
        }

        cmbRoomType.setModel(model);
    }

    public void LoadTable() {
        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Room_GetAll}");
            ResultSet resultSet = callableStatement.executeQuery();
            fillTable(resultSet);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading rooms: " + e.getMessage()
            );
        }
    }

    private void fillTable(ResultSet resultSet) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tblRooms.getModel();
        model.setRowCount(0);

        while (resultSet.next()) {
            Object[] row = {
                resultSet.getInt("RoomId"),
                resultSet.getString("RoomNumber"),
                resultSet.getString("RoomTypeName"),
                resultSet.getInt("FloorNumber"),
                resultSet.getString("RoomStatus")
            };
            model.addRow(row);
        }
    }

    private Integer getSelectedRoomTypeId() {
        Object selected = cmbRoomType.getSelectedItem();
        if (selected == null) {
            return null;
        }
        return roomTypeIds.get(selected.toString());
    }

    private boolean validateRoom() {
        String roomNumber = txtRoomNumber.getText().trim();
        String floorNumber = txtFloorNumber.getText().trim();
        Integer roomTypeId = getSelectedRoomTypeId();
        String status = cmbStatus.getSelectedItem() == null
                ? ""
                : cmbStatus.getSelectedItem().toString().trim();

        if (roomNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Room Number is required.");
            txtRoomNumber.requestFocus();
            return false;
        }

        if (!roomNumber.matches("^[A-Za-z0-9-]{1,10}$")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Room Number should contain letters, numbers or hyphen only."
            );
            txtRoomNumber.requestFocus();
            return false;
        }

        if (roomTypeId == null) {
            JOptionPane.showMessageDialog(this, "Please select a room type.");
            cmbRoomType.requestFocus();
            return false;
        }

        if (floorNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Floor Number is required.");
            txtFloorNumber.requestFocus();
            return false;
        }

        if (!floorNumber.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, "Floor Number must contain numbers only.");
            txtFloorNumber.requestFocus();
            return false;
        }

        int floorValue = Integer.parseInt(floorNumber);
        if (floorValue < 0 || floorValue > 50) {
            JOptionPane.showMessageDialog(this, "Floor Number must be between 0 and 50.");
            txtFloorNumber.requestFocus();
            return false;
        }

        if (status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a status.");
            cmbStatus.requestFocus();
            return false;
        }

        return true;
    }

    private void updateRoom(
            int roomId,
            String roomNumber,
            int roomTypeId,
            int floorNumber,
            String roomStatus) {

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Room_Update(?,?,?,?,?,?)}");
            callableStatement.setInt(1, roomId);
            callableStatement.setString(2, roomNumber);
            callableStatement.setInt(3, roomTypeId);
            callableStatement.setInt(4, floorNumber);
            callableStatement.setString(5, roomStatus);
            callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);
            callableStatement.execute();

            String result = callableStatement.getString(6);
            if (result != null && result.toLowerCase().contains("not found")) {
                JOptionPane.showMessageDialog(
                        this,
                        result,
                        "Update Failed",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            if (result != null && result.toLowerCase().contains("exist")) {
                JOptionPane.showMessageDialog(
                        this,
                        result,
                        "Update Failed",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    result == null ? "Room updated successfully." : result,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            LoadTable();
            Clear();
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
        lblRoomNumber = new javax.swing.JLabel();
        txtRoomNumber = new javax.swing.JTextField();
        lblRoomType = new javax.swing.JLabel();
        cmbRoomType = new javax.swing.JComboBox<>();
        lblFloorNumber = new javax.swing.JLabel();
        txtFloorNumber = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnGetAll = new javax.swing.JButton();
        btnGetById = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRooms = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rooms");
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
        jLabel2.setText("Rooms");

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
        lblFormTitle.setText("Room Details");
        jPanel3.add(lblFormTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 300, -1));

        lblRoomNumber.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblRoomNumber.setForeground(new java.awt.Color(255, 255, 255));
        lblRoomNumber.setText("ROOM NUMBER");
        jPanel3.add(lblRoomNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 300, -1));

        txtRoomNumber.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtRoomNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 300, 28));

        lblRoomType.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblRoomType.setForeground(new java.awt.Color(255, 255, 255));
        lblRoomType.setText("ROOM TYPE");
        jPanel3.add(lblRoomType, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 300, -1));

        cmbRoomType.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmbRoomType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Room Type" }));
        jPanel3.add(cmbRoomType, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 300, 28));

        lblFloorNumber.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblFloorNumber.setForeground(new java.awt.Color(255, 255, 255));
        lblFloorNumber.setText("FLOOR NUMBER");
        jPanel3.add(lblFloorNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 300, -1));

        txtFloorNumber.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtFloorNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 300, 28));

        lblStatus.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblStatus.setText("STATUS");
        jPanel3.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 300, -1));

        cmbStatus.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available", "Occupied", "Maintenance", "Cleaning" }));
        jPanel3.add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 300, 28));

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
        jPanel3.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 145, 32));

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
        jPanel3.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 310, 145, 32));

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
        jPanel3.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 145, 32));

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
        jPanel3.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 350, 145, 32));

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
        jPanel3.add(btnGetAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 145, 32));

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
        jPanel3.add(btnGetById, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 390, 145, 32));

        btnClear.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnClear.setText("Clear");
        btnClear.setFocusPainted(false);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel3.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 300, 32));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 340, 550));

        tblRooms.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        tblRooms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ROOM ID", "ROOM NUMBER", "ROOM TYPE", "FLOOR", "STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRooms.setRowHeight(32);
        tblRooms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRoomsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblRooms);

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
        Clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!validateRoom()) {
            return;
        }

        String roomNumber = txtRoomNumber.getText().trim();
        int roomTypeId = getSelectedRoomTypeId();
        int floorNumber = Integer.parseInt(txtFloorNumber.getText().trim());
        String status = cmbStatus.getSelectedItem().toString();

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Room_Insert(?,?,?,?,?)}");
            callableStatement.setString(1, roomNumber);
            callableStatement.setInt(2, roomTypeId);
            callableStatement.setInt(3, floorNumber);
            callableStatement.setString(4, status);
            callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
            callableStatement.execute();

            String result = callableStatement.getString(5);
            if (result != null && result.toLowerCase().contains("exist")) {
                JOptionPane.showMessageDialog(this, result, "Add Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, result == null ? "Room created successfully!" : result);
            Clear();
            LoadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnGetAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetAllActionPerformed
        LoadTable();
    }//GEN-LAST:event_btnGetAllActionPerformed

    private void tblRoomsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRoomsMouseClicked
        int selectedRow = tblRooms.getSelectedRow();

        if (selectedRow != -1) {
            selectedRoomId = Integer.parseInt(
                    tblRooms.getValueAt(selectedRow, 0).toString()
            );

            String roomNumber = tblRooms.getValueAt(selectedRow, 1).toString();
            String roomTypeName = tblRooms.getValueAt(selectedRow, 2).toString();
            String floorNumber = tblRooms.getValueAt(selectedRow, 3).toString();
            String status = tblRooms.getValueAt(selectedRow, 4).toString();

            txtRoomNumber.setText(roomNumber);
            cmbRoomType.setSelectedItem(roomTypeName);
            txtFloorNumber.setText(floorNumber);
            cmbStatus.setSelectedItem(status);
        }
    }//GEN-LAST:event_tblRoomsMouseClicked

    private void btnGetByIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetByIdActionPerformed
        String input = JOptionPane.showInputDialog(this, "Enter Room Id", "Get Room", JOptionPane.QUESTION_MESSAGE);

        if (input == null) {
            return;
        }

        input = input.trim();
        if (!input.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Room Id.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int roomId = Integer.parseInt(input);
        String sql = "SELECT r.RoomId, r.RoomNumber, rt.RoomTypeName, "
                    + "r.FloorNumber, r.RoomStatus "
                + "FROM Rooms r "
                + "INNER JOIN RoomTypes rt ON r.RoomTypeId = rt.RoomTypeId "
                + "WHERE r.RoomId = ?";

        try (Connection connection = JDBC.con(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, roomId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) tblRooms.getModel();
                model.setRowCount(0);

                if (resultSet.next()) {
                    selectedRoomId = resultSet.getInt("RoomId");
                    txtRoomNumber.setText(resultSet.getString("RoomNumber"));
                    cmbRoomType.setSelectedItem(resultSet.getString("RoomTypeName"));
                    txtFloorNumber.setText(String.valueOf(resultSet.getInt("FloorNumber")));
                    cmbStatus.setSelectedItem(resultSet.getString("RoomStatus"));

                    Object[] row = {
                        selectedRoomId,
                        resultSet.getString("RoomNumber"),
                        resultSet.getString("RoomTypeName"),
                        resultSet.getInt("FloorNumber"),
                        resultSet.getString("RoomStatus")
                    };
                    model.addRow(row);
                    tblRooms.setRowSelectionInterval(0, 0);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "No room found for Id: " + roomId,
                            "Get By ID",
                            JOptionPane.INFORMATION_MESSAGE
                    );
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
    }//GEN-LAST:event_btnGetByIdActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedRoomId == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a room from the table first.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!validateRoom()) {
            return;
        }

        updateRoom(
                selectedRoomId,
                txtRoomNumber.getText().trim(),
                getSelectedRoomTypeId(),
                Integer.parseInt(txtFloorNumber.getText().trim()),
                cmbStatus.getSelectedItem().toString()
        );
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedRoomId == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a room from the table first.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this room?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Room_Delete(?,?)}");
            callableStatement.setInt(1, selectedRoomId);
            callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
            callableStatement.execute();

            String result = callableStatement.getString(2);
            JOptionPane.showMessageDialog(
                    this,
                    result == null ? "Room deleted successfully." : result
            );
            Clear();
            LoadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String searchText = JOptionPane.showInputDialog(
                this,
                "Enter Room Number:",
                "Search Room",
                JOptionPane.QUESTION_MESSAGE
        );

        if (searchText == null) {
            return;
        }

        searchText = searchText.trim();

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a room number.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String sql = "SELECT r.RoomId, r.RoomNumber, rt.RoomTypeName, "
                    + "r.FloorNumber, r.RoomStatus "
                + "FROM Rooms r "
                + "INNER JOIN RoomTypes rt ON r.RoomTypeId = rt.RoomTypeId "
                + "WHERE r.RoomNumber LIKE ?";

        try (Connection connection = JDBC.con(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + searchText + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) tblRooms.getModel();
                model.setRowCount(0);

                boolean found = false;
                while (resultSet.next()) {
                    found = true;
                    Object[] row = {
                        resultSet.getInt("RoomId"),
                        resultSet.getString("RoomNumber"),
                        resultSet.getString("RoomTypeName"),
                        resultSet.getInt("FloorNumber"),
                        resultSet.getString("RoomStatus")
                    };
                    model.addRow(row);
                }

                if (!found) {
                    JOptionPane.showMessageDialog(
                            this,
                            "No room found for: " + searchText,
                            "Search Result",
                            JOptionPane.INFORMATION_MESSAGE
                    );
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RoomsFrame().setVisible(true);
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
    private javax.swing.JComboBox<String> cmbRoomType;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFloorNumber;
    private javax.swing.JLabel lblFormTitle;
    private javax.swing.JLabel lblRoomNumber;
    private javax.swing.JLabel lblRoomType;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTable tblRooms;
    private javax.swing.JTextField txtFloorNumber;
    private javax.swing.JTextField txtRoomNumber;
    // End of variables declaration//GEN-END:variables
}
