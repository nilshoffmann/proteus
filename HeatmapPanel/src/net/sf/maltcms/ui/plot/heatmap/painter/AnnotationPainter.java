/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap.painter;

import cross.datastructures.tuple.Tuple2D;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class AnnotationPainter<T, U extends JComponent> extends AbstractPainter<U>
        implements IProcessorResultListener<Point2D>, PropertyChangeListener {

    private AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
    private Point2D activeModelPoint = null;
    private HeatmapDataset<T> hm;
    private double searchRadius = 10.0d;
    private Point2D activeViewPoint = null;
    private Tuple2D<Point2D, Annotation<T>> activeSelection = null;
    private Point2D viewPoint;
    private Point2D modelPoint;
    private Tuple2D<Point2D, Annotation<T>> a = null;
    private Shape annotationShape;

    public AnnotationPainter(HeatmapDataset<T> hm) {
//        annotations = qt;
        this.hm = hm;
        this.hm.addPropertyChangeListener(this);
        setCacheable(false);
    }

    public double getSearchRadius() {
        return this.searchRadius;
    }

    public void setSearchRadius(double d) {
        double old = this.searchRadius;
        this.searchRadius = d;
        firePropertyChange("searchRadius", old, this.searchRadius);
    }

    public void setActivePoint(Point2D activePoint) {
        deselectAnnotation();
//        this.hm.getDataProvider().getViewToModelTransform().transform(activeModelPoint, null);
        this.activeModelPoint = activePoint;//this.hm.getDataProvider().getViewToModelTransform().transform(activeModelPoint, null);;
    }

    public Point2D getActivePoint() {
        return this.activeModelPoint;
    }

    public void selectAnnotation() {
        if (activeModelPoint != null) {
            try {
                Tuple2D<Point2D, Annotation<T>> tpl = this.hm.getClosestInRadius(
                        activeModelPoint, this.searchRadius);
                tpl.getSecond().setSelected(true);
                firePropertyChange("annotations", null, tpl.getSecond());
            } catch (ElementNotFoundException e) {
            }
        }
    }

    public void deselectAnnotation() {
        if (activeModelPoint != null) {
            try {
                Tuple2D<Point2D, Annotation<T>> tpl = this.hm.getClosestInRadius(
                        activeModelPoint, this.searchRadius);
                tpl.getSecond().setSelected(false);
                firePropertyChange("annotations", null, tpl.getSecond());
            } catch (ElementNotFoundException e) {
            }
        }
    }

    public void addAnnotation() {
        if (activeModelPoint != null) {
            T t = hm.getItemAt(activeModelPoint);
            Annotation<T> a = this.hm.addAnnotation(activeModelPoint, t);
            firePropertyChange("annotations", null, a);
        }
    }

    public void removeAnnotation() {
        if (activeModelPoint != null) {
            try {
                Tuple2D<Point2D, Annotation<T>> tpl = this.hm.getClosestInRadius(
                        activeModelPoint, 10.0d);
                Annotation a = this.hm.removeAnnotation(tpl.getFirst());
                firePropertyChange("annotations", null, a);
            } catch (ElementNotFoundException fnfe) {
            }
        }
    }

    public Annotation<T> getAnnotation(T t) {
        for (Tuple2D<Point2D, Annotation<T>> ann : getAnnotations()) {
            if (ann.getSecond().getPayload().equals(t)) {
                return ann.getSecond();
            }
        }
        return null;
    }

    public List<Tuple2D<Point2D, Annotation<T>>> getAnnotations() {
        List<Tuple2D<Point2D, Annotation<T>>> l = new ArrayList<Tuple2D<Point2D, Annotation<T>>>(this.hm.
                size());
        Iterator<Tuple2D<Point2D, Annotation<T>>> iter = this.hm.
                getAnnotationIterator();
        while (iter.hasNext()) {
            l.add(iter.next());
        }
        return l;
    }

    @Override
    protected void doPaint(Graphics2D g, JComponent t, int width, int height) {
        if (this.hm != null) {



            AffineTransform old = g.getTransform();
            g.setTransform(hm.getTransform());
//            AffineTransform activeTransform = null;
//            if (t == null) {
//                activeTransform = AffineTransform.getTranslateInstance(0, 0);
//            } else {
//                activeTransform = at;
//            }
//            g.setTransform(activeTransform);
//            Shape clip = g.getClip();
//            Composite c = g.getComposite();
//            AlphaComposite ac = AlphaComposite.getInstance(
//                    AlphaComposite.SRC_OVER, 0.3f);
//            g.setComposite(ac);
//
//            AffineTransform inverseTransf = null;
//            Rectangle2D selection = null;
//
//            try {
//                inverseTransf = activeTransform.createInverse();
//                if (t != null) {
//                    selection = inverseTransf.createTransformedShape(t.
//                            getVisibleRect()).getBounds2D();
//                    g.setClip(selection);
//                } else {
//                    if (clip != null) {
//                        selection = inverseTransf.createTransformedShape(clip).
//                                getBounds2D();
//                        g.setClip(selection);
//                    }
//                }
//            } catch (NoninvertibleTransformException ex) {
//                Logger.getLogger(AnnotationPainter.class.getName()).log(
//                        Level.SEVERE, null, ex);
//            }
//
            Iterator<Tuple2D<Point2D, Annotation<T>>> iter = null;//this.annotations.getChildrenInRange(selection.getBounds2D()).iterator();
//
//            if (selection == null) {
            iter = this.hm.getAnnotationIterator();
////                System.out.println("Drawing all annotations");
//            } else {
//                List<Tuple2D<Point2D, Annotation<T>>> list = this.hm.
//                        getChildrenInRange(selection);
////                System.out.println("Drawing " + list.size() + " annotations in active viewport");
//                iter = list.iterator();
//            }
            while (iter.hasNext()) {
                Tuple2D<Point2D, Annotation<T>> tple = iter.next();
//                Annotation<T> a = ;
                drawAnnotation(g, tple.getSecond());//, activeAnnotations);
            }
//            g.setClip(clip);
//            g.setComposite(c);
//            g.setTransform(old);
        }
    }

    private void drawAnnotation(Graphics2D g, Annotation<T> a) {
        a.draw(g);
    }

    @Override
    public void listen(Point2D t, MouseEvent et) {
////        try {
//            Point2D old = this.activeViewPoint;
//            //Point2D newSel = at.inverseTransform(t, null);
//            this.activeViewPoint = t;
//            //this.activeModelPoint = newSel;
//            //System.out.println("Active view point: " + t + " active model point: " + newSel);
//            propertyChange(new PropertyChangeEvent(this, "point", old, t));
////            setDirty(true);
////        } catch (NoninvertibleTransformException ex) {
////            Logger.getLogger(AnnotationPainter.class.getName()).log(Level.SEVERE, null, ex);
////        }
//        if (et.getMet() == MouseEventType.CLICKED) {
        
//        }
        selectAnnotation(t);
    }

    private void selectAnnotation(Point2D viewPoint) {
        Tuple2D<Point2D, Annotation<T>> old = a;
        if (old != null) {
            old.getSecond().setSelected(false);
        }
        Point2D oldViewPoint = this.viewPoint;
        this.viewPoint = viewPoint;
        try {
            this.modelPoint = hm.toModelPoint(viewPoint);
            try {
                a = hm.getClosestInRadius(
                        this.modelPoint, searchRadius);
                if (a != null) {
//                    System.out.println(
//                            "Found annotation within radius 50 around " + this.modelPoint);
                    this.annotationShape = hm.toViewShape(
                            a.getSecond().getShape());
                    this.a.getSecond().setSelected(true);
                } else {
//                    System.out.println(
//                            "Could not find annotation in search radius 50 around " + this.modelPoint);
                }
            } catch (ElementNotFoundException enfe) {
//                System.out.println(
//                        "Could not find annotation in search radius 50 around " + this.modelPoint);
                a = null;
            }
        } catch (NoninvertibleTransformException ex) {
//            Logger.getLogger(AnnotationPainter.class.getName()).log(Level.SEVERE, null, ex);
        }
        firePropertyChange("annotationPointSelection", old, a);
        firePropertyChange("point", oldViewPoint, viewPoint);
        setDirty(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
//        System.out.println("AnnotationPainter received event: " + pce.getPropertyName()+" from "+pce.getSource().getClass());
//        if (pce.getPropertyName().equals("selection")) {
//            Rectangle2D old = (Rectangle2D) pce.getOldValue();
//            List<Tuple2D<Point2D, Annotation<T>>> oldSelection = Collections.emptyList();
//            try {
//                if (old != null) {
//                    old = at.createInverse().createTransformedShape(old).getBounds2D();
//                    oldSelection = this.hm.getChildrenInRange(old);
//                    for (Tuple2D<Point2D, Annotation<T>> tpl : oldSelection) {
//                        tpl.getSecond().setSelected(false);
//                    }
//                }
//                Rectangle2D newSel = (Rectangle2D) pce.getNewValue();
//                List<Tuple2D<Point2D, Annotation<T>>> newSelection = Collections.emptyList();
//                if (newSel != null) {
//                    newSel = at.createInverse().createTransformedShape(newSel).getBounds2D();
//                    newSelection = this.hm.getChildrenInRange(newSel);
//                    for (Tuple2D<Point2D, Annotation<T>> tpl : newSelection) {
//                        tpl.getSecond().setSelected(true);
//                    }
//                    firePropertyChange("annotationMultiSelection", oldSelection, newSelection);
//                }
//
//            } catch (NoninvertibleTransformException ex) {
//                Logger.getLogger(AnnotationPainter.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ElementNotFoundException enfe) {
//                firePropertyChange("annotationMultiSelection", oldSelection, Collections.emptyList());
//            }
//        } else 
        if (pce.getPropertyName().equals("point")) {

//            Point2D oldPoint = (Point2D) pce.getOldValue();
            Point2D newPoint = (Point2D) pce.getNewValue();
            selectAnnotation(newPoint);
        }
//            System.out.println("Received point property change");
//            Point2D old = (Point2D) pce.getOldValue();
//
//            Tuple2D<Point2D, Annotation<T>> oldSelection = null;
//            if (activeSelection != null) {
//                activeSelection.getSecond().setSelected(false);
//            }
//
//            oldSelection = activeSelection;
//
//            Point2D newSel = (Point2D) pce.getNewValue();
//            Tuple2D<Point2D, Annotation<T>> newSelection = null;
//            if (newSel != null) {
//                try {
//                    newSel = at.inverseTransform(newSel, null);
//                    try {
//                        newSelection = this.hm.getClosestInRadius(newSel, this.searchRadius);
//                        newSelection.getSecond().setSelected(true);
//                        activeSelection = newSelection;
//                    } catch (ElementNotFoundException enfe) {
//                    } catch (IllegalArgumentException iae) {
//                    }
//                    System.out.println("New Selection around model coordinates: "+newSel+" view: "+pce.getNewValue());
//                } catch (NoninvertibleTransformException ex) {
//                    Logger.getLogger(AnnotationPainter.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            firePropertyChange("annotationPointSelection", oldSelection, newSelection);
//        } else if (pce.getPropertyName().equals(HeatmapDataset.PROP_TRANSFORM)) {
//            this.at = (AffineTransform) pce.getNewValue();
//            setDirty(true);
//        } else if (pce.getPropertyName().equals(JScrollPane.AccessibleJComponent.ACCESSIBLE_VALUE_PROPERTY)) {
//            setDirty(true);
//        } else {
//            setDirty(true);
//        }
        setDirty(true);
    }
}
