package de.unibielefeld.gi.kotte.laborprogramm.proteomik.dataImporter.projectData;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Gel;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Group;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.Project;
import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.ProjectData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author kotte
 */
public class ProjectDataReader {

    public static void main(String[] args) {
        JFileChooser jfc = new JFileChooser();
        int status = jfc.showOpenDialog(null);
        if(status == JFileChooser.APPROVE_OPTION) {
            File f= jfc.getSelectedFile();
            ProjectDataReader gdr = new ProjectDataReader();
            gdr.getGels(f);
        }
    }

    public List<IGel> getGels(File f) {
        try {
            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.kotte.laborprogramm.xml.projectData");
            Unmarshaller u = jc.createUnmarshaller();
            ProjectData gd = (ProjectData)u.unmarshal(
                    new FileInputStream(f));
            System.out.println(gd);
            for(Project proj: gd.getProjects().getProject()) {
                System.out.println("Name: "+proj.getName()+" creation data: "+proj.getCreationDate()+" description: "+proj.getDescription());
                for(Group group:proj.getGroups().getGroup()) {
                    System.out.println("Group: "+group.getName()+" id: "+group.getId());
                    for(Gel gel:group.getGels().getGel()) {
                        System.out.println("Gel id: "+gel.getGelid());
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProjectDataReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(ProjectDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.emptyList();
    }
}
