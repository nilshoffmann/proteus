package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayUIProject;
import java.io.IOException;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

/**
 * Default implementation of IPathwayProjectFactory.
 *
 * @author Konstantin Otte
 */
@ServiceProvider(service = ProjectFactory.class)
public class PathwayUIProjectFactory implements ProjectFactory {

    public static final String PROJECT_FILE = "pathways.pwpr";

    @Override
    public boolean isProject(FileObject fo) {
        System.out.println("Checking if " + fo.getPath() + " contains a valid project file: " + fo.getFileObject(PathwayUIProjectFactory.PROJECT_FILE));
        return fo.getFileObject(PathwayUIProjectFactory.PROJECT_FILE) != null;
    }

    @Override
    public org.netbeans.api.project.Project loadProject(FileObject fo, ProjectState ps) throws IOException {
        if (isProject(fo)) {
            System.out.println("Loading project from " + fo.getPath());
            PathwayUIProject project = new PathwayUIProject();
//            projec.setProjectState(ps);
            project.activate(fo.getFileObject(PROJECT_FILE).getURL());
            return project;
        } else {
            return null;
        }
    }

    @Override
    public void saveProject(org.netbeans.api.project.Project prjct) throws IOException, ClassCastException {
        if (prjct instanceof IPathwayUIProject) {
            ((IPathwayUIProject) prjct).close();
        }
    }
}
