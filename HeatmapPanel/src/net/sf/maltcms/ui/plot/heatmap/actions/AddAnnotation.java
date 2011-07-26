/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class AddAnnotation extends AbstractAction implements IProcessorResultListener<Point2D>{
    
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
            annotationPainter.addAnnotation();
        }
    }

    @Override
    public void listen(Point2D t, MouseEvent et) {
        if(this.annotationPainter!=null) {
            annotationPainter.addAnnotation();
        }
    }

}
