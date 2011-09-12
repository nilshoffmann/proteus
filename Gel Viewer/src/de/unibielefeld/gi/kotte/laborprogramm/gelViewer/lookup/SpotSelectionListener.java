/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.lookup;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.awt.geom.Point2D;
import java.util.Collection;
import net.sf.maltcms.ui.plot.heatmap.painter.AnnotationPainter;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;

/**
 *
 * @author nilshoffmann
 */
public class SpotSelectionListener extends AbstractLookupListener<ISpot> {

    public SpotSelectionListener(Class<? extends ISpot> typeToListenFor,
            Lookup contentProvider) {
            super(typeToListenFor, contentProvider);
    }

    public SpotSelectionListener(Class<? extends ISpot> typeToListenFor) {
        super(typeToListenFor);
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Collection<? extends ISpot> spots = getResult().allInstances();
        for(ISpot spot:spots) {
            AnnotationPainter ap = getContentProviderLookup().lookup(AnnotationPainter.class);
            Point2D.Double p = new Point2D.Double(spot.getPosX(),spot.getPosY());
            ap.setActivePoint(p);
            ap.selectAnnotation();
            break;
        }
    }

}
