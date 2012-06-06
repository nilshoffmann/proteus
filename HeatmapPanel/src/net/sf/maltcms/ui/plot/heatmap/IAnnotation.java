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
package net.sf.maltcms.ui.plot.heatmap;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;

/**
 *
 * @author nilshoffmann
 */
public interface IAnnotation<T> {
    String PROP_PAYLOAD = "payload";
    String PROP_POSITION = "position";
    String PROP_SELECTED = "selected";
    String PROP_SHAPE = "shape";

    void addPropertyChangeListener(PropertyChangeListener listener);

    void draw(Graphics2D g);

    T getPayload();

    Point2D getPosition();

    Shape getShape();

    boolean isSelected();

    void removePropertyChangeListener(PropertyChangeListener listener);

    void setPayload(T t);

    void setPosition(Point2D point);

    void setSelected(boolean selected);

    void setShape(Shape shape);
    
}
