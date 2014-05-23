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
package net.sf.maltcms.ui.plot.heatmap;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import maltcms.ui.viewer.datastructures.tree.ElementNotFoundException;
import maltcms.ui.viewer.datastructures.tree.QuadTree;
import net.sf.maltcms.ui.plot.heatmap.event.IProcessorResultListener;

/**
 *
 * @author nilshoffmann
 */
public class HeatmapDataset<T> implements IProcessorResultListener<Tuple2D<Point2D, Double>> {

    private BufferedImage dataImage;
    private final IDataProvider<T> dp;
    private AffineTransform transform = AffineTransform.getTranslateInstance(0, 0);
    private Tuple2D<Point2D, Double> zoom = new Tuple2D<Point2D, Double>(new Point(0, 0), 1.0d);
    private final QuadTree<IAnnotation<T>> quadTree;
    private Tuple2D<Point2D, IAnnotation<T>> lastAnnotation;

    public Tuple2D<Point2D, Double> getZoom() {
        return zoom;
    }

    public void setZoom(Tuple2D<Point2D, Double> zoom) {
        Tuple2D<Point2D, Double> oldZoom = this.zoom;
        this.zoom = zoom;
        System.out.println("HMD: setting zoom to: "+this.zoom+" was: "+oldZoom);
        propertyChangeSupport.firePropertyChange(PROP_ZOOM, oldZoom, this.zoom);
    }

    public HeatmapDataset(IDataProvider<T> dp) {
        this.dataImage = dp.createDataImage();
        this.dp = dp;
        this.quadTree = new QuadTree<IAnnotation<T>>(dp.getDataBounds());
    }

    public IDataProvider<T> getDataProvider() {
        return this.dp;
    }

    public void addAnnotation(Point2D p, IAnnotation<T> annotation) {
        this.quadTree.put(p, annotation);
    }

    @Deprecated
    public IAnnotation<T> addAnnotation(Point2D p, T t) {
        Annotation<T> a = new Annotation<T>(p, t);
        return this.quadTree.put(p, a);
    }

    public IAnnotation<T> removeAnnotation(Point2D p) {
        return this.quadTree.remove(p);
    }

    public T getItemAt(Point2D p) {
        return this.dp.get(p);
    }

    public IAnnotation<T> get(Point2D p) {
        return this.quadTree.get(p);
    }

    public Tuple2D<Point2D, IAnnotation<T>> getClosestInRadius(final Point2D p, double radius) {
        //shortcut
//        if(lastAnnotation!=null && lastAnnotation.getSecond().getPosition().distance(p)<=radius) {
//            return lastAnnotation;
//        }

        List<Tuple2D<Point2D,IAnnotation<T>>> rangeMembers = getChildrenInRange(new Rectangle2D.Double(p.getX()-radius,p.getY()-radius,radius*2,radius*2));
        if(rangeMembers.isEmpty()) {
            lastAnnotation = null;
            return null;
        }
        Collections.sort(rangeMembers, new Comparator<Tuple2D<Point2D,IAnnotation<T>>>() {

            @Override
            public int compare(Tuple2D<Point2D, IAnnotation<T>> t, Tuple2D<Point2D, IAnnotation<T>> t1) {
                double d1 = p.distance(t.getSecond().getPosition());
                double d2 = p.distance(t1.getSecond().getPosition());
                return Double.compare(d1,d2);
            }
        });
        lastAnnotation = rangeMembers.get(0);
        return lastAnnotation;
//        try {
//            lastAnnotation = this.quadTree.getClosestInRadius(p, radius);
//            return lastAnnotation;
//        } catch (ElementNotFoundException enfe) {
//            lastAnnotation = null;
//            return null;
////            return new Tuple2D<Point2D, Annotation<T>>(p, new Annotation<T>(p, getItemAt(p)));
//        }
    }

    public int size() {
        return this.quadTree.size();
    }

