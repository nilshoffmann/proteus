package de.unibielefeld.gi.kotte.laborprogramm.project.spi.factory;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.project.spi.ProteomicProject;
import java.io.IOException;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Konstantin Otte
 */
@org.openide.util.lookup.ServiceProvider(service = ProjectFactory.class)
public class ProteomicProjectFactory implements ProjectFactory {

    public static final String PROJECT_FILE = "plop.ppr";

    @Override
    public boolean isProject(FileObject fo) {
        System.out.println("Checking if "+fo.getPath()+" contains a valid project file: "+fo.getFileObject(ProteomicProjectFactory.PROJECT_FILE));
        return fo.getFileObject(ProteomicProjectFactory.PROJECT_FILE) != null;
    }

    @Override
    public org.netbeans.api.project.Project loadProject(FileObject fo, ProjectState ps) throws IOException {

        if (isProject(fo)) {
            System.out.println("Loading project from " + fo.getPath());
            ProteomicProject project = null;
            project = new ProteomicProject();
            project.setProjectState(ps);
            project.activate(fo.getFileObject(PROJECT_FILE).getURL());
            return project;
        } else {
            return null;
        }
    }

    @Override
    public void saveProject(org.netbeans.api.project.Project prjct) throws IOException, ClassCastException {
        if(prjct instanceof IProteomicProject) {
            ((IProteomicProject)prjct).close();
        }
    }

}
