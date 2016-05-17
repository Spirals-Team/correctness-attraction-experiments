package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    private final int cx = 200;
    private final int cy = 350;
    private final int r = 100;

    public Window(Model m) {
        m.addObserver(this);
        this.label = new JLabel();
        this.c = new Control(m, label);

        this.panel = new JPanel();
        this.panel.add(c);

        this.panel.add(label);
        this.panel.setPreferredSize(new Dimension(400,400));
        this.panel.setBackground(Color.WHITE);

        this.frame = new JFrame();
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setContentPane(panel);
        this.frame.setPreferredSize(new Dimension(400,400));
        this.frame.pack();

        Graphics2D g = (Graphics2D) this.panel.getGraphics();
        g.clearRect(0, this.c.getHeight() + 10, 400, 400);
        this.drawSuccessMeter(g);

    }

    private void drawSuccessMeter(Graphics2D g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("resources/img/success_meter.png"));
            g.drawImage(img, -45, 400 - img.getHeight(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        float percentage = ((Model) observable).getPercentageOfSuccess();
        System.out.println(String.format("%.2f", percentage) + " % of success");
        this.panel.setBackground(Color.WHITE);
        Graphics2D g = (Graphics2D) this.panel.getGraphics();
        g.clearRect(0, this.c.getHeight() + 10, 400, 400);
        this.drawSuccessMeter(g);
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.red);
        double a = ( (percentage + 900 ) * 180 / 100) * Math.PI / 180F;
        float x = (float)(cx + r * Math.cos(a));
        float y = (float)(cy + r * Math.sin(a));
        g.draw(new Line2D.Float(cx, cy, x, y));
    }



    public static void main(String[] args) {
        Model m = new Model();
        Window w = new Window(m);
    }

}
