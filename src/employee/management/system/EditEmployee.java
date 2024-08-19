package employee.management.system;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditEmployee extends JFrame implements ActionListener {

    private String empID; // Define empID as an instance variable

    JTextField tname, tfname, taddress, tphone, taadhar, temail, tsalary, tdesignation;
    JLabel empIDLabel;
    JDateChooser tdob;

    JButton update, back;

    JComboBox<String> Boxeducation;

    EditEmployee(String empID) {
        this.empID = empID; // Set the empID value

        getContentPane().setBackground(new Color(163, 255, 188));

        JLabel heading = new JLabel("Edit Employee Details");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("serif", Font.BOLD, 25));
        add(heading);

        try {
            conn c = new conn(); // Initialize the conn object to connect to the database
            String query = "select * from employee where empID = '" + empID + "'";
            ResultSet resultSet = c.statement.executeQuery(query);
            if (resultSet.next()) {
                JLabel name = new JLabel("Name");
                name.setBounds(50, 150, 150, 30);
                name.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
                add(name);

                tname = new JTextField(resultSet.getString("name"));
                tname.setBounds(200, 150, 150, 30);
                tname.setBackground(new Color(177, 252, 197));
                add(tname);

                JLabel fname = new JLabel("Father's Name");
                fname.setBounds(400,150,150,30);
                fname.setFont(new Font("SAN_SERIF", Font.BOLD,20));
                add(fname);

                tfname = new JTextField(resultSet.getString("fname"));
                tfname.setBounds(600,150,150,30);
                tfname.setBackground(new Color(177,252,197));
                add(tfname);

                JLabel dob = new JLabel("Date Of Birth");
                dob.setBounds(50,200,150,30);
                dob.setFont(new Font("SAN_SERIF", Font.BOLD,20));
                add(dob);

                tdob = new JDateChooser();
                tdob.setBounds(200,200,150,30);
                tdob.setBackground(new Color(177,252,197));
                add(tdob);

                JLabel salary = new JLabel("Salary");
                salary.setBounds(400,200,150,30);
                salary.setFont(new Font("SAN_SERIF", Font.BOLD,20));
                add(salary);

                tsalary = new JTextField(resultSet.getString("salary"));
                tsalary.setBounds(600,200,150,30);
                tsalary.setBackground(new Color(177,252,197));
                add(tsalary);

                JLabel address = new JLabel("Address");
                address.setBounds(50,250,150,30);
                address.setFont(new Font("SAN_SERIF", Font.BOLD,20));
                add(address);

                taddress= new JTextField(resultSet.getString("address"));
                taddress.setBounds(200,250,150,30);
                taddress.setBackground(new Color(177,252,197));
                add(taddress);


                JLabel phone = new JLabel("Phone");
                phone.setBounds(400,250,150,30);
                phone.setFont(new Font("SAN_SERIF", Font.BOLD,20));
                add(phone);

                tphone= new JTextField(resultSet.getString("phone"));
                tphone.setBounds(600,250,150,30);
                tphone.setBackground(new Color(177,252,197));
                add(tphone);

                JLabel email = new JLabel("Email");
                email.setBounds(50,300,150,30);
                email.setFont(new Font("SAN_SERIF", Font.BOLD,20));
                add(email);

                temail= new JTextField(resultSet.getString("email"));
                temail.setBounds(200,300,150,30);
                temail.setBackground(new Color(177,252,197));
                add(temail);

                JLabel education = new JLabel("Higest Education");
                education.setBounds(400,300,150,30);
                education.setFont(new Font("SAN_SERIF", Font.BOLD,20));
                add(education);

                String items[] = {"BBA","B.Tech","BCA", "BA", "BSC", "B.COM", "MBA", "MCA", "MA", "MTech", "MSC", "PHD"};
                Boxeducation = new JComboBox(items);
                Boxeducation.setBackground(new Color(177,252,197));
                Boxeducation.setBounds(600,300,150,30);
                add(Boxeducation);

                JLabel aadhar = new JLabel("Aadhar Number");
                aadhar.setBounds(400,350,150,30);
                aadhar.setFont(new Font("SAN_SERIF", Font.BOLD,20));
                add(aadhar);

                taadhar= new JTextField(resultSet.getString("aadhaar"));
                taadhar.setBounds(600,350,150,30);
                taadhar.setBackground(new Color(177,252,197));
                add(taadhar);

                JLabel designation = new JLabel("Designation");
                designation.setBounds(50,350,150,30);
                designation.setFont(new Font("SAN_SERIF", Font.BOLD,20));
                add(designation);

                tdesignation= new JTextField(resultSet.getString("designation"));
                tdesignation.setBounds(200,350,150,30);
                tdesignation.setBackground(new Color(177,252,197));
                add(tdesignation);


                // Similar modifications for other fields (fname, dob, address, phone, email, education, aadhar, empID, and designation)

                // Update button
                update = new JButton("Update");
                update.setBounds(450, 550, 150, 40);
                update.setBackground(Color.black);
                update.setForeground(Color.WHITE);
                update.addActionListener(this);
                add(update);

                // Back button
                back = new JButton("Back");
                back.setBounds(250, 550, 150, 40);
                back.setBackground(Color.black);
                back.setForeground(Color.WHITE);
                back.addActionListener(this);
                add(back);
            } else {
                JOptionPane.showMessageDialog(null, "Employee not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setSize(900, 700);
        setLayout(null);
        setLocation(300, 50);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == update) {
            // Retrieve the updated details from the text fields
            String name = tname.getText();
            String fName = tfname.getText();
            String dob = ((JTextField) tdob.getDateEditor().getUiComponent()).getText();
            String address = taddress.getText();
            String phone = tphone.getText();
            String email = temail.getText();
            String salary = tsalary.getText();
            String education = (String) Boxeducation.getSelectedItem();
            String designation = tdesignation.getText();
            String aadhar = taadhar.getText();

            try {
                conn c = new conn(); // Initialize the conn object to connect to the database
                String query = "UPDATE employee SET name = ?, fname = ?, dob = ?,  address = ?, phone = ?, email = ?, education = ? WHERE empID = ?";
                PreparedStatement pstmt = c.connection.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.setString(2, fName);
                pstmt.setString(3, dob);
                pstmt.setString(4, address);
                pstmt.setString(5, phone);
                pstmt.setString(6, email);
                pstmt.setString(7, education);
                pstmt.setString(8, empID);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Details updated successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update details");
                }

                setVisible(false);
                new EmployeeDashboard(empID);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new EmployeeDashboard(empID);
        }
    }

    public static void main(String[] args) {
        new EditEmployee("883893"); // Pass the empID
    }
}