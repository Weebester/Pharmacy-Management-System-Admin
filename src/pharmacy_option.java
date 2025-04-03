import javax.swing.*;
import java.awt.*;

public class pharmacy_option extends JButton {


    pharmacy_option(int phId) {

        putClientProperty("JButton.buttonType", "roundRect");
        setBackground(MainWindow.Comp);
        setForeground(MainWindow.TexComp);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        setText("Go");
        addActionListener(e -> {
            System.out.println(phId);
        });

    }
}
