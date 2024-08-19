package employee.management.system;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BossAttendanceFrame extends JFrame {
    private JTable table;
    private JComboBox<String> employeeComboBox;
    private JDateChooser dateChooser; // Use JDateChooser for date input
    private JButton markPresentButton;
    private JButton markAbsentButton;
    private Connection conn;

    public BossAttendanceFrame() {
        setTitle("Simple Attendance");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        conn = createConnection();

        employeeComboBox = new JComboBox<>(fetchEmployeeNames());

        dateChooser = new JDateChooser(); // Initialize JDateChooser

        markPresentButton = new JButton("Mark Present");
        markAbsentButton = new JButton("Mark Absent");

        employeeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedEmployee = (String) employeeComboBox.getSelectedItem();
                String employeeID = getEmployeeID(selectedEmployee);
                refreshTable(employeeID);
            }
        });

        markPresentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markAttendance(true);
            }
        });

        markAbsentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markAttendance(false);
            }
        });

        String[] columnNames = {"Attendance ID", "Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][3], columnNames);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(employeeComboBox, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(new JLabel("Select Date:"));
        rightPanel.add(dateChooser);
        rightPanel.add(markPresentButton);
        rightPanel.add(markAbsentButton);

        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    private String getEmployeeID(String selectedEmployee) {
        String[] parts = selectedEmployee.split(" - ");
        if (parts.length > 0) {
            return parts[0];
        } else {
            return "";
        }
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

    private String[] fetchEmployeeNames() {
        if (conn == null) {
            return new String[0];
        }

        String sql = "SELECT empID, name FROM employee";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ArrayList<String> employeeNames = new ArrayList<>();
            while (rs.next()) {
                String empID = rs.getString("empID");
                String name = rs.getString("name");
                employeeNames.add(empID + " - " + name);
            }

            return employeeNames.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching employee names");
            return new String[0];
        }
    }

    private void markAttendance(boolean isPresent) {
        String selectedEmployee = (String) employeeComboBox.getSelectedItem();
        String employeeID = getEmployeeID(selectedEmployee);
        Date selectedDate = dateChooser.getDate(); // Get the date from JDateChooser
        String status = isPresent ? "Present" : "Absent";

        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not available.");
            return;
        }

        // Parse the date from the string
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(selectedDate);
            Date parsedDate = sdf.parse(dateString);

            String sql = "INSERT INTO employee_attendance (empID, date, status) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, employeeID);
                pstmt.setDate(2, new java.sql.Date(parsedDate.getTime()));
                pstmt.setString(3, status);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Attendance marked successfully");
                    refreshTable(employeeID);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to mark attendance");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error marking attendance");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd.");
        }
    }

    private void refreshTable(String employeeID) {
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
                BossAttendanceFrame frame = new BossAttendanceFrame();
                frame.setVisible(true);
            }
        });
    }
}