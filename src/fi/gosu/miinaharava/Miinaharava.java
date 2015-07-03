package fi.gosu.miinaharava;

import javax.swing.SwingUtilities;

public class Miinaharava {

    public static void main(String[] args) {
        Kayttoliittyma kl = new Kayttoliittyma(1000, 1000, 8);
        SwingUtilities.invokeLater(kl);
    }
}
