import org.json.JSONArray;
import org.json.JSONObject;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

public class MedTab extends JPanel {


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
                TAbox.addItem(Full_TAs_List[i]);
            }


            JTextField editor = (JTextField) TAbox.getEditor().getEditorComponent();

            editor.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String input = editor.getText();
                    TAbox.hidePopup();
                    TAbox.removeAllItems();

                    for (TA ta : Full_TAs_List) {
                        if (ta.name.toLowerCase().startsWith(input.toLowerCase())) {
                            TAbox.addItem(ta);
                        }
                    }

                    editor.setText(input);
                    TAbox.showPopup();
                }
            });



        }).exceptionally(ex -> {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        });



    }

    JButton FetchMed = new JButton("Fetch");
    JLabel MedIDL = new JLabel("MedID:");
    JTextField MedIDField = new JTextField("");

    JLabel MedName = new JLabel("MedName: Null");
    JLabel Manufacturer = new JLabel("Manufacturer: Null");
    JLabel Country = new JLabel("Country: Null");
    JLabel POM = new JLabel("POM: Null");
    JLabel Brand = new JLabel("Brand: Null");
    JLabel MedImage = new JLabel();
    JComboBox<TA> TAbox=new JComboBox<>();;


    public void UpdateTheme() {
        setBackground(MainWindow.BG);
        FetchMed.setBackground(MainWindow.Comp);
        FetchMed.setForeground(MainWindow.TexComp);
        MedIDL.setForeground(MainWindow.Tex);
        MedName.setForeground(MainWindow.Tex);
        Manufacturer.setForeground(MainWindow.Tex);
        Country.setForeground(MainWindow.Tex);
        POM.setForeground(MainWindow.Tex);
        Brand.setForeground(MainWindow.Tex);
        MedImage.setBorder(BorderFactory.createLineBorder(MainWindow.Comp, 7));



        /*
        workerList.setBackground(MainWindow.BG);
        workerListContent.setBackground(MainWindow.BG);
        LogsArea.setBackground(MainWindow.BG);
        LogsContent.setBackground(MainWindow.BG);
        LogsContent.setForeground(MainWindow.Tex);
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

*/
    }


    MedTab() {


        setLayout(new RelativeLayout());

        populateTAs();

        TAbox.putClientProperty("JComponent.roundRect", true);
        TAbox.setEditable(true);
        TAbox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));


        FetchMed.putClientProperty("JButton.buttonType", "roundRect");
        FetchMed.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        FetchMed.addActionListener(e -> {
            ApiCaller.GetRequest("/MedDetails?med_id=" + MedIDField.getText()).thenAccept(response -> {

                JSONObject jsonObject = new JSONObject(response);
                MedName.setText("MedName: " + jsonObject.optString("Med"));
                Manufacturer.setText("Manufacturer: " + jsonObject.optString("manufacturer"));
                Country.setText("Country: " + jsonObject.optString("country"));

                loadImageAsync(ApiCaller.serverAddress + "/MedIMG?ImageId=" + MedIDField.getText());

            }).exceptionally(ex -> {
                MedName.setText("MedName: Null");
                Manufacturer.setText("Manufacturer: Null");
                Country.setText("Country: Null");
                loadPlaceHolder();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            });

        });

        MedIDL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        MedIDField.putClientProperty("JComponent.roundRect", true);
        MedIDField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));

        MedName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        Manufacturer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        Country.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        POM.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        Brand.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        add(FetchMed, new float[]{0.825f, 0.05f, 0.15f, 0.1f, 35.0f});
        add(MedIDL, new float[]{0.0225f, 0.05f, 0.1075f, 0.1f, 35.0f});
        add(MedIDField, new float[]{0.08f, 0.05f, 0.72f, 0.1f, 35.0f});

        add(MedImage, new float[]{0.075f, 0.175f, 0.2f, 0.35f, 30.0f});
        add(MedName, new float[]{0.075f, 0.535f, 0.4f, 0.05f, 30.0f});
        add(Manufacturer, new float[]{0.075f, 0.585f, 0.4f, 0.05f, 30.0f});
        add(Country, new float[]{0.075f, 0.635f, 0.4f, 0.05f, 30.0f});
        add(POM, new float[]{0.075f, 0.685f, 0.4f, 0.05f, 30.0f});
        add(Brand, new float[]{0.075f, 0.735f, 0.4f, 0.05f, 30.0f});
        add(TAbox, new float[]{0.075f, 0.835f, 0.4f, 0.05f, 30.0f});



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
            updateImage();
            MedImage.setText("");
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
                    loadPlaceHolder();
                    return;
                }
                updateImage();
            }
        }.execute();
    }

    void updateImage() {
        if (img == null) return;
        Image scaled = img.getScaledInstance(MedImage.getWidth(), MedImage.getHeight(), Image.SCALE_SMOOTH);
        MedImage.setIcon(new ImageIcon(scaled));
    }


}
