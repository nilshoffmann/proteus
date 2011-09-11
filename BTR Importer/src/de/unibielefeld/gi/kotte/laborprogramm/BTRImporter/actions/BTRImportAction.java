package de.unibielefeld.gi.kotte.laborprogramm.BTRImporter.actions;

import de.unibielefeld.gi.kotte.laborprogramm.BTRImporter.BTRReader;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

/**
 * Action for importing a protein identification file from MS and/or MSMS
 * identification in BTR format.
 *
 * @author kotte
 */
public class BTRImportAction implements ActionListener {

    private final IPlate384 context;

    public BTRImportAction(IPlate384 context) {
        assert context != null;
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File f = null;
        JFileChooser jfc = new JFileChooser();
        //jfc.setCurrentDirectory(new java.io.File("."));
        jfc.setDialogTitle("BTR Datei auswaehlen");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);
        int status = jfc.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            f = jfc.getSelectedFile();
        }
        BTRReader.readBTRFile(f, context);
        BTRReader.checkStatus(context);
        //System.out.println(context.toFullyRecursiveString());
    }
}
