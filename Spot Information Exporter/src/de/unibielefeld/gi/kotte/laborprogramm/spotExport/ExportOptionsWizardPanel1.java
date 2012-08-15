package de.unibielefeld.gi.kotte.laborprogramm.spotExport;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.*;
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
public class ExportOptionsWizardPanel1 implements WizardDescriptor.ValidatingPanel<WizardDescriptor>, PropertyChangeListener {

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
        if (component.getFileName().isEmpty()) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please enter a filename.");
            return false;
        }
        if (component.getDirectory() == null) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Please pick an output directory.");
            return false;
        }
        if (!(component.isShowGroupLabel() || component.isShowIdentification())) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE,
                    "Please pick at least one output option.");
            return false;
        }
        if (component.isShowIdentification() && !component.isShowIdentName()
                && !component.isShowIdentPlate96Position() && !component.isShowIdentPlate384Position()
                && !component.isShowIdentGelName() && !component.isShowIdentAbbreviation()
                && !component.isShowIdentAccession() && !component.isShowIdentEcNumbers()
                && !component.isShowIdentCoverage() && !component.isShowIdentPIValue()
                && !component.isShowIdentScore() && !component.isShowIdentWeight()) {
            descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Cannot show empty identifications.");
            return false;
        }
//        if (component.isFilterMascotUsed()) {
            String filterMascotValue = component.getFilterMascotValue();
            try {
                Float value = Float.parseFloat(filterMascotValue);
            } catch (NumberFormatException nfe) {
                descriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, "Mascot Score Filter is not a number!");
                return false;
            }
//        }

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
    public void readSettings(WizardDescriptor settings) {
        //get descriptor
        this.descriptor = settings;
        //set directory
        File directory = (File) this.descriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_DIRECTORY);
        ((ExportOptionsVisualPanel1) getComponent()).setDirectory(directory);
        //get spot list
        List<ISpot> spotList = (List<ISpot>) this.descriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SPOTLIST);
        //Liste der Methoden anhand der vorhandenen Identifikationen erstellen
        Set<String> methods = new LinkedHashSet<String>();
        for (ISpot spot : spotList) {
            IWell96 well96 = spot.getWell();
            if (well96 != null) {
                for (IWell384 well384 : well96.get384Wells()) {
                    for (IIdentificationMethod method : well384.getIdentification().getMethods()) {
                        methods.add(method.getName());
                    } 
                }
            }
        }
        ((ExportOptionsVisualPanel1) getComponent()).setAvailableMethods(methods);
    }

    @Override
    public void storeSettings(WizardDescriptor settings) {
        WizardDescriptor s = settings;
        ExportOptionsVisualPanel1 eovp = (ExportOptionsVisualPanel1) getComponent();

        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_DIRECTORY, eovp.getDirectory());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_FILENAME, eovp.getFileName());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_METHODS, eovp.getSelectedMethods());

        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_LABEL, eovp.isShowGroupLabel());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_METHOD_NAME, eovp.isShowMethodName());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENTIFICATION, eovp.isShowIdentification());

        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_NAME, eovp.isShowIdentName());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_PLATE96_POSITION, eovp.isShowIdentPlate96Position());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_PLATE384_POSITION, eovp.isShowIdentPlate384Position());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_GEL_NAME, eovp.isShowIdentGelName());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_ABBREVIATION, eovp.isShowIdentAbbreviation());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_ACCESSION, eovp.isShowIdentAccession());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_KEGG_NUMBERS, eovp.isShowIdentEcNumbers());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_COVERAGE, eovp.isShowIdentCoverage());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_PI_VALUE, eovp.isShowIdentPIValue());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_SCORE, eovp.isShowIdentScore());
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_WEIGHT, eovp.isShowIdentWeight());
        
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_FILTER_MASCOT_USAGE, eovp.isFilterMascotUsed());
        String filterMascotValue = eovp.getFilterMascotValue();
        s.putProperty(ExportOptionsVisualPanel1.PROPERTY_FILTER_MASCOT_VALUE, Float.parseFloat(filterMascotValue));
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
