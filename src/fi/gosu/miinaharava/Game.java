package fi.gosu.miinaharava;

import java.awt.Container;

public class Game implements View {

    private final Kayttoliittyma kl;
    private Hopscotch mainHopscotch;
    private boolean gameHasEnded = true;

    public Game(Kayttoliittyma kl) {
        this.kl = kl;
        startGame();
    }

    private void startGame() {
        this.gameHasEnded = false;
        int width = kl.getWidth();
        int height = kl.getHeight();
        int deep = kl.getDeep();
        Resources r = kl.getResources();
        this.mainHopscotch = new Hopscotch(width / 2, height / 2, r);
        for (int i = 0; i < deep; i++) {
            this.mainHopscotch.createNeightborhood(i + 1);
            this.mainHopscotch.clearCreated();
            this.mainHopscotch.generateMinePositions();
            this.mainHopscotch.clearCreated();
        }
    }

    public void addToContainer(Container container) {
        container.removeAll();
        this.mainHopscotch.addToContainer(container);
    }

    public void onResume() {
        if (this.gameHasEnded) {
            startGame();
        }
    }

    public void onInactive() {

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
