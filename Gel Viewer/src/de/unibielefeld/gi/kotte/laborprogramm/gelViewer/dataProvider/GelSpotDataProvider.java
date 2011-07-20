/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.dataProvider;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import maltcms.ui.viewer.datastructures.tree.QuadTree;
import net.sf.maltcms.ui.plot.heatmap.IDataProvider;

/**
 *
 * @author nilshoffmann
 */
public class GelSpotDataProvider implements IDataProvider<ISpot> {

    private BufferedImage dataImage = null;
    private Rectangle2D dataBounds = null;
    private int maxRow = 0;
    private Point2D toOrigin;
    private File gelLocation;
    private QuadTree<ISpot> qt = null;

    public GelSpotDataProvider(File gelLocation, IGel gel) {
        this(gelLocation, gel, new Point2D.Double(0,0));
    }

    public GelSpotDataProvider(File gelLocation, IGel gel, Point2D toOrigin) {
        this.gelLocation = gelLocation;
        BufferedImage bi = null;
//        try {
        //        if (file == 0) {
        //            bi = ImageIO.read(HeatmapPanel.class.getResource(s));
        //            hmd = new HeatmapDataset<List<Integer>>(new HeatmapDataProvider(
        //                    bi, new Point2D.Double(0, bi.getHeight())));
        //        } else {
        //
        //        }
        PlanarImage im = null;
        im = JAI.create("fileload", gelLocation.getAbsolutePath());
        bi = im.getAsBufferedImage();
        this.dataImage = bi;
        this.toOrigin = toOrigin;
        this.dataBounds = new Rectangle2D.Double(0, 0, this.dataImage.getWidth(), this.dataImage.getHeight());
        System.out.println("DataBounds: " + this.dataBounds);
        this.maxRow = (int) this.dataBounds.getHeight() - 1;
        this.qt = new QuadTree<ISpot>(this.dataBounds);
        for(ISpot spot:gel.getSpots()) {
            this.qt.put(new Point2D.Double(spot.getPosX(),spot.getPosY()), spot);
        }
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
    public ISpot get(Point2D p) {
        System.out.println("Screen point: "+p);
//        int rbg = -1;
//        Point2D dataPoint = p;//transformToModel(p);
//        System.out.println("Model point: "+dataPoint);
//        if (this.dataBounds.contains(dataPoint)) {
////            try {
////            if (flipY) {
////                dataPoint = new Point2D.Double(p.getX(), this.dataBounds.getHeight() - p.getY());
////            }
////                dataPoint = p;
////                System.out.println("Retrieving data at " + p + " transformed: " + dataPoint);
//            rbg = this.dataImage.getRGB((int) dataPoint.getX(), ((int) dataPoint.getY()));
////            } catch (NoninvertibleTransformException ex) {
////                Logger.getLogger(HeatmapDataProvider.class.getName()).log(Level.SEVERE, null, ex);
////            }
//
//        } else {
//            throw new IllegalArgumentException("Point out of bounds: " + dataBounds);
//        }
//
//        Integer red = (int) ((rbg >> 16) & 0xFF);
//        Integer green = (int) ((rbg >> 8) & 0xFF);
//        Integer blue = (int) ((rbg) & 0xFF);
//        return ((double) ((red * 0.3f) + (green * 0.59f) + (blue * 0.11f)));
        //return Arrays.asList(red, green, blue);
        return qt.get(p);
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
