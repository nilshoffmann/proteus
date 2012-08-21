package de.unibielefeld.gi.kotte.laborprogramm.dataImporter.actions;

import de.unibielefeld.gi.kotte.laborprogramm.dataImporter.ExcelReader;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

/**
 * Action for importing Delta2D Escel report files in order to add 
 * additional informations to the selected Proteus project.
 * 
 * @author kotte
 */
@ActionID(category = "Proteus/ProteomicProject",
id = "de.unibielefeld.gi.kotte.laborprogramm.dataImporter.actions.ImportExcelReportAction")
@ActionRegistration(displayName = "#CTL_ImportExcelReportAction")
@ActionReferences({@ActionReference(path = "Actions/ProteomicProject", position = 200)})
@Messages("CTL_ImportExcelReportAction=Import Quantification Data")
public final class ImportExcelReportAction implements ActionListener {

    private final IProject context;

    public ImportExcelReportAction(IProject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        File f;
        JFileChooser jfc = new JFileChooser();
        //jfc.setCurrentDirectory(new java.io.File("."));
        jfc.setDialogTitle("Choose Delta2D Excel Report File");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);
        int status = jfc.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            f = jfc.getSelectedFile();
            ExcelReader exr = new ExcelReader(context);
            exr.parseExport(f);
        }
    }
}
