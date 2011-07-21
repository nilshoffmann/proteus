package de.unibielefeld.gi.kotte.laborprogramm.project.spi;

//import de.unibielefeld.gi.kotte.laborprogramm.centralLookup.CentralLookup;
import de.unibielefeld.gi.kotte.laborprogramm.centralLookup.CentralLookup;
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
import net.sf.maltcms.chromaui.db.api.exceptions.AuthenticationException;
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
    //active project should not be available in lookup
    IProject activeProject = null;
    InstanceContent instanceContent = new InstanceContent();
    Lookup lookup = null;
    URL dblocation = null;
    SaveCookie singletonSaveCookie = null;
    private final static String ICON_PATH = "de/unibielefeld/gi/kotte/laborprogramm/project/resources/ProjectIcon.png";

    public ProteomicProject() {
        getLookup();
    }

    public ProteomicProject(IProject project) {
        this();
        this.activeProject = project;
    }

    private ICrudSession getCrudSession() {
        openSession();
        return ics;
    }

//    private synchronized void persist() {
////        getLookup().lookup(ProjectState.class).markModified();
//        //instanceContent.remove(activeProject);
//        instanceContent.add(new ProjectSaveCookie());
//    }
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        System.out.println("Received property change event");
//        persist();
        //due to a but in Netbeans RCP, multiple SaveCookie instances in
        //lookup will disable the save action
//        if(singletonSaveCookie==null) {
//            singletonSaveCookie = new ProjectSaveCookie();
//            instanceContent.add(singletonSaveCookie);
//        }
//        if(ics!=null) {
//            ics.update(Arrays.asList(this.activeProject));
//            System.out.println("Persisting state of "+pce.getPropertyName()+" "+pce.getNewValue());
////            ics.create(Arrays.asList(pce.getNewValue()));
//            //activeProject = getFromDB();
//        }
//        getLookup().lookup(ProjectState.class).markModified();
    }

    private IProject showOverwriteDatabaseDialog(IProject project) throws AuthenticationException {
        if (project!=null && activeProject != null) {
            JPanel jp = new JPanel();
            jp.add(new JLabel(
                    "A project is already present in the database, discard and overwrite?"),
                    BorderLayout.CENTER);
            NotifyDescriptor nd = new NotifyDescriptor(jp,
                    "Overwrite project in database?",
                    NotifyDescriptor.YES_NO_OPTION,
                    NotifyDescriptor.QUESTION_MESSAGE, null,
                    NotifyDescriptor.NO_OPTION);
            Object returnValue = DialogDisplayer.getDefault().notify(nd);
            if (returnValue == NotifyDescriptor.YES_OPTION) {
                ics.delete(Arrays.asList(activeProject));
                ics.create(Arrays.asList(project));
                //instanceContent.remove(singletonSaveCookie);
                //singletonSaveCookie = null;
                return getFromDB();
            } else {
                //instanceContent.remove(singletonSaveCookie);
                //singletonSaveCookie = null;
                return project;
            }
        } else if (project!=null && activeProject == null) {
            ics.create(Arrays.asList(project));
            //instanceContent.remove(singletonSaveCookie);
            //singletonSaveCookie = null;
            return getFromDB();
        }
        return null;
    }

