package fi.gosu.miinaharava;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ClickListener implements MouseListener, MouseMotionListener {

    private final Game game;

    public ClickListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.game.getMainHopscotch().openHopscotch(e.getX(), e.getY());
            this.game.repaint();
            this.game.getMainHopscotch().clearChecked();
            this.game.checkWin();
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            this.game.getMainHopscotch().markHopscotch(e.getX(), e.getY());
            this.game.repaint();
            this.game.getMainHopscotch().clearChecked();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
