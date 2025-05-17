import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;

public class AddManagerForm extends JPanel {
    JLabel nameLabel = new JLabel("Name");
    JTextField nameField = new JTextField();

    JLabel emailLabel = new JLabel("Email");
    JTextField emailField = new JTextField();

    JLabel passwordLabel = new JLabel("Password");
    JPasswordField passwordField = new JPasswordField();
    JCheckBox showPasswordCheckbox = new JCheckBox("Show");

    JLabel pharmacyLabel = new JLabel("Pharmacy Name");
    JTextField pharmacyField = new JTextField();

    JButton createButton = new JButton("Create Manager");

    public AddManagerForm() {
        setLayout(new RelativeLayout());

        // Label & Input Styling
        Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 35);
        Font inputFont = new Font(Font.SANS_SERIF, Font.PLAIN, 35);

        for (JLabel label : new JLabel[]{nameLabel, emailLabel, passwordLabel, pharmacyLabel}) {
            label.setFont(labelFont);
            label.setForeground(MainWindow.Tex);
        }

        for (JTextField field : new JTextField[]{nameField, emailField, pharmacyField}) {
            field.setFont(inputFont);
            field.putClientProperty("JComponent.roundRect", true);
        }

        passwordField.setFont(inputFont);
        passwordField.putClientProperty("JComponent.roundRect", true);
        passwordField.setEchoChar('#');

        showPasswordCheckbox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('#');
            }
        });

        createButton.setFont(labelFont);
        createButton.putClientProperty("JButton.buttonType", "roundRect");
        createButton.setBackground(MainWindow.Comp);
        createButton.setForeground(MainWindow.TexComp);

        createButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String pharmacy = pharmacyField.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || pharmacy.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JSONObject body = new JSONObject();
            body.put("name", name);
            body.put("email", email);
            body.put("passW", password);
            body.put("ph_name", pharmacy);

            ApiCaller.PostRequest("/add_user", body).thenAccept(response -> {
                JOptionPane.showMessageDialog(this, response, "Success", JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.getWindowAncestor(this).dispose();
                JSONObject jsonObject = new JSONObject(response);
                Main.MainW.AccountT.triggerFetch(jsonObject.getString("FB_id"));
            }).exceptionally(ex -> {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            });
        });

        // Layout — keeping name & pharmacy on same Y level
        add(nameLabel, new float[]{0.05f, 0.05f, 0.2f, 0.1f, 35});
        add(nameField, new float[]{0.05f, 0.15f, 0.4f, 0.1f, 35});
        add(pharmacyLabel, new float[]{0.55f, 0.05f, 0.35f, 0.1f, 35});
        add(pharmacyField, new float[]{0.55f, 0.15f, 0.4f, 0.1f, 35});

        add(emailLabel, new float[]{0.05f, 0.28f, 0.4f, 0.1f, 35});
        add(emailField, new float[]{0.05f, 0.38f, 0.9f, 0.1f, 35});

        add(passwordLabel, new float[]{0.05f, 0.51f, 0.4f, 0.1f, 35});
        add(passwordField, new float[]{0.05f, 0.61f, 0.75f, 0.1f, 35});
        add(showPasswordCheckbox, new float[]{0.82f, 0.61f, 0.15f, 0.1f, 25});

        add(createButton, new float[]{0.05f, 0.78f, 0.9f, 0.12f, 40});
    }
}

