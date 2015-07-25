package fi.gosu.miinaharava;

import java.awt.Container;

public class Settings implements View {

    private final Kayttoliittyma kl;

    public Settings(Kayttoliittyma kl) {
        this.kl = kl;
    }

    @Override
    public void addToContainer(Container container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onResume() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onInactive() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
