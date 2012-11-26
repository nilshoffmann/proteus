package de.unibielefeld.gi.kotte.laborprogramm.pathway.sbml.viewconstructor;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.sbml.IPathwayMap;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.sbml.IPathwayMapFactory;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.manager.PathwayOverviewTopComponent;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.CancellableRunnable;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.MetacycController;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.ResultListener;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.PGDB;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.RequestProcessor;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.stax.SBMLReader;

public class PathwayViewController {

    public void generate(final String name, final File sbmlFile, final PGDB organism) {
        CancellableRunnable<IPathwayProject> cr = new CancellableRunnable<IPathwayProject>() {
            @Override
            public void body() {
                try {
                    //create project
                    this.handle.progress("Setting up new Pathway Project");
                    IPathwayProjectFactory ippf = Lookup.getDefault().lookup(IPathwayProjectFactory.class);
                    IPathwayProject project = ippf.createProject(name);
                    File projectDirectory = new File(new File(System.getProperty("user.home"),"ProteusProjects"), name);
                    projectDirectory.mkdirs();
                    File databaseFile = new File(projectDirectory,"pathways.pwpr");
                    project.activate(databaseFile.toURI().toURL());
                    //load SBML document from File
                    try {
                        this.handle.progress("Loading SBML File " + sbmlFile.getName());
                        SBMLReader reader = new SBMLReader();
                        SBMLDocument document = reader.readSBML(sbmlFile);
                        project.setDocument(document);
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
                    pathwayMap.setOrganismName(PathwayOverviewTopComponent.getSpeciesName(organism));
                    MetacycController mc = new MetacycController();
                    this.handle.progress("Querying database");
                    List<Pathway> pathwaysForOrganism = mc.getPathwaysForOrganism(organism.getOrgid());
                    this.handle.progress("Adding Pathway to the Pathway Map");
                    for (Pathway p : pathwaysForOrganism) {
                        pathwayMap.addPathway(p);
                    }
                    project.setPathwayMap(pathwayMap);
                    notifyListeners(project);
                } catch (MalformedURLException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        };
        cr.addResultListener(new ResultListener<IPathwayProject>() {
            @Override
            public void listen(IPathwayProject project) {
                OpenProjects.getDefault().open(new Project[]{project}, false, true);
            }
        });

        final ProgressHandle ph = ProgressHandleFactory.createHandle("Generating SBML View", cr);
        cr.setHandle(ph);
        RequestProcessor rp = new RequestProcessor(PathwayOverviewTopComponent.class);
        rp.post(cr);
    }
}