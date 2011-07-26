/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap.event.mouse;

import cross.datastructures.tuple.Tuple2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import net.sf.maltcms.ui.plot.heatmap.event.AEventProcessor;

/**
 *
 * @author nilshoffmann
 */
public class ZoomProcessor extends AEventProcessor<Tuple2D<Point2D, Double>> {

    Tuple2D<Point2D, Double> result = new Tuple2D<Point2D, Double>(new Point2D.Double(0, 0), 1.0d);
    double zoom = 1.0d;
    private double minZoom = 0.25d;
    private double maxZoom = 5.0d;
    private double zoomDelta = 0.1d;

    public double getZoomDelta() {
        return zoomDelta;
    }

    public void setZoomDelta(double zoomDelta) {
        this.zoomDelta = zoomDelta;
    }

    public double getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(double maxZoom) {
        this.maxZoom = maxZoom;
    }

    public double getMinZoom() {
        return minZoom;
    }

    public void setMinZoom(double minZoom) {
        this.minZoom = minZoom;
    }

    public void zoomIn(Point2D point) {
        zoom = Math.min(maxZoom, zoom + zoomDelta);
        result = new Tuple2D<Point2D, Double>(point, zoom);
//            System.out.println("ZoomProcessor notifying listeners for mouse wheel event!");
        notifyListeners();
    }

    public void zoomOut(Point2D point) {
        zoom = Math.max(minZoom, zoom - zoomDelta);
        result = new Tuple2D<Point2D, Double>(point, zoom);
//            System.out.println("ZoomProcessor notifying listeners for mouse wheel event!");
        notifyListeners();
    }

    @Override
    public void processMouseEvent(MouseEvent me, MouseEventType et) {
        super.processMouseEvent(me, et);
        boolean changed = true;
//        double zoom = 1.0d;
        switch (et) {
            case CLICKED:
                if (me.isAltDown() && me.getButton() == MouseEvent.BUTTON1) {//UP
                    zoomIn(me.getPoint());
                } else if (me.isAltDown() && me.getButton() == MouseEvent.BUTTON3) {//DOWN
                    zoomOut(me.getPoint());
                } else {
                    changed = false;
                }
                break;
            case WHEEL_UP:
                if (me.isShiftDown()) {
                    zoomIn(me.getPoint());
                }else{
                    changed = false;
                }
                break;
            case WHEEL_DOWN:
                if (me.isShiftDown()) {
                    zoomOut(me.getPoint());
                }else{
                    changed = false;
                }
                break;
        }
    }

    @Override
    public Tuple2D<Point2D, Double> getProcessingResult() {
        return result;
    }
}
