import javax.swing.*;
import java.awt.*;

public class TaOption extends JPanel {

    JLabel DetL = new JLabel();
    JButton remove = new JButton("Remove");

    public void UpdateTheme() {
        remove.setBackground(MainWindow.Comp2);
        remove.setForeground(MainWindow.TexComp);
        DetL.setForeground(MainWindow.Tex);
    }

    TaOption(String details, int MedID, int TaID) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(null);

        DetL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        DetL.setText(details);

        remove.putClientProperty("JButton.buttonType", "roundRect");
        remove.setPreferredSize(new Dimension(100, remove.getPreferredSize().height));
        remove.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        remove.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this Ta from dosage?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                ApiCaller.DeleteRequest("/dosage_delete?MedID=" + MedID + "&TaID=" + TaID).thenAccept(response -> {
                    JOptionPane.showMessageDialog(null, response, "Success", JOptionPane.INFORMATION_MESSAGE);

                }).exceptionally(ex -> {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                });
            }
            Main.MainW.MedT.triggerFetch(""+MedID);
        });


        add(DetL);
        add(remove);
    }
}
