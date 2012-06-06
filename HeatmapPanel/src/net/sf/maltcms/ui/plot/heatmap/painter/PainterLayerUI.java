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

import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;
import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.BufferedLayerUI;
import org.jdesktop.swingx.painter.CompoundPainter;

/**
 *
 * @author nilshoffmann
 */
public class PainterLayerUI<T extends JComponent> extends BufferedLayerUI<T> {

    private final CompoundPainter<T> cp;
    private Rectangle r = null;

    public PainterLayerUI(CompoundPainter<T> cp) {
        if (cp == null) {
            throw new NullPointerException("CompoundPainter was null!");
        }
        this.cp = cp;
        this.cp.setCheckingDirtyChildPainters(true);
        this.cp.setCacheable(false);
    }

    @Override
    protected void paintLayer(Graphics2D g2, JXLayer<? extends T> l) {
        super.paintLayer(g2, l);
        this.cp.paint(g2, l.getView(), l.getWidth(), l.getHeight());
    }
}
