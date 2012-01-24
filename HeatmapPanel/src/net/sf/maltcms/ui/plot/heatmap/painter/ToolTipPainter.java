/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap.painter;

import cross.datastructures.tuple.Tuple2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import maltcms.ui.viewer.datastructures.tree.ElementNotFoundException;
import net.sf.maltcms.ui.plot.heatmap.Annotation;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;
import net.sf.maltcms.ui.plot.heatmap.event.IProcessorResultListener;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent;
import org.jdesktop.swingx.painter.AbstractPainter;

/**
 * FIXME resolve shift + mouse click on annotation, shift + mouse move etc. behaviour
 * @author nilshoffmann
 */
public abstract class ToolTipPainter<T, U extends JComponent> extends AbstractPainter<U>
        implements IProcessorResultListener<Point2D>, PropertyChangeListener {

    private Tuple2D<Point2D, Annotation<T>> a = null;
    private boolean drawLabels = true;
//    private AffineTransform at = AffineTransform.getTranslateInstance(1.0d, 1.0d);
    private float labelFontSize = 14.0f;
    private double margin = 5.0f;
    private HeatmapDataset<T> hm;
    private Point2D viewPoint;
    private Point2D modelPoint;
    private Shape annotationShape;

    public ToolTipPainter(HeatmapDataset<T> hm) {
        setCacheable(false);
        this.hm = hm;
    }

    public void setPoint(Point2D viewPoint) {
        this.viewPoint = viewPoint;
        try {
            this.modelPoint = hm.toModelPoint(viewPoint);
            try {
                a = hm.getClosestInRadius(
                        this.modelPoint, 50);
                if (a != null) {
                    System.out.println(
                            "Found annotation within radius 50 around " + this.modelPoint);
                    this.annotationShape = hm.toViewShape(
                            a.getSecond().getShape());
                } else {
                    System.out.println(
                            "Could not find annotation in search radius 50 around " + this.modelPoint);
                }
            } catch (ElementNotFoundException enfe) {
                System.out.println(
                        "Could not find annotation in search radius 50 around " + this.modelPoint);
                a = null;
            }
        } catch (NoninvertibleTransformException ex) {
//            Logger.getLogger(AnnotationPainter.class.getName()).log(Level.SEVERE, null, ex);
        }
        setDirty(true);
    }

    public Point2D getPoint() {
        return this.viewPoint;
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
        if (a != null && annotationShape != null && drawLabels) {
            AffineTransform originalTransform = g.getTransform();

            Point2D lineCross = new Point2D.Double(annotationShape.getBounds2D().
                    getCenterX(), annotationShape.getBounds2D().getCenterY());
            Line2D.Double l1 = new Line2D.Double(0, lineCross.getY(),
                    t.getWidth(), lineCross.getY());
            Line2D.Double l2 = new Line2D.Double(lineCross.getX(), 0, lineCross.
                    getX(), t.getHeight());
            g.setColor(Color.BLACK);
            g.draw(l1);
            g.draw(l2);

            Paint fill = new Color(250, 250, 210, 240);
            Paint border = new Color(218, 165, 32, 240);
            String label = getStringFor(a.getSecond());

//            AffineTransform origTrans = transforms.pop();

            //return to standard coordinate system
//            g.setTransform(AffineTransform.getTranslateInstance(0, 0));
            Font currentFont = g.getFont();
            Font labelFont = currentFont.deriveFont(labelFontSize);
            g.setFont(labelFont);
            Tuple2D<Rectangle2D, Point2D> tple = PainterTools.getBoundingBox(
                    fill, border, label, g, 10);
            Rectangle2D r = tple.getFirst();
            double lshift = margin;
            double ushift = margin;
            if (lineCross.getX() + margin + r.getWidth() >= t.getVisibleRect().
                    getMaxX()) {
                lshift = -r.getWidth() - margin;
            }
            if (lineCross.getY() + margin + r.getHeight() >= t.getVisibleRect().
                    getMaxY()) {
                ushift = -r.getHeight() - margin;
            }
            double finalx = Math.max(margin,
                    lineCross.getX() + lshift - r.getX());
            double finaly = Math.max(margin,
                    lineCross.getY() + ushift - r.getY());
            AffineTransform transl = AffineTransform.getTranslateInstance(finalx,
                    finaly);
            g.setTransform(transl);
            drawLabelBox(g, fill, r, border, label, tple);

            g.setFont(currentFont);
            g.setTransform(originalTransform);
        }
    }

    private void drawLabelBox(Graphics2D g, Paint fill, Rectangle2D r,
            Paint border, String label, Tuple2D<Rectangle2D, Point2D> tple) {
        //            Shape s = at.createTransformedShape(r);
        g.setPaint(fill);
        RoundRectangle2D boxArea = new RoundRectangle2D.Double(r.getX(),
                r.getY(), r.getWidth(), r.getHeight(), 10, 10);
        g.fill(boxArea);
        g.setPaint(border);
        g.draw(boxArea);
        g.setPaint(Color.BLACK);
        g.drawString(label, (float) (tple.getSecond().getX()), (float) (tple.
                getSecond().getY()));
    }

    @Override
    public void listen(Point2D t, MouseEvent et) {
        setPoint(t);
    }

    public abstract String getStringFor(Annotation<T> t);

    /**
     * FIXME selection issue when selecting the same annotation
     * @param pce
     */
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if (pce.getPropertyName().equals("point")) {

//            Point2D oldPoint = (Point2D) pce.getOldValue();
            Point2D newPoint = (Point2D) pce.getNewValue();
            setPoint(newPoint);
        }

////        System.out.println("ToolTipPainter Event: " + pce.getPropertyName());
//        if (pce.getPropertyName().equals("annotationPointSelection")) {
//////            System.out.println("ToolTipPainter received annotationPointSelection received for old: " + pce.getOldValue() + " new: " + pce.getNewValue());
////            Tuple2D<Point2D, Annotation<T>> oldVal = (Tuple2D<Point2D, Annotation<T>>) pce.getOldValue();
////            Tuple2D<Point2D, Annotation<T>> newVal = (Tuple2D<Point2D, Annotation<T>>) pce.getNewValue();
////            if (oldVal != null) {
////                oldVal.getSecond().setSelected(false);
////            }
////            if (newVal != null) {
////                this.a = newVal;
////                this.a.getSecond().setSelected(true);
////                setPoint(this.a.getFirst());
//////                setDrawLabels(true);
////            } else {
////                this.a = null;
////                setPoint(null);
//////                setDrawLabels(false);
//        }
//        } else if (pce.getPropertyName().equals(HeatmapDataset.PROP_TRANSFORM)) {
//            System.out.println("Received transform change event!");
//            this.at = (AffineTransform) pce.getNewValue();
//            setDirty(true);
//        }
    }
}

