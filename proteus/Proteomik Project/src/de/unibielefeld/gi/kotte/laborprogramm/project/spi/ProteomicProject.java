package de.unibielefeld.gi.kotte.laborprogramm.project.spi;

//import de.unibielefeld.gi.kotte.laborprogramm.centralLookup.CentralLookup;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
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
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Konstantin Otte
 */
@org.openide.util.lookup.ServiceProvider(service = Project.class)
public class ProteomicProject implements IProteomicProject {

    private ICrudProvider icp = null;
    private ICrudSession ics = null;
    private IProject activeProject = null;
    private InstanceContent instanceContent = new InstanceContent();
    private Lookup lookup = null;
    private URL dblocation = null;
    private SaveCookie singletonSaveCookie = null;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private FileObject projectDatabaseFile;
    private FileObject parentFile;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/ProjectIcon.png";

    public ProteomicProject() {
        getLookup();
    }

    private ICrudSession getCrudSession() {
        return ics;
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        System.out.println(
                "Received property change event in ProteomicsProject!");
        pcs.firePropertyChange(pce);
    }

//    private IProject showOverwriteDatabaseDialog(IProject project) throws AuthenticationException {
//        if (project != null && activeProject != null) {
//            JPanel jp = new JPanel();
//            jp.add(new JLabel(
//                    "A project is already present in the database, discard and overwrite?"),
//                    BorderLayout.CENTER);
//            NotifyDescriptor nd = new NotifyDescriptor(jp,
//                    "Overwrite project in database?",
//                    NotifyDescriptor.YES_NO_OPTION,
//                    NotifyDescriptor.QUESTION_MESSAGE, null,
//                    NotifyDescriptor.NO_OPTION);
//            Object returnValue = DialogDisplayer.getDefault().notify(nd);
//            if (returnValue == NotifyDescriptor.YES_OPTION) {
//                ics.delete(Arrays.asList(activeProject));
//                ics.create(Arrays.asList(project));
//                //instanceContent.remove(singletonSaveCookie);
//                //singletonSaveCookie = null;
//                return getFromDB();
//            } else {
//                //instanceContent.remove(singletonSaveCookie);
//                //singletonSaveCookie = null;
//                return project;
//            }
//        } else if (project != null && activeProject == null) {
//            ics.create(Arrays.asList(project));
//            //instanceContent.remove(singletonSaveCookie);
//            //singletonSaveCookie = null;
//            return getFromDB();
//        }
//        return null;
//    }

//    public final class ProjectSaveCookie implements SaveCookie {
//
//        @Override
//        public void save() throws IOException {
//            showOverwriteDatabaseDialog(Arrays.asList(activeProject));
//        }
//    }
    @Override
    public synchronized <T> Collection<T> retrieve(Class<T> c) {
        Collection<T> coll = getCrudSession().retrieve(c);
        return coll;
//        T t = coll.iterator().next();
//        return t;
    }

    @Override
    public <T> void persist(List<T> objects) {
        getCrudSession().create(objects);
    }
    
    @Override
    public <T> void store(T...t) {
        getCrudSession().create(t);
    }
    
    @Override
    public void delete(Object...obj) {
        getCrudSession().delete(obj);
    }

    private IProject getFromDB() {
        Collection<IProject> projects = getCrudSession().retrieve(IProject.class);
        if (projects.size() > 1) {
            throw new IllegalArgumentException(
                    "Found more than one instance of IProject in project database!");
        } else if (projects.isEmpty()) {
			System.out.println("Project db is empty");
//            throw new IllegalArgumentException(
//                    "Failed to find an instance of IProject in project database!");
			return null;
        }
        IProject project = projects.iterator().next();
        project.addPropertyChangeListener(WeakListeners.propertyChange(this, project));
        return project;
    }

