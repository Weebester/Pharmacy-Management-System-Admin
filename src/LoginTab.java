import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginTab extends JPanel {

    JButton Login = new JButton("Login");
    JButton Logout = new JButton("Logout");
    JLabel AdminL = new JLabel("AdminID");
    JTextField AdminIDField = new JTextField("");
    JLabel PasswordL = new JLabel("Password");
    JPasswordField PasswordField = new JPasswordField("");
    JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");


    LoginTab() {
        setLayout(new RelativeLayout());

        setBackground(MainWindow.BG);
        Login.putClientProperty("JButton.buttonType", "roundRect");
        Login.setBackground(MainWindow.Comp);
        Login.setForeground(MainWindow.TexComp);
        Login.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));

        Logout.putClientProperty("JButton.buttonType", "roundRect");
        Logout.setBackground(MainWindow.Comp2);
        Logout.setForeground(MainWindow.TexComp);
        Logout.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));

        AdminL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        AdminIDField.putClientProperty("JComponent.roundRect", true);
        AdminIDField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        PasswordL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        PasswordField.putClientProperty("JComponent.roundRect", true);
        PasswordField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        PasswordField.setEchoChar('#');

        Login.addActionListener(e -> {
            try {
                ApiCaller.Login(AdminIDField.getText(), String.copyValueOf(PasswordField.getPassword()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        Logout.addActionListener(e -> {
            ApiCaller.Logout();
        });

        showPasswordCheckbox.addActionListener(e -> {
                    if (showPasswordCheckbox.isSelected()) {
                        PasswordField.setEchoChar((char) 0); // Show password
                    } else {
                        PasswordField.setEchoChar('#'); // Hide password
                    }
                }
        );

        showPasswordCheckbox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));


        add(Login, new float[]{0.3f, 0.75f, 0.15f, 0.1f, 40.0f});
        add(Logout, new float[]{0.55f, 0.75f, 0.15f, 0.1f, 40.0f});
        add(AdminL, new float[]{0.1f, 0.2f, 0.6f, 0.15f, 30.0f});
        add(PasswordL, new float[]{0.1f, 0.45f, 0.6f, 0.15f, 30.0f});
        add(AdminIDField, new float[]{0.2f, 0.2f, 0.6f, 0.15f, 30.0f});
        add(PasswordField, new float[]{0.2f, 0.45f, 0.6f, 0.15f, 30.0f});
        add(showPasswordCheckbox, new float[]{0.45f, 0.6f, 0.2f, 0.1f, 25.0f});
    }
}
