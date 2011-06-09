package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Gel;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Group;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Project;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.ProjectData;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author kotteF
 */
public class ProjectDataReaderTest {

    private final static boolean FILECHOOSER = true;

    public static void main(String[] args) {
        File f = null;
        if (!FILECHOOSER) {
            f = new File("Export_1.xlsx");
            f.deleteOnExit();
            String path = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/projects.xml";
            ResourceHandler.writeResourceToDisk(path, f);
        } else {
            JFileChooser jfc = new JFileChooser();
            int status = jfc.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                f = jfc.getSelectedFile();
            }
        }
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
