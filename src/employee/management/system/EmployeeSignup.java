package employee.management.system;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeSignup extends JFrame implements ActionListener {

    JTextField tusername;
    JPasswordField tpassword;
    JButton signup, back;

    EmployeeSignup() {
        JLabel username = new JLabel("Username");
        username.setBounds(40, 20, 100, 30);
        add(username);

        tusername = new JTextField();
        tusername.setBounds(150, 20, 150, 30);
        add(tusername);

        JLabel password = new JLabel("Password");
        password.setBounds(40, 70, 100, 30);
        add(password);

        tpassword = new JPasswordField();
        tpassword.setBounds(150, 70, 150, 30);
        add(tpassword);

        signup = new JButton("SIGN UP");
        signup.setBounds(150, 140, 150, 30);
        signup.setBackground(Color.BLACK);
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        add(signup);

        back = new JButton("BACK");
        back.setBounds(150, 180, 150, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/LoginB.jpg"));
        Image i2 = i1.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0, 0, 600, 300);
        add(img);

        setSize(600, 300);
        setLocation(450, 200);
        setLayout(null);

        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signup) {
            try {
                String username = tusername.getText();
                String password = new String(tpassword.getPassword());

                conn conn = new conn();
                String query = "INSERT INTO employee1 (empID, password) VALUES ('" + username + "','" + password + "')";
                int result = conn.statement.executeUpdate(query);

                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Account created successfully!");
                    setVisible(false);
                    new EmployeeLogin(); // Redirect to the login page
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to create an account.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new EmployeeLogin();
        }
    }

    public static void main(String[] args) {
        new EmployeeSignup();
    }
}
