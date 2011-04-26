package de.unibielefeld.gi.kotte.laborprogramm.project.factory.spi;

import de.unibielefeld.gi.kotte.laborprogramm.project.factory.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.project.factory.spi.db.DB4oCrudProvider;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.sf.maltcms.chromaui.db.api.ICrudProvider;
import net.sf.maltcms.chromaui.db.api.NoAuthCredentials;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IProteomicProject.class)
public class ProteomicProject implements IProteomicProject{

    ICrudProvider icp = null;
    FileObject projectDatabaseFile = null;

    private IProject persist(IProject project) {
        IProject activeProject = project;
        if(activeProject == null) {
            activeProject = Lookup.getDefault().lookup(IProjectFactory.class).createEmptyProject();
        }
        if(icp!=null) {
            icp.createSession().update(Arrays.asList(activeProject));
        }
        return getFromDB();
    }

    private IProject getFromDB() {
        if(icp!=null) {
            Collection<IProject> projects = icp.createSession().retrieve(IProject.class);
            return projects.iterator().next();
        }
        return null;
    }

    @Override
    public void activate(URL url) {
        if (this.icp != null) {
            this.icp.close();
        }
        File pdbf;
        try {
            pdbf = new File(url.toURI());
            this.icp = new DB4oCrudProvider(pdbf, new NoAuthCredentials(), this.getClass().getClassLoader());
            this.icp.open();
            this.projectDatabaseFile = FileUtil.toFileObject(pdbf);
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public FileObject getProjectDirectory() {
        return projectDatabaseFile.getParent();
    }

    @Override
    public Lookup getLookup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ILogicalGelGroup> getGelGroups() {
        return getFromDB().getGelGroups();
    }

    @Override
    public void setGelGroups(List<ILogicalGelGroup> groups) {
        IProject project = getFromDB();
        project.setGelGroups(groups);
        persist(project);
    }

    @Override
    public void addGelGroup(ILogicalGelGroup group) {
        IProject project = getFromDB();
        project.addGelGroup(group);
        persist(project);
    }

    @Override
    public List<ISpotGroup> getSpotGroups() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSpotGroups(List<ISpotGroup> groups) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addSpotGroup(ISpotGroup group) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getOwner() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setOwner(String owner) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDescription(String description) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<IPlate96> get96Plates() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void set96Plates(List<IPlate96> plates) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<IPlate384> get384Plates() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void set384Plates(List<IPlate384> plates) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void add96Plate(IPlate96 plate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void add384Plate(IPlate384 plate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
