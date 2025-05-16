import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;


public class TicketTab extends JPanel {

    Ticket[] Current = new Ticket[4];
    float[] X = {0.05f, 0.275f, 0.5f, 0.725f};
    String cursorNext = "9223372036854775807";
    String cursorPrev = "9223372036854775807";

    JButton Next = new JButton(">");
    JButton Prev = new JButton("<");

    void load_next_Page() {
        ApiCaller.GetRequest("/tickets_next?cursor=" + cursorNext + "&limit=4").thenAccept(response -> {


            JSONArray Temp = new JSONArray(response);

            if (!Temp.isEmpty()) {
                for (Ticket X : Current) if (X != null) remove(X);
                revalidate();
                repaint();


                JSONObject Obj = Temp.getJSONObject(0);
                Current[0] = new Ticket(Obj.getString("Content"), Obj.getString("Account"), Obj.getInt("Pharmacy"), Obj.getInt("Med"),Obj.get("TK_id").toString());
                add(Current[0], new float[]{0.05f, 0.05f, 0.2f, 0.9f, 30.0f});
                cursorPrev = Obj.get("TK_id").toString();

                if (1 < Temp.length()) {
                    Obj = Temp.getJSONObject(1);
                    Current[1] = new Ticket(Obj.getString("Content"), Obj.getString("Account"), Obj.getInt("Pharmacy"), Obj.getInt("Med"),Obj.get("TK_id").toString());
                    add(Current[1], new float[]{0.275f, 0.05f, 0.2f, 0.9f, 30.0f});
                } else {
                    Current[1] = null;
                }

                if (2 < Temp.length()) {
                    Obj = Temp.getJSONObject(2);
                    Current[2] = new Ticket(Obj.getString("Content"), Obj.getString("Account"), Obj.getInt("Pharmacy"), Obj.getInt("Med"),Obj.get("TK_id").toString());
                    add(Current[2], new float[]{0.5f, 0.05f, 0.2f, 0.9f, 30.0f});
                } else {
                    Current[2] = null;
                }

                if (3 < Temp.length()) {
                    Obj = Temp.getJSONObject(3);
                    Current[3] = new Ticket(Obj.getString("Content"), Obj.getString("Account"), Obj.getInt("Pharmacy"), Obj.getInt("Med"),Obj.get("TK_id").toString());
                    add(Current[3], new float[]{0.725f, 0.05f, 0.2f, 0.9f, 30.0f});
                    cursorNext = Obj.get("TK_id").toString();
                } else {
                    Current[3] = null;
                }
                revalidate();
                repaint();


            }

        }).exceptionally(ex -> {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        });
    }


    void load_previous_Page() {
        ApiCaller.GetRequest("/tickets_prev?cursor=" + cursorPrev + "&limit=4").thenAccept(response -> {

            JSONArray Temp = new JSONArray(response);

            if (!Temp.isEmpty()) {
                for (Ticket X : Current)  if (X != null) remove(X);
                revalidate();
                repaint();

                JSONObject Obj = Temp.getJSONObject(0);
                Current[3] = new Ticket(Obj.getString("Content"), Obj.getString("Account"), Obj.getInt("Pharmacy"), Obj.getInt("Med"),Obj.get("TK_id").toString());
                add(Current[3], new float[]{0.725f, 0.05f, 0.2f, 0.9f, 30.0f});
                cursorNext = Obj.get("TK_id").toString();

                if (1 < Temp.length()) {
                    Obj = Temp.getJSONObject(1);
                    Current[2] = new Ticket(Obj.getString("Content"), Obj.getString("Account"), Obj.getInt("Pharmacy"), Obj.getInt("Med"),Obj.get("TK_id").toString());
                    add(Current[2], new float[]{0.5f, 0.05f, 0.2f, 0.9f, 30.0f});
                } else {
                    Current[2] = null;
                }

                if (2 < Temp.length()) {
                    Obj = Temp.getJSONObject(2);
                    Current[1] = new Ticket(Obj.getString("Content"), Obj.getString("Account"), Obj.getInt("Pharmacy"), Obj.getInt("Med"),Obj.get("TK_id").toString());
                    add(Current[1], new float[]{0.275f, 0.05f, 0.2f, 0.9f, 30.0f});
                } else {
                    Current[1] = null;
                }

                if (3 < Temp.length()) {
                    Obj = Temp.getJSONObject(3);
                    Current[0] = new Ticket(Obj.getString("Content"), Obj.getString("Account"), Obj.getInt("Pharmacy"), Obj.getInt("Med"),Obj.get("TK_id").toString());
                    add(Current[0], new float[]{0.05f, 0.05f, 0.2f, 0.9f, 30.0f});
                    cursorPrev = Obj.get("TK_id").toString();
                } else {
                    Current[0] = null;
                }
                revalidate();
                repaint();

            }

        }).exceptionally(ex -> {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        });
    }


