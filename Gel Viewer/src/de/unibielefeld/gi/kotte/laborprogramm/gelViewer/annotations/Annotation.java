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
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.ta.Activatable;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import net.sf.maltcms.ui.plot.heatmap.IAnnotation;
import net.sf.maltcms.ui.plot.heatmap.painter.PainterTools;

/**
 *
 * @author nilshoffmann
 */
public class Annotation<T> implements IAnnotation<T>, IPropertyChangeSource, Activatable {

    /**
     * PropertyChangeSupport ala JavaBeans(tm)
     * Not persisted!
     */
    private transient PropertyChangeSupport pcs = null;

    @Override
    public synchronized void removePropertyChangeListener(
            PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }

    @Override
    public synchronized void addPropertyChangeListener(
            PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    protected PropertyChangeSupport getPropertyChangeSupport() {
        if (this.pcs == null) {
            this.pcs = new PropertyChangeSupport(this);
        }
        return this.pcs;
    }

    private transient Activator activator;

    @Override
    public void bind(Activator activator) {
        if (this.activator == activator) {
            return;
        }
        if (activator != null && null != this.activator) {
            throw new IllegalStateException(
                    "Object can only be bound to one activator");
        }
        this.activator = activator;
    }

    @Override
    public void activate(ActivationPurpose activationPurpose) {
        if (null != activator) {
            activator.activate(activationPurpose);
        }
    }

    private T payload;
    private Point2D position;
    private boolean selected;
    private Shape shape;

    public Annotation(Point2D position, T t) {
        this.position = position;
        this.payload = t;
        this.shape = new RoundRectangle2D.Double(position.getX() - 10, position.getY() - 10, 20, 20,2,2);
    }

    @Override
    public void setShape(Shape shape) {
        activate(ActivationPurpose.WRITE);
        Shape old = this.shape;
        this.shape = shape;
        getPropertyChangeSupport().firePropertyChange(PROP_SHAPE, old, shape);
    }

    @Override
    public Shape getShape() {
        activate(ActivationPurpose.READ);
        return this.shape;
    }

    @Override
    public Point2D getPosition() {
        activate(ActivationPurpose.READ);
        return this.position;
    }

    @Override
    public void setPosition(Point2D point) {
        activate(ActivationPurpose.WRITE);
        Point2D old = this.position;
        this.position = point;
        getPropertyChangeSupport().firePropertyChange(PROP_POSITION, old, point);
    }

    @Override
    public String toString() {
        return getPayload().toString();
    }

    @Override
    public void setPayload(T t) {
        activate(ActivationPurpose.WRITE);
        T old = this.payload;
        this.payload = t;
        getPropertyChangeSupport().firePropertyChange(PROP_PAYLOAD, old, t);
    }

    @Override
    public T getPayload() {
        activate(ActivationPurpose.READ);
        return payload;
    }

    @Override
    public void setSelected(boolean selected) {
        activate(ActivationPurpose.WRITE);
        boolean old = this.selected;
        this.selected = selected;
        getPropertyChangeSupport().firePropertyChange(PROP_SELECTED, old, selected);
    }

    @Override
    public boolean isSelected() {
        activate(ActivationPurpose.READ);
        return selected;
    }

    @Override
    public void draw(Graphics2D g) {
        if (!isSelected()) {
            g.setPaint(new Color(255, 255, 255, 64));
            g.fill(getShape());
            g.setPaint(Color.GREEN);
            g.draw(getShape());
            g.setPaint(Color.BLACK);

        } else {
            g.setPaint(new Color(255, 255, 255, 64));
            g.fill(getShape());
            g.setPaint(Color.RED);
            g.draw(getShape());
            PainterTools.drawCrossInBox(g, Color.BLACK, getShape().getBounds2D(), 2);
        }
    }
}
