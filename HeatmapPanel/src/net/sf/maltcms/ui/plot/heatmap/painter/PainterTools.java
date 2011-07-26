/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap.painter;

import cross.datastructures.tuple.Tuple2D;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author nilshoffmann
 */
public class PainterTools {

    public static Tuple2D<Rectangle2D,Point2D> getBoundingBox(Paint fill, Paint stroke, String text, Graphics2D g, int insets) {
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(text, g);
        g.setPaint(fill);
        Point2D.Double textBase = new Point2D.Double(bounds.getX(),0); 

        bounds.setFrame(bounds.getX()-insets, bounds.getY()-insets, bounds.getWidth()+(2*insets), bounds.getHeight()+(2*insets));
        Tuple2D<Rectangle2D,Point2D> tp = new Tuple2D<Rectangle2D, Point2D>(bounds,textBase);
        return tp;
    }
    
    public static void drawCrossInBox(Graphics2D g, Paint stroke, Rectangle2D bounds, int offset) {
        Paint paint = g.getPaint();
        g.setPaint(stroke);
        double halfwidth = bounds.getWidth() / 2;
        double halfheight = bounds.getHeight() / 2;
        double halfX = bounds.getMinX() + (halfwidth);
        double halfY = bounds.getMinY() + (halfheight);
        Line2D.Double l1 = new Line2D.Double(halfX, bounds.getMinY(), halfX, halfY - offset);
        Line2D.Double l2 = new Line2D.Double(halfX, bounds.getMaxY(), halfX, halfY + offset);
        Line2D.Double l3 = new Line2D.Double(bounds.getMinX(), halfY, halfX - offset, halfY);
        Line2D.Double l4 = new Line2D.Double(bounds.getMaxX(), halfY, halfX + offset, halfY);
        g.draw(l1);
        g.draw(l2);
        g.draw(l3);
        g.draw(l4);
        g.setPaint(paint);
    }
}
