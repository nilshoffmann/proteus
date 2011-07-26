/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap;

import cross.datastructures.tuple.Tuple2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nilshoffmann
 */
public class HeatmapDataProvider implements IDataProvider<List<Integer>> {

    private BufferedImage dataImage = null;
    private Rectangle2D dataBounds = null;
    private int maxRow = 0;
    private Point2D toOrigin;

    public HeatmapDataProvider(BufferedImage bi) {
        this(bi, new Point2D.Double(0,0));
    }

    public HeatmapDataProvider(BufferedImage bi, Point2D toOrigin) {
        this.dataImage = bi;
        this.toOrigin = toOrigin;
        this.dataBounds = new Rectangle2D.Double(0, 0, this.dataImage.getWidth(), this.dataImage.getHeight());
        System.out.println("DataBounds: " + this.dataBounds);
        this.maxRow = (int) this.dataBounds.getHeight() - 1;
    }

    @Override
    public AffineTransform getViewToModelTransform() {
        AffineTransform at = AffineTransform.getTranslateInstance(toOrigin.getX(),toOrigin.getY());
        double xflip = 1.0d;
        double yflip = 1.0d;
        if(toOrigin.getY()>0) {
            yflip = -1.0d;
            
        }
        if(toOrigin.getX()>0) {
            xflip = -1.0d;
        }
        at.concatenate(AffineTransform.getScaleInstance(xflip, yflip));
        return at;
    }

    @Override
    public List<Integer> get(Point2D p) {
        System.out.println("Screen point: "+p);
        int rbg = -1;
        Point2D dataPoint = p;//transformToModel(p);
        System.out.println("Model point: "+dataPoint);
        if (this.dataBounds.contains(dataPoint)) {
//            try {
//            if (flipY) {
//                dataPoint = new Point2D.Double(p.getX(), this.dataBounds.getHeight() - p.getY());
//            }
//                dataPoint = p;
//                System.out.println("Retrieving data at " + p + " transformed: " + dataPoint);
            rbg = this.dataImage.getRGB((int) dataPoint.getX(), ((int) dataPoint.getY()));
//            } catch (NoninvertibleTransformException ex) {
//                Logger.getLogger(HeatmapDataProvider.class.getName()).log(Level.SEVERE, null, ex);
//            }

        } else {
            throw new IllegalArgumentException("Point out of bounds: " + dataBounds);
        }

        Integer red = (int) ((rbg >> 16) & 0xFF);
        Integer green = (int) ((rbg >> 8) & 0xFF);
        Integer blue = (int) ((rbg) & 0xFF);
//        return ((double) ((red * 0.3f) + (green * 0.59f) + (blue * 0.11f)));
        return Arrays.asList(red, green, blue);
    }
    
    public Point2D transformToModel(Point2D viewPoint) {
        Point2D dataPoint = getViewToModelTransform().transform(viewPoint, null);
        return dataPoint;
//        return viewPoint;
    }

    @Override
    public Rectangle2D getDataBounds() {
        return this.dataBounds;
    }

    @Override
    public BufferedImage createDataImage() {
        return this.dataImage;
    }
}
