package de.unibielefeld.gi.kotte.laborprogramm.pathway.wizard;

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

public class SBMLIMportWizardPanel1 implements WizardDescriptor.ValidatingPanel, PropertyChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private SBMLIMportVisualPanel1 component;
    private WizardDescriptor descriptor;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public SBMLIMportVisualPanel1 getComponent() {
        if (component == null) {
            component = new SBMLIMportVisualPanel1();
            component.addPropertyChangeListener(this);
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    @Override
    public boolean isValid() {
        if (descriptor == null) {
            return false;
        }
        //Infomeldungen setzen fuer noch nicht eingegebene Werte
        if (component.getProjectName().isEmpty()) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please pick a Pathway Project name.");
            return false;
        }
        File f = component.getSBMLFile();
        if (!f.canRead()) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Can't read file.");
            return false;
        }
        if (component.getOrganism() == null) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please select an Organism.");
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

    @Override
    public void readSettings(Object settings) {
        this.descriptor = (WizardDescriptor) settings;
    }

    @Override
    public void storeSettings(Object settings) {
        ((WizardDescriptor) settings).putProperty(SBMLIMportVisualPanel1.PROPERTY_PROJECT_NAME,
                ((SBMLIMportVisualPanel1) getComponent()).getProjectName());
        ((WizardDescriptor) settings).putProperty(SBMLIMportVisualPanel1.PROPERTY_FILE,
                ((SBMLIMportVisualPanel1) getComponent()).getSBMLFile());
        ((WizardDescriptor) settings).putProperty(SBMLIMportVisualPanel1.PROPERTY_ORGANISM,
                ((SBMLIMportVisualPanel1) getComponent()).getOrganism());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        fireChangeEvent();
    }

    @Override
    public void validate() throws WizardValidationException {
//        isValid();
        //Infomeldungen setzen fuer noch nicht eingegebene Werte
        if (component.getProjectName().isEmpty()) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please enter a Pathway Project name.");
            throw new WizardValidationException(component, "Please enter a Pathway Project name.", "Please enter a Pathway Project name.");
        }
        File f = component.getSBMLFile();
        if (!f.canRead()) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Can't read file.");
            throw new WizardValidationException(component, "Can't read sbml file at " + f.getAbsolutePath(), "Can't read sbml file at " + f.getAbsolutePath());
        }
        if (component.getOrganism() == null) {
//            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please select an Organism.");
            throw new WizardValidationException(component, "Please select an Organism", "Please select an Organism");
        }
        //wenn Werte vollstaendig, Infomeldung zuruecksetzen
        descriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, null);
    }
}