    void refresh_Page() {
        ApiCaller.GetRequest("/tickets_refresh?cursor=" + cursorPrev + "&limit=4").thenAccept(response -> {


            JSONArray Temp = new JSONArray(response);

            if (!Temp.isEmpty()) {
                for (Ticket X : Current) if (X != null) remove(X);
                revalidate();
                repaint();


                JSONObject Obj = Temp.getJSONObject(0);
                Current[0] = new Ticket(Obj.getString("Content"), Obj.getString("Account"), Obj.getInt("Pharmacy"), Obj.getInt("Med"),Obj.get("TK_id").toString());
                add(Current[0], new float[]{0.05f, 0.05f, 0.2f, 0.9f, 30.0f});
                cursorPrev = Obj.get("TK_id").toString();

                if (1 < Temp.length()) {
                    Obj = Temp.getJSONObject(1);
                    Current[1] = new Ticket(Obj.getString("Content"), Obj.getString("Account"), Obj.getInt("Pharmacy"), Obj.getInt("Med"),Obj.get("TK_id").toString());
                    add(Current[1], new float[]{0.275f, 0.05f, 0.2f, 0.9f, 30.0f});
                } else {
                    Current[1] = null;
                }

                if (2 < Temp.length()) {
                    Obj = Temp.getJSONObject(2);
                    Current[2] = new Ticket(Obj.getString("Content"), Obj.getString("Account"), Obj.getInt("Pharmacy"), Obj.getInt("Med"),Obj.get("TK_id").toString());
                    add(Current[2], new float[]{0.5f, 0.05f, 0.2f, 0.9f, 30.0f});
                } else {
                    Current[2] = null;
                }

                if (3 < Temp.length()) {
                    Obj = Temp.getJSONObject(3);
                    Current[3] = new Ticket(Obj.getString("Content"), Obj.getString("Account"), Obj.getInt("Pharmacy"), Obj.getInt("Med"),Obj.get("TK_id").toString());
                    add(Current[3], new float[]{0.725f, 0.05f, 0.2f, 0.9f, 30.0f});
                    cursorNext = Obj.get("TK_id").toString();
                } else {
                    Current[3] = null;
                }
                revalidate();
                repaint();


            }

        }).exceptionally(ex -> {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        });
    }

    void updateTheme() {
        setBackground(MainWindow.BG);
        Next.setBackground(MainWindow.Comp);
        Next.setForeground(MainWindow.TexComp);
        Prev.setBackground(MainWindow.Comp);
        Prev.setForeground(MainWindow.TexComp);

        for (Ticket X : Current) if (X != null) X.UpdateTheme();
    }

    public TicketTab() {
        setLayout(new RelativeLayout());

        Next.putClientProperty("JButton.buttonType", "roundRect");
        Next.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        Next.addActionListener(e -> {

            load_next_Page();
            updateTheme();

        });

        Prev.putClientProperty("JButton.buttonType", "roundRect");
        Prev.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 35));
        Prev.addActionListener(e -> {

            load_previous_Page();
            updateTheme();
        });

        add(Prev, new float[]{0.01f, 0.45f, 0.04f, 0.15f, 35.0f});
        add(Next, new float[]{0.95f, 0.45f, 0.04f, 0.15f, 35.0f});
    }

}
