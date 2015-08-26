package fi.gosu.miinaharava.tool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextField;
import javax.swing.Timer;

public class TimeCounter {

    private Long startTime, kulunut;
    private final JTextField tf;
    private final SimpleDateFormat dateFormat;
    private final Timer timer;
    private boolean end;

    public TimeCounter(JTextField tf) {
        this.kulunut = 0L;
        this.tf = tf;
        this.dateFormat = new SimpleDateFormat("mm.ss.SSS");
        this.timer = new Timer(53, new ClockListener());
        timer.setInitialDelay(0);
        this.end = false;
        this.startTime = -1L;
    }

    public void start() {
        if (!end) {
            startTime = System.currentTimeMillis();
            timer.start();
        }
    }

    public void stop(boolean end) {
        if (!this.end) {
            this.end = end;
            timer.stop();
            updateClock();
            kulunut += System.currentTimeMillis() - startTime;
        }
    }

    public void reset() {
        kulunut = 0L;
        end = false;
    }

    private void updateClock() {
        Date elapsed = new Date(System.currentTimeMillis() - startTime + kulunut);
        tf.setText(dateFormat.format(elapsed));
    }

    private class ClockListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateClock();
        }
    }

}
