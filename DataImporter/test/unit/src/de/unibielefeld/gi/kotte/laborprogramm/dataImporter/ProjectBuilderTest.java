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
        //set resource paths
        String projectsPath = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/projects.xml";
        String gelImagesPath = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/gelImages.xml";
        String excelPath = "/de/unibielefeld/gi/kotte/laborprogramm/dataImporter/resources/Export_1.xlsx";

        //get projects data
        File projects = new File("projects.xml");
        projects.deleteOnExit();
        ResourceHandler.writeResourceToDisk(projectsPath, projects);

        //get gelImages data
        File gelImages = new File("gelImages.xml");
        gelImages.deleteOnExit();
        ResourceHandler.writeResourceToDisk(gelImagesPath, gelImages);

        //get excel report
        File excelReport = new File("Export_1.xlsx");
        excelReport.deleteOnExit();
        ResourceHandler.writeResourceToDisk(excelPath, excelReport);

        //create and run ProjectBuilder
        ProjectBuilder pb = new ProjectBuilder();
        List<IProject> projectList = pb.buildProject(projects, gelImages, excelReport);
        for (IProject project : projectList) {
            System.out.println(project);
        }
    }
}
