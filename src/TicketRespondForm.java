import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;

public class TicketRespondForm extends JPanel {
    JLabel title = new JLabel("Response Title");
    JTextField titleField = new JTextField();

    JLabel content = new JLabel("Message");
    JTextArea messageField = new JTextArea();

    JButton sendButton = new JButton("Send Response");

    public TicketRespondForm(String AccountID, String TicketID) {
        setLayout(new RelativeLayout());
        setBackground(MainWindow.BG);
        // Font & UI Setup
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        title.setForeground(MainWindow.Tex);
        titleField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        titleField.putClientProperty("JComponent.roundRect", true);

        content.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        content.setForeground(MainWindow.Tex);
        messageField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        messageField.setLineWrap(true);
        messageField.setWrapStyleWord(true);
        messageField.putClientProperty("JComponent.roundRect", true);

        sendButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        sendButton.putClientProperty("JButton.buttonType", "roundRect");
        sendButton.setBackground(MainWindow.Comp);
        sendButton.setForeground(MainWindow.TexComp);

        sendButton.addActionListener(e -> {
            String titleText = titleField.getText();
            String messageText = messageField.getText();

            if (titleText.isEmpty() || messageText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                JSONObject body = new JSONObject();
                body.put("FB_id", AccountID);
                body.put("Title", titleText);
                body.put("Body", messageText);
                body.put("TK_ID", TicketID);

                ApiCaller.PutRequest("/ticket_respond", body).thenAccept(response -> {
                    JOptionPane.showMessageDialog(this, response, "error", JOptionPane.INFORMATION_MESSAGE);
                    SwingUtilities.getWindowAncestor(this).dispose();
                    Main.MainW.TicketT.refresh_Page();
                }).exceptionally(ex -> {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
                    return null;
                });
            }

        });


        // Add components using relative layout
        add(title, new float[]{0.1f, 0.05f, 0.3f, 0.1f, 40});
        add(titleField, new float[]{0.1f, 0.15f, 0.8f, 0.1f, 35});
        add(content, new float[]{0.1f, 0.3f, 0.3f, 0.1f, 40});
        add(messageField, new float[]{0.1f, 0.4f, 0.8f, 0.4f, 35});
        add(sendButton, new float[]{0.1f, 0.85f, 0.8f, 0.1f, 40});

    }
}
