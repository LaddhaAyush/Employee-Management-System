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

public class EmployeeDashboard extends JFrame implements ActionListener {

    private String empID;

    EmployeeDashboard(String empID) {
        this.empID = empID;

        JButton editDetails = new JButton("Edit Details");
        editDetails.setBounds(10, 10, 150, 30);
        editDetails.setBackground(Color.BLACK);
        editDetails.setForeground(Color.WHITE);
        editDetails.addActionListener(this);
        add(editDetails);

        JButton viewAttendance = new JButton("View Attendance");
        viewAttendance.setBounds(10, 50, 150, 30);
        viewAttendance.setBackground(Color.BLACK);
        viewAttendance.setForeground(Color.WHITE);
        viewAttendance.addActionListener(this);
        add(viewAttendance);

        JButton leaveApplication = new JButton("Leave Application");
        leaveApplication.setBounds(10, 90, 150, 30);
        leaveApplication.setBackground(Color.BLACK);
        leaveApplication.setForeground(Color.WHITE);
        leaveApplication.addActionListener(this);
        add(leaveApplication);

        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image backgroundImg = backgroundIcon.getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT);
        ImageIcon backgroundImageIcon = new ImageIcon(backgroundImg);
        JLabel backgroundLabel = new JLabel(backgroundImageIcon);
        backgroundLabel.setBounds(170, 0, 800, 600);
        add(backgroundLabel);

        setSize(940, 600);
        setLocation(300, 100);
        setLayout(null);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Edit Details")) {
            // Create and show the EditEmployee window
            EditEmployee editEmployee = new EditEmployee(empID);
            setVisible(false);
        } else if (e.getActionCommand().equals("View Attendance")) {
            // Add code to open the "View Attendance" page for the employee.
             EmployeeAttendanceViewFrame attendace =new EmployeeAttendanceViewFrame(empID) ;
            setVisible(false);
        } else if (e.getActionCommand().equals("Leave Application")) {
            // Create and show the LeaveApplicationFrame
            new LeaveApplicationFrame(empID);
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new EmployeeDashboard("E1"); // Pass the empID
    }
}
