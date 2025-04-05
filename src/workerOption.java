import javax.swing.*;
import java.awt.*;

public class workerOption extends JPanel {
    JLabel NameL = new JLabel();
    JButton redirect = new JButton("Go");

    public void UpdateTheme(){
        redirect.setBackground(MainWindow.Comp);
        redirect.setForeground(MainWindow.TexComp);
        NameL.setForeground(MainWindow.Tex);
    }
    workerOption(String Name, String UID) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(null);

        NameL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        NameL.setText(Name);

        redirect.putClientProperty("JButton.buttonType", "roundRect");
        redirect.setPreferredSize(new Dimension(100, redirect.getPreferredSize().height));
        redirect.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        redirect.addActionListener(
                e -> {
                    System.out.println(UID);
                    Main.MainW.AccountT.triggerFetch(UID);
                    Main.MainW.SetTab(1);

                });


        add(NameL);
        add(redirect);
    }
}
