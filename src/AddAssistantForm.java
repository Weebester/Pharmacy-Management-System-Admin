import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;

public class AddAssistantForm extends JPanel {
    JLabel emailLabel = new JLabel("Email");
    JTextField emailField = new JTextField();

    JLabel nameLabel = new JLabel("Name");
    JTextField nameField = new JTextField();

    JLabel passwordLabel = new JLabel("Password");
    JPasswordField passwordField = new JPasswordField();
    JCheckBox showPasswordCheckbox = new JCheckBox("Show");

    JButton addButton = new JButton("Add Assistant");

    public AddAssistantForm(String ph_id) {
        setLayout(new RelativeLayout());
        setBackground(MainWindow.BG);

        emailLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        emailLabel.setForeground(MainWindow.Tex);
        emailField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        emailField.putClientProperty("JComponent.roundRect", true);

        nameLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        nameLabel.setForeground(MainWindow.Tex);
        nameField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        nameField.putClientProperty("JComponent.roundRect", true);

        passwordLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        passwordLabel.setForeground(MainWindow.Tex);
        passwordField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        passwordField.putClientProperty("JComponent.roundRect", true);
        passwordField.setEchoChar('#');

        showPasswordCheckbox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        showPasswordCheckbox.setForeground(MainWindow.Tex);
        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('#');
            }
        });

        addButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        addButton.putClientProperty("JButton.buttonType", "roundRect");
        addButton.setBackground(MainWindow.Comp);
        addButton.setForeground(MainWindow.TexComp);

        addButton.addActionListener(e -> {
            String email = emailField.getText();
            String name = nameField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JSONObject body = new JSONObject();
            body.put("name", name);
            body.put("email", email);
            body.put("passW", password);
            body.put("ph_id", ph_id);

            ApiCaller.PostRequest("/add_user", body).thenAccept(response -> {
                JOptionPane.showMessageDialog(this, response, "Success", JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.getWindowAncestor(this).dispose();
                Main.MainW.PharmacyT.triggerFetch(ph_id);
            }).exceptionally(ex -> {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            });
        });

        add(emailLabel, new float[]{0.1f, 0.05f, 0.4f, 0.1f, 40});
        add(emailField, new float[]{0.1f, 0.15f, 0.8f, 0.1f, 35});
        add(nameLabel, new float[]{0.1f, 0.3f, 0.4f, 0.1f, 40});
        add(nameField, new float[]{0.1f, 0.4f, 0.8f, 0.1f, 35});
        add(passwordLabel, new float[]{0.1f, 0.55f, 0.4f, 0.1f, 40});
        add(passwordField, new float[]{0.1f, 0.65f, 0.7f, 0.1f, 35});
        add(showPasswordCheckbox, new float[]{0.825f, 0.68f, 0.4f, 0.05f, 30});
        add(addButton, new float[]{0.1f, 0.85f, 0.8f, 0.1f, 40});
    }
}
