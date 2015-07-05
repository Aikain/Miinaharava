package fi.gosu.miinaharava;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Resources {

    private final BufferedImage NORMAL;
    private final BufferedImage BOMB;
    private final BufferedImage[] OPENS;
    private final BufferedImage FLAG;
    private final BufferedImage BACKROUND;

    public Resources() throws IOException {
        this.NORMAL = ImageIO.read(this.getClass().getResourceAsStream("/resources/Comb_Normal.png"));
        this.BOMB = ImageIO.read(this.getClass().getResourceAsStream("/resources/Comb_Bomb.png"));
        this.OPENS = new BufferedImage[7];
        for (int i = 0; i < this.OPENS.length; i++) {
            this.OPENS[i] = ImageIO.read(this.getClass().getResourceAsStream("/resources/Comb_" + i + ".png"));
        }
        this.FLAG = ImageIO.read(this.getClass().getResourceAsStream("/resources/Comb_Flag.png"));
        this.BACKROUND = ImageIO.read(this.getClass().getResourceAsStream("/resources/Comb_Background.png"));
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

}
