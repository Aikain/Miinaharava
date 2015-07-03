package fi.gosu.miinaharava;

import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private final int screenWidth, screenHeight, width, height, deep;
    private Drawer drawer;

    public Kayttoliittyma(int width, int height, int deep) {
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.width = width;
        this.height = height;
        this.deep = deep;
    }

    @Override
    public void run() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        if (!gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSLUCENT)) {
            System.err.println("Shaped windows are not supported");
            System.exit(0);
        } else {
            frame = new HexagonWindow(width, height, screenWidth / 2, screenHeight / 2);
            createComponents(frame.getContentPane());
            frame.setVisible(true);
        }
    }

    private void createComponents(Container container) {
        Hopscotch hopscotch = new Hopscotch(width / 2, height / 2);
        for (int i = 0; i < deep; i++) {
            hopscotch.createNeightborhood(i + 1);
            hopscotch.clearCreated();
        }
        drawer = new Drawer(hopscotch);
        container.add(drawer);
    }
}
