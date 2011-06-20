package de.unibielefeld.gi.kotte.laborprogramm.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProjectFactory;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service=IProteomicProjectFactory.class)
//@org.openide.util.lookup.ServiceProvider(service=IProteomicProjectFactory.class)
public class ProteomikProjectFactory implements IProteomicProjectFactory{

    public static final String PROJECT_FILE = "plop.ppr";

    @Override
    public boolean isProject(FileObject fo) {
        //System.out.println("Checking if "+fo.getPath()+" is a valid project!");
        return fo.getFileObject(ProteomikProjectFactory.PROJECT_FILE) != null;
    }

    @Override
    public org.netbeans.api.project.Project loadProject(FileObject fo, ProjectState ps) throws IOException {

        if(isProject(fo)) {
            System.out.println("Loading project from "+fo.getPath());
            //IProteomicProject ipf = Lookup.getDefault().lookup(IProteomicProject.class);
            IProteomicProject project = createProject(FileUtil.toFile(fo));
            //project.setState(ps);
            /**
            project.setState(ps);
            project.activate(FileUtil.toFile(fo.getFileObject(DBProjectFactory.PROJECT_FILE)).toURI().toURL());
            */
            return project;
        }else{
            return null;
        }
    }

    @Override
    public void saveProject(org.netbeans.api.project.Project prjct) throws IOException, ClassCastException {
//        ChromAUIProject cp = (ChromAUIProject)prjct;
//        cp.getCrudProvider().
    }

    @Override
    public IProteomicProject createProject(File projdir) {
        IProteomicProject project = null;
        try {
            project = new ProteomicProject();
            project.activate(new File(projdir, PROJECT_FILE).toURI().toURL());
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return project;
    }

}
