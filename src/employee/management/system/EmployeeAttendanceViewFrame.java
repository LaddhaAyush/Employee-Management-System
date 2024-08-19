package employee.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import employee.management.system.EmployeeDashboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeAttendanceViewFrame extends JFrame {
    private JTable table;
    private String employeeID;
    private Connection conn;

    public EmployeeAttendanceViewFrame(String employeeID) {
        this.employeeID = employeeID;

        setTitle("Employee Attendance View");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        conn = createConnection();

        String[] columnNames = {"Attendance ID", "Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][3], columnNames);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a "Back" button
        JButton backButton = new JButton("Back to Employee Dashboard");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current frame and go back to the employee dashboard
                dispose();
                openEmployeeDashboard();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load attendance data for the employee
        refreshTable(employeeID);
    }

    private void openEmployeeDashboard() {
        EmployeeDashboard employeeDashboard = new EmployeeDashboard(employeeID);
        employeeDashboard.setVisible(true);
    }

    private Connection createConnection() {
        String url = "jdbc:mysql://localhost/employeemanagement";
        String username = "root";
        String password = "Ayush110904";
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the database");
            return null;
        }
    }

    private void refreshTable(String employeeID) {
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not available.");
            return;
        }

        String sql = "SELECT attendance_id, date, status FROM employee_attendance WHERE empID = ?";
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employeeID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int attendanceID = rs.getInt("attendance_id");
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                model.addRow(new Object[]{attendanceID, formatDate(date), status});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching attendance data");
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EmployeeAttendanceViewFrame frame = new EmployeeAttendanceViewFrame("E1"); // Replace with the actual employee ID
                frame.setVisible(true);
            }
        });
    }
}
