package fi.gosu.miinaharava.view;

import fi.gosu.miinaharava.ui.Kayttoliittyma;
import java.awt.Container;

public class Help implements View {
    
    private final Kayttoliittyma kl;

    public Help(Kayttoliittyma kl) {
        this.kl = kl;
    }

    @Override
    public void addToContainer(Container container) {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onInactive() {
    }
    
}
