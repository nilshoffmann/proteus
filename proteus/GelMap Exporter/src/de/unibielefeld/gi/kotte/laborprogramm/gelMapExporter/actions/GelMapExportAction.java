package de.unibielefeld.gi.kotte.laborprogramm.gelMapExporter.actions;

import de.unibielefeld.gi.kotte.laborprogramm.gelMapExporter.GelMapExporter;
import de.unibielefeld.gi.kotte.laborprogramm.gelMapExporter.dialogs.NameDialog;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

@ActionID(
    category = "Proteus/GelNode",
id = "de.unibielefeld.gi.kotte.laborprogramm.gelMapExporter.actions.GelMapExportAction")
@ActionRegistration(
    displayName = "#CTL_GelMapExportAction")
@ActionReferences({
    @ActionReference(path = "Actions/GelNode", position = 320)})
@Messages("CTL_GelMapExportAction=Export Gel for GelMap")
/**
 * Export Action to call the Exporter for IGels in GelMap format.
 *
 * @author Konstantin Otte
 */
public final class GelMapExportAction implements ActionListener {

    private final IGel context;

    public GelMapExportAction(IGel context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        NameDialog dialog = new NameDialog(context.getName());
        NotifyDescriptor nd = new NotifyDescriptor(
                dialog, // instance of your panel
                "Enter Name for GelMap Export File", // title of the dialog
                NotifyDescriptor.OK_CANCEL_OPTION, // it is Yes/No dialog ...
                NotifyDescriptor.PLAIN_MESSAGE, // ... of a question type => a question mark icon
                null, // we have specified YES_NO_OPTION => can be null, options specified by L&F,
                NotifyDescriptor.OK_OPTION // default option is "Yes"
                );
        if (DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.OK_OPTION) {
            StringBuilder path = new StringBuilder();
            IProteomicProject project = Utilities.actionsGlobalContext().lookup(IProteomicProject.class);
            path.append(project.getProjectDirectory().getPath());
            path.append(File.separator).append("export").append(File.separator).append("GelMap");
            File directory = new File(path.toString());
            directory.mkdirs();
            File outFile = new File(directory, dialog.getNameText() + ".txt");
            GelMapExporter.export(outFile, context);
        }
    }
}
