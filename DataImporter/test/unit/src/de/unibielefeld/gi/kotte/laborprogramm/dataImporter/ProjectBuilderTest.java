package de.unibielefeld.gi.kotte.laborprogramm.dataImporter;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.io.File;
import java.util.List;

/**
 *
 * @author kotte
 */
public class ProjectBuilderTest {

    public static void main(String[] args) {
        String projectsPath = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/projects.xml";
        String gelImagesPath = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/gelImages.xml";
        String excelPath = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/Export_1.xlsx";

        File projects = new File("projects.xml");
        projects.deleteOnExit();
        ResourceHandler.writeResourceToDisk(projectsPath, projects);

        //get GelData
        File gelImages = new File("gelImages.xml");
        gelImages.deleteOnExit();
        ResourceHandler.writeResourceToDisk(gelImagesPath, gelImages);

        //get excel reader
        File excelReport = new File("Export_1.xlsx");
        excelReport.deleteOnExit();
        ResourceHandler.writeResourceToDisk(excelPath, excelReport);

        ProjectBuilder pb = new ProjectBuilder();
        List<IProject> projectList = pb.buildProject(projects, gelImages, excelReport);
        for (IProject project : projectList) {
            System.out.println(project);
        }
    }
}
