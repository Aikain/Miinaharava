package fi.gosu.miinaharava;

import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private final int screenWidth, screenHeight, width, height;
    private final Hopscotch mainHopscotch;
    private final Resources r;

    public Kayttoliittyma(int width, int height, int deep) throws IOException {
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.width = width;
        this.height = height;
        this.r = new Resources();
        this.mainHopscotch = new Hopscotch(width / 2, height / 2, this.r);
        for (int i = 0; i < deep; i++) {
            this.mainHopscotch.createNeightborhood(i + 1);
            this.mainHopscotch.clearCreated();
        }
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
            frame.addMouseListener(new ClickListener(this));
            frame.setContentPane(new Background(this.r, width, height));
            createComponents(frame.getContentPane());
            frame.setVisible(true);
        }
    }

    private void createComponents(Container container) {
        container.setLayout(null);
        this.mainHopscotch.addToContainer(container);
    }

    public Hopscotch getMainHopscotch() {
        return mainHopscotch;
    }

    public void repaint() {
        this.frame.repaint();
    }

    public void checkWin() {
        if (this.mainHopscotch.checkWin()) {
            JOptionPane.showMessageDialog(frame, "Voitto");
        }
        this.mainHopscotch.clearChecked();
    }
}