    @Override
    public void activate(final URL url) {
//        if (this.dblocation != null) {
//            Exceptions.printStackTrace(new IllegalStateException(
//                    "ProteomicProject already activated for " + this.dblocation));
//        } else {
//            this.dblocation = url;
//        }
		if (this.icp != null) {
            this.icp.close();
        }
        File pdbf;
        try {
            pdbf = new File(url.toURI());
            this.parentFile = FileUtil.toFileObject(pdbf.getParentFile());
            this.projectDatabaseFile = FileUtil.createData(parentFile, pdbf.getName());//FileUtil.toFileObject(pdbf);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public FileObject getProjectDirectory() {
		return this.parentFile;
//        try {
//            return FileUtil.toFileObject(new File(dblocation.toURI()).getParentFile());
//        } catch (URISyntaxException ex) {
//            Exceptions.printStackTrace(ex);
//        }
//        return null;
    }

    @Override
    public Lookup getLookup() {
        if (lookup == null) {
            instanceContent = new InstanceContent();
            lookup = new AbstractLookup(instanceContent);
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
            instanceContent.add(new ProteomicProjectLogicalView(this));
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
//        getLookup();
//        try {
//            File lockFile = new File(new File(dblocation.toURI()).getParentFile(), "lock");
//            if (lockFile.exists()) {
//                closeSession();
//                throw new RuntimeException("Project database is already in use. If there is no open project, please delete the lock file " + lockFile.getAbsolutePath());
//            }
//            try {
//                lockFile.createNewFile();
//                lockFile.deleteOnExit();
//                lock = lockFile;
//            } catch (IOException ex) {
//                Exceptions.printStackTrace(ex);
//            }
//        } catch (URISyntaxException ex) {
//            Exceptions.printStackTrace(ex);
//        }
//        if (icp == null || ics == null) {
//            try {
//                icp = Lookup.getDefault().lookup(ICrudProviderFactory.class).
//                        getCrudProvider(dblocation, new NoAuthCredentials(), Lookup.getDefault().lookup(
//                        ClassLoader.class));
//                Logger.getLogger(getClass().getName()).log(Level.INFO,
//                        "Using {0} as CRUD provider", icp.getClass().getName());
//                ics = icp.createSession();
//                ics.open();
//                if (activeProject == null) {
//                    activeProject = getFromDB();
//                } else {
//                    ics.create(activeProject);
//                }
////                getLookup();
//                instanceContent.add(activeProject);
//            } catch (RuntimeException re) {
//                closeSession();
//                Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Caught exception while opening database!",re);
//                throw new RuntimeException("Project database is already in use. If there is no open project, please delete the lock file!");
//            }
//        }
		try {
            if (icp != null) {
                closeSession();
            }
            if (projectDatabaseFile == null) {
                throw new IllegalStateException(
                        "Project database file not set, please call 'activate(URL url)' with the appropriate location before 'openSession()' is called!");
            }
            icp = Lookup.getDefault().lookup(ICrudProviderFactory.class).
                    getCrudProvider(projectDatabaseFile.getURL(),
                    new NoAuthCredentials(), Lookup.getDefault().lookup(
                    ClassLoader.class));//new DB4oCrudProvider(pdbf, new NoAuthCredentials(), this.getClass().getClassLoader());
            icp.open();
            ics = icp.createSession();
            ics.open();
        } catch (FileStateInvalidException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private synchronized void closeSession() {
        Logger.getLogger(getClass().getName()).log(Level.INFO,
                "Closing session for project ", dblocation);
        Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().closeTopComponentsForProject(this);
		if (icp != null) {
            icp.close();
            icp = null;
        }
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
			IProject current = getFromDB();
			if(current!=null) {
				setActiveProject(current);
			}
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
                        ProteomicProject.this);
            }
            if (string.equalsIgnoreCase(ActionProvider.COMMAND_COPY)) {
                DefaultProjectOperations.performDefaultCopyOperation(
                        ProteomicProject.this);
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
            return ProteomicProject.this;
        }

        protected void firePropertyChange(String name, Object before,
                Object after) {
            this.pcs.firePropertyChange(name, before, after);
        }
    }

	private void setActiveProject(IProject project) {
		if(activeProject!=null) {
			instanceContent.remove(activeProject);
			instanceContent.remove(this);
		}
		IProject old = activeProject;
		activeProject = project;
		instanceContent.add(activeProject);
		instanceContent.add(this);
		pcs.firePropertyChange("activeProject", old , activeProject);
	}
	
    @Override
    public void setProjectData(IProject project) {
        if (activeProject != null) {
            throw new IllegalArgumentException(
                    "Project is already activated, can not replace project!");
        }
		openSession();
		IProject current = getFromDB();
		if(current==null) {
			//        activeProject = project;
			store(project);
			setActiveProject(getFromDB());
		}else{
			setActiveProject(current);
		}
    }

    @Override
    public String toString() {
        if (activeProject != null) {
            return "IProteomicProject: " + activeProject.getName();
        }
        return "<NOT INITIALIZED>";
    }
}