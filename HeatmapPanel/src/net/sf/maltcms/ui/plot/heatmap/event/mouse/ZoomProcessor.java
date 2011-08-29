/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap.event.mouse;

import cross.datastructures.tuple.Tuple2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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
        System.out.println("ZoomIn");
        zoom = Math.min(maxZoom, zoom + zoomDelta);
        result = new Tuple2D<Point2D, Double>(point, zoom);
//            System.out.println("ZoomProcessor notifying listeners for mouse wheel event!");
        notifyListeners();
    }

    public void zoomOut(Point2D point) {
        System.out.println("ZoomOut");
        zoom = Math.max(minZoom, zoom - zoomDelta);
        result = new Tuple2D<Point2D, Double>(point, zoom);
//            System.out.println("ZoomProcessor notifying listeners for mouse wheel event!");
        notifyListeners();
    }

    @Override
    public void processMouseWheelEvent(MouseWheelEvent mwe, MouseEventType et) {
        super.processMouseWheelEvent(mwe, et);
        System.out.println("MouseWheelEvent on zoom processor: "+mwe.paramString());
        switch(et) {
            case WHEEL_UP:
                System.out.println("Wheel up event!");
//                if (me.isControlDown()) {
                    zoomIn(mwe.getPoint());
//                }
                break;
            case WHEEL_DOWN:
                System.out.println("Wheel down event!");
//                if (me.isControlDown()) {
                    zoomOut(mwe.getPoint());
//                }
                break;
        }
    }

    @Override
    public void processMouseEvent(MouseEvent me, MouseEventType et) {
        super.processMouseEvent(me, et);
        System.out.println("MouseEvent on zoom processor: "+me.paramString());
        switch (et) {
            case CLICKED:
                System.out.println("Click event!");
                if (me.isControlDown() && me.getButton() == MouseEvent.BUTTON1) {//UP
                    zoomIn(me.getPoint());
                } else if (me.isControlDown() && me.getButton() == MouseEvent.BUTTON3) {//DOWN
                    zoomOut(me.getPoint());
                }
                break;
        }
    }

    @Override
    public Tuple2D<Point2D, Double> getProcessingResult() {
        return result;
    }
}
