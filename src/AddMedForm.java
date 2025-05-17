import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddMedForm extends JPanel {

    JLabel nameLabel = new JLabel("Medicine Name");
    JTextField nameField = new JTextField();

    JLabel manufacturerLabel = new JLabel("Manufacturer");
    JComboBox<Manufacturer> manufacturerBox = new JComboBox<>();

    JLabel DosageFormLabel = new JLabel("DosageForm:");
    JComboBox<String> DosageFormBox = new JComboBox<>(new String[]{"TABLET","CAPSULE","SYRUP","SUSPENSION","INJECTION","OINTMENT","CREAM","GEL","DROPS","INHALER","SPRAY","PATCH","POWDER","SOLUTION","SUPPOSITORY","EMULSION","LOZENGE","TROCHE","NASAL_SPRAY","EYE_DROPS","EAR_DROPS","TRANSDERMAL_PATCH","MUCOADHESIVE_FILM","MISC"});


    JLabel descriptionLabel = new JLabel("Description");
    JTextArea descriptionField = new JTextArea();

    JCheckBox pomCheckbox = new JCheckBox("POM");
    JCheckBox brandCheckbox = new JCheckBox("Brand");

    JButton addButton = new JButton("Add Medicine");

    public AddMedForm() {
        setLayout(new RelativeLayout());
        setBackground(MainWindow.BG);

        Font Labelfont = new Font(Font.SANS_SERIF, Font.PLAIN, 40);
        Font InputFont = new Font(Font.SANS_SERIF, Font.PLAIN, 35);

        nameLabel.setFont(Labelfont);
        nameLabel.setForeground(MainWindow.Tex);
        nameField.setFont(InputFont);
        nameField.putClientProperty("JComponent.roundRect", true);

        manufacturerLabel.setFont(Labelfont);
        manufacturerLabel.setForeground(MainWindow.Tex);
        manufacturerBox.setFont(InputFont);
        manufacturerBox.setEditable(true);
        manufacturerBox.putClientProperty("JComponent.roundRect", true);

        DosageFormLabel.setFont(Labelfont);
        DosageFormLabel.setForeground(MainWindow.Tex);
        DosageFormBox.putClientProperty("JComponent.roundRect", true);
        DosageFormBox.setEditable(true);
        DosageFormBox.setFont(InputFont);
        DosageFormBox.setEditable(false);

        descriptionLabel.setFont(Labelfont);
        descriptionLabel.setForeground(MainWindow.Tex);
        descriptionField.setFont(InputFont);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        descriptionField.putClientProperty("JComponent.roundRect", true);

        pomCheckbox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        pomCheckbox.setForeground(MainWindow.Tex);
        brandCheckbox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        brandCheckbox.setForeground(MainWindow.Tex);

        addButton.setFont(Labelfont);
        addButton.putClientProperty("JButton.buttonType", "roundRect");
        addButton.setBackground(MainWindow.Comp);
        addButton.setForeground(MainWindow.TexComp);

        add(nameLabel, new float[]{0.05f, 0.05f, 0.4f, 0.08f, 40});
        add(nameField, new float[]{0.05f, 0.13f, 0.9f, 0.08f, 35});

        add(manufacturerLabel, new float[]{0.05f, 0.23f, 0.4f, 0.08f, 40});
        add(manufacturerBox, new float[]{0.05f, 0.31f, 0.4f, 0.08f, 35});

        add(DosageFormLabel, new float[]{0.55f, 0.23f, 0.4f, 0.08f, 40});
        add(DosageFormBox, new float[]{0.55f, 0.31f, 0.4f, 0.08f, 35});

        add(descriptionLabel, new float[]{0.05f, 0.41f, 0.5f, 0.08f, 40});
        add(descriptionField, new float[]{0.05f, 0.49f, 0.9f, 0.15f, 35});

        add(pomCheckbox, new float[]{0.05f, 0.66f, 0.4f, 0.07f, 30});
        add(brandCheckbox, new float[]{0.55f, 0.66f, 0.4f, 0.07f, 30});

        add(addButton, new float[]{0.05f, 0.75f, 0.9f, 0.12f, 40});

        populateManufacturers();

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            Manufacturer selected = (Manufacturer) manufacturerBox.getSelectedItem();
            String desc = descriptionField.getText().trim();
            String isPom ="No";
            if(pomCheckbox.isSelected()){
                isPom ="Yes";
            }
            String isBrand ="No";
            if(brandCheckbox.isSelected()){
                isPom ="Yes";
            }
            String DForm=DosageFormBox.getSelectedItem().toString();


            if (name.isEmpty() || selected == null) {
                JOptionPane.showMessageDialog(this, "Fill out all required fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JSONObject body = new JSONObject();
            body.put( "Description",desc);
            body.put( "med", name);
            body.put("Manufacturer_id", selected.id);
            body.put("POM", isPom);
            body.put("Brand", isBrand);
            body.put("DosageForm",DForm);
            if (!desc.isEmpty()) body.put("description", desc);

            ApiCaller.PostRequest("/add_med", body).thenAccept(response -> {
                JOptionPane.showMessageDialog(this, "Medicine added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.getWindowAncestor(this).dispose();
                JSONObject jsonObject = new JSONObject(response);
                Main.MainW.MedT.triggerFetch(""+jsonObject.getInt("Med_id"));
            }).exceptionally(ex -> {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            });
        });
    }


    static class Manufacturer {
        int id;
        String name;

        Manufacturer(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    Manufacturer[] fullManufacturerList;
    private void populateManufacturers() {
        ApiCaller.GetRequest("/ManufacturerList").thenAccept(response -> {
            JSONArray arr = new JSONArray(response);
            fullManufacturerList = new Manufacturer[arr.length()];

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                fullManufacturerList[i] = new Manufacturer(obj.getInt("Manufacturer_id"), obj.getString("Manufacturer"));
                manufacturerBox.addItem(fullManufacturerList[i]);
            }

            JTextField editor = (JTextField) manufacturerBox.getEditor().getEditorComponent();
            editor.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String input = editor.getText();
                    manufacturerBox.hidePopup();
                    manufacturerBox.removeAllItems();

                    for (Manufacturer m : fullManufacturerList) {
                        if (m.name.toLowerCase().startsWith(input.toLowerCase())) {
                            manufacturerBox.addItem(m);
                        }
                    }

                    editor.setText(input);
                    manufacturerBox.showPopup();
                }
            });

        }).exceptionally(ex -> {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        });
    }
}

