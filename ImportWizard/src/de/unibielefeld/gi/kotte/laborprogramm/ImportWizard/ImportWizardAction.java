package de.unibielefeld.gi.kotte.laborprogramm.ImportWizard;

import de.unibielefeld.gi.kotte.laborprogramm.dataImporter.ProjectBuilder;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import javax.swing.JComponent;
import org.netbeans.spi.project.ui.support.ProjectChooser;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
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
            //get files from descriptor...
            File baseDirectoryFile = (File) wizardDescriptor.getProperty(ImportVisualPanel1.PROPERTY_BASE_DIRECTORY);
            File projectDirectoryFile = (File) wizardDescriptor.getProperty(ImportVisualPanel1.PROPERTY_PROJECT_DIRECTORY);
            File projectDataFile = (File) wizardDescriptor.getProperty(ImportVisualPanel1.PROPERTY_PROJECT_DATA_FILE);
            File excelDataFile = (File) wizardDescriptor.getProperty(ImportVisualPanel1.PROPERTY_EXCEL_DATA_FILE);
            File gelDataFile = (File) wizardDescriptor.getProperty(ImportVisualPanel1.PROPERTY_GEL_DATA_FILE);
            //create project directory
            projectDirectoryFile.mkdirs();
            //build project structure
            ProjectBuilder pb = new ProjectBuilder();
            List<IProject> l = pb.buildProject(projectDataFile, gelDataFile, excelDataFile);
            IProject p = l.iterator().next();
            //System.out.println(p);//TEST: komplette Projekt Daten ausgeben (langsam)
            System.out.println("Creating project in "+projectDirectoryFile);
            IProteomicProjectFactory ippf = Lookup.getDefault().lookup(IProteomicProjectFactory.class);
//            assert ippf!=null;
//            assert projectDirectoryFile!=null;
//            assert p!=null;
            ippf.createProject(projectDirectoryFile,p);
//            createProject(projectDirectoryFile,p);
            //pp.setProjectData(p);
            //Projekt öffnen
//            OpenProjects op = OpenProjects.getDefault();
//            op.open(new Project[]{pp}, false, true);
            return projectDirectoryFile;
        }
        return null;
    }

//    private IProteomicProject createProject(File projdir, IProject project) {
//        ProteomicProject proproject = null;
//        try {
//            proproject = new ProteomicProject();
//            proproject.activate(new File(projdir, ProteomikProjectFactory.PROJECT_FILE).toURI().toURL());
//           // proproject.store(project);
//            //IProject ipr = proproject.retrieve(IProject.class);
//            //System.out.println("My funky Project: "+ipr.toString());
//            proproject.setProjectData(project);
////            System.out.println("Gel groups: " + proproject.getGelGroups());
//            proproject.close();
//        } catch (MalformedURLException ex) {
//            Exceptions.printStackTrace(ex);
//        }
//        return proproject;
//    }

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
