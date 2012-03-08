package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGelFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroupFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroupFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroupFactory;
import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelData;
import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelImage;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Gel;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Group;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Project;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.ProjectData;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.Lookup;

/**
 * Builds up Proteomic Projects from data files.
 *
 * @author kotte
 */
public class ProjectBuilder {

    public List<IProject> buildProject(File projects, File gelImages, File excelReport) {
        // get ProjectData
        ProjectDataReader pdr = new ProjectDataReader();
        ProjectData pd = pdr.parseProject(projects);

        //get GelData
        GelDataReader gdr = new GelDataReader();
        GelData gd = gdr.getGelData(gelImages);

        //get excel reader
        ExcelReader er = new ExcelReader();

        List<IProject> projectList = new ArrayList<IProject>();

        /** stores gel references by gel id */
        Map<String, IGel> gelMap = new HashMap<String, IGel>();
        //read ProjectData
        for (Project proj : pd.getProjects().getProject()) {
            //init project
            IProject project = Lookup.getDefault().lookup(IProjectFactory.class).createEmptyProject();
            project.setName(proj.getName());
            //System.out.println("creation date: " + proj.getCreationDate());
            project.setDescription(proj.getDescription());

            //read gel groups
            for (Group group : proj.getGroups().getGroup()) {
                ITechRepGelGroup trgg = Lookup.getDefault().lookup(ITechRepGelGroupFactory.class).createTechRepGelGroup();
                trgg.setDescription(project.getName() + " " + group.getId());
                trgg.setName(group.getName());
                
                IBioRepGelGroup brgg = Lookup.getDefault().lookup(IBioRepGelGroupFactory.class).createBioRepGelGroup();
                brgg.setDescription(project.getName() + " " + group.getId());
                brgg.setName(group.getName());
                brgg.addGelGroup(trgg);
                trgg.setParent(brgg);
                
                ILogicalGelGroup lgg = Lookup.getDefault().lookup(ILogicalGelGroupFactory.class).createLogicalGelGroup();
                lgg.setDescription(project.getName() + " " + group.getId());
                lgg.setName(group.getName());
                lgg.addGelGroup(brgg);
                lgg.setParent(project);
                brgg.setParent(lgg);

                //get gels for the group
                for (Gel gel : group.getGels().getGel()) {
                    System.out.println("Parsing gel " + gel.getGelid() + " as child of group " + group.getName() + " and project " + proj.getName());
                    IGel gelObj = Lookup.getDefault().lookup(IGelFactory.class).createGel();
                    String gelid = gel.getGelid();
                    System.out.println("Gelid: '" + gelid + "'");
                    gelMap.put(gelid, gelObj);
                    //add gel to group
                    trgg.addGel(gelObj);
                    gelObj.setParent(trgg);
                }

                //add gel group to project
                project.addGelGroup(lgg);
            }

            projectList.add(project);
        }
        for(IProject project:projectList) {
            //read gelData
            for (GelImage gi : gd.getGelImages().getGelImage()) {
                String gelImageId = gi.getGelImageId().getId();
                System.out.println("GelImageId: '" + gelImageId + "'");
                IGel gel = gelMap.get(gelImageId);
                if (gel == null) {
                    Logger.getLogger(ProjectBuilder.class.getName()).log(Level.WARNING, "Failed to open Gel " + gi.getGelImageId().getId());
                } else {
                    gel.setName(gi.getName());
                    //if gel is fused image, mark it as virtual
                    gel.setVirtual(gi.getName().startsWith("Fused Image"));
                    //don't use gi.getSourceImage() (absolute windows path)
                    gel.setFilename(gi.getGelImageId().getId());
                }
            }
        }
        //add spot and spot group information from excel file
        er.parseExport(excelReport, projectList);
        return projectList;
    }
}
