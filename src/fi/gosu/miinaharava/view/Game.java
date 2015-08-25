package fi.gosu.miinaharava.view;

import fi.gosu.miinaharava.ui.ClickListener;
import fi.gosu.miinaharava.Hopscotch;
import fi.gosu.miinaharava.ui.Kayttoliittyma;
import fi.gosu.miinaharava.tool.Resources;
import fi.gosu.miinaharava.tool.TimeCounter;
import fi.gosu.miinaharava.ui.HexagonButton;
import fi.gosu.miinaharava.ui.WindowActivatedListener;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Game implements View, ActionListener {

    private final Kayttoliittyma kl;
    private Hopscotch mainHopscotch;
    private final ClickListener clickListener;
    private final WindowActivatedListener windowListener;
    private boolean gameHasEnded;
    private final TimeCounter counter;
    private final JTextField tf;
    private JButton returnToMenuButton, newGameButton;

    public Game(Kayttoliittyma kl) {
        this.kl = kl;
        this.clickListener = new ClickListener(this);
        this.gameHasEnded = true;
        this.tf = new JTextField("--:--:---");
        this.tf.setBounds((int) (this.kl.getWidth() * 0.55), (int) (this.kl.getHeight() * 0.13 - 30), 500, 50);
        this.tf.setBorder(null);
        this.tf.setOpaque(false);
        this.tf.setFont(new Font("candara bold", Font.PLAIN, this.kl.getWidth() / 20));
        this.tf.setEditable(false);
        this.counter = new TimeCounter(tf);
        this.windowListener = new WindowActivatedListener(this.counter);
        createButtons();
    }

    private void createButtons() {
        int width = 100;
        int height = 100;

        int returnToMenuY = 100;
        int returnToMenuX = 300;
        int newGameY = returnToMenuY;
        int newGameX = 200;

        this.returnToMenuButton = new HexagonButton("Lopeta");
        this.returnToMenuButton.setBounds(returnToMenuX, returnToMenuY, width, height);
        this.returnToMenuButton.addActionListener(this);

        this.newGameButton = new HexagonButton("Uusi peli");
        this.newGameButton.setBounds(newGameX, newGameY, width, height);
        this.newGameButton.addActionListener(this);
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
        container.add(this.returnToMenuButton);
        container.add(this.newGameButton);
        this.mainHopscotch.addToContainer(container);
        container.add(this.tf);
    }

    public void onResume() {
        if (this.gameHasEnded) {
            startGame();
            this.counter.reset();
        }
        this.kl.getFrame().addMouseListener(this.clickListener);
        this.kl.getFrame().addWindowListener(windowListener);
    }

    public void onInactive() {
        this.counter.stop(false);
        this.kl.getFrame().removeMouseListener(clickListener);
        this.kl.getFrame().removeWindowListener(windowListener);
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
            this.counter.start();
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
        this.counter.stop(true);
    }

    private void lose() {
        gameHasEnded = true;
        mainHopscotch.clearCreated();
        this.counter.stop(true);
        forceOpenAllHopscotches();
        JOptionPane.showMessageDialog(kl.getFrame(), "Häviö");
    }

    private void noteAboutGameEnded() {
        JOptionPane.showMessageDialog(kl.getFrame(), "Peli loppui jo.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.returnToMenuButton) {
            this.kl.menu();
        } else if (e.getSource() == this.newGameButton) {
            this.counter.stop(true);
            this.counter.reset();
            this.startGame();
            this.tf.setText("--:--:---");
            this.addToContainer(this.kl.getFrame().getContentPane());
            this.repaint();
        }
    }
}
