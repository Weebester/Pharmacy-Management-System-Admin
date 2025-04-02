import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class AccountTab extends JPanel {



    JLabel PharmaciesL = new JLabel("Pharmacies:");
    JPanel PhListContent = new JPanel();
    JScrollPane PhList = new JScrollPane(PhListContent);


    JLabel Name = new JLabel("Name: Null");
    JLabel status = new JLabel("Status: Null");
    JLabel email = new JLabel("Email: Null");
    JButton FetchAccount = new JButton("Fetch");
    JLabel AccountIDL = new JLabel("AccountID:");
    JTextField AccountIDField = new JTextField("");


    AccountTab() {
        setLayout(new RelativeLayout());
        PhListContent.setLayout(new GridLayout(0, 1));
        PhList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        PhList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        PhList.setBackground(MainWindow.BG);
        PhList.setBorder(null);
        PhListContent.setBackground(MainWindow.BG);

        PhList.getVerticalScrollBar().setUI(
                new BasicScrollBarUI() {

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
                        // Customize the scrollbar colors
                        thumbColor = MainWindow.Comp; // Teal thumb color
                        trackColor = MainWindow.BG; // Light teal track color
                        thumbHighlightColor = new Color(0, 100, 100); // Darker teal highlight
                    }
                }
        );

        setBackground(MainWindow.BG);
        FetchAccount.putClientProperty("JButton.buttonType", "roundRect");
        FetchAccount.setBackground(MainWindow.Comp);
        FetchAccount.setForeground(MainWindow.TexComp);
        FetchAccount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));

        FetchAccount.addActionListener(e -> {
            PhListContent.removeAll();
            ApiCaller.GetRequest("/retrive_user?FBID=" + AccountIDField.getText())// Pass token to the method
                    .thenAccept(response -> {
                        JSONObject jsonObject = new JSONObject(response);
                        Name.setText("Name: " + jsonObject.optString("user"));
                        String M = jsonObject.optString("Manager");
                        System.out.println(M.equals("Yes"));
                        if (M.equals("Yes")) {
                            status.setText("Status: Manager");
                        } else {
                            status.setText("Status: Assistant");
                        }
                        email.setText("Email: " + jsonObject.optString("email"));

                        JSONArray Names = jsonObject.getJSONArray("pharmaciesN");
                        JSONArray PHIds = jsonObject.getJSONArray("pharmaciesID");

                        for (int i = 0; i < Names.length(); i++) {
                            JPanel itemPanel = new JPanel();
                            itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                            itemPanel.setBackground(null);
                            JLabel label = new JLabel(Names.getString(i));
                            label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                            JButton button = new pharmacy_option(PHIds.getInt(i));
                            button.setPreferredSize(new Dimension(100, button.getPreferredSize().height));
                            itemPanel.add(label);
                            itemPanel.add(button);
                            PhListContent.add(itemPanel);
                            PhListContent.add(Box.createVerticalStrut(10));
                        }
                    })
                    .exceptionally(ex -> {
                        Name.setText("Name: Null");
                        status.setText("Status: Null");
                        email.setText("Email: Null");
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return null;
                    });

            for (int i = 1; i <= 20; i++) {

            }
        });


        AccountIDL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        AccountIDField.putClientProperty("JComponent.roundRect", true);
        AccountIDField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        add(FetchAccount, new float[]{0.825f, 0.05f, 0.15f, 0.1f, 35.0f});
        add(AccountIDL, new float[]{0.025f, 0.05f, 0.075f, 0.1f, 30.0f});
        add(AccountIDField, new float[]{0.10625f, 0.05f, 0.7f, 0.1f, 30.0f});

        Name.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        status.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        email.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        PharmaciesL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        add(Name, new float[]{0.075f, 0.25f, 0.4f, 0.1f, 30.0f});
        add(status, new float[]{0.075f, 0.4f, 0.4f, 0.1f, 30.0f});
        add(email, new float[]{0.075f, 0.55f, 0.4f, 0.1f, 30.0f});

        add(PharmaciesL, new float[]{0.575f, 0.25f, 0.4f, 0.1f, 30.0f});
        add(PhList, new float[]{0.575f, 0.35f, 0.4f, 0.55f, 30.0f});


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g.setColor(Color.gray);
        int mid = this.getWidth() / 2;
        int YStart = (int) (this.getHeight() * 0.175);
        int YEnd = (int) (this.getHeight() * 0.95);

        g.drawLine(mid, YStart, mid, YEnd);
    }
}
