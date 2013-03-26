package de.unibielefeld.gi.kotte.laborprogramm.pathway.sbml;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathwayProject;
import java.awt.BorderLayout;
import javax.swing.SwingUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Top component which displays IPathwayProjects.
 *
 * @author kotte
 */
@ConvertAsProperties(
    dtd = "-//de.unibielefeld.gi.kotte.laborprogramm.pathways.visualization//PathwayExplorer//EN",
autostore = false)
@TopComponent.Description(
    preferredID = "PathwayExplorerTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "properties", openAtStartup = false)
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@ActionID(category = "Window", id = "de.unibielefeld.gi.kotte.laborprogramm.pathways.visualization.PathwayExplorerTopComponent")
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_PathwayExplorerAction",
preferredID = "PathwayExplorerTopComponent")
@Messages({
    "CTL_PathwayExplorerAction=Pathway Explorer",
    "CTL_PathwayExplorerTopComponent=Pathway Explorer",
    "HINT_PathwayExplorerTopComponent=This is a Pathway Explorer Window"
})
public final class PathwayExplorerTopComponent extends TopComponent implements LookupListener {

    private boolean initialized = false;
	private Lookup.Result<IPathwayProject> result;

    public PathwayExplorerTopComponent() {
        initComponents();
        setName(Bundle.CTL_PathwayExplorerTopComponent());
        setToolTipText(Bundle.HINT_PathwayExplorerTopComponent());
    }

    public void openProject(final IPathwayProject project) {
		final PathwayExplorerTopComponent tc = this;
        if (!initialized) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    PathwayDisplay display = new PathwayDisplay(project);
                    setDisplayName("Pathway Explorer of project " + project.getName());
                    setToolTipText("Model Id: " + project.getDocument().getSBMLDocument().getModel().getId());
                    open();
                    add(display, BorderLayout.CENTER);
                    requestActive();
                    initialized = true;
					result.removeLookupListener(tc);
                }
            });
        } else {
            throw new IllegalStateException("Pathway Explorer was already initialized with an SBML File!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
		result = Utilities.actionsGlobalContext().lookupResult(IPathwayProject.class);
		result.addLookupListener(this);
//        IPathwayProject project = Utilities.actionsGlobalContext().lookup(IPathwayProject.class);
//        if(project!=null) {
//			openProject(project);
//		}
    }

    @Override
    public void componentClosed() {
		if(result!=null) {
			result.removeLookupListener(this);
		}
//        SBMLDocument doc = getLookup().lookup(SBMLDocument.class);
//        if (doc != null) {
//            Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().closeTopComponentsFor(doc);
//        }
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public void resultChanged(LookupEvent le) {
		if(!result.allInstances().isEmpty()) {
			System.out.println("Received pathway project from lookup!");
			openProject(result.allInstances().iterator().next());
		}
    }
}
