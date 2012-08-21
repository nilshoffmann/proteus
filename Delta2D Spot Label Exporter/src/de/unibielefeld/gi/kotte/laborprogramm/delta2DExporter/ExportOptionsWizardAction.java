package de.unibielefeld.gi.kotte.laborprogramm.delta2DExporter;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import javax.swing.JComponent;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

/**
 * Wizard Action for the Delta2D Re-Import.
 *
 * @author kotte
 */
@ActionID(category = "Proteus/ProteomicProject",
id = "de.unibielefeld.gi.kotte.laborprogramm.delta2DExporter.ExportOptionsWizardAction")
@ActionRegistration(displayName = "#CTL_ExportOptionsWizardAction")
@ActionReferences({@ActionReference(path = "Actions/ProteomicProject", position = 300)})
@NbBundle.Messages("CTL_ExportOptionsWizardAction=Export Spot Information for Delta2D Re-Import")
public final class ExportOptionsWizardAction implements ActionListener {

    private WizardDescriptor.Panel[] panels;
    private final IProteomicProject context;

    public ExportOptionsWizardAction(IProteomicProject context) {
        System.out.println("ExportOptionsWizardAction for Delta2D Re-Import initialized with " + context);
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        wizardDescriptor.setTitle("Output Options for Spot Export for Delta2D Re-Import");
        wizardDescriptor.putProperty(ExportOptionsVisualPanel1.PROPERTY_PROJECT, context);
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
