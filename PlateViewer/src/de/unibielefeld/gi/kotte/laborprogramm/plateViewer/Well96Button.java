/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.plateViewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;

/**
 *
 * @author hoffmann
 */
public class Well96Button extends JButton {

    private IWell96 well96 = null;

    public IWell96 getWell96() {
        return well96;
    }

    public void setWell96(IWell96 well96) {
        this.well96 = well96;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        Color originalColor = g2.getColor();
        g2.setColor(Well96Status.getColor(well96.getStatus()));
        int width = getWidth()-(getInsets().left+getInsets().right);
        int height = getHeight()-(getInsets().top+getInsets().bottom);
        g2.fillOval(getInsets().left, getInsets().top, width, height);
        g2.setColor(originalColor);
    }

}
