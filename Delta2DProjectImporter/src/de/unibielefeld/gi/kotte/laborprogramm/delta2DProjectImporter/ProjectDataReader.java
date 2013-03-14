package de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter;

import de.unibielefeld.gi.kotte.laborprogramm.xml.projectData.ProjectData;
import java.io.File;

/**
 * Reads in projects.xml files from Delta2D projects.
 *
 * @author kotte
 */
public class ProjectDataReader {

    public ProjectData parseProject(File f) {
		return UnmarshalUtilities.unmarshal(ProjectData.class, "de.unibielefeld.gi.kotte.laborprogramm.xml.projectData", "/com/decodon/dtd/delta2d/ProjectData.dtd", f);
//        try {
//            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.kotte.laborprogramm.xml.projectData");
//            Unmarshaller u = jc.createUnmarshaller();
//			u.setSchema(null);
//            ProjectData pd = (ProjectData) u.unmarshal(new FileInputStream(f));
//            return pd;
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(ProjectDataReader.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JAXBException ex) {
//            Logger.getLogger(ProjectDataReader.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
    }
}
