package fi.gosu.miinaharava;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Kayttoliittyma implements Runnable {

    public JFrame frame;
    public int leveys;
    public int korkeus;

    public Kayttoliittyma() {
        this.leveys = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.korkeus = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    @Override
    public void run() {
        frame = new JFrame("Miinaharava");
        frame.setUndecorated(true);
        palauta();
        luoKomponentit(frame.getContentPane());
        frame.addMouseListener(new HiiriKuuntelija());
        frame.pack();
        frame.setVisible(true);
    }

    private void luoKomponentit(Container container) {
        //container.setLayout(null);
    }

    public void palauta() {
        frame.setLocation(leveys / 2 - 200, korkeus / 2 - 200);
        frame.setSize(400, 400);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
