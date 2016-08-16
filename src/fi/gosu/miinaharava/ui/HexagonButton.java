/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.gosu.miinaharava.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Area;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class HexagonButton extends JButton
        implements ComponentListener {

    protected static final int DEFAULT_WIDTH = 150;
    protected static final int DEFAULT_HEIGHT = 50;

    protected static final Insets INSETS_MARGIN = new Insets(2, 5, 2, 5);

    protected static final int BORDER_WIDTH = 5;

    protected Shape m_shape = null;
    protected Area m_areaFill = null;
    protected Area m_areaDraw = null;

    protected int m_nStringWidthMax = 0;
    protected int m_nMinWidth = 0;

    ////////////////////////////////////////////////
    public HexagonButton(String strLabel) {
        super(strLabel);

        this.setContentAreaFilled(false);
        this.setMargin(INSETS_MARGIN);
        this.setFocusPainted(false);

        this.addComponentListener(this);

        //determine the buttons initial size ----------------------
        //WARNING: Use UIManager font, else font here is not dynamic
        Font font = (Font) UIManager.get("Button.font");
        Frame frame = JOptionPane.getRootFrame();
        FontMetrics fm = frame.getFontMetrics(font);

        m_nStringWidthMax = fm.stringWidth(this.getText());
        m_nStringWidthMax
                = Math.max(m_nStringWidthMax, fm.stringWidth(this.getText()));

        //WARNING: use getMargin. it refers to dist btwn text and border.
        //also use getInsets. it refers to the width of the border
        int nWidth = Math.max(m_nMinWidth,
            m_nStringWidthMax
            + this.getMargin().left
            + this.getInsets().left
            + this.getMargin().right
            + this.getInsets().right);

        this.setPreferredSize(new Dimension(nWidth, DEFAULT_HEIGHT));

        updateShape();
        this.setShape();
    }

    ////////////////////////////////////////////////
    public void setButtonText(String strText) {
        super.setText(strText);

        int nWidth = Math.max(
            m_nMinWidth,
            m_nStringWidthMax
            + this.getInsets().left
            + this.getInsets().right);
        int nHeight = Math.max(0, this.getPreferredSize().height);
        this.setPreferredSize(new Dimension(nWidth, nHeight));

        updateShape();
        setShape();
    }

    ////////////////////////////////////////////////
    protected void setShape() {
        m_areaDraw = new Area(); // Border
        m_areaFill = new Area(); // Background
        m_areaDraw.add(new Area(m_shape));
        m_areaFill.add(new Area(m_shape));
    }

    private void updateShape() {
        Polygon hexagon = new Polygon();
        Rectangle bounds = getBounds();
        int x = bounds.width / 2;
        int y = bounds.height / 2;
        int radius = bounds.width / 2;
        for (int i = 0; i < 6; i++) {
            hexagon.addPoint((int) (x + radius * Math.cos(i * Math.PI / 3)), (int) (y + radius * Math.sin(i * Math.PI / 3)));
        }
        m_shape = hexagon;
    }

    ////////////////////////////////////////////////
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        RenderingHints hints = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2.setRenderingHints(hints);
        g2.setColor(Color.BLUE);
        g2.fill(m_areaFill);
    }

    ////////////////////////////////////////////////
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        RenderingHints hints = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2.setRenderingHints(hints);

        Stroke strokeOld = g2.getStroke();
        g2.setStroke(
            new BasicStroke(
                    BORDER_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
        );

        if (this.isFocusOwner()) {
            g2.setColor(Color.white);
        } else {
            g2.setColor(Color.black);
        }
        g2.draw(m_areaDraw);

        g2.setStroke(strokeOld);
    }

    ////////////////////////////////////////////////
    public boolean contains(int nX, int nY) {
        if (null == m_shape || m_shape.getBounds().equals(getBounds())) {
            updateShape();
        }
        return m_shape.contains(nX, nY);
    }
    
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    //Needed if we want this button to resize
    public void componentResized(ComponentEvent e) {
        updateShape();
        this.setShape();
    }

    ////////////////////////////////////////////////
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

}
