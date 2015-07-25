package fi.gosu.miinaharava;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Menu implements View, ActionListener {

    private final Kayttoliittyma kl;
    private final JButton startGame, highScore, settings, help, exit;

    public Menu(Kayttoliittyma kl, int width, int height) {
        this.kl = kl;
        this.startGame = new JButton("Uusi peli");
        this.startGame.setBounds(width / 2 - 75, (int) (height * 0.3), 150, (int) (height * 0.07));
        this.startGame.addActionListener(this);
        this.highScore = new JButton("Huippupisteet");
        this.highScore.setBounds(width / 2 - 75, (int) (height * 0.4), 150, (int) (height * 0.07));
        this.highScore.addActionListener(this);
        this.settings = new JButton("Asetukset");
        this.settings.setBounds(width / 2 - 75, (int) (height * 0.5), 150, (int) (height * 0.07));
        this.settings.addActionListener(this);
        this.help = new JButton("Ohjeet");
        this.help.setBounds(width / 2 - 75, (int) (height * 0.6), 150, (int) (height * 0.07));
        this.help.addActionListener(this);
        this.exit = new JButton("Poistu");
        this.exit.setBounds(width / 2 - 75, (int) (height * 0.7), 150, (int) (height * 0.07));
        this.exit.addActionListener(this);
    }

    @Override
    public void addToContainer(Container container) {
        container.removeAll();
        container.add(this.startGame);
        container.add(this.highScore);
        container.add(this.settings);
        container.add(this.help);
        container.add(this.exit);
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onInactive() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.startGame) {
            this.kl.startGame();
        } else if (e.getSource() == this.highScore) {
            this.kl.highScore();
        } else if (e.getSource() == this.settings) {
            this.kl.settings();
        } else if (e.getSource() == this.help) {
            this.kl.help();
        } else if (e.getSource() == this.exit) {
            this.kl.exit();
        } else {
            System.out.println("WTF??");
            assert false;
        }
    }

}
