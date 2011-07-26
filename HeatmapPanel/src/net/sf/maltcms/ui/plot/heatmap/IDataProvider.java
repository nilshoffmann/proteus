/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.maltcms.ui.plot.heatmap;

import cross.datastructures.tuple.Tuple2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author nilshoffmann
 */
public interface IDataProvider<T>{

    T get(Point2D p);
    
    Rectangle2D getDataBounds();
    
    BufferedImage createDataImage();
    
    AffineTransform getViewToModelTransform();
    
}
