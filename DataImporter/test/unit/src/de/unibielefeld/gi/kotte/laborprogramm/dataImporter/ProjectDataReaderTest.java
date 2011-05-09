package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.dataImporter.resourceHandler.ResourceHandler;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.io.File;

/**
 *
 * @author kotte
 */
public class ProjectDataReaderTest {

    public static void main(String[] args) {
        File f = new File("projects.xml");
        f.deleteOnExit();
        String path = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/projects.xml";
        ResourceHandler.writeResourceToDisk(path, f);
        ProjectDataReader pdr = new ProjectDataReader();
        IProject proj = pdr.parseProject(f);
        System.out.println(proj);
    }
}
