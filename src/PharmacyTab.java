import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class PharmacyTab extends JPanel {

    JButton Del = new JButton("Delete");
    JButton FetchPharmacy = new JButton("Fetch");
    JButton InspectUpdates = new JButton("InspectUpdates");
    JButton InspectSells = new JButton("InspectSells");
    JLabel PharmacyIDL = new JLabel("PharmacyID:");
    JTextField PharmacyIDField = new JTextField("");
    JTextField FromField = new JTextField("");
    JTextField ToField = new JTextField("");
    JLabel Name = new JLabel("PharmacyName: Null");
    JLabel empListL = new JLabel("List of employees:");
    JLabel FromL = new JLabel("From:");
    JLabel ToL = new JLabel("To:");
    JPanel workerListContent = new JPanel();
    JScrollPane workerList = new JScrollPane(workerListContent);
    JTextPane LogsContent = new JTextPane();
    JScrollPane LogsArea = new JScrollPane(LogsContent);


    public void UpdateTheme() {
        setBackground(MainWindow.BG);
        workerList.setBackground(MainWindow.BG);
        workerListContent.setBackground(MainWindow.BG);
        LogsArea.setBackground(MainWindow.BG);
        LogsContent.setBackground(MainWindow.BG);
        LogsContent.setForeground(MainWindow.Tex);
        FetchPharmacy.setBackground(MainWindow.Comp);
        FetchPharmacy.setForeground(MainWindow.TexComp);
        InspectUpdates.setBackground(MainWindow.Comp);
        InspectUpdates.setForeground(MainWindow.TexComp);
        InspectSells.setBackground(MainWindow.Comp);
        InspectSells.setForeground(MainWindow.TexComp);
        PharmacyIDL.setForeground(MainWindow.Tex);
        Name.setForeground(MainWindow.Tex);
        empListL.setForeground(MainWindow.Tex);
        FromL.setForeground(MainWindow.Tex);
        ToL.setForeground(MainWindow.Tex);
        Del.setBackground(MainWindow.Comp2);
        Del.setForeground(MainWindow.TexComp);

        workerList.getVerticalScrollBar().setUI(new BasicScrollBarUI() {

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
        LogsArea.getVerticalScrollBar().setUI(new BasicScrollBarUI() {

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
        LogsArea.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {

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


        for (Component X : workerListContent.getComponents()) if (X instanceof workerOption temp) temp.UpdateTheme();


    }

    public void triggerFetch(String PhID) {
        PharmacyIDField.setText(PhID);
        FetchPharmacy.doClick();
    }

    public PharmacyTab() {
        setLayout(new RelativeLayout());

        workerListContent.setLayout(new GridLayout(0, 1));
        workerList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        workerList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        workerList.setBorder(null);

        LogsContent.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        LogsArea.setBorder(null);

        FetchPharmacy.putClientProperty("JButton.buttonType", "roundRect");
        FetchPharmacy.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        FetchPharmacy.addActionListener(e -> {
            workerListContent.removeAll();
            LogsContent.setText("");
            ApiCaller.GetRequest("/retrieve_pharma?PhID=" + PharmacyIDField.getText()).thenAccept(response -> {
                Del.setEnabled(true);
                InspectSells.setEnabled(true);
                InspectUpdates.setEnabled(true);
                JSONObject jsonObject = new JSONObject(response);

                Name.setText("PharmacyName: " + jsonObject.optString("phmame"));

                JSONArray Names = jsonObject.getJSONArray("workers");
                JSONArray statuses = jsonObject.getJSONArray("status");
                JSONArray UIDs = jsonObject.getJSONArray("workersIds");

                for (int i = 0; i < Names.length(); i++) {
                    workerOption itemPanel;
                    if (statuses.getString(i).equals("Yes")) {
                        itemPanel = new workerOption(Names.getString(i) + "(Manager)", UIDs.getString(i));
                    } else {
                        itemPanel = new workerOption(Names.getString(i) + "(Assistant)", UIDs.getString(i));
                    }
                    itemPanel.UpdateTheme();
                    workerListContent.add(itemPanel);
                }

            }).exceptionally(ex -> {
                Name.setText("PharmacyName: Null");
                InspectUpdates.setEnabled(false);
                InspectSells.setEnabled(false);
                Del.setEnabled(false);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            });

        });

        InspectUpdates.putClientProperty("JButton.buttonType", "roundRect");
        InspectUpdates.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        InspectUpdates.setEnabled(false);
        InspectUpdates.addActionListener(e -> {
            LogsContent.setText("");
            StringBuilder route = new StringBuilder("/update_Inspect?ph_id=" + PharmacyIDField.getText());
            boolean valid = true;

            if (!FromField.getText().isEmpty() && FromField.getText().matches("\\d{4}-\\d{2}-\\d{2}"))
                route.append("&from_date=").append(FromField.getText());
            else if (!FromField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid 'From' date format (YYYY-MM-DD)", "Error", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }

            if (!ToField.getText().isEmpty() && ToField.getText().matches("\\d{4}-\\d{2}-\\d{2}"))
                route.append("&to_date=").append(ToField.getText());
            else if (!ToField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid 'To' date format (YYYY-MM-DD)", "Error", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }

            if (valid) {
                ApiCaller.GetRequest(route.toString()).thenAccept(response -> {

                    JSONArray logArray = new JSONArray(response);

                    StringBuilder logContent = new StringBuilder();
                    for (int i = 0; i < logArray.length(); i++) {
                        JSONObject logEntry = logArray.getJSONObject(i);
                        String date = logEntry.getString("date");
                        String content = logEntry.getString("content");

                        logContent.append(date).append("\n");
                        logContent.append(content).append("\n");
                        logContent.append("--------------------------------------------------------------------------------------------\n");
                    }
                    LogsContent.setText(logContent.toString());

                }).exceptionally(ex -> {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                });
            }

        });

        InspectSells.putClientProperty("JButton.buttonType", "roundRect");
        InspectSells.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        InspectSells.setEnabled(false);
        InspectSells.addActionListener(e -> {
            LogsContent.setText("");

            StringBuilder route = new StringBuilder("/sell_Inspect?ph_id=" + PharmacyIDField.getText());
            boolean valid = true;

            if (!FromField.getText().isEmpty() && FromField.getText().matches("\\d{4}-\\d{2}-\\d{2}"))
                route.append("&from_date=").append(FromField.getText());
            else if (!FromField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid 'From' date format (YYYY-MM-DD)", "Error", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }

            if (!ToField.getText().isEmpty() && ToField.getText().matches("\\d{4}-\\d{2}-\\d{2}"))
                route.append("&to_date=").append(ToField.getText());
            else if (!ToField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid 'To' date format (YYYY-MM-DD)", "Error", JOptionPane.ERROR_MESSAGE);
                valid = false;
            }

            if (valid) {
                ApiCaller.GetRequest(route.toString()).thenAccept(response -> {

                    JSONArray logArray = new JSONArray(response);

                    StringBuilder logContent = new StringBuilder();
                    for (int i = 0; i < logArray.length(); i++) {
                        JSONObject logEntry = logArray.getJSONObject(i);
                        String date = logEntry.getString("date");
                        String content = logEntry.getString("content");

                        logContent.append(date).append("\n");
                        logContent.append(content);
                        logContent.append("--------------------------------------------------------------------------------------------\n");
                    }
                    LogsContent.setText(logContent.toString());

                }).exceptionally(ex -> {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                });
            }
        });

        Del.setEnabled(false);
        Del.putClientProperty("JButton.buttonType", "roundRect");
        Del.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        Del.addActionListener(e -> {
            int choice =  JOptionPane.showConfirmDialog(null,"Are you sure you want to","Delete",JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                ApiCaller.DeleteRequest("/delete_pharma?ID=" + PharmacyIDField.getText()).thenAccept(response -> {
                    JOptionPane.showMessageDialog(null, response, "Success", JOptionPane.INFORMATION_MESSAGE);
                    Name.setText("PharmacyName: Null");
                    workerListContent.removeAll();
                    LogsContent.setText("");
                    Del.setEnabled(false);
                }).exceptionally(ex -> {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                });
            }
        });

        PharmacyIDL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        PharmacyIDField.putClientProperty("JComponent.roundRect", true);
        PharmacyIDField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));

        Name.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        empListL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        FromL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        ToL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        FromField.putClientProperty("JComponent.roundRect", true);
        FromField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        ToField.putClientProperty("JComponent.roundRect", true);
        ToField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        add(FetchPharmacy, new float[]{0.825f, 0.05f, 0.15f, 0.1f, 35.0f});
        add(PharmacyIDL, new float[]{0.0225f, 0.05f, 0.1075f, 0.1f, 35.0f});
        add(PharmacyIDField, new float[]{0.13f, 0.05f, 0.67f, 0.1f, 35.0f});

        add(Name, new float[]{0.075f, 0.2f, 0.4f, 0.1f, 30.0f});
        add(empListL, new float[]{0.075f, 0.30f, 0.4f, 0.1f, 30.0f});
        add(FromL, new float[]{0.525f, 0.2f, 0.05f, 0.075f, 30.0f});
        add(ToL, new float[]{0.775f, 0.2f, 0.05f, 0.075f, 30.0f});

        add(FromField, new float[]{0.5625f, 0.2f, 0.175f, 0.075f, 30.0f});
        add(ToField, new float[]{0.8f, 0.2f, 0.175f, 0.075f, 30.0f});

        add(InspectUpdates, new float[]{0.525f, 0.3f, 0.2125f, 0.075f, 30.0f});
        add(InspectSells, new float[]{0.775f, 0.3f, 0.2125f, 0.075f, 30.0f});
        add(Del, new float[]{0.075f, 0.875f, 0.35f, 0.1f, 30.0f});

        add(workerList, new float[]{0.075f, 0.4f, 0.35f, 0.45f, 30.0f});
        add(LogsArea, new float[]{0.525f, 0.4f, 0.45f, 0.55f, 30.0f});
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g.setColor(MainWindow.Tex);
        int mid = this.getWidth() / 2;
        int YStart = (int) (this.getHeight() * 0.1825);
        int YEnd = (int) (this.getHeight() * 0.97);
        g.drawLine(mid, YStart, mid, YEnd);
    }
}
