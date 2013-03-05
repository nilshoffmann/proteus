package de.unibielefeld.gi.kotte.laborprogramm.delta2DImportWizard;

import de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter.ProjectBuilder;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Wizard Panel for the Delta2D project import.
 * 
 * @author kotte
 */
public class ProjectImportWizardPanel1 implements WizardDescriptor.Panel, PropertyChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private ProjectImportVisualPanel1 component;
    private WizardDescriptor descriptor;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public Component getComponent() {
        if (component == null) {
            component = new ProjectImportVisualPanel1();
            component.addPropertyChangeListener(this);
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx(SampleWizardPanel1.class);
    }

    @Override
    public boolean isValid() {
        if (descriptor == null) {
            return false;
        }
        //Infomeldungen setzen fuer noch nicht eingegebene Werte
        if (component.getProjectDirectory() == null) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please pick a Delta2D project directory.");
            return false;
        }
        if (component.getSelectedProject() == null) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please pick a Delta2D project to import.");
            return false;
        }
        if (component.getProjectName().isEmpty()) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please pick a Proteus project name.");
            return false;
        }
        if (component.getProjectParentDirectoryFile() == null) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please choose a Proteus project directory.");
            return false;
        }
        File f = new File(component.getProjectParentDirectoryFile(),component.getProjectName());
        File[] kids = f.listFiles();
        if (f.exists() && kids != null && kids.length > 0) {
            // Folder exists and is not empty
            descriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE,
                    "Project folder already exists and is not empty.");
            return false;
        }
        //wenn Werte vollstaendig, Infomeldung zuruecksetzen
        descriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, null);
        return true;
    }

    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

    @Override
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    @Override
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    protected final void fireChangeEvent() {
        Iterator<ChangeListener> it;
        synchronized (listeners) {
            it = new HashSet<ChangeListener>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            it.next().stateChanged(ev);
        }
    }

    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    @Override
    public void readSettings(Object settings) {
        this.descriptor = (WizardDescriptor) settings;
		if(descriptor.getProperty(ProjectImportVisualPanel1.PROPERTY_PROJECT_PARENT_DIRECTORY)==null) {
			File proteusProjects = new File(System.getProperty("user.home"),"ProteusProjects");
			proteusProjects.mkdirs();
			descriptor.putProperty(ProjectImportVisualPanel1.PROPERTY_PROJECT_PARENT_DIRECTORY, proteusProjects);
			((ProjectImportVisualPanel1)getComponent()).setProjectParentDirectoryFile(proteusProjects);
		}
    }

    @Override
    public void storeSettings(Object settings) {
        ((WizardDescriptor) settings).putProperty(ProjectImportVisualPanel1.PROPERTY_PROJECT_DIRECTORY,
                ((ProjectImportVisualPanel1) getComponent()).getProjectDirectory());
        ((WizardDescriptor) settings).putProperty(ProjectImportVisualPanel1.PROPERTY_PROJECT_PARENT_DIRECTORY,
                ((ProjectImportVisualPanel1) getComponent()).getProjectParentDirectoryFile());
        ((WizardDescriptor) settings).putProperty(ProjectImportVisualPanel1.PROPERTY_PROJECT_NAME,
                ((ProjectImportVisualPanel1) getComponent()).getProjectName());
        ((WizardDescriptor) settings).putProperty(ProjectImportVisualPanel1.PROPERTY_SELECTED_PROJECT_NAME,
                ((ProjectImportVisualPanel1) getComponent()).getSelectedProject());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        isValid();
        //get project list
        if(evt.getPropertyName().equals(ProjectImportVisualPanel1.PROPERTY_PROJECT_DIRECTORY)) {
            ProjectBuilder pb = new ProjectBuilder();
            File projectDataFile = new File(((File) evt.getNewValue()).getAbsolutePath()
                + File.separator + "projects" + File.separator + "projects.xml");
            component.setProjectNamesList(pb.getProjectNames(projectDataFile));
        }
        
        fireChangeEvent();
    }
}
