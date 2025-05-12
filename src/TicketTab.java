import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class TicketTab extends JPanel {

    Ticket[] Current = new Ticket[4];
    float[] X={0.05f,0.275f,0.5f,0.725f};
    String cursorNext = "9223372036854775807";
    String cursorPrev = "9223372036854775807";

    void load_next_Page() {
        ApiCaller.GetRequest("/tickets_next?cursor=" + cursorNext + "&limit=4").thenAccept(response -> {

            JSONArray Temp = new JSONArray(response);

            JSONObject lastObj = null;


            for (int i = 0; i < 4; i++) {

                if (i < Temp.length()) {
                    lastObj = Temp.getJSONObject(i);
                    Current[i] = new Ticket(lastObj.getString("Content"), lastObj.getString("Account"), lastObj.getInt("Pharmacy"), lastObj.getInt("Med"));
                    if (i == 0) {
                        cursorPrev = lastObj.get("TK_id").toString();
                    }
                    add(Current[i],new float[]{X[i], 0.05f, 0.2f, 0.9f, 30.0f});
                } else {
                    remove(Current[i]);
                    Current[i] = null;
                }
            }


            if (lastObj != null) {
                cursorNext = lastObj.get("TK_id").toString();
            }


        }).exceptionally(ex -> {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        });
    }




    void load_previous_Page() {
        ApiCaller.GetRequest("/tickets_prev?cursor=" + cursorPrev + "&limit=4").thenAccept(response -> {

            JSONArray Temp = new JSONArray(response);

            JSONObject lastObj = null;


            for (int i = 3; i >= 0; i--) {

                if (i < Temp.length()) {
                    lastObj = Temp.getJSONObject(i);
                    Current[i] = new Ticket(lastObj.getString("Content"), lastObj.getString("Account"), lastObj.getInt("Pharmacy"), lastObj.getInt("Med"));
                    if (i == 0) {
                        cursorNext = lastObj.get("TK_id").toString();
                    }
                    add(Current[i],new float[]{X[i], 0.05f, 0.2f, 0.9f, 30.0f});
                } else {
                    remove(Current[i]);
                    Current[i] = null;
                }
            }


            if (lastObj != null) {
                cursorPrev = lastObj.get("TK_id").toString();
            }


        }).exceptionally(ex -> {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        });
    }


    void updateTheme() {
        setBackground(MainWindow.BG);
        for (Ticket X : Current) if (X != null) X.UpdateTheme();
    }

    //Ticket temp = new Ticket("hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello ", "0", 2, 3);

    public TicketTab() {
        setLayout(new RelativeLayout());
        //add(temp, new float[]{0.05f, 0.05f, 0.2f, 0.9f, 30.0f});
    }

}
