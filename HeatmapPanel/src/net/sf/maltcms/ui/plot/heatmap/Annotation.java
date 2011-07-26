/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import net.sf.maltcms.ui.plot.heatmap.painter.PainterTools;

/**
 *
 * @author nilshoffmann
 */
public class Annotation<T> {

    private final T t;
    private Point2D position;
    private boolean selected;
    private Shape shape;

    public Annotation(Point2D position, T t) {
        this.position = position;
        this.t = t;
        this.shape = new RoundRectangle2D.Double(position.getX() - 10, position.getY() - 10, 20, 20,2,2);
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return this.shape;
    }

    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public String toString() {
        return this.t.toString();
    }

    public T getPayload() {
        return t;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void draw(Graphics2D g) {
        if (!isSelected()) {
            g.setPaint(new Color(255, 255, 255, 64));
            g.fill(shape);
            g.setPaint(Color.GREEN);
            g.draw(shape);
            g.setPaint(Color.BLACK);

        } else {
            g.setPaint(new Color(255, 255, 255, 64));
            g.fill(shape);
            g.setPaint(Color.RED);
            g.draw(shape);
            PainterTools.drawCrossInBox(g, Color.BLACK, shape.getBounds2D(), 2);
        }
    }
}
