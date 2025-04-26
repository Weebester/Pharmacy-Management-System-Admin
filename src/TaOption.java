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

    TaOption(String details, int MedID,int TaID) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(null);

        DetL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        DetL.setText(details);

        remove.putClientProperty("JButton.buttonType", "roundRect");
        remove.setPreferredSize(new Dimension(100, remove.getPreferredSize().height));
        remove.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        remove.addActionListener(
                e -> {
                    System.out.println(MedID+","+TaID);
                });


        add(DetL);
        add(remove);
    }
}
