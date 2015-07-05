package fi.gosu.miinaharava;

import java.io.IOException;
import javax.swing.SwingUtilities;

public class Miinaharava {

    public static void main(String[] args) throws IOException {
        Kayttoliittyma kl = new Kayttoliittyma(1000, 1000, 8);
        SwingUtilities.invokeLater(kl);
    }
}
