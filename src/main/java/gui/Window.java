package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by bdanglot on 13/05/16.
 */
public class Window implements Observer {

    private Control c;

    private JFrame frame;

    private JPanel panel;

    private JLabel label;

    public Window(Model m) {
        m.addObserver(this);
        this.label = new JLabel();
        this.c = new Control(m, label);
        this.frame = new JFrame();
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(400,800));
        this.frame.setContentPane(panel);
        this.panel.add(c);

        this.panel.add(label);
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println(String.format("%.2f", ((Model) observable).getPercentageOfSuccess()) + " % of success");
    }

    public static void main(String[] args) {
        Model m = new Model();
        Window w = new Window(m);
    }

}
