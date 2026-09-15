package uiPackages;

import java.sql.*;
import java.util.HashMap;
import ProjectSources.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BookingsFrame extends javax.swing.JFrame {

    private Integer selectedBookingId = -1;
    private int createdByUserId = -1;
    private HashMap<String, Integer> guestMap = new HashMap<>();
    private HashMap<String, Integer> roomMap = new HashMap<>();

    public BookingsFrame() {
        initComponents();
        LoadCreatedByUser();
        LoadGuests();
        LoadRooms();
        Clear();
        LoadTable();
    }

    public void Clear() {
        selectedBookingId = -1;
        txtCheckInDate.setText("");
        txtCheckOutDate.setText("");
        txtNumberOfGuests.setText("");
        if (cmbGuest.getItemCount() > 0) {
            cmbGuest.setSelectedIndex(0);
        }
        if (cmbRoom.getItemCount() > 0) {
            cmbRoom.setSelectedIndex(0);
        }
        if (cmbBookingStatus.getItemCount() > 0) {
            cmbBookingStatus.setSelectedIndex(0);
        }
        tblBookings.clearSelection();
    }

    private void LoadCreatedByUser() {
        try (Connection connection = JDBC.con()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT TOP 1 UserId FROM Users ORDER BY UserId"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                createdByUserId = resultSet.getInt("UserId");
            }
        } catch (Exception e) {
            createdByUserId = -1;
        }
    }

    public void LoadGuests() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Select Guest");
        guestMap.clear();

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Guest_GetAll}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String item = resultSet.getInt("GuestId") + " - "
                        + resultSet.getString("FirstName") + " "
                        + resultSet.getString("LastName");
                guestMap.put(item, resultSet.getInt("GuestId"));
                model.addElement(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading guests: " + e.getMessage());
        }

        cmbGuest.setModel(model);
    }

    public void LoadRooms() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Select Room");
        roomMap.clear();

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Room_GetAll}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String item = resultSet.getString("RoomNumber") + " - "
                        + resultSet.getString("RoomTypeName");
                roomMap.put(item, resultSet.getInt("RoomId"));
                model.addElement(item);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading rooms: " + e.getMessage());
        }

        cmbRoom.setModel(model);
    }

    public void LoadTable() {
        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Booking_GetAll}");
            ResultSet resultSet = callableStatement.executeQuery();
            fillTable(resultSet);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading bookings: " + e.getMessage());
        }
    }

    private void fillTable(ResultSet resultSet) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tblBookings.getModel();
        model.setRowCount(0);

        while (resultSet.next()) {
            model.addRow(new Object[]{
                resultSet.getInt("BookingId"),
                resultSet.getString("GuestName"),
                resultSet.getString("RoomNumber"),
                resultSet.getDate("CheckInDate"),
                resultSet.getDate("CheckOutDate"),
                resultSet.getInt("NumberOfGuests"),
                resultSet.getString("BookingStatus"),
                resultSet.getBigDecimal("TotalRoomAmount")
            });
        }
    }

    private Integer getSelectedGuestId() {
        Object selected = cmbGuest.getSelectedItem();
        if (selected == null) {
            return null;
        }
        return guestMap.get(selected.toString());
    }

    private Integer getSelectedRoomId() {
        Object selected = cmbRoom.getSelectedItem();
        if (selected == null) {
            return null;
        }
        return roomMap.get(selected.toString());
    }

    private void selectGuestByName(String guestName) {
        for (int i = 0; i < cmbGuest.getItemCount(); i++) {
            if (cmbGuest.getItemAt(i).contains(guestName)) {
                cmbGuest.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selectRoomByNumber(String roomNumber) {
        for (int i = 0; i < cmbRoom.getItemCount(); i++) {
            if (cmbRoom.getItemAt(i).startsWith(roomNumber + " - ")) {
                cmbRoom.setSelectedIndex(i);
                return;
            }
        }
    }

    private Date parseDate(String value, String fieldName) {
        try {
            return Date.valueOf(value);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, fieldName + " must be in YYYY-MM-DD format.");
            return null;
        }
    }

    private boolean validateBooking(boolean requireStatus) {
        if (getSelectedGuestId() == null) {
            JOptionPane.showMessageDialog(this, "Please select a guest.");
            cmbGuest.requestFocus();
            return false;
        }

        if (getSelectedRoomId() == null) {
            JOptionPane.showMessageDialog(this, "Please select a room.");
            cmbRoom.requestFocus();
            return false;
        }

        String checkInText = txtCheckInDate.getText().trim();
        String checkOutText = txtCheckOutDate.getText().trim();
        String guestsText = txtNumberOfGuests.getText().trim();

        if (checkInText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Check In Date is required.");
            txtCheckInDate.requestFocus();
            return false;
        }

        if (checkOutText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Check Out Date is required.");
            txtCheckOutDate.requestFocus();
            return false;
        }

        Date checkInDate = parseDate(checkInText, "Check In Date");
        if (checkInDate == null) {
            txtCheckInDate.requestFocus();
            return false;
        }

        Date checkOutDate = parseDate(checkOutText, "Check Out Date");
        if (checkOutDate == null) {
            txtCheckOutDate.requestFocus();
            return false;
        }

        if (!checkOutDate.after(checkInDate)) {
            JOptionPane.showMessageDialog(this, "Check-out date must be after Check-in date.");
            txtCheckOutDate.requestFocus();
            return false;
        }

        if (guestsText.isEmpty() || !guestsText.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, "Number of guests must be a valid number.");
            txtNumberOfGuests.requestFocus();
            return false;
        }

        int guests = Integer.parseInt(guestsText);
        if (guests <= 0) {
            JOptionPane.showMessageDialog(this, "Number of guests must be greater than 0.");
            txtNumberOfGuests.requestFocus();
            return false;
        }

        if (requireStatus && (cmbBookingStatus.getSelectedItem() == null
                || cmbBookingStatus.getSelectedItem().toString().trim().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Please select a booking status.");
            cmbBookingStatus.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isFailedResult(String result) {
        if (result == null) {
            return false;
        }
        String lower = result.toLowerCase();
        return lower.contains("exist")
                || lower.contains("not found")
                || lower.contains("must be")
                || lower.contains("already")
                || lower.contains("exceeds")
                || lower.contains("cannot");
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
        lblGuest = new javax.swing.JLabel();
        cmbGuest = new javax.swing.JComboBox<>();
        lblRoom = new javax.swing.JLabel();
        cmbRoom = new javax.swing.JComboBox<>();
        lblCheckInDate = new javax.swing.JLabel();
        txtCheckInDate = new javax.swing.JTextField();
        lblCheckOutDate = new javax.swing.JLabel();
        txtCheckOutDate = new javax.swing.JTextField();
        lblNumberOfGuests = new javax.swing.JLabel();
        txtNumberOfGuests = new javax.swing.JTextField();
        lblBookingStatus = new javax.swing.JLabel();
        cmbBookingStatus = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnGetAll = new javax.swing.JButton();
        btnGetById = new javax.swing.JButton();
        btnCancelBooking = new javax.swing.JButton();
        btnAvailable = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBookings = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bookings");
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
        jLabel2.setText("Bookings");

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
        lblFormTitle.setText("Booking Details");
        jPanel3.add(lblFormTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 12, 320, -1));

        lblGuest.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblGuest.setForeground(new java.awt.Color(255, 255, 255));
        lblGuest.setText("GUEST");
        jPanel3.add(lblGuest, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 320, -1));

        cmbGuest.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmbGuest.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Guest" }));
        jPanel3.add(cmbGuest, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 58, 320, 26));

        lblRoom.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblRoom.setForeground(new java.awt.Color(255, 255, 255));
        lblRoom.setText("ROOM");
        jPanel3.add(lblRoom, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 320, -1));

        cmbRoom.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmbRoom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Room" }));
        jPanel3.add(cmbRoom, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 108, 320, 26));

        lblCheckInDate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblCheckInDate.setForeground(new java.awt.Color(255, 255, 255));
        lblCheckInDate.setText("CHECK IN DATE (YYYY-MM-DD)");
        jPanel3.add(lblCheckInDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 320, -1));

        txtCheckInDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtCheckInDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 158, 320, 26));

        lblCheckOutDate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblCheckOutDate.setForeground(new java.awt.Color(255, 255, 255));
        lblCheckOutDate.setText("CHECK OUT DATE (YYYY-MM-DD)");
        jPanel3.add(lblCheckOutDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 320, -1));

        txtCheckOutDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtCheckOutDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 208, 320, 26));

        lblNumberOfGuests.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblNumberOfGuests.setForeground(new java.awt.Color(255, 255, 255));
        lblNumberOfGuests.setText("NUMBER OF GUESTS");
        jPanel3.add(lblNumberOfGuests, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 320, -1));

        txtNumberOfGuests.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel3.add(txtNumberOfGuests, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 258, 320, 26));

        lblBookingStatus.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblBookingStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblBookingStatus.setText("BOOKING STATUS");
        jPanel3.add(lblBookingStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 320, -1));

        cmbBookingStatus.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmbBookingStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Booked", "CheckedIn", "CheckedOut", "Cancelled" }));
        jPanel3.add(cmbBookingStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 308, 320, 26));

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
        jPanel3.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 155, 32));

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
        jPanel3.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 350, 155, 32));

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
        jPanel3.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 155, 32));

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
        jPanel3.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 390, 155, 32));

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
        jPanel3.add(btnGetAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 155, 32));

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
        jPanel3.add(btnGetById, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 430, 155, 32));

        btnCancelBooking.setBackground(new java.awt.Color(194, 101, 85));
        btnCancelBooking.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnCancelBooking.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelBooking.setText("Cancel Booking");
        btnCancelBooking.setFocusPainted(false);
        btnCancelBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelBookingActionPerformed(evt);
            }
        });
        jPanel3.add(btnCancelBooking, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 155, 32));

        btnAvailable.setBackground(new java.awt.Color(19, 90, 139));
        btnAvailable.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnAvailable.setForeground(new java.awt.Color(255, 255, 255));
        btnAvailable.setText("Available");
        btnAvailable.setFocusPainted(false);
        btnAvailable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvailableActionPerformed(evt);
            }
        });
        jPanel3.add(btnAvailable, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 470, 155, 32));

        btnClear.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        btnClear.setText("Clear");
        btnClear.setFocusPainted(false);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel3.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 320, 32));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 360, 650));

        tblBookings.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblBookings.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BOOKING ID", "GUEST", "ROOM", "CHECK IN", "CHECK OUT", "GUESTS", "STATUS", "AMOUNT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBookings.setRowHeight(30);
        tblBookings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBookingsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBookings);

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
        LoadRooms();
        Clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!validateBooking(false)) {
            return;
        }

        if (createdByUserId == -1) {
            JOptionPane.showMessageDialog(this, "No user found to create booking. Please add a user first.");
            return;
        }

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Booking_Insert(?,?,?,?,?,?,?)}");
            callableStatement.setInt(1, getSelectedGuestId());
            callableStatement.setInt(2, getSelectedRoomId());
            callableStatement.setDate(3, Date.valueOf(txtCheckInDate.getText().trim()));
            callableStatement.setDate(4, Date.valueOf(txtCheckOutDate.getText().trim()));
            callableStatement.setInt(5, Integer.parseInt(txtNumberOfGuests.getText().trim()));
            callableStatement.setInt(6, createdByUserId);
            callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);
            callableStatement.execute();

            String result = callableStatement.getString(7);
            if (isFailedResult(result)) {
                JOptionPane.showMessageDialog(this, result, "Add Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, result == null ? "Booking inserted successfully" : result);
            Clear();
            LoadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedBookingId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking from the table first.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validateBooking(true)) {
            return;
        }

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Booking_Update(?,?,?,?,?,?,?,?)}");
            callableStatement.setInt(1, selectedBookingId);
            callableStatement.setInt(2, getSelectedGuestId());
            callableStatement.setInt(3, getSelectedRoomId());
            callableStatement.setDate(4, Date.valueOf(txtCheckInDate.getText().trim()));
            callableStatement.setDate(5, Date.valueOf(txtCheckOutDate.getText().trim()));
            callableStatement.setInt(6, Integer.parseInt(txtNumberOfGuests.getText().trim()));
            callableStatement.setString(7, cmbBookingStatus.getSelectedItem().toString());
            callableStatement.registerOutParameter(8, java.sql.Types.VARCHAR);
            callableStatement.execute();

            String result = callableStatement.getString(8);
            if (isFailedResult(result)) {
                JOptionPane.showMessageDialog(this, result, "Update Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, result == null ? "Booking updated successfully" : result);
            Clear();
            LoadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedBookingId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking from the table first.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this booking?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Booking_Delete(?,?)}");
            callableStatement.setInt(1, selectedBookingId);
            callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
            callableStatement.execute();
            String result = callableStatement.getString(2);
            JOptionPane.showMessageDialog(this, result == null ? "Booking deleted successfully" : result);
            Clear();
            LoadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String input = JOptionPane.showInputDialog(this, "Enter Guest Id", "Search Bookings", JOptionPane.QUESTION_MESSAGE);
        if (input == null) {
            return;
        }

        input = input.trim();
        if (!input.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Guest Id.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Booking_GetByGuest(?)}");
            callableStatement.setInt(1, Integer.parseInt(input));
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) tblBookings.getModel();
                model.setRowCount(0);
                boolean found = false;
                while (resultSet.next()) {
                    found = true;
                    model.addRow(new Object[]{
                        resultSet.getInt("BookingId"),
                        resultSet.getString("GuestName"),
                        resultSet.getString("RoomNumber"),
                        resultSet.getDate("CheckInDate"),
                        resultSet.getDate("CheckOutDate"),
                        resultSet.getInt("NumberOfGuests"),
                        resultSet.getString("BookingStatus"),
                        resultSet.getBigDecimal("TotalRoomAmount")
                    });
                }
                if (!found) {
                    JOptionPane.showMessageDialog(this, "No bookings found for Guest Id: " + input, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    LoadTable();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnGetAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetAllActionPerformed
        LoadRooms();
        LoadTable();
    }//GEN-LAST:event_btnGetAllActionPerformed

    private void btnGetByIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetByIdActionPerformed
        String input = JOptionPane.showInputDialog(this, "Enter Booking Id", "Get Booking", JOptionPane.QUESTION_MESSAGE);
        if (input == null) {
            return;
        }

        input = input.trim();
        if (!input.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Booking Id.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Booking_GetById(?)}");
            callableStatement.setInt(1, Integer.parseInt(input));
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                DefaultTableModel model = (DefaultTableModel) tblBookings.getModel();
                model.setRowCount(0);
                if (resultSet.next()) {
                    selectedBookingId = resultSet.getInt("BookingId");
                    selectGuestByName(resultSet.getString("GuestName"));
                    selectRoomByNumber(resultSet.getString("RoomNumber"));
                    txtCheckInDate.setText(resultSet.getDate("CheckInDate").toString());
                    txtCheckOutDate.setText(resultSet.getDate("CheckOutDate").toString());
                    txtNumberOfGuests.setText(String.valueOf(resultSet.getInt("NumberOfGuests")));
                    cmbBookingStatus.setSelectedItem(resultSet.getString("BookingStatus"));
                    model.addRow(new Object[]{
                        resultSet.getInt("BookingId"),
                        resultSet.getString("GuestName"),
                        resultSet.getString("RoomNumber"),
                        resultSet.getDate("CheckInDate"),
                        resultSet.getDate("CheckOutDate"),
                        resultSet.getInt("NumberOfGuests"),
                        resultSet.getString("BookingStatus"),
                        resultSet.getBigDecimal("TotalRoomAmount")
                    });
                    tblBookings.setRowSelectionInterval(0, 0);
                } else {
                    JOptionPane.showMessageDialog(this, "No booking found for Id: " + input, "Get By ID", JOptionPane.INFORMATION_MESSAGE);
                    LoadTable();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGetByIdActionPerformed

    private void btnCancelBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelBookingActionPerformed
        if (selectedBookingId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking from the table first.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Cancel this booking?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Booking_Cancel(?,?)}");
            callableStatement.setInt(1, selectedBookingId);
            callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
            callableStatement.execute();
            String result = callableStatement.getString(2);
            JOptionPane.showMessageDialog(this, result == null ? "Booking cancelled successfully" : result);
            Clear();
            LoadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCancelBookingActionPerformed

    private void btnAvailableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvailableActionPerformed
        String checkInText = txtCheckInDate.getText().trim();
        String checkOutText = txtCheckOutDate.getText().trim();

        Date checkInDate = parseDate(checkInText, "Check In Date");
        if (checkInDate == null) {
            txtCheckInDate.requestFocus();
            return;
        }

        Date checkOutDate = parseDate(checkOutText, "Check Out Date");
        if (checkOutDate == null) {
            txtCheckOutDate.requestFocus();
            return;
        }

        if (!checkOutDate.after(checkInDate)) {
            JOptionPane.showMessageDialog(this, "Check-out date must be after Check-in date.");
            return;
        }

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Select Room");
        roomMap.clear();

        try (Connection connection = JDBC.con()) {
            CallableStatement callableStatement = connection.prepareCall("{call sp_Booking_GetAvailableRooms(?,?)}");
            callableStatement.setDate(1, checkInDate);
            callableStatement.setDate(2, checkOutDate);
            ResultSet resultSet = callableStatement.executeQuery();

            int count = 0;
            while (resultSet.next()) {
                count++;
                String item = resultSet.getString("RoomNumber") + " - "
                        + resultSet.getString("RoomTypeName");
                roomMap.put(item, resultSet.getInt("RoomId"));
                model.addElement(item);
            }

            cmbRoom.setModel(model);
            JOptionPane.showMessageDialog(this, count + " available room(s) found for the selected dates.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAvailableActionPerformed

    private void tblBookingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBookingsMouseClicked
        int selectedRow = tblBookings.getSelectedRow();
        if (selectedRow != -1) {
            selectedBookingId = Integer.parseInt(tblBookings.getValueAt(selectedRow, 0).toString());
            selectGuestByName(tblBookings.getValueAt(selectedRow, 1).toString());
            selectRoomByNumber(tblBookings.getValueAt(selectedRow, 2).toString());
            txtCheckInDate.setText(tblBookings.getValueAt(selectedRow, 3).toString());
            txtCheckOutDate.setText(tblBookings.getValueAt(selectedRow, 4).toString());
            txtNumberOfGuests.setText(tblBookings.getValueAt(selectedRow, 5).toString());
            cmbBookingStatus.setSelectedItem(tblBookings.getValueAt(selectedRow, 6).toString());
        }
    }//GEN-LAST:event_tblBookingsMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookingsFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAvailable;
    private javax.swing.JButton btnCancelBooking;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnGetAll;
    private javax.swing.JButton btnGetById;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbBookingStatus;
    private javax.swing.JComboBox<String> cmbGuest;
    private javax.swing.JComboBox<String> cmbRoom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBookingStatus;
    private javax.swing.JLabel lblCheckInDate;
    private javax.swing.JLabel lblCheckOutDate;
    private javax.swing.JLabel lblFormTitle;
    private javax.swing.JLabel lblGuest;
    private javax.swing.JLabel lblNumberOfGuests;
    private javax.swing.JLabel lblRoom;
    private javax.swing.JTable tblBookings;
    private javax.swing.JTextField txtCheckInDate;
    private javax.swing.JTextField txtCheckOutDate;
    private javax.swing.JTextField txtNumberOfGuests;
    // End of variables declaration//GEN-END:variables
}
