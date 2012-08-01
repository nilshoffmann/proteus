/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.AnnotationManager;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.GelSpotAnnotations;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Collection;
import net.sf.maltcms.chromaui.ui.support.api.AProgressAwareRunnable;
import net.sf.maltcms.ui.plot.heatmap.IAnnotation;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

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
                Collection<? extends IGel> gels = project.retrieve(IGel.class);
                getProgressHandle().start(gels.size()+1);
                int cnt = 0;
                getProgressHandle().progress("Deleting old visual spot annotations!", cnt++);
                Collection<IAnnotation> oldAnnotations = project.retrieve(IAnnotation.class);
//                for (IAnnotation annotation : oldAnnotations) {
//                    project.delete(annotation);
//                }
                project.delete(oldAnnotations.toArray());
                
                Collection<SpotAnnotation> oldSpotAnnotations = project.retrieve(SpotAnnotation.class);
//                for (SpotAnnotation annotation : oldSpotAnnotations) {
//                    project.delete(annotation);
//                }
                project.delete(oldSpotAnnotations.toArray());
                
                Collection<GelSpotAnnotations> oldGelSpotAnnotations = project.retrieve(GelSpotAnnotations.class);
//                for (GelSpotAnnotations annotation : oldGelSpotAnnotations) {
//                    project.delete(annotation);
//                }
                project.delete(oldGelSpotAnnotations.toArray());
                
                System.out.println("Finished deletion of old visual spot annotations!");
                System.out.println("Recreating new ones!");
                for (IGel iGel : gels) {
                    getProgressHandle().progress("Processing gel "+iGel.getName(), cnt++);
                    AnnotationManager.addAnnotations(project, iGel);
                }
                OpenProjects.getDefault().close(new Project[]{project});
                OpenProjects.getDefault().open(new Project[]{project},false,true);
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            } finally {
                getProgressHandle().finish();
            }
        }
    }
}
