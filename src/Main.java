import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;

public class Main {
    static MainWindow MainW;
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainW = new MainWindow();
    }
}
