package fi.gosu.miinaharava.ui;

import fi.gosu.miinaharava.view.Game;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ClickListener implements MouseListener {

    private final Game game;

    public ClickListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.game.leftMouseClicked(e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            this.game.rightMouseClicked(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
