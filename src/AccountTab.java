import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class AccountTab extends JPanel {


    JButton Del = new JButton("Delete");
    JButton AddBranch = new JButton("Add Branch");
    JButton AddNewAccount = new JButton("Add Account");
    JLabel PharmaciesL = new JLabel("Pharmacies:");
    JPanel PhListContent = new JPanel();
    JScrollPane PhList = new JScrollPane(PhListContent);


    JLabel Name = new JLabel("Name: Null");
    JLabel status = new JLabel("Status: Null");
    JLabel email = new JLabel("Email: Null");
    JButton FetchAccount = new JButton("Fetch");
    JLabel AccountIDL = new JLabel("AccountID:");
    JTextField AccountIDField = new JTextField("");

    public void UpdateTheme() {
        setBackground(MainWindow.BG);
        PhList.setBackground(MainWindow.BG);
        PhListContent.setBackground(MainWindow.BG);
        FetchAccount.setBackground(MainWindow.Comp);
        FetchAccount.setForeground(MainWindow.TexComp);
        AddNewAccount.setBackground(MainWindow.Comp);
        AddNewAccount.setForeground(MainWindow.TexComp);
        Del.setBackground(MainWindow.Comp2);
        Del.setForeground(MainWindow.TexComp);
        AddBranch.setBackground(MainWindow.Comp);
        AddBranch.setForeground(MainWindow.TexComp);
        PharmaciesL.setForeground(MainWindow.Tex);
        AccountIDL.setForeground(MainWindow.Tex);
        Name.setForeground(MainWindow.Tex);
        status.setForeground(MainWindow.Tex);
        email.setForeground(MainWindow.Tex);

        PhList.getVerticalScrollBar().setUI(new BasicScrollBarUI() {

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

        for (Component X : PhListContent.getComponents()) if (X instanceof pharmacyOption temp) temp.UpdateTheme();

    }

    public void triggerFetch(String UID) {
        AccountIDField.setText(UID);
        FetchAccount.doClick();
    }

    AccountTab() {
        setLayout(new RelativeLayout());

        PhListContent.setLayout(new GridLayout(0, 1));
        PhList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        PhList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        PhList.setBorder(null);

        FetchAccount.putClientProperty("JButton.buttonType", "roundRect");
        FetchAccount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        FetchAccount.addActionListener(e -> {
            PhListContent.removeAll();
            ApiCaller.GetRequest("/retrieve_user?FB_ID=" + AccountIDField.getText()).thenAccept(response -> {
                Del.setEnabled(true);

                JSONObject jsonObject = new JSONObject(response);
                Name.setText("Name: " + jsonObject.optString("user"));
                String M = jsonObject.optString("Manager");
                System.out.println(M.equals("Yes"));
                if (M.equals("Yes")) {
                    status.setText("Status: Manager");
                    AddBranch.setEnabled(true);
                } else {
                    status.setText("Status: Assistant");
                    AddBranch.setEnabled(false);
                }
                email.setText("Email: " + jsonObject.optString("email"));

                if (!jsonObject.isNull("pharmaciesN")) {
                    JSONArray Names = jsonObject.getJSONArray("pharmaciesN");
                    JSONArray PHIds = jsonObject.getJSONArray("pharmaciesID");

                    for (int i = 0; i < Names.length(); i++) {
                        pharmacyOption itemPanel = new pharmacyOption(Names.getString(i), PHIds.getInt(i));
                        itemPanel.UpdateTheme();
                        PhListContent.add(itemPanel);
                    }
                }
            }).exceptionally(ex -> {
                Name.setText("Name: Null");
                status.setText("Status: Null");
                email.setText("Email: Null");
                AddBranch.setEnabled(false);
                Del.setEnabled(false);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            });

        });

        AddNewAccount.putClientProperty("JButton.buttonType", "roundRect");
        AddNewAccount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        AddNewAccount.addActionListener(e -> {
        });

        Del.setEnabled(false);
        Del.putClientProperty("JButton.buttonType", "roundRect");
        Del.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        Del.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this account?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                ApiCaller.DeleteRequest("/delete_user?FB_ID=" + AccountIDField.getText()).thenAccept(response -> {
                    JOptionPane.showMessageDialog(null, response, "Success", JOptionPane.INFORMATION_MESSAGE);
                    Name.setText("Name: Null");
                    status.setText("Status: Null");
                    email.setText("Email: Null");
                    AddBranch.setEnabled(false);
                    Del.setEnabled(false);
                }).exceptionally(ex -> {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                });
            }
        });

        AddBranch.setEnabled(false);
        AddBranch.putClientProperty("JButton.buttonType", "roundRect");
        AddBranch.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        AddBranch.addActionListener(e -> {
        });


        AccountIDL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        AccountIDField.putClientProperty("JComponent.roundRect", true);
        AccountIDField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        Name.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        status.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        email.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        PharmaciesL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));


        add(FetchAccount, new float[]{0.825f, 0.05f, 0.15f, 0.1f, 35.0f});
        add(AccountIDL, new float[]{0.025f, 0.05f, 0.875f, 0.1f, 35.0f});
        add(AccountIDField, new float[]{0.115f, 0.05f, 0.685f, 0.1f, 35.0f});

        add(Name, new float[]{0.075f, 0.2f, 0.4f, 0.1f, 30.0f});
        add(status, new float[]{0.075f, 0.35f, 0.4f, 0.1f, 30.0f});
        add(email, new float[]{0.075f, 0.5f, 0.4f, 0.1f, 30.0f});

        add(PharmaciesL, new float[]{0.575f, 0.2f, 0.4f, 0.1f, 30.0f});
        add(PhList, new float[]{0.575f, 0.35f, 0.35f, 0.5f, 30.0f});

        add(AddNewAccount, new float[]{0.05f, 0.875f, 0.25f, 0.1f, 35.0f});
        add(Del, new float[]{0.375f, 0.875f, 0.25f, 0.1f, 35.0f});
        add(AddBranch, new float[]{0.7f, 0.875f, 0.25f, 0.1f, 35.0f});

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g.setColor(MainWindow.Tex);
        int mid = this.getWidth() / 2;
        int YStart = (int) (this.getHeight() * 0.1825);
        int YEnd = (int) (this.getHeight() * 0.85);
        g.drawLine(mid, YStart, mid, YEnd);
    }
}
