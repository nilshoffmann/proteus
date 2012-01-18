package de.unibielefeld.gi.kotte.laborprogramm.spotTableExporter;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Wizard Panel for the Spot Data Export.
 *
 * @author kotte
 */
public class ExportOptionsWizardPanel1 implements WizardDescriptor.ValidatingPanel, PropertyChangeListener {

    private WizardDescriptor descriptor;
    private ExportOptionsVisualPanel1 component;
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1);

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public Component getComponent() {
        if (component == null) {
            component = new ExportOptionsVisualPanel1();
            component.addPropertyChangeListener(this);
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public boolean isValid() {
        if (descriptor == null) {
            return false;
        }
        //Werte ueberpruefen
        if (!(component.isSpotGroupId() || component.isIdentificationName())) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Bitte mindestens eine Spalte auswaehlen.");
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
        ((WizardDescriptor) settings).putProperty(ExportOptionsVisualPanel1.PROPERTY_SPOT_GROUP_ID, ((ExportOptionsVisualPanel1) getComponent()).isSpotGroupId());
        ((WizardDescriptor) settings).putProperty(ExportOptionsVisualPanel1.PROPERTY_IDENTIFICATION_NAME, ((ExportOptionsVisualPanel1) getComponent()).isIdentificationName());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        isValid();
        fireChangeEvent();
    }

    @Override
    public void validate() throws WizardValidationException {
        isValid();
        fireChangeEvent();
    }
}
