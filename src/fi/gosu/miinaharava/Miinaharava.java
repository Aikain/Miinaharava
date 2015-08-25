package fi.gosu.miinaharava;

import fi.gosu.miinaharava.ui.Kayttoliittyma;
import java.io.IOException;
import javax.swing.SwingUtilities;

public class Miinaharava {

    public static void main(String[] args) throws IOException {
        Kayttoliittyma kl = new Kayttoliittyma();
        SwingUtilities.invokeLater(kl);
    }
}
