/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap.painter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;
import org.jdesktop.swingx.painter.AbstractPainter;

/**
 * FIXME resolve shift + mouse click on annotation, shift + mouse move etc. behaviour
 * @author nilshoffmann
 */
public class LocationRectanglePainter<T, U extends JComponent> extends AbstractPainter<U>
        implements PropertyChangeListener {

    private HeatmapDataset<T> hm;
    private boolean drawLocationIndicator = true;

    public LocationRectanglePainter(HeatmapDataset<T> hm) {
        setCacheable(false);
        this.hm = hm;
        this.hm.addPropertyChangeListener(this);
    }

    public void setDrawLocationIndicator(boolean b) {
        boolean old = this.drawLocationIndicator;
        this.drawLocationIndicator = b;
        setDirty(true);
        firePropertyChange("DRAWLOCATIONINDICATOR", old, this.drawLocationIndicator);
    }

    @Override
    protected void doPaint(Graphics2D g, JComponent t, int width, int height) {
        if (drawLocationIndicator) {
            Rectangle2D databounds = hm.getDataBounds();
            Rectangle2D visibleRect = t.getVisibleRect();

            AffineTransform scale = AffineTransform.getScaleInstance(0.02, 0.02);
            Shape frame = scale.createTransformedShape(databounds);
            Shape fill;
            try {
                fill = scale.createTransformedShape(hm.getTransform().
                        createInverse().createTransformedShape(visibleRect));
                Color c = Color.ORANGE;
                Color d = Color.DARK_GRAY;
                AlphaComposite ac = AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, 0.7f);
                Composite oldComp = g.getComposite();
                Color bg = g.getBackground();
                Color fg = g.getColor();
                AffineTransform orig = g.getTransform();
                g.setComposite(ac);
                g.setTransform(
                        AffineTransform.getTranslateInstance(
                        t.getVisibleRect().x + t.getVisibleRect().width - frame.
                        getBounds2D().getWidth() - 10, t.getVisibleRect().y + 10));
                g.setColor(c);
                g.fill(fill);
                g.setColor(d);
                g.draw(frame);

                g.setTransform(orig);
                g.setComposite(oldComp);
                g.setBackground(bg);
                g.setColor(fg);
            } catch (NoninvertibleTransformException ex) {
                Logger.getLogger(LocationRectanglePainter.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * @param pce
     */
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        setDirty(true);
    }
}
