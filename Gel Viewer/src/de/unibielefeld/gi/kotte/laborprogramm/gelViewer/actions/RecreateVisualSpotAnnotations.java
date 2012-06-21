/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.AnnotationManager;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.AnnotationsResetMarker;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.GelSpotAnnotations;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Collection;
import net.sf.maltcms.chromaui.ui.support.api.AProgressAwareRunnable;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

@ActionID(category = "ProteomicProject",
id = "de.unibielefeld.gi.kotte.laborprogramm.gelViewer.actions.RecreateVisualSpotAnnotations")
@ActionRegistration(displayName = "#CTL_RecreateVisualSpotAnnotations")
@ActionReferences({})
@Messages("CTL_RecreateVisualSpotAnnotations=Recreate Visual Spot Annotations")
public final class RecreateVisualSpotAnnotations implements ActionListener {

    private final IProteomicProject context;

    public RecreateVisualSpotAnnotations(IProteomicProject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        RecreateVisualSpotAnnotationsTask task = new RecreateVisualSpotAnnotationsTask(context);
        RecreateVisualSpotAnnotationsTask.createAndRun("Recreating Visual Spot Annotations", task);
    }

    protected class RecreateVisualSpotAnnotationsTask extends AProgressAwareRunnable {

        private final IProteomicProject project;

        public RecreateVisualSpotAnnotationsTask(IProteomicProject project) {
            this.project = project;
        }

        @Override
        public void run() {
            try {
                getProgressHandle().setDisplayName("Recreating Visual Spot Annotations");
                IProteomicProject ipp = Utilities.actionsGlobalContext().lookup(IProteomicProject.class);
                Collection<? extends IGel> gels = project.retrieve(IGel.class);
                getProgressHandle().start(gels.size());
                int cnt = 0;
                for (IGel iGel : gels) {
                    getProgressHandle().progress("Processing gel "+iGel.getName(), cnt++);
                    System.out.println("Deleting old annotations for " + iGel.getName());
                    Collection<GelSpotAnnotations> gelSpotAnnotations = AnnotationManager.getAnnotations(ipp);
                    for (GelSpotAnnotations gsa : gelSpotAnnotations) {
                        for (SpotAnnotation sa : gsa.getSpotAnnotations()) {
                            ipp.delete(sa);
                        }
                        ipp.delete(gsa);
                    }
                    System.out.println("Recreating annotations!");
                    AnnotationManager.addAnnotations(project, iGel);
                }
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            } finally {
                getProgressHandle().finish();
            }
        }
    }
}
