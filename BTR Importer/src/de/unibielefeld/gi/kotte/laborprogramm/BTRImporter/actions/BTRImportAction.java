package de.unibielefeld.gi.kotte.laborprogramm.BTRImporter.actions;

import de.unibielefeld.gi.kotte.laborprogramm.BTRImporter.BTRReader;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
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
 * Action for using the BTR Importer Module.
 * 
 * @author Konstantin Otte
 */
@ActionID(
    category = "Proteus/Plate384Node",
id = "de.unibielefeld.gi.kotte.laborprogramm.BTRImporter.actions.BTRImportAction")
@ActionRegistration(
    displayName = "#CTL_BTRImportAction")
@ActionReferences({@ActionReference(path = "Actions/Plate384Node", position = 200)})
@Messages("CTL_BTRImportAction=Import BTR Report")
public final class BTRImportAction implements ActionListener {

    private final IPlate384 context;

    public BTRImportAction(IPlate384 context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        File f;
        JFileChooser jfc = new JFileChooser();
        //jfc.setCurrentDirectory(new java.io.File("."));
        jfc.setDialogTitle("Choose *.BTR file");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);
        int status = jfc.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            f = jfc.getSelectedFile();
            BTRReader.readBTRFile(f, context);
            BTRReader.checkStatus(context);
        }
    }
}
