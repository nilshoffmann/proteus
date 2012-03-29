package de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter;

import de.unibielefeld.gi.kotte.laborprogramm.dataImporter.GelDataReader;
import de.unibielefeld.gi.kotte.laborprogramm.dataImporter.ProjectDataReader;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGelFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpotFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroupFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroupFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroupFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroupFactory;
import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelData;
import de.unibielefeld.gi.kotte.laborprogramm.xml.gelData.GelImage;
import de.unibielefeld.gi.kotte.laborprogramm.xml.labels.Label;
import de.unibielefeld.gi.kotte.laborprogramm.xml.labels.LabelData;
import de.unibielefeld.gi.kotte.laborprogramm.xml.labels.Labels;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Gel;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Group;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Project;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.ProjectData;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Projects;
import de.unibielefeld.gi.kotte.laborprogramm.xml.quantitation.QuantitationData;
import de.unibielefeld.gi.kotte.laborprogramm.xml.quantitation.Spot;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.Lookup;

/**
 * Builds up Proteus Projects from data files.
 * 
 * @author kotte
 */
public class ProjectBuilder {

    private IProject project;

    public IProject buildProject(File projectDirectory, String selectedProjectName) {
        //get ProjectData
        File projectDataFile = new File(projectDirectory.getAbsolutePath()
                + File.separator + "projects" + File.separator + "projects.xml");
        ProjectDataReader pdr = new ProjectDataReader();
        ProjectData pd = pdr.parseProject(projectDataFile);
        Projects projects = pd.getProjects();
        List<Project> projectList = projects.getProject();

        //search for selected project
        Project proj = null;
        Iterator<Project> it = projectList.iterator();
        while (it.hasNext()) {
            proj = it.next();
            if (proj.getName().equals(selectedProjectName)) {
                break;
            }
        }

        //create IProject
        project = Lookup.getDefault().lookup(IProjectFactory.class).createEmptyProject();
        project.setName(proj.getName());
        project.setDescription(proj.getDescription());

        //read gel groups
        /** stores gel references by gel id */
        Map<String, IGel> gelMap = new HashMap<String, IGel>();
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
                System.out.println("Parsing gel " + gel.getGelid() + " as child of group "
                        + group.getName() + " and project " + proj.getName());
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

        //read gelData
        File gelDataFile = new File(projectDirectory.getAbsolutePath() + File.separator
                + "gelImages" + File.separator + "gelImages.xml");
        GelDataReader gdr = new GelDataReader();
        GelData gd = gdr.getGelData(gelDataFile);
        String fusedImageId = null;
        for (GelImage gi : gd.getGelImages().getGelImage()) {
            String gelImageId = gi.getGelImageId().getId();
            System.out.println("GelImageId: '" + gelImageId + "' Name: '" + gi.getName() + "'");
            if (gelMap.containsKey(gelImageId)) {
                IGel gel = gelMap.get(gelImageId);
                gel.setName(gi.getName());
                //if gel is fused image, mark it as virtual
                if (gi.getName().startsWith("Fused Image")) {
                    gel.setVirtual(true);
                    fusedImageId = gelImageId;
                }
                //don't use gi.getSourceImage() (absolute windows path)
                gel.setFilename(gelImageId);

                //read quantitations data
                File quantitationsFile = new File(projectDirectory.getAbsolutePath() + File.separator
                        + "gelImages" + File.separator + "quantitations" + File.separator + gelImageId + ".xml");
                QuantitationDataReader qdr = new QuantitationDataReader();
                QuantitationData quantitationData = qdr.parseQuantification(quantitationsFile);
                for (Spot spotData : quantitationData.getSpotList().getSpot()) {
                    ISpot spotObj = Lookup.getDefault().lookup(ISpotFactory.class).createSpot();
                    spotObj.setGel(gel);
                    spotObj.setGroup(getSpotGroup(spotData.getId()));
                    try {
                        spotObj.setNumber(Integer.parseInt(spotData.getId()));
                        spotObj.setPosX(Double.parseDouble(spotData.getCenter().getX()));
                        spotObj.setPosX(Double.parseDouble(spotData.getCenter().getX()));
                    } catch (NumberFormatException e) {
                        Logger.getLogger(ProjectBuilder.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
        }

        //read label spot relations
        File labelSpotRelationsFile = new File(projectDirectory.getAbsolutePath() + File.separator
                + "gelImages" + File.separator + "labelSpotRelations" + File.separator + fusedImageId + ".xml");
        if (!labelSpotRelationsFile.exists()) {
            System.out.println("Label Spot Relations File '" + labelSpotRelationsFile.getAbsolutePath() + "' doesn't exist!");
        } else {
        LabelSpotRelationsDataReader lsrdr = new LabelSpotRelationsDataReader();
        Map<String, String> labelSpotRelations = lsrdr.parseLabelSpotRelations(labelSpotRelationsFile);
        
        
        //read label data
        File labelsFile = new File(projectDirectory.getAbsolutePath() + File.separator + "gelImages"
                + File.separator + "labels" + File.separator + fusedImageId + ".xml");
        LabelDataReader ldr = new LabelDataReader();
        LabelData labelData = ldr.parseLabels(labelsFile);

        //apply labels to spot groups
        for (Object obj : labelData.getStylesOrLabels()) {
            for (Label label : ((Labels) obj).getLabel()) {
                String labelString = label.getName();
                String labelId = label.getId();
                ISpotGroup group = getSpotGroup(labelSpotRelations.get(labelId));
                group.setLabel(labelString);
                //apply label to each spot of the group
                for (ISpot spot : group.getSpots()) {
                    spot.setLabel(labelString);
                }
            }
        }
        }
        
        return project;
    }
    
    private Map<String, ISpotGroup> spotGroups = new HashMap<String, ISpotGroup>();

    private ISpotGroup getSpotGroup(String groupId) {
        if (spotGroups.containsKey(groupId)) {
            return spotGroups.get(groupId);
        } else {
            ISpotGroup group = Lookup.getDefault().lookup(ISpotGroupFactory.class).createSpotGroup();
            group.setParent(project);
            int spotId = 0;
            try {
                spotId = Integer.parseInt(groupId);
            } catch (NumberFormatException e) {
                System.out.println("Cannot parse labelId: '" + groupId + "'");
            }
            group.setNumber(spotId);
            spotGroups.put(groupId, group);
            return group;
        }
    }

    public List<String> getProjectNames(File projectDataFile) {
        List<String> projectNames = new ArrayList<String>();
        if(projectDataFile.exists()) {
            //get ProjectData
            ProjectDataReader pdr = new ProjectDataReader();
            ProjectData pd = pdr.parseProject(projectDataFile);
            Projects projects = pd.getProjects();
            List<Project> projectList = projects.getProject();
            //add names to list
            for (Iterator<Project> it = projectList.iterator(); it.hasNext();) {
                Project p = it.next();
                projectNames.add(p.getName());
            }
        }
        return projectNames;
    }
}
