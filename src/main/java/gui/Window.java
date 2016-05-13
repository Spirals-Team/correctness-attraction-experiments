package gui;

import javax.swing.*;

/**
 * Created by bdanglot on 13/05/16.
 */
public class Window extends JFrame {


    public static void main(String[] args) {

        Window w = new Window();
        w.setVisible(true);
        w.add(new JPanel());

        w.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }


}
