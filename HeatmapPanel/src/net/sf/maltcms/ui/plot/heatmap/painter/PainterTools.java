/*
 *  Copyright (C) 2008-2012 Nils Hoffmann
 *  Nils.Hoffmann A T CeBiTec.Uni-Bielefeld.DE
 *
 *  This file is part of Maui.
 *
 * Maui is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * Maui is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Maui.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.maltcms.ui.plot.heatmap.painter;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import net.sf.maltcms.ui.plot.heatmap.Tuple2D;

/**
 *
 * @author nilshoffmann
 */
public class PainterTools {

    public static Tuple2D<Rectangle2D,Point2D> getBoundingBox(String text, Graphics2D g, int insets) {
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(text, g);
        Point2D.Double textBase = new Point2D.Double(bounds.getX(),0); 
        double scaledInsets = insets/g.getTransform().getScaleX();
        bounds.setFrame(bounds.getX()-scaledInsets, bounds.getY()-scaledInsets, bounds.getWidth()+(2*scaledInsets), bounds.getHeight()+(2*scaledInsets));
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
