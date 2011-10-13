/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.lookup;

import cross.datastructures.tuple.Tuple2D;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.List;
import net.sf.maltcms.chromaui.lookupResultListener.api.AbstractLookupResultListener;
import net.sf.maltcms.ui.plot.heatmap.Annotation;
import net.sf.maltcms.ui.plot.heatmap.painter.AnnotationPainter;
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
                IGel gel = getContentProviderLookup().lookup(IGel.class);
                //this is our own spot
                if (gel.equals(spot.getGel())) {
                    //let's find the one that belongs to us
                    if (ap != null) {
                        ISpot privateActiveSpot = getContentProviderLookup().lookup(ISpot.class);
                        //we have no active spot/annotation
                        if (privateActiveSpot == null) {
                        } else {//we have an active spot
                            //try to retrieve the annotation for the spot
                            Annotation ann = ap.getAnnotation(spot);
                            //annotation is present in painter
                            if (ann != null) {
                                List<Tuple2D<Point2D, Annotation>> annotations = ap.getAnnotations();
                                for (Tuple2D<Point2D, Annotation> t : annotations) {
                                    t.getSecond().setSelected(false);
                                }
                            }
                            ap.setActivePoint(new Point2D.Double(privateActiveSpot.getPosX(), privateActiveSpot.getPosY()));
                            ap.selectAnnotation();
                        }
                    } else {
                        System.out.println("Annotation Painter is null!");
                    }
                }

                break;
            }
        }
    }
}
