package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaveApplicationFrame extends JFrame {
    private JComboBox<String> leaveTypeComboBox;
    private JTextArea reasonTextArea;
    private JButton submitButton;

    public LeaveApplicationFrame(String empID) {
        setTitle("Leave Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        JPanel leaveTypePanel = new JPanel();
        leaveTypePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel leaveTypeLabel = new JLabel("Type of Leave:");
        leaveTypeComboBox = new JComboBox<>();
        leaveTypeComboBox.addItem("Sick Leave");
        leaveTypeComboBox.addItem("Vacation Leave");
        leaveTypePanel.add(leaveTypeLabel);
        leaveTypePanel.add(leaveTypeComboBox);

        JPanel reasonPanel = new JPanel();
        reasonPanel.setLayout(new BorderLayout());

        reasonTextArea = new JTextArea();
        reasonTextArea.setLineWrap(true);
        reasonPanel.add(new JLabel("Exact Reason for Leave:"), BorderLayout.NORTH);
        reasonPanel.add(new JScrollPane(reasonTextArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        submitButton = new JButton("Submit");
        buttonPanel.add(submitButton);

        mainPanel.add(leaveTypePanel, BorderLayout.NORTH);
        mainPanel.add(reasonPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLeaveType = leaveTypeComboBox.getSelectedItem().toString();
                String reason = reasonTextArea.getText();
                String applicantName = getEmployeeName(empID); // Fetch name from the database

                // Get the current date (you can use Java libraries for this)
                java.util.Date currentDate = new java.util.Date();
                java.sql.Date applicationDate = new java.sql.Date(currentDate.getTime());

                String url = "jdbc:mysql://localhost:3306/employeemanagement"; // Replace with your database URL
                String username = "root";
                String password = "Ayush110904";

                try {
                    Connection connection = DriverManager.getConnection(url, username, password);

                    String sql = "INSERT INTO LeaveApplications (empID, leave_type, reason, application_date, applicant_name) VALUES (?, ?, ?, ?, ?)";

                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    preparedStatement.setString(1, empID); // Replace with the actual employee's empID
                    preparedStatement.setString(2, selectedLeaveType);
                    preparedStatement.setString(3, reason);
                    preparedStatement.setDate(4, applicationDate);
                    preparedStatement.setString(5, applicantName);

                    preparedStatement.executeUpdate();

                    preparedStatement.close();
                    connection.close();

                    JOptionPane.showMessageDialog(null, "Leave application submitted successfully!");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Leave application submission failed!");
                }
            }
        });
    }

    private String getEmployeeName(String empID) {
        String name = ""; // Default value
        // Query the database to retrieve the employee's name based on the empID
        try {
            String url = "jdbc:mysql://localhost:3306/employeemanagement";
            String username = "root";
            String password = "Ayush110904";
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT name FROM employee WHERE empID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, empID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("name");
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return name;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LeaveApplicationFrame("883893").setVisible(true);
            }
        });
    }
}
