package fi.gosu.miinaharava.view;

import fi.gosu.miinaharava.Miinaharava;
import fi.gosu.miinaharava.ui.Kayttoliittyma;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class Settings implements View, ActionListener {

    private final Kayttoliittyma kl;
    private final JComboBox resBox, sizeBox, deepBox;
    private final JButton save, cancel;
    private final JTextField textSize, textDeep, textRes;

    public Settings(Kayttoliittyma kl) {
        this.kl = kl;
        List<String> paths = new ArrayList<>();
        try {
            URI uri = Miinaharava.class.getResource("/resources/").toURI();
            Path myPath;
            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                myPath = fileSystem.getPath("/resources/");
            } else {
                myPath = Paths.get(uri);
            }
            Stream<Path> walk = Files.walk(myPath, 1);
            for (Iterator<Path> it = walk.iterator(); it.hasNext();) {
                String[] name = it.next().toString().split("resources\\\\");
                if (name.length == 2) {
                    paths.add(name[1]);
                }
            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.textDeep = new JTextField("Kentän syvyys:");
        this.textSize = new JTextField("Kentän koko:");
        this.textRes = new JTextField("Grafiikka:");
        textDeep.setEditable(false);
        textSize.setEditable(false);
        textRes.setEditable(false);
        this.resBox = new JComboBox(paths.toArray());
        this.sizeBox = new JComboBox(new String[]{"400x400", "600x600", "800x800", "1000x1000", "1200x1200", "2000x2000"});
        this.deepBox = new JComboBox(new Integer[]{2, 3, 4, 5, 6, 7, 8, 9});
        this.resBox.setSelectedItem(this.kl.getConfig().getResDir());
        this.sizeBox.setSelectedItem(this.kl.getConfig().getWidth() + "x" + this.kl.getConfig().getHeight());
        this.deepBox.setSelectedItem(this.kl.getConfig().getDeep());
        this.textDeep.setBounds(this.kl.getWidth() / 2 - 100, (int) (this.kl.getHeight() * 0.3), 100, 30);
        this.textSize.setBounds(this.kl.getWidth() / 2 - 100, (int) (this.kl.getHeight() * 0.3) + 40, 100, 30);
        this.textRes.setBounds(this.kl.getWidth() / 2 - 100, (int) (this.kl.getHeight() * 0.3) + 80, 100, 30);
        this.deepBox.setBounds(this.kl.getWidth() / 2, (int) (this.kl.getHeight() * 0.3), 100, 30);
        this.sizeBox.setBounds(this.kl.getWidth() / 2, (int) (this.kl.getHeight() * 0.3) + 40, 100, 30);
        this.resBox.setBounds(this.kl.getWidth() / 2, (int) (this.kl.getHeight() * 0.3) + 80, 100, 30);
        this.save = createButton("Tallenna", this.kl.getWidth() / 2 - 100, (int) (this.kl.getHeight() * 0.8), 90, 30);
        this.cancel = createButton("Peruuta", this.kl.getWidth() / 2 + 10, (int) (this.kl.getHeight() * 0.8), 90, 30);
    }

    public JButton createButton(String name, int x, int y, int width, int height) {
        JButton button = new JButton(name);
        button.setBounds(x, y, width, height);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void addToContainer(Container container) {
        container.removeAll();
        container.add(save);
        container.add(cancel);
        container.add(textDeep);
        container.add(textRes);
        container.add(textSize);
        container.add(sizeBox);
        container.add(deepBox);
        container.add(resBox);
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onInactive() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.save) {
            this.kl.getConfig().setResDir(this.resBox.getSelectedItem().toString());
            String[] size = this.sizeBox.getSelectedItem().toString().split("x");
            this.kl.getConfig().setWidth(Integer.parseInt(size[0]));
            this.kl.getConfig().setHeight(Integer.parseInt(size[1]));
            this.kl.getConfig().setDeep(Integer.parseInt(deepBox.getSelectedItem().toString()));
            this.kl.saveXml();
            this.kl.menu();
        } else if (e.getSource() == this.cancel) {
            this.kl.menu();
        }
    }

}
