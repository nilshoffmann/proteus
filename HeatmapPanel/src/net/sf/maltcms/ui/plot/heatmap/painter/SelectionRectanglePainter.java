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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
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
public class SelectionRectanglePainter<T, U extends JComponent> extends AbstractPainter<U>
        implements IProcessorResultListener<Rectangle2D> {

    private Rectangle2D selection = null;
    private HeatmapDataset<T> hm;
    private List<Tuple2D<Point2D, IAnnotation<T>>> selectedAnnotations;

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
        if (et.getMe().isShiftDown() && et.getMe().getID()==java.awt.event.MouseEvent.MOUSE_DRAGGED) {
            try {
                setSelection(hm.toModelShape(t).getBounds2D());
                if (selectedAnnotations != null) {
                    for (Tuple2D<Point2D, IAnnotation<T>> tpl : selectedAnnotations) {
                        tpl.getSecond().setSelected(false);
                    }
                }
                selectedAnnotations = hm.getChildrenInRange(getSelection());
                for (Tuple2D<Point2D, IAnnotation<T>> tpl : selectedAnnotations) {
                    tpl.getSecond().setSelected(true);
                }
            } catch (NoninvertibleTransformException ex) {
                Logger.getLogger(SelectionRectanglePainter.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        } else if(et.getMe().isShiftDown() && et.getMe().getID()==java.awt.event.MouseEvent.MOUSE_RELEASED) {
            
        } else {// if (et.getMet() == MouseEventType.RELEASED) {
            if (selectedAnnotations != null) {
                for (Tuple2D<Point2D, IAnnotation<T>> tpl : selectedAnnotations) {
                    tpl.getSecond().setSelected(false);
                }
                selectedAnnotations = null;
            }
            setSelection(null);
        }
    }
}
