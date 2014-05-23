package de.unibielefeld.gi.kotte.laborprogramm.delta2DImportWizard;

import de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter.ProjectBuilder;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.MessageFormat;
import javax.swing.JComponent;
import org.netbeans.spi.project.ui.support.ProjectChooser;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 * Wizard Action for the Delta2D project import.
 * 
 * @author Konstantin Otte
 */
public final class ProjectImportWizardAction implements ActionListener {

    private WizardDescriptor.Panel[] panels;

    public @Override
    void actionPerformed(ActionEvent e) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        wizardDescriptor.setTitle("Data import for new Proteus projekt");
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        dialog.setVisible(true);
        dialog.toFront();
        File parent = createProject(wizardDescriptor);
        if (parent != null && parent.exists()) {
            ProjectChooser.setProjectsFolder(parent);
        }
    }

    protected File createProject(WizardDescriptor wizardDescriptor) {
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {
            //get files
            File projectFile = (File) wizardDescriptor.getProperty(
                    ProjectImportVisualPanel1.PROPERTY_PROJECT_DIRECTORY);
            File projectParentDirectoryFile = (File) wizardDescriptor.getProperty(
                    ProjectImportVisualPanel1.PROPERTY_PROJECT_PARENT_DIRECTORY);
            String projectName = (String) wizardDescriptor.getProperty(
                    ProjectImportVisualPanel1.PROPERTY_PROJECT_NAME);
            String selectedProjectName = (String) wizardDescriptor.getProperty(
                    ProjectImportVisualPanel1.PROPERTY_SELECTED_PROJECT_NAME);
            
            ProjectBuilder pb = new ProjectBuilder();
            IProject p = pb.buildProject(projectFile, selectedProjectName);
            
            File projectDirectoryFile = new File(projectParentDirectoryFile, projectName);
            try {
                //copy gel images
                File gelDirectoryFile = new File(projectDirectoryFile.getAbsolutePath() + File.separator + "gels");
                gelDirectoryFile.mkdirs();
                File relativeGelsFolder = new File("gels");
                for (ILogicalGelGroup illgg : p.getGelGroups()) {
                    for (IBioRepGelGroup ibrgg : illgg.getGelGroups()) {
                        for (ITechRepGelGroup itrgg : ibrgg.getGelGroups()) {
                            for (IGel gel : itrgg.getGels()) {
//                                String oldPath = projectFile.getParent() + File.separator + "gelImages" + File.separator + gel.getFilename();
//                                FileObject originalFileObject = FileUtil.toFileObject(new File(oldPath));
//                                if(originalFileObject == null) {
//                                    System.out.println("originalFileObject is null! Path: " + oldPath);
//                                } else {
//                                FileObject gelFileObject = FileUtil.copyFile(
//                                        originalFileObject, FileUtil.toFileObject(gelDirectoryFile), gel.getName());
//                                gel.setFilename(gelFileObject.getNameExt());
                                File oldGelFile = new File(projectFile + File.separator + "gelImages" + File.separator + gel.getFilename());
                                File newGelFile = new File(gelDirectoryFile, gel.getFilename());
                                try {
                                    copyFile(oldGelFile, newGelFile);
                                } catch (IOException ex) {
                                    Exceptions.printStackTrace(ex);
                                }
                                
                                //create a relative file uri
//                                File relativeGelFile = new File(relativeGelsFolder, gelFileObject.getNameExt());
                                File relativeGelFile = new File(relativeGelsFolder, gel.getFilename());
                                System.out.println("Gel location:" + relativeGelFile.getPath());
                                gel.setLocation(relativeGelFile);
                                }
                            }
                        }
                    }
                //create Project
                IProteomicProjectFactory ippf = Lookup.getDefault().lookup(
                        IProteomicProjectFactory.class);
                IProteomicProject pp = ippf.createProject(projectDirectoryFile, p);
                pp.close();
            } catch (IllegalArgumentException iae) {
                Exceptions.printStackTrace(iae);
                projectDirectoryFile.delete();
                return null;
            }
            return projectDirectoryFile;
        }
        return null;
    }

    private static void copyFile(File sourceFile, File destFile) throws IOException {
        if(!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        }
        finally {
            if(source != null) {
                source.close();
            }
            if(destination != null) {
                destination.close();
            }
        }
    }

    
    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    protected WizardDescriptor.Panel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.Panel[]{
                new ProjectImportWizardPanel1()
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
