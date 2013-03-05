package de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter;

import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.ProjectData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Reads in projects.xml files from Delta2D projects.
 *
 * @author kotte
 */
public class ProjectDataReader {

    public ProjectData parseProject(File f) {
        try {
            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.kotte.laborprogramm.xml.projectData");
            Unmarshaller u = jc.createUnmarshaller();
			u.setSchema(null);
            ProjectData pd = (ProjectData) u.unmarshal(new FileInputStream(f));
            return pd;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProjectDataReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(ProjectDataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
