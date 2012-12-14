package de.unibielefeld.gi.kotte.laborprogramm.pathway.wizard;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathwayProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathwayProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.sbml.IPathwayMap;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.sbml.IPathwayMapFactory;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayUIProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayUIProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.CancellableRunnable;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.MetacycController;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.NameTools;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.ResultListener;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.PGDB;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.xml.stream.XMLStreamException;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.stax.SBMLReader;

@ActionID(
    category = "Proteus/ProteomicProject",
id = "de.unibielefeld.gi.kotte.laborprogramm.pathway.wizard.SBMLImportWizardAction")
@ActionRegistration(
    displayName = "#CTL_SBMLImportWizardAction")
@ActionReferences({
    @ActionReference(path = "Actions/ProteomicProject", position = 201)})
@NbBundle.Messages("CTL_SBMLImportWizardAction=Import SBML File")
public final class SBMLIMportWizardAction implements ActionListener {

    private final IProteomicProject context;

    public SBMLIMportWizardAction(IProteomicProject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new SBMLIMportWizardPanel1());
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            // Default step name to component name of panel.
            steps[i] = c.getName();
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<WizardDescriptor>(panels));
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle("Import SBML File for Pathway View");
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            //get data from wizard descriptor
            String projectName = (String) wiz.getProperty(SBMLIMportVisualPanel1.PROPERTY_PROJECT_NAME);
            File sbmlFile = (File) wiz.getProperty(SBMLIMportVisualPanel1.PROPERTY_FILE);
            PGDB organism = (PGDB) wiz.getProperty(SBMLIMportVisualPanel1.PROPERTY_ORGANISM);
            //create project
            generate(context, projectName, sbmlFile, organism);
        }
    }

    private void generate(final IProteomicProject parent, final String name, final File sbmlFile, final PGDB organism) {
        CancellableRunnable<IPathwayUIProject> cr = new CancellableRunnable<IPathwayUIProject>() {
            @Override
            public void body() {
                //create project
                this.handle.progress("Setting up new Pathway Project");
                IPathwayUIProjectFactory ippf = Lookup.getDefault().lookup(IPathwayUIProjectFactory.class);
                IPathwayUIProject uiProject = ippf.createProject(parent, name);
//                IPathwayProjectFactory ippf2 = Lookup.getDefault().lookup(IPathwayProjectFactory.class);
//                IPathwayProject project = ippf2.createProject(name);
//                uiProject.setProjectData(project);
                //load SBML document from File
                try {
                    this.handle.progress("Loading SBML File " + sbmlFile.getName());
                    SBMLReader reader = new SBMLReader();
                    SBMLDocument document = reader.readSBML(sbmlFile);
                    uiProject.getProjectData().setDocument(document);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (XMLStreamException ex) {
                    Exceptions.printStackTrace(ex);
                }
                //generate pathway Map
                this.handle.progress("Generating new Pathway Map");
                IPathwayMapFactory ipmf = Lookup.getDefault().lookup(IPathwayMapFactory.class);
                IPathwayMap pathwayMap = ipmf.createPathwayMap();
                pathwayMap.setOrganismID(organism.getOrgid());
                pathwayMap.setOrganismName(NameTools.getSpeciesName(organism));
                MetacycController mc = new MetacycController();
                this.handle.progress("Querying database");
                List<Pathway> pathwaysForOrganism = mc.getPathwaysForOrganism(organism.getOrgid());
                this.handle.progress("Adding Pathway to the Pathway Map");
                for (Pathway p : pathwaysForOrganism) {
                    pathwayMap.addPathway(p);
                }
                uiProject.getProjectData().setPathwayMap(pathwayMap);
                notifyListeners(uiProject);
            }
        };
        cr.addResultListener(new ResultListener<IPathwayUIProject>() {
            @Override
            public void listen(IPathwayUIProject project) {
                OpenProjects.getDefault().open(new Project[]{project}, false, true);
            }
        });

        final ProgressHandle ph = ProgressHandleFactory.createHandle("Generating SBML View", cr);
        cr.setHandle(ph);
        RequestProcessor rp = new RequestProcessor(SBMLIMportWizardAction.class);
        rp.post(cr);
    }
}
