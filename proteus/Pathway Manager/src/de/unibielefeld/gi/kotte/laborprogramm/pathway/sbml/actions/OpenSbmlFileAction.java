/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.pathway.sbml.actions;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.sbml.PathwayExplorerTopComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.NbBundle.Messages;

@ActionID(
		category = "Proteus/SBMLDocument",
		id = "de.unibielefeld.gi.kotte.laborprogramm.pathway.sbml.actions.OpenSbmlFileAction")
@ActionRegistration(
		displayName = "#CTL_OpenSbmlFileAction")
@ActionReference(path = "Menu/File", position = 950)
@Messages("CTL_OpenSbmlFileAction=Open SBML Document")
public final class OpenSbmlFileAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		FileChooserBuilder fcb = new FileChooserBuilder(OpenSbmlFileAction.class);
		fcb.addFileFilter(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return (pathname.getName().toLowerCase().endsWith(".sbml") || pathname.getName().toLowerCase().endsWith(".xml"));
			}

			@Override
			public String getDescription() {
				return "SBML Documents";
			}
		});
		fcb.setFileHiding(false);
		fcb.setDirectoriesOnly(false);
		File file = fcb.showOpenDialog();
		PathwayExplorerTopComponent petc = new PathwayExplorerTopComponent();
		petc.openDocument(file);
	}
}
