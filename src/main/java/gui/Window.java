package gui;

import quicksort.QuickSortManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by bdanglot on 13/05/16.
 */
public class Window extends JFrame {

    private JPanel panel;

    private final int cx;
    private final int cy;
    private final int r = 100;

    private JLabel labelAccExec;

    private JLabel labelAccExecSuccess;

    private JLabel labelavgPertubationPerExecution;

    private JLabel labelNbTask;

    private JSlider sliderNb;

    private JLabel labelSize;

    private JSlider sliderSize;

    private Model model;

    private JTextArea rand;

    private JPanel panelImg;

    private BufferedImage img;

    private ButtonGroup typeOfPerturbation;


    public Window(Model m) {
        super();
        this.model = m;
        this.rand = new JTextArea(String.format("%.2f", this.model.getRnd()));
        this.rand.setEditable(false);

        this.labelAccExec = new JLabel(String.valueOf(this.model.getAccExec()));
        this.labelAccExecSuccess = new JLabel(String.valueOf(this.model.getAccExecSuccess()));
        this.labelavgPertubationPerExecution = new JLabel(String.valueOf(this.model.getAvgPerturbationPerExec()));

        try {
            this.img = ImageIO.read(new File("resources/img/success_meter.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));

        this.panel.add(this.buildPanelLabel());
        this.panel.add(this.buildTaskManager());
        this.panel.add(this.buildLocationManager());

        this.panel.add(new JLabel());
        this.panelImg = new JPanel();
        this.panelImg.setPreferredSize(new Dimension(this.img.getWidth(), this.img.getHeight()));
        this.panel.add(this.panelImg);
        this.panel.add(new JLabel());

        this.panel.setBackground(Color.WHITE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        this.pack();

        this.cy = this.panel.getHeight() - r / 2;
        this.cx = this.panel.getWidth() / 2;

        Graphics2D g2D = (Graphics2D) this.panel.getGraphics();
        g2D.clearRect(this.panelImg.getX(), this.panelImg.getY(),
                this.panelImg.getX() + this.panelImg.getWidth(), this.panelImg.getY() + this.panelImg.getHeight());
        int xImg = (this.panel.getWidth() / 2) - (this.img.getWidth() / 2);
        g2D.drawImage(img, xImg, this.panel.getHeight() - this.img.getHeight(), null);

        this.setIgnoreRepaint(true);

        this.addListeners();
    }

    private void addListeners() {
        this.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                switch (e.getWheelRotation()) {
                    case -1:
                        model.incRnd();
                        break;
                    case 1:
                        model.decRnd();
                        break;
                }
                rand.setText(String.format("%.3f", model.getRnd()));
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    Class<?> clazz = e.getClass();
                    Field fieldRawCode = clazz.getDeclaredField("rawCode");
                    fieldRawCode.setAccessible(true);
                    long rawCode = (long) fieldRawCode.get(e);
                    if (rawCode == 123)
                        model.incRnd();
                    else if (rawCode == 122)
                        model.decRnd();
                    rand.setText(String.format("%.3f", model.getRnd()));
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private JPanel buildPanelLabel() {
        JPanel panelLabel = new JPanel();
        panelLabel.setLayout(new GridLayout(3, 3));

        panelLabel.add(new JLabel());
        panelLabel.add(this.rand);
        panelLabel.add(new JLabel());

        panelLabel.add(this.labelavgPertubationPerExecution);
        panelLabel.add(new JLabel());
        panelLabel.add(this.labelAccExec);

        panelLabel.add(new JLabel());
        panelLabel.add(new JLabel());
        panelLabel.add(this.labelAccExecSuccess);
        return panelLabel;
    }

    private JPanel buildTaskManager() {
        JPanel taskManager = new JPanel();
        this.labelNbTask = new JLabel(String.valueOf(this.model.getNumberOfTask()));
        this.sliderNb = new JSlider(JSlider.HORIZONTAL, 1, 200, this.model.getNumberOfTask());
        this.labelSize = new JLabel(String.valueOf(this.model.getSize()));
        this.sliderSize = new JSlider(JSlider.HORIZONTAL, 1, 1000, this.model.getSize());
        this.sliderNb.addChangeListener(event -> this.model.setNumberOfTask(sliderNb.getValue()));
        this.sliderSize.addChangeListener(event -> this.model.setSize(sliderSize.getValue()));
        taskManager.add(this.labelNbTask);
        taskManager.add(this.sliderNb);
        taskManager.add(this.labelSize);
        taskManager.add(this.sliderSize);
        return taskManager;
    }

    private JPanel buildLocationManager() {
        JPanel panelLocation = new JPanel();
        this.typeOfPerturbation = new ButtonGroup();
        JRadioButton integer = new JRadioButton("Numerical", true);
        integer.addActionListener(e -> this.model.setType("Numerical"));
        this.typeOfPerturbation.add(integer);
        JRadioButton booleanb = new JRadioButton("Boolean");
        booleanb.addActionListener(e -> this.model.setType("Boolean"));
        this.typeOfPerturbation.add(booleanb);
        panelLocation.add(integer);
        panelLocation.add(booleanb);

        Checkbox antifragile = new Checkbox("Antifragile", null, true);
        antifragile.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                model.addClassLocation("Antifragile");
            else if (e.getStateChange() == ItemEvent.DESELECTED)
                model.removeClassLocation("Antifragile");
        });
        panelLocation.add(antifragile);

        Checkbox robust = new Checkbox("Robust");
        robust.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                model.addClassLocation("Robust");
            else if (e.getStateChange() == ItemEvent.DESELECTED)
                model.removeClassLocation("Robust");
        });
        panelLocation.add(robust);

        Checkbox weak = new Checkbox("Weak");
        weak.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                model.addClassLocation("Weak");
            else if (e.getStateChange() == ItemEvent.DESELECTED)
                model.removeClassLocation("Weak");
        });
        panelLocation.add(weak);

        return panelLocation;
    }

    public void run() {
        Graphics2D g2D = (Graphics2D) this.panel.getGraphics();

        this.labelAccExec.setText(String.valueOf(this.model.getAccExec()));
        this.labelAccExecSuccess.setText(String.valueOf(this.model.getAccExecSuccess()));
        this.labelavgPertubationPerExecution.setText(String.format("%.2f", this.model.getAvgPerturbationPerExec()));
        this.labelSize.setText(String.valueOf(this.model.getSize()));
        this.labelNbTask.setText(String.valueOf(this.model.getNumberOfTask()));

        double percentage = this.model.runAllTask();

        g2D.clearRect(this.panelImg.getX(), this.panelImg.getY(),
                this.panelImg.getX() + this.panelImg.getWidth(), this.panelImg.getY() + this.panelImg.getHeight());
        int xImg = (this.panel.getWidth() / 2) - (this.img.getWidth() / 2);
        g2D.drawImage(img, xImg, this.panel.getHeight() - this.img.getHeight(), null);
        g2D.setStroke(new BasicStroke(3));
        g2D.setColor(Color.red);
        double a = ((percentage + 900) * 180 / 100) * Math.PI / 180F;
        float x = (float) (cx + r * Math.cos(a));
        float y = (float) (cy + r * Math.sin(a));
        g2D.draw(new Line2D.Float(cx, cy, x, y));

    }

    public static void launch(Class<?> clazz) {
        Model m = new Model(clazz);
        Window w = new Window(m);
        while (true) {
//            w.requestFocus();
            w.run();
        }
    }

    public static void main() {
        launch(QuickSortManager.class);
    }

}
