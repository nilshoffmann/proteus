package de.unibielefeld.gi.kotte.laborprogramm.pathway.options;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JComponent;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

@OptionsPanelController.SubRegistration(
    location = "BioCyc",
displayName = "#AdvancedOption_DisplayName_PathwayManagerCache",
keywords = "#AdvancedOption_Keywords_PathwayManagerCache",
keywordsCategory = "de-unibielefeld-gi-kotte-laborprogramm-pathways-options-BioCycWebServiceOptionsPanelController/PathwayManagerCache")
@org.openide.util.NbBundle.Messages({"AdvancedOption_DisplayName_PathwayManagerCache=Pathway Manager Cache", "AdvancedOption_Keywords_PathwayManagerCache=Pathways Database Cache MetaCyc BioCyc"})
public final class PathwayManagerCacheOptionsPanelController extends OptionsPanelController {

    private PathwayManagerCachePanel panel;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean changed;

    @Override
    public void update() {
        getPanel().load();
        changed = false;
    }

    @Override
    public void applyChanges() {
        getPanel().store();
        changed = false;
    }

    @Override
    public void cancel() {
        // need not do anything special, if no changes have been persisted yet
    }

    @Override
    public boolean isValid() {
        return getPanel().valid();
    }

    @Override
    public boolean isChanged() {
        return changed;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null; // new HelpCtx("...ID") if you have a help set
    }

    @Override
    public JComponent getComponent(Lookup masterLookup) {
        return getPanel();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    private PathwayManagerCachePanel getPanel() {
        if (panel == null) {
            panel = new PathwayManagerCachePanel(this);
        }
        return panel;
    }

    void changed() {
        if (!changed) {
            changed = true;
            pcs.firePropertyChange(OptionsPanelController.PROP_CHANGED, false, true);
        }
        pcs.firePropertyChange(OptionsPanelController.PROP_VALID, null, null);
    }
}
