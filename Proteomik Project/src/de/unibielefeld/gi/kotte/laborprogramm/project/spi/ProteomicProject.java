package de.unibielefeld.gi.kotte.laborprogramm.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.sf.maltcms.chromaui.db.api.ICrudProvider;
import net.sf.maltcms.chromaui.db.api.ICrudProviderFactory;
import net.sf.maltcms.chromaui.db.api.ICrudSession;
import net.sf.maltcms.chromaui.db.api.NoAuthCredentials;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.ProjectOpenedHook;
import org.netbeans.spi.project.ui.support.DefaultProjectOperations;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = Project.class)
public class ProteomicProject implements IProteomicProject {

    ICrudProvider icp = null;
    ICrudSession ics = null;
    IProject activeProject = null;
    InstanceContent instanceContent = new InstanceContent();
    Lookup lookup = null;
    URL dblocation = null;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/ProjectIcon.png";

    public ProteomicProject() {
    }

    public ProteomicProject(IProject project) {
        this();
        this.activeProject = project;
    }

    private ICrudSession getCrudSession() {
        openSession();
        return ics;
    }

    private synchronized void persist() {
        instanceContent.add(new ProjectSaveCookie());
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        persist();
    }

    private final class ProjectSaveCookie implements SaveCookie {

        @Override
        public void save() throws IOException {
            getCrudSession().update(Arrays.asList(activeProject));
            getLookup().lookup(Info.class).firePropertyChange(getClass().getName(), null, this);
            try {
                instanceContent.remove(this);
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            }
        }
    }

    public synchronized <T> T retrieve(Class<T> c) {
        Collection<T> coll = getCrudSession().retrieve(c);
        T t = coll.iterator().next();
        return t;
    }

    private IProject getFromDB() {
        //Logger.getLogger(getClass().getName()).log(Level.INFO, "CrudProvider: {0}",icp);
        //if (icp != null && ics != null) {
        //ICrudSession ics = icp.createSession();
        Collection<IProject> projects = getCrudSession().retrieve(IProject.class);
        if (projects.size() > 1) {
            //ics.close();
            throw new IllegalArgumentException("Found more than one instance of IProject in project database!");
        } else if (projects.size() == 0) {
            throw new IllegalArgumentException("Failed to find an instance of IProject in project database!");
        }
        try {
            IProject project = projects.iterator().next();
            //initialize project listeners
            for(IPlate384 ipl:project.get384Plates()) {
                for(IWell384 well:ipl.getWells()) {
                    well.addPropertyChangeListener(this);
                }
                ipl.addPropertyChangeListener(this);
            }
            for(IPlate96 ipl:project.get96Plates()) {
                for(IWell96 well:ipl.getWells()) {
                    well.addPropertyChangeListener(this);
                }
                ipl.addPropertyChangeListener(this);
            }
            for(ILogicalGelGroup ilgg:project.getGelGroups()) {
                for(IBioRepGelGroup blgg:ilgg.getGelGroups()) {
                    for(ITechRepGelGroup trgg:blgg.getGelGroups()) {
                        for(IGel gel:trgg.getGels()) {
                            for(ISpot spot:gel.getSpots()) {
                                spot.addPropertyChangeListener(this);
                            }
                            gel.addPropertyChangeListener(this);
                        }
                        trgg.addPropertyChangeListener(this);
                    }
                    blgg.addPropertyChangeListener(this);
                }
                ilgg.addPropertyChangeListener(this);
            }
            for(ISpotGroup sg:project.getSpotGroups()) {
                sg.addPropertyChangeListener(this);
            }

            //ics.close();
            return project;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Exception: {0}", e.getLocalizedMessage());
        } finally {
            //ics.close();
        }
//            return myProject;
        //}
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not retrieve Project instance from database");
        return null;
    }

    @Override
    public void activate(final URL url) {
//        if (this.icp != null) {
//            this.icp.close();
//        }
        if (this.dblocation != null) {
            Exceptions.printStackTrace(new IllegalStateException("ProteomicProject already activated for " + this.dblocation));
            //throw new IllegalArgumentException("Activate already called! DB location is: " + this.dblocation);
        } else {
            this.dblocation = url;
        }
    }

