/*
 *  Copyright (C) 2008-2012 Nils Hoffmann
 *  Nils.Hoffmann A T CeBiTec.Uni-Bielefeld.DE
 *
 *  This file is part of Maui.
 *
 *  Maui is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Maui is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Maui.  If not, see <http://www.gnu.org/licenses/>.
 *
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
