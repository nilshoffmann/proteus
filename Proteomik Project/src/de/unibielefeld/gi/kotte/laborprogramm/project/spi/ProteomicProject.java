package de.unibielefeld.gi.kotte.laborprogramm.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.sf.maltcms.chromaui.db.api.ICrudProvider;
import net.sf.maltcms.chromaui.db.api.ICrudProviderFactory;
import net.sf.maltcms.chromaui.db.api.ICrudSession;
import net.sf.maltcms.chromaui.db.api.NoAuthCredentials;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.support.DefaultProjectOperations;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IProteomicProject.class)
public class ProteomicProject implements IProteomicProject {

    ICrudProvider icp = null;
    FileObject projectDatabaseFile = null;
    //IProject myProject = null;
    ProjectState state;
    Lookup lookup;

    private IProject persist(IProject project) {
        IProject activeProject = project;
        if (activeProject == null) {
            activeProject = Lookup.getDefault().lookup(IProjectFactory.class).createEmptyProject();
        }
        if (icp != null) {
            ICrudSession ics = icp.createSession();
            ics.update(Arrays.asList(activeProject));
            ics.close();
        }
        return getFromDB();
    }

    private IProject getFromDB() {
        if (icp != null) {
            ICrudSession ics = icp.createSession();
            Collection<IProject> projects = ics.retrieve(IProject.class);
            if (projects.size() > 1) {
                throw new IllegalArgumentException("Found more than one instance of IProject in project database!");
            }
            IProject project = projects.iterator().next();
            ics.close();
            return project;
//            return myProject;
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
            this.icp = Lookup.getDefault().lookup(ICrudProviderFactory.class).getCrudProvider(url, new NoAuthCredentials());//new DB4oCrudProvider(pdbf, new NoAuthCredentials(), this.getClass().getClassLoader());
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
        if (lookup == null) {
            lookup = Lookups.fixed(new Object[]{
                        state, //allow outside code to mark the project as needing saving
                        new ActionProviderImpl(), //Provides standard actions like Build and Clean
                        //                        new DemoDeleteOperation(),
                        //                        new DemoCopyOperation(this),
                        new Info(), //Project information implementation
                        new ProteomicProjectLogicalView(this), //Logical view of project implementation
                    });
        }
        return lookup;
    }

    private final class ActionProviderImpl implements ActionProvider {

        private String[] supported = new String[]{
            ActionProvider.COMMAND_DELETE,
            ActionProvider.COMMAND_COPY,};

        @Override
        public String[] getSupportedActions() {
            return supported;
        }

        @Override
        public void invokeAction(String string, Lookup lookup) throws IllegalArgumentException {
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_DELETE)) {
                DefaultProjectOperations.performDefaultDeleteOperation(ProteomicProject.this);
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_COPY)) {
                DefaultProjectOperations.performDefaultCopyOperation(ProteomicProject.this);
            }
        }

        @Override
        public boolean isActionEnabled(String command, Lookup lookup) throws IllegalArgumentException {
            if ((command.equals(ActionProvider.COMMAND_DELETE))) {
                return true;
            } else if ((command.equals(ActionProvider.COMMAND_COPY))) {
                return true;
            } else {
                throw new IllegalArgumentException(command);
            }
        }
    }

    private final class Info implements ProjectInformation {

        private PropertyChangeSupport pcs = new PropertyChangeSupport(getProject());

        @Override
        public Icon getIcon() {
            return new ImageIcon(ImageUtilities.loadImage(
                    "de/unibielefeld/gi/kotte/laborprogramm/project/resources/projectIcon.png"));
        }

        @Override
        public String getName() {
            return getProjectDirectory().getName();
        }

        @Override
        public String getDisplayName() {
            return getName();
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener pcl) {
            this.pcs.addPropertyChangeListener(pcl);
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener pcl) {
            this.pcs.removePropertyChangeListener(pcl);
        }

        @Override
        public Project getProject() {
            return ProteomicProject.this;
        }
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
        return getFromDB().getSpotGroups();
    }

    @Override
    public void setSpotGroups(List<ISpotGroup> groups) {
        IProject project = getFromDB();
        project.setSpotGroups(groups);
        persist(project);
    }

    @Override
    public void addSpotGroup(ISpotGroup group) {
        IProject project = getFromDB();
        project.addSpotGroup(group);
        persist(project);
    }

    @Override
    public String getName() {
        return getFromDB().getName();
    }

    @Override
    public void setName(String name) {
        IProject project = getFromDB();
        project.setName(name);
        persist(project);
    }

    @Override
    public String getOwner() {
        return getFromDB().getOwner();
    }

    @Override
    public void setOwner(String owner) {
        IProject project = getFromDB();
        project.setOwner(owner);
        persist(project);
    }

    @Override
    public String getDescription() {
        return getFromDB().getDescription();
    }

    @Override
    public void setDescription(String description) {
        IProject project = getFromDB();
        project.setDescription(description);
        persist(project);
    }

    @Override
    public List<IPlate96> get96Plates() {
        return getFromDB().get96Plates();
    }

    @Override
    public void set96Plates(List<IPlate96> plates) {
        IProject project = getFromDB();
        project.set96Plates(plates);
        persist(project);
    }

    @Override
    public List<IPlate384> get384Plates() {
        return getFromDB().get384Plates();
    }

    @Override
    public void set384Plates(List<IPlate384> plates) {
        IProject project = getFromDB();
        project.set384Plates(plates);
        persist(project);
    }

    @Override
    public void add96Plate(IPlate96 plate) {
        IProject project = getFromDB();
        project.add96Plate(plate);
        persist(project);
    }

    @Override
    public void add384Plate(IPlate384 plate) {
        IProject project = getFromDB();
        project.add384Plate(plate);
        persist(project);
    }

    @Override
    public void setProjectData(IProject project) {
        persist(project);
    }
}
