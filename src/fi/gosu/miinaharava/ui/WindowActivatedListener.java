package fi.gosu.miinaharava.ui;

import fi.gosu.miinaharava.tool.TimeCounter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowActivatedListener extends WindowAdapter {

    private final TimeCounter counter;

    public WindowActivatedListener(TimeCounter counter) {
        this.counter = counter;
    }

    @Override
    public void windowActivated(WindowEvent e) {
        counter.start();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        counter.stop(false);
    }

}
