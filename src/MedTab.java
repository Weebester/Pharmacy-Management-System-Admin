import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MedTab extends JPanel {

    JButton FetchMed = new JButton("Fetch");
    JLabel MedIDL = new JLabel("MedID:");
    JTextField MedIDField = new JTextField("");
    JLabel MedName = new JLabel("MedName: Null");
    JLabel Manufacturer = new JLabel("Manufacturer: Null");
    JLabel Country = new JLabel("Country: Null");
    JLabel MedImage=new JLabel();


    public void UpdateTheme() {
        setBackground(MainWindow.BG);
        FetchMed.setBackground(MainWindow.Comp);
        FetchMed.setForeground(MainWindow.TexComp);
        MedIDL.setForeground(MainWindow.Tex);
        MedName.setForeground(MainWindow.Tex);
        Manufacturer.setForeground(MainWindow.Tex);
        Country.setForeground(MainWindow.Tex);

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


    MedTab(){


        setLayout(new RelativeLayout());


        FetchMed.putClientProperty("JButton.buttonType", "roundRect");
        FetchMed.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        FetchMed.addActionListener(e -> {
        });

        MedIDL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        MedIDField.putClientProperty("JComponent.roundRect", true);
        MedIDField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));

        MedName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        Manufacturer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        Country.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        add(FetchMed, new float[]{0.825f, 0.05f, 0.15f, 0.1f, 35.0f});
        add(MedIDL, new float[]{0.0225f, 0.05f, 0.1075f, 0.1f, 35.0f});
        add(MedIDField, new float[]{0.08f, 0.05f, 0.72f, 0.1f, 35.0f});

        add(MedImage,new float[]{0.075f, 0.2f, 0.2f, 0.3f, 30.0f});
        add(MedName, new float[]{0.075f, 0.55f, 0.4f, 0.1f, 30.0f});
        add(Manufacturer, new float[]{0.075f, 0.65f, 0.4f, 0.1f, 30.0f});
        add(Country, new float[]{0.075f, 0.75f, 0.4f, 0.1f, 30.0f});


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
                return ImageIO.read(new URL(urlString));
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
