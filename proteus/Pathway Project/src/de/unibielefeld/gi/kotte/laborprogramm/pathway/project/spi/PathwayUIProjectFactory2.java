package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathwayProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.IPathwayProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayUIProject;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayUIProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import java.io.File;
import java.net.MalformedURLException;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IPathwayUIProjectFactory.class)
public class PathwayUIProjectFactory2 implements IPathwayUIProjectFactory {

    @Override
    public IPathwayUIProject createProject(IProteomicProject parent, String name) {
        PathwayUIProject result = new PathwayUIProject();
        IPathwayProject pp = Lookup.getDefault().lookup(IPathwayProjectFactory.class).createProject(name);
        File baseDir = FileUtil.toFile(parent.getProjectDirectory());
        File subprojectDir = new File(baseDir, "pathways");
        File projectDirectory = new File(subprojectDir, name);
        if (projectDirectory.exists()) {
            throw new RuntimeException("Project directory " + projectDirectory + " already exists!");
        }
        projectDirectory.mkdirs();
        File databaseFile = new File(projectDirectory, PathwayUIProjectFactory.PROJECT_FILE);
        try {
            result.activate(databaseFile.toURI().toURL());
            result.setProjectData(pp);
            result.close();
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        result = new PathwayUIProject();
        try {
            result.activate(databaseFile.toURI().toURL());
            result.open();
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return result;
    }
}