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
package net.sf.maltcms.ui.plot.heatmap.actions;

import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import javax.swing.AbstractAction;
import net.sf.maltcms.ui.plot.heatmap.event.IProcessorResultListener;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEventType;
import net.sf.maltcms.ui.plot.heatmap.painter.AnnotationPainter;

/**
 *
 * @author nilshoffmann
 */
public class RemoveAnnotation extends AbstractAction implements IProcessorResultListener<Point2D>{
    
    private AnnotationPainter annotationPainter = null;

    public AnnotationPainter getAnnotationPainter() {
        return annotationPainter;
    }

    public void setAnnotationPainter(AnnotationPainter ap) {
        this.annotationPainter = ap;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.annotationPainter!=null) {
            annotationPainter.removeAnnotation();
        }
    }

    @Override
    public void listen(Point2D t, MouseEvent et) {
        if(this.annotationPainter!=null) {
            annotationPainter.removeAnnotation();
        }
    }

}
