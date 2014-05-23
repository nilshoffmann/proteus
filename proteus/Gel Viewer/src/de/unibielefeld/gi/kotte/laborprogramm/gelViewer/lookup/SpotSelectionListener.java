/*
 *  Copyright (C) 2008-2012 Nils Hoffmann
 *  Nils.Hoffmann A T CeBiTec.Uni-Bielefeld.DE
 *
 *  This file is part of Maui.
 *
 *  Maui is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Maui is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Maui.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.lookup;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.awt.geom.Point2D;
import java.util.Collection;
import net.sf.maltcms.chromaui.lookupResultListener.api.AbstractLookupResultListener;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;
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
                HeatmapDataset hd = getContentProviderLookup().lookup(HeatmapDataset.class);
//                ToolTipPainter tp = getContentProviderLookup().lookup(ToolTipPainter.class);
                IGel gel = getContentProviderLookup().lookup(IGel.class);
                //this is our own spot
                if (gel!= null && gel.equals(spot.getGel())) {
                    System.out.println("Spot is on gel");
                    //let's find the one that belongs to us
                    if (ap != null && hd!=null) {
                        System.out.println("Annotation painter is not null");
                        ISpot privateActiveSpot = getContentProviderLookup().lookup(ISpot.class);
                        Point2D oldPoint = null;
                        if (privateActiveSpot != null) {//we have an active spot
                            System.out.println("Active spot is not null");
                            oldPoint = new Point2D.Double(privateActiveSpot.getPosX(), privateActiveSpot.getPosY());
                        }
                        Point2D newPoint = new Point2D.Double(spot.getPosX(), spot.getPosY());
//                            try {
                        ap.selectAnnotation(hd.toViewPoint(newPoint));
//                        Point2D newPoint = new Point2D.Double(spot.getPosX(), spot.getPosY());
//                        ap.propertyChange(new PropertyChangeEvent(this, "point", oldPoint, newPoint));
                    } else {
                        System.out.println("Annotation Painter is null!");
                    }
                }

                break;
            }
        }
    }
}
