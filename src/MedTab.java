import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

public class MedTab extends JPanel {




    JButton AddMed = new JButton("Add New Med");
    JButton ChangeState = new JButton("Change State");
    JButton ChangePic = new JButton("Change Picture");
    JButton FetchMed = new JButton("Fetch");
    JLabel MedIDL = new JLabel("MedID:");
    JTextField MedIDField = new JTextField("");
    JLabel MedName = new JLabel("MedName: Null");
    JLabel DosageForm = new JLabel("DosageForm: Null");
    JLabel Manufacturer = new JLabel("Manufacturer: Null");
    JLabel Country = new JLabel("Country: Null");
    JLabel POM = new JLabel("POM: Null");
    JLabel Brand = new JLabel("Brand: Null");
    JLabel Obsolete = new JLabel("Obsolete: Null");
    JLabel MedImage = new JLabel();
    JComboBox<TA> TA_Box = new JComboBox<>();
    JComboBox<String> Unit = new JComboBox<>(new String[]{"g", "ml/g", "ml/ml", "g/g", "meq/ml", "mcg/ml", "mcg/g", "mg", "mcg", "mg/ml", "mg/g", "W/W %", "W/V %", "V/V %", "UI"});
    JLabel DoseL = new JLabel("Dose:");
    JTextField Dose = new JTextField("");
    JButton addTA = new JButton("Add Ta");

    JLabel TasL = new JLabel("Therapeutic Agents:");
    JPanel MedTAListContent = new JPanel();
    JScrollPane MedTAList = new JScrollPane(MedTAListContent);


    public void UpdateTheme() {
        setBackground(MainWindow.BG);
        MedTAList.setBackground(MainWindow.BG);
        MedTAListContent.setBackground(MainWindow.BG);
        FetchMed.setBackground(MainWindow.Comp);
        FetchMed.setForeground(MainWindow.TexComp);
        addTA.setBackground(MainWindow.Comp);
        addTA.setForeground(MainWindow.TexComp);
        MedIDL.setForeground(MainWindow.Tex);
        TasL.setForeground(MainWindow.Tex);
        DoseL.setForeground(MainWindow.Tex);
        MedName.setForeground(MainWindow.Tex);
        DosageForm.setForeground(MainWindow.Tex);
        Manufacturer.setForeground(MainWindow.Tex);
        Country.setForeground(MainWindow.Tex);
        POM.setForeground(MainWindow.Tex);
        Brand.setForeground(MainWindow.Tex);
        Obsolete.setForeground(MainWindow.Tex);
        MedImage.setBorder(BorderFactory.createLineBorder(MainWindow.Comp, 7));
        AddMed.setBackground(MainWindow.Comp);
        AddMed.setForeground(MainWindow.TexComp);
        ChangeState.setBackground(MainWindow.Comp);
        ChangeState.setForeground(MainWindow.TexComp);
        ChangePic.setBackground(MainWindow.Comp);
        ChangePic.setForeground(MainWindow.TexComp);

        MedTAList.getVerticalScrollBar().setUI(new BasicScrollBarUI() {

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


        for (Component X : MedTAListContent.getComponents()) if (X instanceof TaOption temp) temp.UpdateTheme();


    }

    public void triggerFetch(String MedID) {
        MedIDField.setText(MedID);
        FetchMed.doClick();
    }

    MedTab() {


        setLayout(new RelativeLayout());

        populateTAs();

        TasL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        MedTAListContent.setLayout(new GridLayout(0, 1));
        MedTAList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        MedTAList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        MedTAList.setBorder(null);

        TA_Box.putClientProperty("JComponent.roundRect", true);
        TA_Box.setEditable(true);
        TA_Box.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        Unit.putClientProperty("JComponent.roundRect", true);
        Unit.setEditable(true);
        Unit.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        Unit.setEditable(false);


        FetchMed.putClientProperty("JButton.buttonType", "roundRect");
        FetchMed.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        FetchMed.addActionListener(e -> {
            MedTAListContent.removeAll();
            ApiCaller.GetRequest("/MedDetails?med_id=" + MedIDField.getText()).thenAccept(response -> {

                JSONObject jsonObject = new JSONObject(response);
                MedName.setText("MedName: " + jsonObject.optString("Med"));
                DosageForm.setText("DosageForm: " + jsonObject.optString("Form"));
                Manufacturer.setText("Manufacturer: " + jsonObject.optString("manufacturer"));
                Country.setText("Country: " + jsonObject.optString("country"));
                POM.setText("POM: " + jsonObject.optString("POM"));
                Brand.setText("Brand: " + jsonObject.optString("Brand"));
                Obsolete.setText("Obsolete: " + jsonObject.optString("Obsolete"));
                addTA.setEnabled(true);
                ChangeState.setEnabled(true);
                ChangePic.setEnabled(true);

                loadImageAsync(ApiCaller.serverAddress + "/MedIMG?ImageId=" + MedIDField.getText());

                if (!jsonObject.isNull("TAs")) {
                    JSONArray tas = jsonObject.getJSONArray("TAs");
                    JSONArray tasIds = jsonObject.getJSONArray("TA_ids");
                    JSONArray con = jsonObject.getJSONArray("concentrations");
                    JSONArray unit = jsonObject.getJSONArray("units");

                    for (int i = 0; i < tas.length(); i++) {
                        TaOption itemPanel = new TaOption(
                                tas.getString(i) + ":" + con.getString(i) + unit.getString(i),
                                jsonObject.getInt("Med_id"),
                                tasIds.getInt(i)
                        );
                        itemPanel.UpdateTheme();
                        MedTAListContent.add(itemPanel);
                    }
                }

            }).exceptionally(ex -> {
                MedName.setText("MedName: Null");
                DosageForm.setText("DosageForm: Null");
                Manufacturer.setText("Manufacturer: Null");
                Country.setText("Country: Null");
                POM.setText("POM: Null");
                Brand.setText("Brand: Null");
                Obsolete.setText("Obsolete: Null");
                addTA.setEnabled(false);
                ChangeState.setEnabled(false);
                ChangePic.setEnabled(false);
                loadPlaceHolder();

                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            });
        });


        addTA.putClientProperty("JButton.buttonType", "roundRect");
        addTA.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        addTA.setEnabled(false);
        addTA.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this Ta to dosage?", "Confirm", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                JSONObject body = new JSONObject();
                body.put("MedID", MedIDField.getText());
                TA ta = (TA) TA_Box.getSelectedItem();
                assert ta != null;
                body.put("TaID", ta.id);
                String unit = (String) Unit.getSelectedItem();
                body.put("unit", unit);
                body.put("concetration", Dose.getText());

                ApiCaller.PostRequest("/dosage_add", body).thenAccept(response -> {
                    JOptionPane.showMessageDialog(null, response, "Success", JOptionPane.INFORMATION_MESSAGE);

                }).exceptionally(ex -> {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                });
                triggerFetch(MedIDField.getText());
            }
        });


