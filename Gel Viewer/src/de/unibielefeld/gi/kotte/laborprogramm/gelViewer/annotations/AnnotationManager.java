/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations;

import com.db4o.collections.ActivatableArrayList;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collection;
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

    protected GelSpotAnnotations addAnnotations() {
        List<SpotAnnotation> spotAnnotations = new ActivatableArrayList<SpotAnnotation>();
        for (ISpot spot : gel.getSpots()) {
            SpotAnnotation ann = new SpotAnnotation(new Point2D.Double(spot.getPosX(), spot.getPosY()), spot);
            spotAnnotations.add(ann);
        }
        GelSpotAnnotations gsa = new GelSpotAnnotations();
        gsa.setGel(gel);
        gsa.setSpotAnnotations(spotAnnotations);
        project.persist(Arrays.asList(gsa));
        return gsa;
    }

    public void open() {

        Collection<GelSpotAnnotations> gelSpotAnnotations = project.retrieve(GelSpotAnnotations.class);

        if (gelSpotAnnotations.isEmpty()) {
            System.out.println("Did not find any spot GelSpotAnnotations in database!");
            gelSpotAnnotation = addAnnotations();
        } else {
            for (GelSpotAnnotations gsa : gelSpotAnnotations) {
                if (gsa.getGel().getFilename().equals(gel.getFilename())) {
                    System.out.println("Found existing GelSpotAnnotations for gel " + gel.getFilename());
                    gelSpotAnnotation = gsa;
                }
            }
            if (gelSpotAnnotation == null) {
                System.out.println("Could not find existing GelSpotAnnotations for gel " + gel.getFilename());
                gelSpotAnnotation = addAnnotations();
            }
        }

        for (Annotation ann : gelSpotAnnotation.getSpotAnnotations()) {
            SpotAnnotation annotation = (SpotAnnotation) ann;
            ISpot spot = annotation.getPayload();
            if(spot==null) {
                System.err.println("Spot for annotation: "+annotation+" was null!");
            }
            spot.addPropertyChangeListener(hp);
            annotation.addPropertyChangeListener(hp);
            hmd.addAnnotation(new Point2D.Double(spot.getPosX(), spot.getPosY()), annotation);
        }
    }

    public void close() {
    }
}
