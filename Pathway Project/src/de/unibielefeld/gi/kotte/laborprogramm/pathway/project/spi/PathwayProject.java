package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.spi;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api.IPathwayProject;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.sf.maltcms.chromaui.db.api.ICrudProvider;
import net.sf.maltcms.chromaui.db.api.ICrudProviderFactory;
import net.sf.maltcms.chromaui.db.api.ICrudSession;
import net.sf.maltcms.chromaui.db.api.NoAuthCredentials;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.spi.project.ActionProvider;
import org.netbeans.spi.project.DeleteOperationImplementation;
import org.netbeans.spi.project.ProjectState;
import org.netbeans.spi.project.ui.ProjectOpenedHook;
import org.netbeans.spi.project.ui.support.DefaultProjectOperations;
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
public class PathwayProject implements IPathwayProject {

    ICrudProvider icp = null;
    ICrudSession ics = null;
    //active project should not be available in lookup
//    IProject activeProject = null;
    InstanceContent instanceContent = new InstanceContent();
    Lookup lookup = null;
    URL dblocation = null;
//    SaveCookie singletonSaveCookie = null;
    PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    File lock;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/pathway/project/resources/PathwayProjectIcon.png";

    public PathwayProject() {
        getLookup();
    }

    private ICrudSession getCrudSession() {
        return ics;
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        System.out.println("Received property change event in PathwayProject!");
        pcs.firePropertyChange(pce);
    }

    @Override
    public synchronized <T> Collection<T> retrieve(Class<T> c) {
        Collection<T> coll = getCrudSession().retrieve(c);
        return coll;
//        T t = coll.iterator().next();
//        return t;
    }

    @Override
    public <T> void store(T... t) {
        getCrudSession().create(t);
    }

    @Override
    public void delete(Object... obj) {
        getCrudSession().delete(obj);
    }

    @Override
    public void activate(final URL url) {
        if (this.dblocation != null) {
            Exceptions.printStackTrace(new IllegalStateException(
                    "ProteomicProject already activated for " + this.dblocation));
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
            instanceContent = new InstanceContent();
            lookup = new AbstractLookup(instanceContent);
            instanceContent.add(this);
            instanceContent.add(new ProjectState() {
                @Override
                public void markModified() {
//                    System.out.println("Mark modified called on ProjectState");
//                    instanceContent.add(new ProjectSaveCookie());
                    throw new UnsupportedOperationException(
                            "Mark modified is not supported yet.");
                }

                @Override
                public void notifyDeleted() throws IllegalStateException {
                    System.out.println("Deleting project!");
                }
            });
            instanceContent.add(new ActionProviderImpl());
            instanceContent.add(new OpenCloseHook());
            instanceContent.add(new Info());
            instanceContent.add(new PathwayProjectLogicalView(this));
            instanceContent.add(new DeleteOperationImplementation() {
                @Override
                public void notifyDeleting() throws IOException {
                }

                @Override
                public void notifyDeleted() throws IOException {
                }

                @Override
                public List<FileObject> getMetadataFiles() {
//                    throw new UnsupportedOperationException("Not supported yet.");
                    return Collections.emptyList();
                }

                @Override
                public List<FileObject> getDataFiles() {
                    ArrayList<FileObject> dataFiles = new ArrayList<FileObject>();
                    Enumeration<? extends FileObject> enumeration = getProjectDirectory().
                            getChildren(true);
                    while (enumeration.hasMoreElements()) {
                        dataFiles.add(enumeration.nextElement());
                    }
                    return dataFiles;
                }
            });
        }
        return lookup;
    }

    @Override
    public void close() {
        closeSession();
    }

    @Override
    public void setProjectState(ProjectState ps) {
        System.out.println("Set project state called");
        instanceContent.remove(getLookup().lookup(ProjectState.class));
        instanceContent.add(ps);
    }

    private synchronized void openSession() {
        getLookup();
        try {
            File lockFile = new File(new File(dblocation.toURI()).getParentFile(), "lock");
            if (lockFile.exists()) {
                closeSession();
                throw new RuntimeException("Project database is already in use. If there is no open project, please delete the lock file " + lockFile.getAbsolutePath());
            }
            try {
                lockFile.createNewFile();
                lockFile.deleteOnExit();
                lock = lockFile;
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (icp == null || ics == null) {
            try {
                icp = Lookup.getDefault().lookup(ICrudProviderFactory.class).
                        getCrudProvider(dblocation, new NoAuthCredentials(), Lookup.getDefault().lookup(
                        ClassLoader.class));
                Logger.getLogger(getClass().getName()).log(Level.INFO,
                        "Using {0} as CRUD provider", icp.getClass().getName());
                ics = icp.createSession();
                ics.open();
//                getLookup();
            } catch (RuntimeException re) {
                closeSession();
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Caught exception while opening database!", re);
                throw new RuntimeException("Project database is already in use. If there is no open project, please delete the lock file!");
            }
        }
    }

    private synchronized void closeSession() {
        Logger.getLogger(getClass().getName()).log(Level.INFO,
                "Closing session for project ", dblocation);
        //we are saving anyway, so remove SaveCookie
        //instanceContent.remove(SaveCookie.class);
//        getCrudSession()
        //allow gc of all session related objects
        if (ics != null) {
//            ics.update(Arrays.asList(activeProject));
            ics.close();
            ics = null;
        }
        if (icp != null) {
            icp.close();
            icp = null;
        }
        //instanceContent.remove(this);
//        Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().closeTopComponentsFor(this);
        if (lock != null && lock.exists()) {
            lock.delete();
        }
        lookup = null;
        instanceContent = null;
        //CentralLookup.getDefault().remove(this);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
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
            ActionProvider.COMMAND_COPY};

        @Override
        public String[] getSupportedActions() {
            return supported;
        }

        @Override
        public void invokeAction(String string, Lookup lookup) throws IllegalArgumentException {
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_DELETE)) {
                DefaultProjectOperations.performDefaultDeleteOperation(
                        PathwayProject.this);
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_COPY)) {
                DefaultProjectOperations.performDefaultCopyOperation(
                        PathwayProject.this);
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

        private PropertyChangeSupport pcs = new PropertyChangeSupport(
                getProject());

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
            return PathwayProject.this;
        }

        protected void firePropertyChange(String name, Object before,
                Object after) {
            this.pcs.firePropertyChange(name, before, after);
        }
    }
}
