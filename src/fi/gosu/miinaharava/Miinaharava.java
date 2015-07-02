package fi.gosu.miinaharava;

import javax.swing.SwingUtilities;

public class Miinaharava {

    public static void main(String[] args) {
        Kayttoliittyma kl = new Kayttoliittyma();
        SwingUtilities.invokeLater(kl);
    }
}
