package fi.gosu.miinaharava;

import java.awt.HeadlessException;
import java.awt.Polygon;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class HexagonWindow extends JFrame {

    public HexagonWindow(int width, int height, int centerX, int centerY) throws HeadlessException {
        setSize(width, height);
        setLocation(centerX - width / 2, centerY - height / 2);
        setUndecorated(true);
        Polygon polygon = new Polygon();
        for (int i = 0; i < 6; i++) {
            int x = (int) (width / 2 * Math.cos(i * Math.PI / 3)) + width / 2;
            int y = (int) (height / 2 * Math.sin(i * Math.PI / 3)) + height / 2;
            polygon.addPoint(x, y);
        }
        GeneralPath path = new GeneralPath();
        path.append(polygon, true);
        setShape(path);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
