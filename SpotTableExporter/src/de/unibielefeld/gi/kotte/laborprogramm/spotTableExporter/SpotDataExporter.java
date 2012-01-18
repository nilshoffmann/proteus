package de.unibielefeld.gi.kotte.laborprogramm.spotTableExporter;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import org.openide.WizardDescriptor;

/**
 *
 * @author kotte
 */
public class SpotDataExporter {

    public static void export(WizardDescriptor wizardDescriptor, IProteomicProject project) {
        System.out.println("Wizard fertig ausgefuert");
        Boolean spotGroupId = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SPOT_GROUP_ID);
        if(spotGroupId) System.out.println("spotGroupId ist true");
        Boolean IdentificationName = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_IDENTIFICATION_NAME);
        if(IdentificationName) System.out.println("spotGroupId ist true");
    }
}