    @Override
    public FileObject getProjectDirectory() {
        try {
            return FileUtil.toFileObject(new File(dblocation.toURI()).getParentFile());
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    @Override
    public Lookup getLookup() {
        if (lookup == null) {
            lookup = new AbstractLookup(instanceContent);
            instanceContent.add(new ProjectState() {

                @Override
                public void markModified() {
                    instanceContent.add(new ProjectSaveCookie());
                }

                @Override
                public void notifyDeleted() throws IllegalStateException {
                    throw new UnsupportedOperationException("Deletion is not supported yet.");
                }
            });
            instanceContent.add(new ActionProviderImpl());
            instanceContent.add(new OpenCloseHook());
            instanceContent.add(new Info());
            instanceContent.add(new ProteomicProjectLogicalView(this));
        }
        return lookup;
    }

    @Override
    public void close() {
        closeSession();
    }

    @Override
    public void setProjectState(ProjectState ps) {
        instanceContent.remove(getLookup().lookup(ProjectState.class));
        instanceContent.add(ps);
    }

    private synchronized void openSession() {
        //closeSession();
        if (icp == null || ics == null) {
            icp = Lookup.getDefault().lookup(ICrudProviderFactory.class).getCrudProvider(dblocation, new NoAuthCredentials());//new DB4oCrudProvider(pdbf, new NoAuthCredentials(), this.getClass().getClassLoader());
            Logger.getLogger(getClass().getName()).log(Level.INFO, "Using {0} as CRUD provider", icp.getClass().getName());
//            icp.open();
            ics = icp.createSession();
            ics.open();
            if (this.activeProject == null) {
                this.activeProject = getFromDB();
                this.activeProject.addPropertyChangeListener(this);
            } else {
                Collection<IProject> projects = ics.retrieve(IProject.class);
                if (!projects.isEmpty()) {
                    JPanel jp = new JPanel();
                    jp.add(new JLabel("A project is already present in the database, discard and overwrite?"), BorderLayout.CENTER);
                    NotifyDescriptor nd = new NotifyDescriptor(
                            jp, // instance of your panel
                            "Overwrite project in database?", // title of the dialog
                            NotifyDescriptor.YES_NO_OPTION, // it is Yes/No dialog ...
                            NotifyDescriptor.QUESTION_MESSAGE, // ... of a question type => a question mark icon
                            null, // we have specified YES_NO_OPTION => can be null, options specified by L&F,
                            // otherwise specify options as:
                            //     new Object[] { NotifyDescriptor.YES_OPTION, ... etc. },
                            NotifyDescriptor.NO_OPTION // default option is "Yes"
                            );

                    Object returnValue = DialogDisplayer.getDefault().notify(nd);
                    if (returnValue == NotifyDescriptor.YES_OPTION) {
                        ics.delete(projects);
                        ics.create(Arrays.asList(activeProject));
                    } else {
                        activeProject = projects.iterator().next();
                    }
                }
            }
        }


    }

    private synchronized void closeSession() {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Closing database for project ", dblocation);
        getCrudSession().update(Arrays.asList(this.activeProject));
        if (ics != null) {
            ics.close();
            ics = null;
        }
        if (icp != null) {
            icp.close();
            icp = null;
        }
        this.activeProject.removePropertyChangeListener(this);
        this.activeProject = null;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        getLookup().lookup(Info.class).addPropertyChangeListener(pcl);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        getLookup().lookup(Info.class).removePropertyChangeListener(pcl);
    }

    private final class OpenCloseHook extends ProjectOpenedHook {

        @Override
        protected void projectOpened() {
            openSession();
        }

        @Override
        protected void projectClosed() {
            closeSession();
        }
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
            return new ImageIcon(ImageUtilities.loadImage(ICON_PATH));
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

        protected void firePropertyChange(String name, Object before, Object after) {
            this.pcs.firePropertyChange(name, before, after);
        }
    }

    @Override
    public List<ILogicalGelGroup> getGelGroups() {
        return activeProject.getGelGroups();
    }

    @Override
    public void setGelGroups(List<ILogicalGelGroup> groups) {
        activeProject.setGelGroups(groups);
        persist();
    }

    @Override
    public void addGelGroup(ILogicalGelGroup group) {
        activeProject.addGelGroup(group);
        persist();
    }

    @Override
    public List<ISpotGroup> getSpotGroups() {
        return activeProject.getSpotGroups();
    }

    @Override
    public void setSpotGroups(List<ISpotGroup> groups) {
        activeProject.setSpotGroups(groups);
        persist();
    }

    @Override
    public void addSpotGroup(ISpotGroup group) {
        activeProject.addSpotGroup(group);
        persist();
    }

    @Override
    public String getName() {
        return activeProject.getName();
    }

    @Override
    public void setName(String name) {
        activeProject.setName(name);
        persist();
    }

    @Override
    public String getOwner() {
        return activeProject.getOwner();
    }

    @Override
    public void setOwner(String owner) {
        activeProject.setOwner(owner);
        persist();
    }

    @Override
    public String getDescription() {
        return activeProject.getDescription();
    }

    @Override
    public void setDescription(String description) {
        activeProject.setDescription(description);
        persist();
    }

    @Override
    public List<IPlate96> get96Plates() {
        return activeProject.get96Plates();
    }

    @Override
    public void set96Plates(List<IPlate96> plates) {
        activeProject.set96Plates(plates);
        persist();
    }

    @Override
    public List<IPlate384> get384Plates() {
        return activeProject.get384Plates();
    }

    @Override
    public void set384Plates(List<IPlate384> plates) {
        activeProject.set384Plates(plates);
        persist();
    }

    @Override
    public void add96Plate(IPlate96 plate) {
        activeProject.add96Plate(plate);
        persist();
    }

    @Override
    public void add384Plate(IPlate384 plate) {
        activeProject.add384Plate(plate);
        persist();
    }

    @Override
    public void setProjectData(IProject project) {
        if (activeProject != null) {
            persist();
        }
        activeProject = project;
        persist();
        //activeProject = retrieve(IProject.class);
    }

    @Override
    public String toString() {
        if (activeProject != null) {
            return activeProject.toString();
        }
//        if (icp == null || ics == null || activeProject == null) {
        return "<NOT INITIALIZED>";
//        }
//        return activeProject.toString();
    }
}
