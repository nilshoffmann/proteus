package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Gel;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Group;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Project;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.ProjectData;
import java.io.File;

/**
 *
 * @author kotteF
 */
public class ProjectDataReaderTest {

    public static void main(String[] args) {
        File f = new File("projects.xml");
        f.deleteOnExit();
        String path = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/projects.xml";
        ResourceHandler.writeResourceToDisk(path, f);
        ProjectDataReader pdr = new ProjectDataReader();
        ProjectData pd = pdr.parseProject(f);
        for (Project proj : pd.getProjects().getProject()) {
            System.out.println("Name: " + proj.getName() + " creation data: " + proj.getCreationDate() + " description: " + proj.getDescription());
            for (Group group : proj.getGroups().getGroup()) {
                System.out.println("Group: " + group.getName() + " id: " + group.getId());
                for (Gel gel : group.getGels().getGel()) {
                    System.out.println("Gel id: " + gel.getGelid());
                }
            }
        }
    }
}
