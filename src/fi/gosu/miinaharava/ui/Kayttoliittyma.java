package fi.gosu.miinaharava.ui;

import fi.gosu.miinaharava.Config;
import fi.gosu.miinaharava.tool.*;
import fi.gosu.miinaharava.view.*;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
import javax.xml.bind.JAXBException;

public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private final int screenWidth, screenHeight, width, height, deep;
    private final Resources r;
    private View currentView;
    private final View menu, game, highScore, settings, help;
    private final Config c;
    private final ReadXml<Config> xml;

    public Kayttoliittyma() throws IOException {
        this.xml = new ReadXml<>(Config.class, "config.xml");
        Config c;
        try {
            c = xml.getObj();
        } catch (JAXBException ex) {
            c = new Config();
        }
        this.c = c;
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.width = c.getWidth();
        this.height = c.getHeight();
        this.deep = c.getDeep();
        this.r = new Resources(c.getResDir());
        this.menu = new Menu(this, width, height);
        this.game = new Game(this);
        this.highScore = new HighScore(this);
        this.settings = new Settings(this);
        this.help = new Help(this);
        this.currentView = this.menu;
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
            frame.setContentPane(new Background(this.r, width, height));
            createComponents(frame.getContentPane());
            frame.setVisible(true);
        }
    }

    private void createComponents(Container container) {
        container.setLayout(null);
        currentView.addToContainer(container);
    }

    public void repaint() {
        this.frame.repaint();
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getDeep() {
        return this.deep;
    }

    public Resources getResources() {
        return this.r;
    }
    
    public Config getConfig() {
        return this.c;
    }

    private void changeView(View newView) {
        this.currentView.onInactive();
        this.currentView = newView;
        this.currentView.addToContainer(this.frame.getContentPane());
        this.currentView.onResume();
        this.repaint();
    }

    public void startGame() {
        changeView(this.game);
    }

    public void highScore() {
        changeView(this.highScore);
    }

    public void settings() {
        changeView(this.settings);
    }

    public void help() {
        changeView(this.help);
    }
    
    public void menu() {
        changeView(this.menu);
    }

    public void exit() {
        System.exit(-1);
    }
    
    public void saveXml() {
        try {
            this.xml.writeToXml(c);
        } catch (JAXBException ex) {
            System.out.println("Tallentaminen ei onnistunut");
        }
    }
}
