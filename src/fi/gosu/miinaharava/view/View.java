package fi.gosu.miinaharava.view;

import java.awt.Container;

public interface View {

    public void addToContainer(Container container);
    
    public void onResume();
    
    public void onInactive();
}
