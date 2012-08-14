/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.project.spi.factory.ProteomicProjectFactory2;
import de.unibielefeld.gi.kotte.laborprogramm.project.spi.factory.ProteomikProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.XMLPersistenceFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ui.OpenProjects;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Cancellable;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;

@ActionID(category = "Project",
id = "de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions.LoadFromXml")
@ActionRegistration(displayName = "#CTL_LoadFromXml")
@ActionReferences({
//    @ActionReference(path = "Menu/File", position = 1750)
})
@Messages("CTL_LoadFromXml=Load Backup from Xml")
public final class LoadFromXml implements ActionListener {

    private final IProteomicProject context;

    public LoadFromXml(IProteomicProject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        ImportFromXmlCallable runnable = new ImportFromXmlCallable(context);
        final ProgressHandle handle = ProgressHandleFactory.createHandle(
                "Restoring Project from XML", runnable);
        runnable.setProgressHandle(handle);
        final RequestProcessor rp = new RequestProcessor(
                "Restoring Project from XML");
        Future<IProject> task = rp.submit(runnable);
    }

    private class ImportFromXmlCallable implements Callable<IProject>, Cancellable, ExceptionListener {

        private final IProteomicProject context;
        private ProgressHandle handle;

        public ImportFromXmlCallable(IProteomicProject context) {
            this.context = context;
        }

        public void setProgressHandle(ProgressHandle handle) {
            this.handle = handle;
        }

        @Override
        public IProject call() throws Exception {
            handle.start();
            handle.switchToIndeterminate();
            FileObject projectDir = context.getProjectDirectory();
            File output = new File(FileUtil.toFile(projectDir), "backup");
            output.mkdirs();
            IProject project = context.getLookup().lookup(IProject.class);
            OpenProjects.getDefault().close(new Project[]{context});
            File backupFile = new File(output, project.getName() + ".xml");
            System.out.println("Project: " + project.toString());
            IProject projectFromBackup = project;
            try {
                XMLPersistenceFactory xp = new XMLPersistenceFactory();
                projectFromBackup = xp.unmarshal(backupFile);
//                JAXBContext jc = JAXBContext.newInstance(IProject.class);
//                StreamSource xml = new StreamSource(backupFile);
//                Unmarshaller unmarshaller = jc.createUnmarshaller();
//                JAXBElement<IProject> je1 = unmarshaller.unmarshal(xml, IProject.class);
//                projectFromBackup = je1.getValue();
//                XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(backupFile)), null, this, Lookup.getDefault().lookup(ClassLoader.class));
//                projectFromBackup = (IProject) decoder.readObject();
                ProteomicProjectFactory2 ppf = new ProteomicProjectFactory2();
                File oldDatabaseFile = new File(output.getParentFile(), ProteomikProjectFactory.PROJECT_FILE);
                oldDatabaseFile.renameTo(new File(output.getParent(), ProteomikProjectFactory.PROJECT_FILE + ".bak"));
                IProteomicProject proj = ppf.createProject(output.getParentFile(), projectFromBackup);
                proj.close();

                try {
                    Project nbproject;
                    nbproject = ProjectManager.getDefault().findProject(projectDir);
                    Project[] array = new Project[1];
                    array[0] = nbproject;
                    OpenProjects.getDefault().open(array, false, true);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IllegalArgumentException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } catch (Exception e) {
                Exceptions.printStackTrace(e);
            } finally {
                handle.finish();
            }
            return project;
        }

        @Override
        public boolean cancel() {
            throw new UnsupportedOperationException("Cancellation not supported!");
        }

        @Override
        public void exceptionThrown(Exception e) {
            Exceptions.printStackTrace(e);
        }
    }
}
