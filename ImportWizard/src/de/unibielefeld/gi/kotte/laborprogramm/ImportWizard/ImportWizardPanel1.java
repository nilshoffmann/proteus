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

public class ImportWizardPanel1 implements WizardDescriptor.ValidatingPanel, PropertyChangeListener {

    private File baseDirectoryFile;
    private File projectDirectoryFile;
    private File projectDataFile;
    private File excelDataFile;
    private File gelDataFile;
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
        if (component.getProjectName().isEmpty()) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Bitte Projektnamen angeben.");
            return false;
        }

        baseDirectoryFile = component.getBaseDirectoryFile();
        projectDirectoryFile = component.getProjectDirectoryFile();
        projectDataFile = component.getProjectDataFile();
        excelDataFile = component.getExcelDataFile();
        gelDataFile = component.getGelDataFile();

        if (baseDirectoryFile == null) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Bitte Basisverzeichnis auswaehlen.");
            return false;
        }
//        if (projectDirectoryFile == null) {
//            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Bitte Projektverzeichnis auswaehlen.");
//            return false;
//        }
        if (gelDataFile == null) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Bitte Gel XML-Datei auswaehlen.");
            return false;
        }
        if (projectDataFile == null) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Bitte Projekt XML-Datei auswaehlen.");
            return false;
        }
        if (excelDataFile == null) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Bitte Excel Export Datei auswaehlen.");
            return false;
        }
        //wenn kein Fehler mehr auftritt, Fehlermeldung zuruecksetzen
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
        ((WizardDescriptor) settings).putProperty(ImportVisualPanel1.PROPERTY_PROJECT_DIRECTORY, ((ImportVisualPanel1) getComponent()).getProjectDirectoryFile());
        ((WizardDescriptor) settings).putProperty(ImportVisualPanel1.PROPERTY_PROJECT_DATA_FILE, ((ImportVisualPanel1) getComponent()).getProjectDataFile());
        ((WizardDescriptor) settings).putProperty(ImportVisualPanel1.PROPERTY_EXCEL_DATA_FILE, ((ImportVisualPanel1) getComponent()).getExcelDataFile());
        ((WizardDescriptor) settings).putProperty(ImportVisualPanel1.PROPERTY_GEL_DATA_FILE, ((ImportVisualPanel1) getComponent()).getGelDataFile());
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
