package gui;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import static javax.sound.sampled.FloatControl.Type.MASTER_GAIN;

/**
 * Created by bdanglot on 13/05/16.
 */
public class Window extends JFrame {

    private Control c;

    private JPanel panel;

    private JLabel label;

    private final int cx = 200;
    private final int cy = 300;
    private final int r = 100;

    private Model model;

    private JTextArea text;

    public Window(Model m) {
        super();
        this.model = m;
        this.label = new JLabel();
        this.c = new Control(m, label);

        this.panel = new JPanel();
        this.panel.add(c);

        this.text = new JTextArea("toto");

        this.panel.add(text);
        this.panel.add(label);
        this.panel.setPreferredSize(new Dimension(400,350));
        this.panel.setBackground(Color.WHITE);

        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        this.setPreferredSize(new Dimension(400,350));
        this.pack();

        this.text.addKeyListener(new KeyAdapter() {


            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("pressed " + e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("released " + e.getKeyCode());
            }
        });

        Graphics2D g = (Graphics2D) this.panel.getGraphics();
        g.clearRect(0, this.c.getHeight() + 10, 400, 350);
        this.drawSuccessMeter(g);
    }

    private void drawSuccessMeter(Graphics2D g) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("resources/img/success_meter.png"));
            g.drawImage(img, -45, 350 - img.getHeight(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Graphics2D g2D = (Graphics2D) this.panel.getGraphics();
        double percentage = this.model.runAllTask();
        System.out.println(String.format("%.2f", percentage) + " % of success");
        this.panel.setBackground(Color.WHITE);
        g2D.clearRect(0, this.c.getHeight() + 15, 400, 350);
        this.drawSuccessMeter(g2D);
        g2D.setStroke(new BasicStroke(3));
        g2D.setColor(Color.red);
        double a = ( (percentage + 900 ) * 180 / 100) * Math.PI / 180F;
        float x = (float)(cx + r * Math.cos(a));
        float y = (float)(cy + r * Math.sin(a));
        g2D.draw(new Line2D.Float(cx, cy, x, y));
    }

    public static void main(String[] args) {
        Model m = new Model();
        Window w = new Window(m);
        while (true) {
            w.run();
        }
    }

}
