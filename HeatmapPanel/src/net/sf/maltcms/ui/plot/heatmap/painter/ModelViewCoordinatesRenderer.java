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
package net.sf.maltcms.ui.plot.heatmap.painter;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import javax.swing.JComponent;
import maltcms.ui.viewer.datastructures.tree.ElementNotFoundException;
import net.sf.maltcms.ui.plot.heatmap.IAnnotation;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;
import net.sf.maltcms.ui.plot.heatmap.Tuple2D;
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
                Tuple2D<Point2D, IAnnotation<T>> tpl = hm.getClosestInRadius(
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
