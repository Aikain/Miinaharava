package fi.gosu.miinaharava.tool;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Resources {

    private final BufferedImage NORMAL, BOMB, FLAG, BACKROUND;
    private final BufferedImage[] OPENS;
    private final ImageIcon EXIT, HISCRORE, NEWGAME, OPTIONS, HELP;

    public Resources(String kansio) throws IOException {
        this.NORMAL = ImageIO.read(this.getClass().getResourceAsStream("/resources/" + kansio + "/Normal.png"));
        this.BOMB = ImageIO.read(this.getClass().getResourceAsStream("/resources/" + kansio + "/Bomb.png"));
        this.OPENS = new BufferedImage[7];
        for (int i = 0; i < this.OPENS.length; i++) {
            this.OPENS[i] = ImageIO.read(this.getClass().getResourceAsStream("/resources/" + kansio + "/" + i + ".png"));
        }
        this.FLAG = ImageIO.read(this.getClass().getResourceAsStream("/resources/" + kansio + "/Flag.png"));
        this.BACKROUND = ImageIO.read(this.getClass().getResourceAsStream("/resources/" + kansio + "/Background.png"));
        this.EXIT = new ImageIcon(getClass().getResource("/resources/" + kansio + "/Exit.png"));
        this.HISCRORE = new ImageIcon(this.getClass().getResource("/resources/" + kansio + "/HiScore.png"));
        this.NEWGAME = new ImageIcon(this.getClass().getResource("/resources/" + kansio + "/Newgame.png"));
        this.OPTIONS = new ImageIcon(this.getClass().getResource("/resources/" + kansio + "/Options.png"));
        this.HELP = new ImageIcon(this.getClass().getResource("/resources/" + kansio + "/Help.png"));
    }

    public BufferedImage getNORMAL() {
        return NORMAL;
    }

    public BufferedImage getBOMB() {
        return BOMB;
    }

    public BufferedImage getOpen(int i) {
        return OPENS[i];
    }

    public BufferedImage getFLAG() {
        return FLAG;
    }

    public BufferedImage getBACKROUND() {
        return BACKROUND;
    }

    public ImageIcon getEXIT() {
        return EXIT;
    }

    public ImageIcon getHISCRORE() {
        return HISCRORE;
    }

    public ImageIcon getNEWGAME() {
        return NEWGAME;
    }

    public ImageIcon getOPTIONS() {
        return OPTIONS;
    }

    public ImageIcon getHELP() {
        return HELP;
    }
    
}
