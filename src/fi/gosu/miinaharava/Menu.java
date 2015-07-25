package fi.gosu.miinaharava;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public final class Menu implements View, ActionListener {

    private final Kayttoliittyma kl;
    private final JButton startGame, highScore, settings, help, exit;

    public Menu(Kayttoliittyma kl, int width, int height) {
        this.kl = kl;
        this.startGame = createButton("Uusi peli", width / 2 - 75, (int) (height * 0.3), 150, (int) (height * 0.07));
        this.highScore = createButton("Huippupisteet", width / 2 - 75, (int) (height * 0.4), 150, (int) (height * 0.07));
        this.settings = createButton("Asetukset", width / 2 - 75, (int) (height * 0.5), 150, (int) (height * 0.07));
        this.help = createButton("Ohjeet", width / 2 - 75, (int) (height * 0.6), 150, (int) (height * 0.07));
        this.exit = createButton("Poistu", width / 2 - 75, (int) (height * 0.7), 150, (int) (height * 0.07));
    }

    public JButton createButton(String name, int x, int y, int width, int height) {
        JButton button = new JButton(name);
        button.setBounds(x, y, width, height);
        button.addActionListener(this);
        return button;
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