//    public final class ProjectSaveCookie implements SaveCookie {
//
//        @Override
//        public void save() throws IOException {
//            showOverwriteDatabaseDialog(Arrays.asList(activeProject));
//        }
//    }

    public synchronized <T> T retrieve(Class<T> c) {
        Collection<T> coll = getCrudSession().retrieve(c);
        T t = coll.iterator().next();
        return t;
    }

    private IProject getFromDB() {
        Collection<IProject> projects = getCrudSession().retrieve(IProject.class);
        if (projects.size() > 1) {
            throw new IllegalArgumentException(
                    "Found more than one instance of IProject in project database!");
        } else if (projects.isEmpty()) {
            throw new IllegalArgumentException(
                    "Failed to find an instance of IProject in project database!");
        }
        return projects.iterator().next();
//        try {
//            IProject project = projects.iterator().next();
//            //initialize project listeners
//            for (IPlate384 ipl : project.get384Plates()) {
//                for (IWell384 well : ipl.getWells()) {
//                    well.addPropertyChangeListener(this);
//                }
//                ipl.addPropertyChangeListener(this);
//            }
//            System.out.println("Found "+project.get96Plates().size()+" 96WellPlates");
//            for (IPlate96 ipl : project.get96Plates()) {
//                for (IWell96 well : ipl.getWells()) {
//                    well.addPropertyChangeListener(this);
//                }
//                ipl.addPropertyChangeListener(this);
//            }
//            for (ILogicalGelGroup ilgg : project.getGelGroups()) {
//                for (IBioRepGelGroup blgg : ilgg.getGelGroups()) {
//                    for (ITechRepGelGroup trgg : blgg.getGelGroups()) {
//                        for (IGel gel : trgg.getGels()) {
//                            for (ISpot spot : gel.getSpots()) {
//                                spot.addPropertyChangeListener(this);
//                            }
//                            gel.addPropertyChangeListener(this);
//                        }
//                        trgg.addPropertyChangeListener(this);
//                    }
//                    blgg.addPropertyChangeListener(this);
//                }
//                ilgg.addPropertyChangeListener(this);
//            }
//            for (ISpotGroup sg : project.getSpotGroups()) {
//                sg.addPropertyChangeListener(this);
//            }
//            project.addPropertyChangeListener(this);
//            return project;
//        } catch (Exception e) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE,
//                    "Exception: {0}", e.getLocalizedMessage());
//        } finally {
//        }
//        Logger.getLogger(getClass().getName()).log(Level.SEVERE,
//                "Could not retrieve Project instance from database");
//        return null;
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
            return FileUtil.toFileObject(new File(dblocation.toURI()).
                    getParentFile());
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
//                    System.out.println("Mark modified called on ProjectState");
//                    instanceContent.add(new ProjectSaveCookie());
                    throw new UnsupportedOperationException(
                            "Mark modified is not supported yet.");
                }

                @Override
                public void notifyDeleted() throws IllegalStateException {
                    throw new UnsupportedOperationException(
                            "Deletion is not supported yet.");
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
        System.out.println("Set project state called");
        instanceContent.remove(getLookup().lookup(ProjectState.class));
        instanceContent.add(ps);
    }

    private synchronized void openSession() {
        if (icp == null || ics == null) {
            icp = Lookup.getDefault().lookup(ICrudProviderFactory.class).
                    getCrudProvider(dblocation, new NoAuthCredentials());
            Logger.getLogger(getClass().getName()).log(Level.INFO,
                    "Using {0} as CRUD provider", icp.getClass().getName());
            ics = icp.createSession();
            ics.open();
            if (activeProject == null) {
                activeProject = getFromDB();
            }else{
                showOverwriteDatabaseDialog(activeProject);
//                throw new IllegalStateException("activeProject database is not null! Call close() before opening a new session!");
            }
//            } else {
//                this.activeProject = showOverwriteDatabaseDialog(ics.retrieve(IProject.class));
//            }
            instanceContent.add(activeProject);
            instanceContent.add(this);
        }
    }

    private synchronized void closeSession() {
        Logger.getLogger(getClass().getName()).log(Level.INFO,
                "Closing database for project ", dblocation);
        //we are saving anyway, so remove SaveCookie
        //instanceContent.remove(SaveCookie.class);
        getCrudSession().update(Arrays.asList(activeProject));
        //allow gc of all session related objects
        if (ics != null) {
            ics.close();
            ics = null;
        }
        if (icp != null) {
            icp.close();
            icp = null;
        }
        activeProject.removePropertyChangeListener(this);
        instanceContent.remove(activeProject);
        instanceContent.remove(this);
        activeProject = null;
        CentralLookup.getDefault().remove(this);
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

    @Override
    public void setProjectData(IProject project) {
        showOverwriteDatabaseDialog(project);
        closeSession();
        openSession();
    }

    @Override
    public String toString() {
        if (activeProject != null) {
            return "IProteomicProject: "+activeProject.getName();
        }
        return "<NOT INITIALIZED>";
    }
}
