package fi.gosu.miinaharava;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Config {

    private Integer width, height, deep;
    private String resDir;

    @XmlElement
    public Integer getWidth() {
        return width != null ? width : 1000;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @XmlElement
    public Integer getHeight() {
        return height != null ? height : 1000;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @XmlElement
    public Integer getDeep() {
        return deep != null ? deep : 8;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    @XmlElement
    public String getResDir() {
        return resDir != null ? resDir : "def";
    }

    public void setResDir(String resDir) {
        this.resDir = resDir;
    }

}