    public Iterator<Tuple2D<Point2D, IAnnotation<T>>> getAnnotationIterator() {
        return this.quadTree.iterator();
    }

    public List<Tuple2D<Point2D, IAnnotation<T>>> getChildrenInRange(Rectangle2D r) {
        try {
            return this.quadTree.getChildrenInRange(r);
        } catch (ElementNotFoundException enfe) {
            return Collections.emptyList();
        }
    }

    public Rectangle2D getDataBounds() {
        return this.dp.getDataBounds();
    }
    private Rectangle2D regionOfInterest;
    public static final String PROP_REGIONOFINTEREST = "regionOfInterest";
    public static final String PROP_TRANSFORM = "transform";
    public static final String PROP_ZOOM = "zoom";

    /**
     * Get the value of regionOfInterest
     *
     * @return the value of regionOfInterest
     */
    public Rectangle2D getRegionOfInterest() {
        return regionOfInterest;
    }

    /**
     * Set the value of regionOfInterest
     *
     * @param regionOfInterest new value of regionOfInterest
     */
    public void setRegionOfInterest(Rectangle2D regionOfInterest) {
        Rectangle2D oldRegionOfInterest = this.regionOfInterest;
        this.regionOfInterest = getDataBounds().createIntersection(regionOfInterest);
        propertyChangeSupport.firePropertyChange(PROP_REGIONOFINTEREST, oldRegionOfInterest, regionOfInterest);
        setTransform(getTransformForROI(regionOfInterest));
    }

    protected AffineTransform getTransformForROI(Rectangle2D regionOfInterest) {
//        double zoomX = regionOfInterest.getWidth()/getDataBounds().getWidth();
//        double zoomY = regionOfInterest.getWidth()/getDataBounds().getWidth();

        double zoomX = zoom.getSecond();
        double zoomY = zoom.getSecond();

        AffineTransform t = AffineTransform.getTranslateInstance(0, 0);
        //AffineTransforms are applied from last to first, so read the following from
        //bottom to top!

        //finally translate to center of region of interest
        t.concatenate(AffineTransform.getTranslateInstance(regionOfInterest.getWidth() / 2.0d, regionOfInterest.getHeight() / 2.0d));
        //scale by given amount
        t.concatenate(AffineTransform.getScaleInstance(zoomX, zoomY));
        //translate to center of data bounds
        t.concatenate(AffineTransform.getTranslateInstance(-getDataBounds().getWidth() / 2.0d, -getDataBounds().getHeight() / 2.0d));
//        t.concatenate(this.dp.getViewToModelTransform());
        return t;
    }

    public void setTransform(AffineTransform at) {
        AffineTransform oldTransform = transform;
        transform = at;
        propertyChangeSupport.firePropertyChange(PROP_TRANSFORM, oldTransform, transform);
    }

    public AffineTransform getTransform() {
        return this.transform;
    }
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public BufferedImage getImageInROI() {
        return this.dataImage.getSubimage((int) this.regionOfInterest.getX(), (int) this.regionOfInterest.getY(), (int) this.regionOfInterest.getWidth(), (int) this.regionOfInterest.getHeight());
    }

    public BufferedImage getImage() {
        return this.dataImage;
    }

    @Override
    public void listen(Tuple2D<Point2D, Double> t, net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent et) {
//        if (et.getMet() == MouseEventType.CLICKED) {
        System.out.println("HeatmapDataset received mouse event information!");           
        setZoom(t);
//        }
    }

    public Point2D toModelPoint(Point2D view) throws NoninvertibleTransformException {
        return getTransform().inverseTransform(view, null);
    }

    public Shape toModelShape(Shape view) throws NoninvertibleTransformException {
        AffineTransform inv = getTransform().createInverse();
        return inv.createTransformedShape(view);
    }


    public Point2D toViewPoint(Point2D model) {
        return getTransform().transform(model, null);
    }

    public Shape toViewShape(Shape s) {
        return getTransform().createTransformedShape(s);
    }
}
