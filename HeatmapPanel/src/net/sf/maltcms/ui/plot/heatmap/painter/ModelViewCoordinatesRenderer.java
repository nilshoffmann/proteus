/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap.painter;

import cross.datastructures.tuple.Tuple2D;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import javax.swing.JComponent;
import maltcms.ui.viewer.datastructures.tree.ElementNotFoundException;
import net.sf.maltcms.ui.plot.heatmap.Annotation;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;
import net.sf.maltcms.ui.plot.heatmap.event.IProcessorResultListener;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent;
import org.jdesktop.swingx.painter.AbstractPainter;

/**
 *
 * @author nilshoffmann
 */
public class ModelViewCoordinatesRenderer<T, U extends JComponent> extends AbstractPainter<U>
        implements IProcessorResultListener<Point2D> {

    private Point2D viewPoint, modelPoint;
    private Shape annotationShape;
    private HeatmapDataset<T> hm;

    public ModelViewCoordinatesRenderer(HeatmapDataset<T> hm) {
        this.hm = hm;
    }

    @Override
    protected void doPaint(Graphics2D gd, U t, int i, int i1) {
        if (viewPoint != null && modelPoint != null ) {
            gd.drawString(
                    "View: " + viewPoint + "; Model: " + modelPoint + "", (float) viewPoint.
                    getX(), (float) viewPoint.getY());
        }
        if (this.annotationShape != null) {
            gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    0.3f));
            gd.fill(annotationShape);
        }
    }

    @Override
    public void listen(Point2D t, MouseEvent me) {
        this.viewPoint = t;
        try {
            this.modelPoint = hm.toModelPoint(t);
            try {
                Tuple2D<Point2D, Annotation<T>> tpl = hm.getClosestInRadius(
                        this.modelPoint, 50);
                if (tpl != null) {
                    System.out.println("Found annotation within radius 50 around "+this.modelPoint);
                    this.annotationShape = hm.toViewShape(tpl.
                            getSecond().getShape());
                } else {
                    System.out.println(
                            "Could not find annotation in search radius 50 around " + this.modelPoint);
                }
            } catch (ElementNotFoundException enfe) {
                System.out.println(
                            "Could not find annotation in search radius 50 around " + this.modelPoint);
            }
        } catch (NoninvertibleTransformException ex) {
//            Logger.getLogger(AnnotationPainter.class.getName()).log(Level.SEVERE, null, ex);
        }
        setDirty(true);
    }
}
