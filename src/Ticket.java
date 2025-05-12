import com.formdev.flatlaf.ui.FlatBorder;

import javax.swing.*;

import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class Ticket extends JPanel {


    JTextPane TContent;
    JScrollPane contentArea;
    JButton gotoAC=new JButton("Go to Account");
    JButton gotoPH=new JButton("Go to Pharmacy");
    JButton gotoMed=new JButton("Go to Med");
    JButton respond=new JButton("respond");
    JButton discard=new JButton("discard");

    public void UpdateTheme() {
        setBackground(MainWindow.BG);
        contentArea.setBackground(MainWindow.BG);
        TContent.setBackground(Color.WHITE);
        TContent.setForeground(Color.BLACK);
        gotoAC.setBackground(MainWindow.Comp);
        gotoAC.setForeground(MainWindow.TexComp);
        gotoPH.setBackground(MainWindow.Comp);
        gotoPH.setForeground(MainWindow.TexComp);
        gotoMed.setBackground(MainWindow.Comp);
        gotoMed.setForeground(MainWindow.TexComp);
        respond.setBackground(MainWindow.Comp);
        respond.setForeground(MainWindow.TexComp);
        discard.setBackground(MainWindow.Comp2);
        discard.setForeground(MainWindow.TexComp);



        contentArea.getVerticalScrollBar().setUI(new BasicScrollBarUI() {

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new JButton() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected void configureScrollBarColors() {
                thumbColor = MainWindow.Comp;
                trackColor = MainWindow.BG;
                thumbHighlightColor = new Color(0, 100, 100);
            }
        });
        contentArea.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new JButton() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected void configureScrollBarColors() {
                thumbColor = MainWindow.Comp;
                trackColor = MainWindow.BG;
                thumbHighlightColor = new Color(0, 100, 100);
            }
        });

        contentArea.setBorder(new FlatBorder() {
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(10, 10, 10, 10);
            }

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(MainWindow.Comp);// Border color
                g2.fillRoundRect(x, y, width - 1, height - 1, 30, 30); // 30px rounded edges
            }
        });



    }

    public Ticket(String content, String AccountId, int PharmacyId, int MedId) {
        setLayout(new RelativeLayout());

        TContent = new JTextPane() {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };
        TContent.setText("CONTENT: "+content);
        TContent.setEditable(false);
        TContent.setContentType("text/plain"); // ensures plain text rendering
        contentArea = new JScrollPane(TContent);
        contentArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        TContent.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        contentArea.setBorder(null);

        gotoAC.putClientProperty("JButton.buttonType", "roundRect");
        gotoAC.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 80));
        gotoAC.addActionListener(e -> {
            System.out.println(AccountId);
        });
        gotoPH.putClientProperty("JButton.buttonType", "roundRect");
        gotoPH.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 80));
        gotoPH.addActionListener(e -> {
            System.out.println(PharmacyId);
        });
        gotoMed.putClientProperty("JButton.buttonType", "roundRect");
        gotoMed.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 80));
        gotoMed.addActionListener(e -> {
            System.out.println(MedId);
        });

        respond.putClientProperty("JButton.buttonType", "roundRect");
        respond.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 80));
        respond.addActionListener(e -> {
            System.out.println("respond");
        });
        discard.putClientProperty("JButton.buttonType", "roundRect");
        discard.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 80));
        discard.addActionListener(e -> {
            System.out.println("discard");
        });


        add(contentArea,new float[]{0.1f, 0.05f, 0.8f, 0.5f, 15.0f});
        add(gotoAC,new float[]{0.1f, 0.60f, 0.8f, 0.075f, 80.0f});
        add(gotoPH,new float[]{0.1f, 0.7f, 0.8f, 0.075f, 80.0f});
        add(gotoMed,new float[]{0.1f, 0.8f, 0.8f, 0.075f, 80.0f});
        add(respond,new float[]{0.1f, 0.9f, 0.375f, 0.075f, 80.0f});
        add(discard,new float[]{0.525f, 0.9f, 0.375f, 0.075f, 80.0f});

       UpdateTheme();

    }

}
