/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.lookup;

import cross.datastructures.tuple.Tuple2D;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.List;
import net.sf.maltcms.chromaui.lookupResultListener.api.AbstractLookupResultListener;
import net.sf.maltcms.ui.plot.heatmap.Annotation;
import net.sf.maltcms.ui.plot.heatmap.painter.AnnotationPainter;
import net.sf.maltcms.ui.plot.heatmap.painter.ToolTipPainter;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;

/**
 *
 * @author nilshoffmann
 */
public class SpotSelectionListener extends AbstractLookupResultListener<ISpot> {

    public SpotSelectionListener(Class<? extends ISpot> typeToListenFor,
            Lookup contentProvider) {
        super(typeToListenFor, contentProvider);
    }

    public SpotSelectionListener(Class<? extends ISpot> typeToListenFor) {
        super(typeToListenFor);
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        if (getResult() != null) {
            //receive spots from external lookup
            Collection<? extends ISpot> spots = getResult().allInstances();
            //these are all spots of a spot group
            for (ISpot spot : spots) {
                //retrieve annotation painter from private lookup
                AnnotationPainter ap = getContentProviderLookup().lookup(AnnotationPainter.class);
//                ToolTipPainter tp = getContentProviderLookup().lookup(ToolTipPainter.class);
                IGel gel = getContentProviderLookup().lookup(IGel.class);
                //this is our own spot
                if (gel.equals(spot.getGel())) {
                    System.out.println("Spot is on gel");
                    //let's find the one that belongs to us
                    if (ap != null) {
                        System.out.println("Annotation painter is not null");
                        ISpot privateActiveSpot = getContentProviderLookup().lookup(ISpot.class);
                        Point2D oldPoint = null;
                        if (privateActiveSpot != null) {//we have an active spot
                            System.out.println("Active spot is not null");
                            oldPoint = new Point2D.Double(privateActiveSpot.getPosX(), privateActiveSpot.getPosY());
                        }
                        Point2D newPoint = new Point2D.Double(spot.getPosX(), spot.getPosY());
                        ap.propertyChange(new PropertyChangeEvent(this, "point", oldPoint, newPoint));
                    } else {
                        System.out.println("Annotation Painter is null!");
                    }
                }

                break;
            }
        }
    }
}
