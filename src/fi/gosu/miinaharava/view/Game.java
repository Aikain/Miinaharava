package fi.gosu.miinaharava.view;

import fi.gosu.miinaharava.ui.ClickListener;
import fi.gosu.miinaharava.Hopscotch;
import fi.gosu.miinaharava.ui.Kayttoliittyma;
import fi.gosu.miinaharava.tool.Resources;
import fi.gosu.miinaharava.ui.HexagonButton;
import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Game implements View {

    private final Kayttoliittyma kl;
    private Hopscotch mainHopscotch;
    private ClickListener clickListener;
    private boolean gameHasEnded;
    private JButton hb;

    public Game(Kayttoliittyma kl) {
        this.kl = kl;
        this.clickListener = new ClickListener(this);
        this.gameHasEnded = true;
        this.hb = new HexagonButton("OK");
        this.hb.setBounds(200, 150, 150, 150);
    }

    private void startGame() {
        this.gameHasEnded = false;
        int width = kl.getWidth();
        int height = kl.getHeight();
        int deep = kl.getDeep();
        Resources r = kl.getResources();
        this.mainHopscotch = new Hopscotch(width / 2, height / 2, r, kl);
        for (int i = 0; i < deep; i++) {
            this.mainHopscotch.createNeightborhood(i + 1);
            this.mainHopscotch.clearCreated();
        }
    }

    private void forceOpenAllHopscotches() {
        this.mainHopscotch.clearCreated();
        this.mainHopscotch.openAll();
        repaint();
    }

    public void addToContainer(Container container) {
        container.removeAll();
        container.add(this.hb);
        this.mainHopscotch.addToContainer(container);
    }

    public void onResume() {
        if (this.gameHasEnded) {
            startGame();
        }
        this.kl.getFrame().addMouseListener(this.clickListener);
    }

    public void onInactive() {
        this.kl.getFrame().removeMouseListener(clickListener);
    }

    public ClickListener getClickListener() {
        return clickListener;
    }

    public void leftMouseClicked(int xCoord, int yCoord) {
        Hopscotch clicked = getMainHopscotch().getHopscotch(xCoord, yCoord);
        getMainHopscotch().clearChecked();
        if (clicked == null) {
            return;
        } else if (gameHasEnded) {
            noteAboutGameEnded();
        } else if (!clicked.isCreated()) {
            clicked.generateMinePositions(0);
        }
        if (!clicked.open()) {
            lose();
            return;
        }
        repaint();
        checkWin();
    }

    public void rightMouseClicked(int xCoord, int yCoord) {
        if (gameHasEnded) {
            noteAboutGameEnded();
            return;
        }
        getMainHopscotch().markHopscotch(xCoord, yCoord);
        repaint();
        getMainHopscotch().clearChecked();
    }

    public Hopscotch getMainHopscotch() {
        return mainHopscotch;
    }

    public void checkWin() {
        if (this.mainHopscotch.checkWin()) {
            win();
        }
        this.mainHopscotch.clearChecked();
    }

    public void repaint() {
        kl.repaint();
    }

    private void win() {
        gameHasEnded = true;
        JOptionPane.showMessageDialog(kl.getFrame(), "Voitto");
    }

    private void lose() {
        gameHasEnded = true;
        mainHopscotch.clearCreated();
        forceOpenAllHopscotches();
        JOptionPane.showMessageDialog(kl.getFrame(), "Häviö");
    }
    
    private void noteAboutGameEnded() {
        JOptionPane.showMessageDialog(kl.getFrame(), "Peli loppui jo.");
    }
}
