import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    JTabbedPane Tabs = new JTabbedPane();
    LoginTab LoginT = new LoginTab();

    public void SetTabsState(boolean MedAccess,boolean pharmaAccess){
        Tabs.setEnabledAt(1,MedAccess);
    }

    public void SetTab(int index){
        Tabs.setSelectedIndex(0);
    }


    MainWindow() {
        setTitle("Admin Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setMinimumSize(new Dimension(800,600));
        Tabs.add("Login",LoginT);
        Tabs.add("Temp",new JPanel());
        SetTabsState(false,false);
        add(Tabs);
        setVisible(true);
    }
}
