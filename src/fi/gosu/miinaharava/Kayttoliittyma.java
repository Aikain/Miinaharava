package fi.gosu.miinaharava;

import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private final int screenWidth, screenHeight, width, height, deep;
    private Resources r;
    private Game game;

    public Kayttoliittyma(int width, int height, int deep) throws IOException {
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.width = width;
        this.height = height;
        this.deep = deep;
        this.r = new Resources();
        this.game = new Game(this);
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
        game.addComponentsToContainer(container);
    }

    public void repaint() {
        this.frame.repaint();
    }

    public void win() {
        JOptionPane.showMessageDialog(frame, "Voitto");
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public int getDeep(){
        return this.deep;
    }
    
    public Resources getResources(){
        return this.r;
    }
    
    public Hopscotch getMainHopscotch(){
        return this.game.getMainHopscotch();
    }
    
    public void checkWin(){
        this.game.checkWin();
    }
}
