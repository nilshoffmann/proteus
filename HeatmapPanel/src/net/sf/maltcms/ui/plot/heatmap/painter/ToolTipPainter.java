/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap.painter;

import cross.datastructures.tuple.Tuple2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Stack;
import javax.swing.JComponent;
import net.sf.maltcms.ui.plot.heatmap.Annotation;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;
import net.sf.maltcms.ui.plot.heatmap.event.IProcessorResultListener;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEventType;
import org.jdesktop.swingx.painter.AbstractPainter;

/**
 * FIXME resolve shift + mouse click on annotation, shift + mouse move etc. behaviour
 * @author nilshoffmann
 */
public abstract class ToolTipPainter<T, U extends JComponent> extends AbstractPainter<U> implements IProcessorResultListener<Point2D>, PropertyChangeListener {

    private Point2D p = null;
    private Tuple2D<Point2D,Annotation<T>> a = null;
    private boolean drawLabels = true;
    private AffineTransform at = AffineTransform.getTranslateInstance(1.0d, 1.0d);

    public ToolTipPainter() {
        setCacheable(false);
    }

    public void setPoint(Point2D p) {
        Point2D old = this.p;
        this.p = p;
        setDirty(true);
//        firePropertyChange("point", old, p);
    }

    public Point2D getPoint() {
        return this.p;
    }

    public boolean isDrawLabels() {
        return drawLabels;
    }

    public void setDrawLabels(boolean drawLabels) {
        boolean oldValue = this.drawLabels;
        this.drawLabels = drawLabels;
        setDirty(true);
        firePropertyChange("labels", oldValue, drawLabels);
    }

    @Override
    protected void doPaint(Graphics2D g, JComponent t, int width, int height) {
        Shape clip = g.getClip();
//        Rectangle2D bounds = at.createTransformedShape(t.getBounds()).getBounds2D();
//        if (p != null && a==null) {
//            g.setClip(t.getVisibleRect());
//            Line2D.Double l1 = new Line2D.Double(0, p.getY(), t.getWidth(), p.getY());
//            Line2D.Double l2 = new Line2D.Double(p.getX(), 0, p.getX(), t.getHeight());
//            g.setColor(Color.BLACK);
//            g.draw(l1);
//            g.draw(l2);
//        }
        if ((a != null && p != null && drawLabels)) {
            Shape annotationClipShape = a.getSecond().getShape();
            Area area = new Area(g.getClip());
            area.subtract(new Area(annotationClipShape));
            g.setClip(area);
            Color fillColor = new Color(255,255,255,225);
            g.setColor(fillColor);
            g.fill(clip);
            Stack<AffineTransform> transforms = new Stack<AffineTransform>();
            //g.setClip(t.getVisibleRect());
            transforms.push(g.getTransform());
//            transforms.push(at);
            Point2D lineCross = at.transform(a.getFirst(), null);
            Line2D.Double l1 = new Line2D.Double(0, lineCross.getY(), t.getWidth(), lineCross.getY());
            Line2D.Double l2 = new Line2D.Double(lineCross.getX(), 0, lineCross.getX(), t.getHeight());
            g.setColor(Color.BLACK);
            g.draw(l1);
            g.draw(l2);
            g.setTransform(at);

//            Point2D transfPoint = at.transform(a.getFirst(),null);
//            Line2D.Double l1 = new Line2D.Double(0, a.getFirst().getY(), t.getWidth(), a.getFirst().getY());
//            Line2D.Double l2 = new Line2D.Double(a.getFirst().getX(), 0, a.getFirst().getX(), t.getHeight());
//            g.setColor(Color.BLACK);
//            g.draw(l1);
//            g.draw(l2);
            
//            g.setTransform(at);
            int margin = 10;
            a.getSecond().draw(g);
//            AffineTransform translateToBoxOrigin = AffineTransform.getTranslateInstance(width, width)
            g.setTransform(transforms.pop());
            //prepare hovering label
            Paint fill = new Color(250, 250, 210, 240);
            Paint border = new Color(218, 165, 32, 240);
            String label = getStringFor(a.getSecond());
            Tuple2D<Rectangle2D, Point2D> tple = PainterTools.getBoundingBox(fill, border, label, g, 10);
            Rectangle2D r = tple.getFirst();
            double lshift = margin;
            double ushift = margin;
            if (p.getX() + r.getWidth() >= t.getVisibleRect().getMaxX()) {
                lshift = -r.getWidth() - margin;
            }
            if (p.getY() + r.getHeight() >= t.getVisibleRect().getMaxY()) {
                ushift =  -r.getHeight() - margin;
            }
            double finalx = Math.max(margin, p.getX() + lshift - r.getX());
            double finaly = Math.max(margin, p.getY() + ushift - r.getY());
            AffineTransform at = AffineTransform.getTranslateInstance(finalx, finaly);
//            AffineTransform transf = g.getTransform();
            transforms.push(g.getTransform());
//            transforms.push(at);
            g.setTransform(at);
//            Shape s = at.createTransformedShape(r);
            g.setPaint(fill);
            RoundRectangle2D boxArea = new RoundRectangle2D.Double(r.getX(),r.getY(),r.getWidth(),r.getHeight(),10,10);
            g.fill(boxArea);
            g.setPaint(border);
            g.draw(boxArea);
            g.setPaint(Color.BLACK);

            g.drawString(label, (float) (tple.getSecond().getX()), (float) (tple.getSecond().getY()));
            g.setTransform(transforms.pop());
        }
        g.setClip(clip);
    }

    @Override
    public void listen(Point2D t, MouseEvent et) {
        if (et.getMet() == MouseEventType.MOVED || et.getMet() == MouseEventType.CLICKED) {
            setPoint(t);
        } else {
            this.a = null;
            setDrawLabels(false);
        }
    }

    public abstract String getStringFor(Annotation<T> t);

    /**
     * FIXME selection issue when selecting the same annotation
     * @param pce
     */
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        System.out.println("ToolTipPainter Event: " + pce.getPropertyName());
        if (pce.getPropertyName().equals("annotationPointSelection")) {
//            System.out.println("ToolTipPainter received annotationPointSelection received for old: " + pce.getOldValue() + " new: " + pce.getNewValue());
            Tuple2D<Point2D, Annotation<T>> oldVal = (Tuple2D<Point2D, Annotation<T>>) pce.getOldValue();
            Tuple2D<Point2D, Annotation<T>> newVal = (Tuple2D<Point2D, Annotation<T>>) pce.getNewValue();
            if (oldVal != null) {
                oldVal.getSecond().setSelected(false);
            }
            if (newVal != null) {
                this.a = newVal;
                this.a.getSecond().setSelected(true);
                setPoint(this.a.getFirst());
//                setDrawLabels(true);
            } else {
                this.a = null;
                setPoint(null);
//                setDrawLabels(false);
            }
        } else if (pce.getPropertyName().equals(HeatmapDataset.PROP_TRANSFORM)) {
            this.at = (AffineTransform) pce.getNewValue();
            setDirty(true);
        }
    }
}
