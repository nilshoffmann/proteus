package de.unibielefeld.gi.kotte.laborprogramm.ImportWizard;

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
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Wizard Panel class for the Project creation wizard.
 *
 * @author kotte
 */
public class ImportWizardPanel1 implements WizardDescriptor.ValidatingPanel, PropertyChangeListener {

    private WizardDescriptor descriptor;
    private ImportVisualPanel1 component;
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1);

    @Override
    public Component getComponent() {
        if (component == null) {
            component = new ImportVisualPanel1();
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
        if (component.getProjectName().isEmpty()) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please pick a project name.");
            return false;
        }
        if (component.getProjectParentDirectoryFile() == null) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please choose a project directory.");
            return false;
        }
        //System.out.println(component.getProjectDirectory());
        File[] kids = component.getProjectDirectory().listFiles();
        if (component.getProjectDirectory().exists() && kids != null && kids.length > 0) {
            // Folder exists and is not empty
            descriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE,
                    "Project folder already exists and is not empty.");
            return false;
        }
        File baseDirectoryFile = component.getBaseDirectoryFile();
        if (baseDirectoryFile == null) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please choose a Delta2D project directory.");
            return false;
        }
        String projectDataPath = baseDirectoryFile.getAbsolutePath() + File.separator + "projects" + File.separator + "projects.xml";
        File projectDataFile = new File(projectDataPath);
        if (!projectDataFile.exists()) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, projectDataPath + " does not exist.");
            return false;
        }
        String gelDataPath = baseDirectoryFile.getAbsolutePath() + File.separator + "gelImages" + File.separator + "gelImages.xml";
        File gelDataFile = new File(gelDataPath);
        if (!gelDataFile.exists()) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, gelDataPath + " does not exist.");
            return false;
        }
        if (component.getExcelDataFile() == null) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please choose Excel report file.");
            return false;
        }
        //wenn Werte vollstaendig, Infomeldung zuruecksetzen
        descriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, null);
        return true;
    }

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

    @Override
    public void readSettings(Object settings) {
        this.descriptor = (WizardDescriptor) settings;
    }

    @Override
    public void storeSettings(Object settings) {
        ((WizardDescriptor) settings).putProperty(ImportVisualPanel1.PROPERTY_BASE_DIRECTORY, ((ImportVisualPanel1) getComponent()).getBaseDirectoryFile());
        ((WizardDescriptor) settings).putProperty(ImportVisualPanel1.PROPERTY_PROJECT_PARENT_DIRECTORY, ((ImportVisualPanel1) getComponent()).getProjectParentDirectoryFile());
        ((WizardDescriptor) settings).putProperty(ImportVisualPanel1.PROPERTY_PROJECT_NAME, ((ImportVisualPanel1) getComponent()).getProjectName());
        ((WizardDescriptor) settings).putProperty(ImportVisualPanel1.PROPERTY_EXCEL_DATA_FILE, ((ImportVisualPanel1) getComponent()).getExcelDataFile());
    }

    @Override
    public void validate() throws WizardValidationException {
        isValid();
        fireChangeEvent();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        isValid();
        fireChangeEvent();
    }
}
