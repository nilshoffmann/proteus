package de.unibielefeld.gi.kotte.laborprogramm.ImportWizard;

import de.unibielefeld.gi.kotte.laborprogramm.dataImporter.ProjectBuilder;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import javax.swing.JComponent;
import org.netbeans.spi.project.ui.support.ProjectChooser;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.actions.CallableSystemAction;

public final class ImportWizardAction extends CallableSystemAction implements ActionListener {

    private WizardDescriptor.Panel[] panels;

    @Override
    public void performAction() {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        wizardDescriptor.setTitle("Datei Import fuer neues Proteomik Projekt");
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
            File projectParentDirectoryFile = (File) wizardDescriptor.getProperty(ImportVisualPanel1.PROPERTY_PROJECT_PARENT_DIRECTORY);
            String projectName = (String) wizardDescriptor.getProperty(ImportVisualPanel1.PROPERTY_PROJECT_NAME);
            File projectDirectoryFile = new File(projectParentDirectoryFile, projectName);
            projectDirectoryFile.mkdir();
            File baseDirectoryFile = (File) wizardDescriptor.getProperty(ImportVisualPanel1.PROPERTY_BASE_DIRECTORY);
            File projectDataFile = new File(baseDirectoryFile.getAbsolutePath() + File.separator + "projects" + File.separator + "projects.xml");
            File gelDataFile = new File(baseDirectoryFile.getAbsolutePath() + File.separator + "gelImages" + File.separator + "gelImages.xml");
            File excelDataFile = (File) wizardDescriptor.getProperty(ImportVisualPanel1.PROPERTY_EXCEL_DATA_FILE);

            //build project structure
            ProjectBuilder pb = new ProjectBuilder();
            IProject p = null;
            try {
                List<IProject> l = pb.buildProject(projectDataFile, gelDataFile, excelDataFile);
                p = l.iterator().next();
                //System.out.println(p);//TEST: komplette Projekt Daten ausgeben (langsam)
                System.out.println("Creating project in " + projectDirectoryFile);
                
                //copy gel images
                File gelDirectoryFile = new File(projectDirectoryFile.getAbsolutePath() + File.separator + "gels");
                gelDirectoryFile.mkdir();
                for (ILogicalGelGroup illgg : p.getGelGroups()) {
                    for (IBioRepGelGroup ibrgg : illgg.getGelGroups()) {
                        for (ITechRepGelGroup itrgg : ibrgg.getGelGroups()) {
                            for (IGel gel : itrgg.getGels()) {
                                String oldPath = baseDirectoryFile.getAbsolutePath() + File.separator + "gelImages" + File.separator + gel.getFilename();
                                FileObject originalFileObject = FileUtil.toFileObject(new File(oldPath));
                                FileObject gelFileObject = FileUtil.copyFile(originalFileObject, FileUtil.toFileObject(gelDirectoryFile), gel.getName());
                                gel.setFilename("gels"+File.separator+gelFileObject.getNameExt());
                            }
                        }
                    }
                }
                //create Project
                IProteomicProjectFactory ippf = Lookup.getDefault().lookup(IProteomicProjectFactory.class);
                ippf.createProject(projectDirectoryFile, p);
            } catch (IllegalArgumentException iae) {
                Exceptions.printStackTrace(iae);
                projectDirectoryFile.delete();
                return null;
            } catch (IOException ioe) {
                Exceptions.printStackTrace(ioe);
            }
            return projectDirectoryFile;
        }
        return null;
    }

    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    protected WizardDescriptor.Panel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.Panel[]{
                        new ImportWizardPanel1()
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
                    // TODO if using org.openide.dialogs >= 7.8, can use WizardDescriptor.PROP_*:
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

    @Override
    public String getName() {
        return "Starte Proteomik Projekt Wizard";
    }

    @Override
    public String iconResource() {
        return null;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}
