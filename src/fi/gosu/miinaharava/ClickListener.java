package fi.gosu.miinaharava;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ClickListener implements MouseListener, MouseMotionListener {

    private final Kayttoliittyma kl;

    public ClickListener(Kayttoliittyma kl) {
        this.kl = kl;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.kl.getMainHopscotch().openHopscotch(e.getX(), e.getY());
            this.kl.repaint();
            this.kl.getMainHopscotch().clearChecked();
            this.kl.checkWin();
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            this.kl.getMainHopscotch().markHopscotch(e.getX(), e.getY());
            this.kl.repaint();
            this.kl.getMainHopscotch().clearChecked();
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
