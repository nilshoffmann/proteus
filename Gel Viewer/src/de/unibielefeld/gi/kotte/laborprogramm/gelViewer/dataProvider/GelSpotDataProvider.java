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

    private final PlanarImage dataImage;
    private final Rectangle2D dataBounds;
    private final Point2D toOrigin;
    private final QuadTree<ISpot> qt;

    public GelSpotDataProvider(File gelLocation, IGel gel) {
        this(gelLocation, gel, new Point2D.Double(0,0));
    }

    public GelSpotDataProvider(File gelLocation, IGel gel, Point2D toOrigin) {
        dataImage = JAI.create("fileload", gelLocation.getAbsolutePath());
        this.toOrigin = toOrigin;
        Rectangle2D spotBounds = new Rectangle2D.Double(0, 0, this.dataImage.getWidth(), this.dataImage.getHeight());
        for(ISpot spot:gel.getSpots()) {
            spotBounds.add(new Point2D.Double(spot.getPosX(),spot.getPosY()));
        }
        this.dataBounds = spotBounds;
        this.qt = new QuadTree<ISpot>(spotBounds);
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
        return qt.get(p);
    }
    
    public Point2D transformToModel(Point2D viewPoint) {
        Point2D dataPoint = getViewToModelTransform().transform(viewPoint, null);
        return dataPoint;
    }

    @Override
    public Rectangle2D getDataBounds() {
        return this.dataBounds;
    }

    @Override
    public BufferedImage createDataImage() {
        return this.dataImage.getAsBufferedImage();
    }
}
