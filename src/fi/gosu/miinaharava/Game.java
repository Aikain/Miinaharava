package fi.gosu.miinaharava;

import java.awt.Container;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Game {
    private final Kayttoliittyma kl;
    private final Hopscotch mainHopscotch;
    
    public Game(Kayttoliittyma kl){
        this.kl = kl;
        int width = kl.getWidth();
        int height = kl.getHeight();
        int deep = kl.getDeep();
        Resources r = kl.getResources();
        this.mainHopscotch = new Hopscotch(width / 2, height / 2, r);
        for (int i = 0; i < deep; i++) {
            this.mainHopscotch.createNeightborhood(i + 1);
            this.mainHopscotch.clearCreated();
        }
    }

    public void addComponentsToContainer(Container container) {
        this.mainHopscotch.addToContainer(container);
    }

    public Hopscotch getMainHopscotch() {
        return mainHopscotch;
    }

    public void checkWin() {
        if (this.mainHopscotch.checkWin()) {
            kl.win();
        }
        this.mainHopscotch.clearChecked();
    }
}
