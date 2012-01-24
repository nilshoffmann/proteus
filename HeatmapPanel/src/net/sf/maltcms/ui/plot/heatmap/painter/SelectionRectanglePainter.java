/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap.painter;

import cross.datastructures.tuple.Tuple2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import net.sf.maltcms.ui.plot.heatmap.Annotation;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;
import net.sf.maltcms.ui.plot.heatmap.event.IProcessorResultListener;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEventType;
import org.jdesktop.swingx.painter.AbstractPainter;

/**
 *
 * @author nilshoffmann
 */
public class SelectionRectanglePainter<T, U extends JComponent> extends AbstractPainter<U>
        implements IProcessorResultListener<Rectangle2D> {

    private Rectangle2D selection = null;
    private HeatmapDataset<T> hm;
    private List<Tuple2D<Point2D, Annotation<T>>> selectedAnnotations;

    public SelectionRectanglePainter(HeatmapDataset<T> hm) {
        this.hm = hm;
    }

    public void setSelection(Rectangle2D r) {
        Rectangle2D oldSelection = this.selection;
        this.selection = r;//at.createTransformedShape(r).getBounds2D();
        setDirty(true);
        firePropertyChange("selection", oldSelection, r);
    }

    public Rectangle2D getSelection() {
        return this.selection;
    }

    @Override
    protected void doPaint(Graphics2D gd, JComponent t, int width, int height) {
        if (selection != null) {
//            System.out.println(getClass().getName() + ": Doing paint");
            Paint fill = new Color(255, 255, 255, 128);
            Paint border = new Color(128, 128, 128, 192);
            gd.setPaint(fill);
            gd.fill(selection);
            gd.setPaint(border);
            gd.draw(selection);
        }
    }

    @Override
    public void listen(Rectangle2D t, MouseEvent et) {
        if (et.getMet() == MouseEventType.DRAGGED) {
            try {
                setSelection(hm.toModelShape(t).getBounds2D());
                for (Tuple2D<Point2D, Annotation<T>> tpl : hm.getChildrenInRange(
                        getSelection())) {
                    tpl.getSecond().setSelected(true);
                }
            } catch (NoninvertibleTransformException ex) {
                Logger.getLogger(SelectionRectanglePainter.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        } else if (et.getMet() == MouseEventType.RELEASED) {
            setSelection(null);
        } else {
            try {
                Shape s = hm.toModelShape(t);
                for (Tuple2D<Point2D, Annotation<T>> tpl : hm.getChildrenInRange(s.
                        getBounds2D())) {
                    tpl.getSecond().setSelected(false);
                }
            } catch (NoninvertibleTransformException ex) {
                Logger.getLogger(SelectionRectanglePainter.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
            setSelection(null);
        }
    }
}
