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

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

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
            rbg = this.dataImage.getRGB((int) dataPoint.getX(), ((int) dataPoint.getY()));
        } else {
            throw new IllegalArgumentException("Point out of bounds: " + dataBounds);
        }

        Integer red = ((rbg >> 16) & 0xFF);
        Integer green = ((rbg >> 8) & 0xFF);
        Integer blue = ((rbg) & 0xFF);
        return Arrays.asList(red, green, blue);
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
        return this.dataImage;
    }
}
