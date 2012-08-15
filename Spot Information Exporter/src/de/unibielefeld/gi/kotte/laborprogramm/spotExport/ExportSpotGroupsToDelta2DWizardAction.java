package de.unibielefeld.gi.kotte.laborprogramm.spotExport;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

@ActionID(
    category = "SpotGroupNode",
id = "de.unibielefeld.gi.kotte.laborprogramm.spotExport.ExportSpotGroupsToDelta2DWizardAction")
@ActionRegistration(
    displayName = "#CTL_ExportSpotGroupsToDelta2DWizardAction")
@Messages("CTL_ExportSpotGroupsToDelta2DWizardAction=export selected spot group(s) for Delta2D Re-Import")
public final class ExportSpotGroupsToDelta2DWizardAction implements ActionListener {

    private final List<ISpotGroup> context;
    private WizardDescriptor.Panel[] panels;

    public ExportSpotGroupsToDelta2DWizardAction(List<ISpotGroup> context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        wizardDescriptor.setTitle("Output Options for Spot Export for Delta2D Re-Import");
        
        List<ISpot> spots = new ArrayList<ISpot>();
        for(ISpotGroup group:context) {
            for(ISpot spot:group.getSpots()) {
                spots.add(spot);
            }
        }
        //pass spots to the wizardDescriptor
        wizardDescriptor.putProperty(ExportOptionsVisualPanel1.PROPERTY_SPOTLIST, spots);
        //get path
        IProteomicProject project = Utilities.actionsGlobalContext().lookup(IProteomicProject.class);
        StringBuilder path = new StringBuilder();
        path.append(project.getProjectDirectory().getPath());
        path.append(File.separator).append("export").append(File.separator).append("spotTables");
        File directory = new File(path.toString());
        wizardDescriptor.putProperty(ExportOptionsVisualPanel1.PROPERTY_DIRECTORY, directory);
        
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {
            //create data export
            SpotDataExporter.export(wizardDescriptor);
        }
    }

    /**
     * Initialize panels representing individual wizard's steps and sets various
     * properties for them influencing wizard appearance.
     */
    private WizardDescriptor.Panel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.Panel[]{
                new ExportOptionsWizardPanel1()
            };
            String[] steps = new String[panels.length];
            for (int i = 0; i < panels.length; i++) {
                Component c = panels[i].getComponent();
                // Default step name to component name of panel. Mainly useful
                // for getting the name of the target chooser to appear in the
                // list of steps.
                steps[i] = c.getName();
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    // Sets step number of a component
                    jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(i));
                    // Sets steps names for a panel
                    jc.putClientProperty("WizardPanel_contentData", steps);
                    // Turn on subtitle creation on each step
                    jc.putClientProperty("WizardPanel_autoWizardStyle", Boolean.TRUE);
                    // Show steps on the left side with the image on the background
                    jc.putClientProperty("WizardPanel_contentDisplayed", Boolean.FALSE);
                    // Turn on numbering of all steps
                    jc.putClientProperty("WizardPanel_contentNumbered", Boolean.TRUE);
                }
            }
        }
        return panels;
    }
}
