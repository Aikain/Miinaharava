package fi.gosu.miinaharava;

import java.awt.Container;
import javax.swing.JButton;

public class Menu implements View {

    private final JButton newGame, highScore, settings, help, exit;

    public Menu(int width, int height) {
        this.newGame = new JButton("Uusi peli");
        this.newGame.setBounds(width / 2 - 75, (int) (height * 0.3), 150, (int) (height * 0.07));
        this.highScore = new JButton("Huippupisteet");
        this.highScore.setBounds(width / 2 - 75, (int) (height * 0.4), 150, (int) (height * 0.07));
        this.settings = new JButton("Asetukset");
        this.settings.setBounds(width / 2 - 75, (int) (height * 0.5), 150, (int) (height * 0.07));
        this.help = new JButton("Ohjeet");
        this.help.setBounds(width / 2 - 75, (int) (height * 0.6), 150, (int) (height * 0.07));
        this.exit = new JButton("Poistu");
        this.exit.setBounds(width / 2 - 75, (int) (height * 0.7), 150, (int) (height * 0.07));
    }

    @Override
    public void addToContainer(Container container) {
        container.removeAll();
        container.add(this.newGame);
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

}