        AddMed.putClientProperty("JButton.buttonType", "roundRect");
        AddMed.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        AddMed.addActionListener(e ->{
            JDialog dialog = new JDialog((Frame) null, "AddMedForm", true);
            dialog.setContentPane(new AddMedForm());
            dialog.setSize(900, 500);
            dialog.setLocationRelativeTo(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        });


        ChangeState.setEnabled(false);
        ChangeState.putClientProperty("JButton.buttonType", "roundRect");
        ChangeState.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        ChangeState.addActionListener(e -> {
            JSONObject body = new JSONObject();
            body.put("Med_id", MedIDField.getText());

            ApiCaller.PutRequest("/change_med_state", body).thenAccept(response -> {
                JOptionPane.showMessageDialog(null, response, "Success", JOptionPane.INFORMATION_MESSAGE);
                triggerFetch(MedIDField.getText());
            }).exceptionally(ex -> {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            });
        });

        ChangePic.setEnabled(false);
        ChangePic.putClientProperty("JButton.buttonType", "roundRect");
        ChangePic.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        ChangePic.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select PNG Image");
            chooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));

            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                String MedID = MedIDField.getText();

                if (MedID != null && !MedID.trim().isEmpty()) {
                    ApiCaller.UploadImage(MedID.trim(), selectedFile).thenAccept(response -> {
                        JOptionPane.showMessageDialog(this, "Image uploaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        triggerFetch(MedID);
                    }).exceptionally(ex -> {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Upload Error", JOptionPane.ERROR_MESSAGE);
                        return null;
                    });
                }
            }
        });


        MedIDL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        MedIDField.putClientProperty("JComponent.roundRect", true);
        MedIDField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));

        DoseL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        Dose.putClientProperty("JComponent.roundRect", true);
        Dose.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        MedName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        DosageForm.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        Manufacturer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        Country.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        POM.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        Brand.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        Obsolete.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        add(FetchMed, new float[]{0.825f, 0.05f, 0.15f, 0.1f, 35.0f});
        add(MedIDL, new float[]{0.0225f, 0.05f, 0.1075f, 0.1f, 35.0f});
        add(MedIDField, new float[]{0.08f, 0.05f, 0.72f, 0.1f, 35.0f});

        add(MedImage, new float[]{0.068f, 0.1625f, 0.2f, 0.35f, 30.0f});
        add(MedName, new float[]{0.075f, 0.52f, 0.4f, 0.05f, 30.0f});
        add(DosageForm, new float[]{0.075f, 0.57f, 0.4f, 0.05f, 30.0f});
        add(Manufacturer, new float[]{0.075f, 0.62f, 0.4f, 0.05f, 30.0f});
        add(Country, new float[]{0.075f, 0.67f, 0.4f, 0.05f, 30.0f});
        add(POM, new float[]{0.075f, 0.72f, 0.4f, 0.05f, 30.0f});
        add(Brand, new float[]{0.075f, 0.77f, 0.4f, 0.05f, 30.0f});
        add(Obsolete, new float[]{0.075f, 0.82f, 0.4f, 0.05f, 30.0f});
        add(TA_Box, new float[]{0.525f, 0.8f, 0.1875f, 0.05f, 30.0f});
        add(Unit, new float[]{0.725f, 0.8f, 0.1f, 0.05f, 30.0f});
        add(DoseL, new float[]{0.8325f, 0.8f, 0.05f, 0.05f, 30.0f});
        add(Dose, new float[]{0.875f, 0.8f, 0.1f, 0.05f, 30.0f});
        add(addTA, new float[]{0.525f, 0.875f, 0.45f, 0.1f, 35.0f});

        add(TasL, new float[]{0.525f, 0.2f, 0.2f, 0.05f, 30.0f});
        add(MedTAList, new float[]{0.525f, 0.275f, 0.45f, 0.5f, 30.0f});

        add(AddMed, new float[]{0.025f, 0.875f, 0.15f, 0.1f, 30.0f});
        add(ChangeState, new float[]{0.18f, 0.875f, 0.15f, 0.1f, 30.0f});
        add(ChangePic, new float[]{0.335f, 0.875f, 0.15f, 0.1f, 30.0f});


        loadPlaceHolder();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updateImage();
            }
        });
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


    BufferedImage img;

    void loadPlaceHolder() {
        try {
            img = ImageIO.read(new File("placeholder.png"));
            MedImage.setText("");
            Image scaled = img.getScaledInstance(MedImage.getWidth(), MedImage.getHeight(), Image.SCALE_SMOOTH);
            MedImage.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            MedImage.setText("Failed to load placeholder");
        }
    }

    void loadImageAsync(String urlString) {
        new SwingWorker<BufferedImage, Void>() {
            @Override
            protected BufferedImage doInBackground() throws Exception {
                return ImageIO.read(URI.create(urlString).toURL());
            }

            @Override
            protected void done() {
                try {
                    img = get();
                } catch (Exception e) {
                    img = null;
                    e.printStackTrace();
                }
                updateImage();

            }
        }.execute();
    }

    void updateImage() {
        if (img == null) {
            loadPlaceHolder();
            return;
        }
        Image scaled = img.getScaledInstance(MedImage.getWidth(), MedImage.getHeight(), Image.SCALE_SMOOTH);
        MedImage.setIcon(new ImageIcon(scaled));
    }

    static class TA {
        int id;
        String name;

        public TA(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    TA[] Full_TAs_List;
    public void populateTAs() {

        ApiCaller.GetRequest("/TAList").thenAccept(response -> {

            JSONArray TAArray = new JSONArray(response);

            Full_TAs_List = new TA[TAArray.length()];
            for (int i = 0; i < TAArray.length(); i++) {
                JSONObject ta = TAArray.getJSONObject(i);
                Full_TAs_List[i] = new TA(ta.getInt("TA_id"), ta.getString("TA"));
                TA_Box.addItem(Full_TAs_List[i]);
            }


            JTextField editor = (JTextField) TA_Box.getEditor().getEditorComponent();

            editor.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String input = editor.getText();
                    TA_Box.hidePopup();
                    TA_Box.removeAllItems();

                    for (TA ta : Full_TAs_List) {
                        if (ta.name.toLowerCase().startsWith(input.toLowerCase())) {
                            TA_Box.addItem(ta);
                        }
                    }

                    editor.setText(input);
                    TA_Box.showPopup();
                }
            });


        }).exceptionally(ex -> {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        });


    }


}
