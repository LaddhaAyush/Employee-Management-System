
package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class EmployeeLogin extends JFrame implements ActionListener {

    JTextField tEmpID;
    JPasswordField tPassword;
    JButton login, back;

    EmployeeLogin() {
        JLabel empIDLabel = new JLabel("Employee ID");
        empIDLabel.setBounds(40, 20, 100, 30);
        add(empIDLabel);

        tEmpID = new JTextField();
        tEmpID.setBounds(150, 20, 150, 30);
        add(tEmpID);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(40, 70, 100, 30);
        add(passwordLabel);

        tPassword = new JPasswordField();
        tPassword.setBounds(150, 70, 150, 30);
        add(tPassword);

        login = new JButton("LOGIN");
        login.setBounds(150, 140, 150, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        back = new JButton("BACK");
        back.setBounds(150, 180, 150, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        ImageIcon imgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/employee.png"));
        Image img = imgIcon.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);
        ImageIcon imageIcon = new ImageIcon(img);
        JLabel imgLabel = new JLabel(imageIcon);
        imgLabel.setBounds(350, 10, 600, 400);
        add(imgLabel);

        ImageIcon loginIcon = new ImageIcon(ClassLoader.getSystemResource("icons/employee.png"));
        Image loginImage = loginIcon.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        ImageIcon loginImageIcon = new ImageIcon(loginImage);
        JLabel loginImageLabel = new JLabel(loginImageIcon);
        loginImageLabel.setBounds(0, 0, 600, 300);
        add(loginImageLabel);

        setSize(600, 300);
        setLocation(450, 200);
        setLayout(null);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            try {
                String empID = tEmpID.getText();
                String password = tPassword.getText();

                conn conn = new conn();
                String query = "select * from employee1 where empID = '" + empID + "' and password = '" + password + "'";
                ResultSet resultSet = conn.statement.executeQuery(query);
                if (resultSet.next()) {
                    setVisible(false);
                    new EmployeeDashboard(empID); // You may want to pass empID to the dashboard
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Employee ID or password");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == back) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new EmployeeLogin();
    }
}

