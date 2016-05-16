package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by beyni on 16/05/16.
 */
public class Control extends JSlider {

    private static final int RND_MIN = 0;//0.001 = 1 / 1000
    private static final int RND_MAX = 40;//0.9 = 900 / 1000

    private Model model;
    private JLabel label;

    public Control(Model m, JLabel l) {
        super(JSlider.HORIZONTAL, RND_MIN, RND_MAX, RND_MIN);
        this.model = m;
        this.label = l;
        this.setMajorTickSpacing(5);
        this.setMinorTickSpacing(1);
        this.setPaintTicks(true);
        this.setPaintLabels(true);
        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                float rand = getRnd();
                System.err.println(rand);
                label.setText("" + rand);
                model.setRnd(getRnd());
            }
        });
    }

    public float getRnd() {
        return ((float)this.getValue() / (float)1000);
    }

}
