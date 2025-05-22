import java.awt.*;
import java.util.HashMap;

public class RelativeLayout implements LayoutManager2 {
    private static final int PREF_W = 1920;
    private static final int PREF_H = 1080;
    private static final int MIN_W = 400;
    private static final int MIN_H = 300;
    private static final int MAX_W = 2560;
    private static final int MAX_H = 1440;
    private static final float MIN_F = 8f;
    private static final float MAX_F = 64f;

    private final HashMap<Component, float[]> constraints = new HashMap<>();

    //constraint (x, y, width, height as 0.0 to 1.0,Font Ref)
    public void addLayoutComponent(Component comp, Object constraint) {
        if (constraint instanceof float[] values && values.length == 5) {
            constraints.put(comp, values);
        } else {
            throw new IllegalArgumentException("Constraint must be a double[5] array (x, y, width, height,font scale)");
        }
    }

    public void removeLayoutComponent(Component comp) {
        constraints.remove(comp);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(MAX_W, MAX_H);  // Maximum size
    }

    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(PREF_W, PREF_H); // Default size
    }

    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(MIN_W, MIN_H); // Minimum size
    }

    public void layoutContainer(Container parent) {
        int width = parent.getWidth();
        int height = parent.getHeight();
        float ScaleF = Math.min(width / (float) PREF_W, height / (float) PREF_H);


        for (Component comp : parent.getComponents()) {
            float[] values = constraints.get(comp);

            if (values != null) {
                int x = (int) (values[0] * width);
                int y = (int) (values[1] * height);
                int w = (int) (values[2] * width);
                int h = (int) (values[3] * height);
                comp.setBounds(x, y, w, h);

                if (comp.getFont() != null) {
                    Font Old = comp.getFont();
                    float Nsize = Math.max(MIN_F, Math.min(values[4] * ScaleF, MAX_F));
                    Font New = Old.deriveFont(Nsize);
                    comp.setFont(New);
                }
            }


        }
    }

    // Unused methods for LayoutManager2
    public void addLayoutComponent(String name, Component comp) {
    }

    public void invalidateLayout(Container target) {
    }

    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }
}


