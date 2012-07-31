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
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations;

import com.db4o.collections.ActivatableArrayList;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.sf.maltcms.ui.plot.heatmap.Annotation;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;
import net.sf.maltcms.ui.plot.heatmap.HeatmapPanel;

/**
 *
 * @author hoffmann
 */
public class AnnotationManager {

    private IProteomicProject project;
    private IGel gel;
    private HeatmapDataset<ISpot> hmd;
    private HeatmapPanel<ISpot> hp;
    private GelSpotAnnotations gelSpotAnnotation = null;

    public AnnotationManager(IProteomicProject project, IGel gel, HeatmapDataset<ISpot> hmd, HeatmapPanel<ISpot> hp) {
        this.project = project;
        this.gel = gel;
        this.hmd = hmd;
        this.hp = hp;
    }

    public static GelSpotAnnotations addAnnotations(IProteomicProject project, IGel gel) {
        List<SpotAnnotation> spotAnnotations = new ActivatableArrayList<SpotAnnotation>();
        for (ISpot spot : gel.getSpots()) {
            SpotAnnotation ann = new SpotAnnotation(new Point2D.Double(spot.getPosX(), spot.getPosY()), spot);
            spotAnnotations.add(ann);
            project.store(ann);
        }
        project.store(spotAnnotations);
        GelSpotAnnotations gsa = new GelSpotAnnotations();
        gsa.setGel(gel);
        gsa.setSpotAnnotations(spotAnnotations);
        project.store(gsa);
        return gsa;
    }

    public static Collection<GelSpotAnnotations> getAnnotations(IProteomicProject project) {
        try {
            return project.retrieve(GelSpotAnnotations.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }

    }

    public static GelSpotAnnotations getAnnotationsForGel(IProteomicProject project, IGel gel) {
        for (GelSpotAnnotations gsa : getAnnotations(project)) {
            if (gsa.getGel().getId().equals(gel.getId())){
                System.out.println("Found existing GelSpotAnnotations for gel " + gel.getFilename());
                return gsa;
            }
        }
        return null;
    }

    public static SpotAnnotation getSpotAnnotation(IProteomicProject project, ISpot spot) {
        GelSpotAnnotations annotations = getAnnotationsForGel(project, spot.getGel());
        if (annotations == null) {
            return null;
        }
        for (Annotation ann : annotations.getSpotAnnotations()) {
            SpotAnnotation sann = (SpotAnnotation) ann;
            if (sann.getPayload() == spot) {
                return sann;
            }
        }
        return null;
    }

    public void open() {
//        Collection<AnnotationsResetMarker> c = project.retrieve(AnnotationsResetMarker.class);
//        if(c.isEmpty()) {
//            System.out.println("Resetting annotations");
//            //reset annotations
//            Collection<GelSpotAnnotations> gelSpotAnnotations = getAnnotations(project);
//            for(GelSpotAnnotations gsa:gelSpotAnnotations) {
//                for(SpotAnnotation sa:gsa.getSpotAnnotations()){
//                    project.delete(sa);
//                }
//                project.delete(gsa);
//            }
//            System.out.println("Storing annotations reset marker!");
//            AnnotationsResetMarker ars = new AnnotationsResetMarker();
//            ars.setUpdated(true);
//            project.store(ars);
//        }
        Collection<GelSpotAnnotations> gelSpotAnnotations = getAnnotations(project);
        if (gelSpotAnnotations.isEmpty()) {
            System.out.println("Did not find any spot GelSpotAnnotations in database!");
            gelSpotAnnotation = addAnnotations(project, gel);
        } else {
            gelSpotAnnotation = getAnnotationsForGel(project, gel);
            if (gelSpotAnnotation == null) {
                System.out.println("Could not find existing GelSpotAnnotations for gel " + gel.getFilename());
                gelSpotAnnotation = addAnnotations(project, gel);
            }
        }

        try {
            boolean updated = false;
            for (Annotation ann : gelSpotAnnotation.getSpotAnnotations()) {
                SpotAnnotation annotation = (SpotAnnotation) ann;
                ISpot spot = annotation.getPayload();
                if (spot == null) {
                    System.err.println("Spot for annotation: " + annotation + " was null!");
                } else {
                    if (annotation.getShape()==null) {
                        System.err.println("Shape for annotation was null, setting default rectangle!");
                        Shape s = annotation.getPayload().getShape();
                        if(s==null) {
                            annotation.setShape(new Rectangle2D.Double(spot.getPosX() - 10, spot.getPosX() - 10, 20, 20));
                        }else{
                            annotation.setShape(s);
                        }
                        annotation.setDrawSpotBox(true);
                        updated = true;
                    }
                    spot.addPropertyChangeListener(hp);
                    annotation.addPropertyChangeListener(hp);
                    hmd.addAnnotation(new Point2D.Double(spot.getPosX(), spot.getPosY()), annotation);
                }
            }
            if(updated) {
                project.store(gelSpotAnnotation);
            }
        } catch (java.lang.IllegalStateException ise) {
            ise.printStackTrace();
        }
    }

    public void close() {
    }
}
