package fi.gosu.miinaharava.view;

import fi.gosu.miinaharava.ui.Kayttoliittyma;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.scene.paint.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public final class Menu implements View, ActionListener {

    private final Kayttoliittyma kl;
    private final JButton startGame, highScore, settings, help, exit;

    public Menu(Kayttoliittyma kl, int width, int height) {
        this.kl = kl;
        this.startGame = createButton("", this.kl.getResources().getNEWGAME(), width / 2 - 125, (int) (height * 0.2), 250, (int) (height * 0.08));
        this.highScore = createButton("", this.kl.getResources().getHISCRORE(), width / 2 - 125, (int) (height * 0.3), 250, (int) (height * 0.08));
        this.settings = createButton("", this.kl.getResources().getOPTIONS(), width / 2 - 125, (int) (height * 0.4), 250, (int) (height * 0.08));
        this.help = createButton("", this.kl.getResources().getHELP(), width / 2 - 125, (int) (height * 0.5), 250, (int) (height * 0.08));
        this.exit = createButton("", this.kl.getResources().getEXIT(), width / 2 - 125, (int) (height * 0.6), 250, (int) (height * 0.08));
    }

    public JButton createButton(String name, ImageIcon icon, int x, int y, int width, int height) {
        JButton button = new JButton(name, new ImageIcon(icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH)));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
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
