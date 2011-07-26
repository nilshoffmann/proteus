/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.theme;

import cross.datastructures.tuple.Tuple2D;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author nils
 */
public class ThemeUtils {

    public static Tuple2D<Rectangle2D,Point2D> getBoundingBox(String text, Graphics2D g, int insets) {
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(text, g);
        Point2D.Double textBase = new Point2D.Double(bounds.getX(),0); 
        bounds.setFrame(bounds.getX()-insets, bounds.getY()-insets, bounds.getWidth()+(2*insets), bounds.getHeight()+(2*insets));
        Tuple2D<Rectangle2D,Point2D> tp = new Tuple2D<Rectangle2D, Point2D>(bounds,textBase);
        return tp;
    }

}
