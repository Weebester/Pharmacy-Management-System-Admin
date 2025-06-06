import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    static Color BG = new Color(221, 221, 221);
    static Color Comp = new Color(0, 128, 128);
    static Color Comp2 = new Color(250, 0, 96);
    static Color TexComp = new Color(238, 238, 238);
    static Color Tex = new Color(40, 40, 40);

    public void SetThemeDark(boolean theme) {
        if (theme) {
            BG = new Color(70, 70, 70);
            Comp = new Color(0, 180, 180);
            Comp2 = new Color(240, 0, 70);
            Tex = new Color(238, 238, 238);
        } else {
            BG = new Color(221, 221, 221);
            Comp = new Color(0, 128, 128);
            Comp2 = new Color(250, 0, 96);
            Tex = new Color(40, 40, 40);
        }
        LoginT.UpdateTheme();
        AccountT.UpdateTheme();
        PharmacyT.UpdateTheme();
        MedT.UpdateTheme();
        TicketT.updateTheme();
    }

    JTabbedPane Tabs = new JTabbedPane();
    LoginTab LoginT = new LoginTab();
    AccountTab AccountT = new AccountTab();
    PharmacyTab PharmacyT = new PharmacyTab();
    MedTab MedT = new MedTab();
    TicketTab TicketT = new TicketTab();

    public void SetTabsState(boolean MedAccess, boolean pharmaAccess) {
        Tabs.setEnabledAt(1, pharmaAccess);
        Tabs.setEnabledAt(2,pharmaAccess);
        Tabs.setEnabledAt(3,MedAccess);
        Tabs.setEnabledAt(4,(pharmaAccess||MedAccess));
    }

    public void SetTab(int index) {
        Tabs.setSelectedIndex(index);
    }


    MainWindow() {
        setTitle("Admin Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setMinimumSize(new Dimension(800, 600));
        Tabs.add("Login", LoginT);
        Tabs.add("Accounts", AccountT);
        Tabs.add("Pharmacies",PharmacyT);
        Tabs.add("Meds", MedT);
        Tabs.add("Tickets",TicketT);
        Tabs.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        SetTabsState(false, false);
        //SetTabsState(true,true);
        add(Tabs);
        SetThemeDark(false);
        setVisible(true);
    }
}
