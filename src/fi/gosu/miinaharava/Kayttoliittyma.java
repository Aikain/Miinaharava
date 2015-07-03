package fi.gosu.miinaharava;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private int width;
    private int height;
    private Drawer drawer;

    public Kayttoliittyma() {
        this.width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.height = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    @Override
    public void run() {
        frame = new JFrame("Miinaharava");
        frame.setUndecorated(true);
        reset();
        createComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        Hopscotch hopscotch = new Hopscotch();
        hopscotch.createNeightborhood();
        drawer = new Drawer(hopscotch);
        container.add(drawer);
    }

    public void reset() {
        frame.setLocation(width / 2 - 200, height / 2 - 200);
        frame.setSize(400, 400);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
