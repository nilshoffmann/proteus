/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.ExceptionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import net.sf.maltcms.io.xml.serialization.api.ActivatableArrayListConverter;
import net.sf.maltcms.io.xml.serialization.api.GeneralPathConverter;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Cancellable;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;

@ActionID(category = "Project",
id = "de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions.ExportToXml")
@ActionRegistration(displayName = "#CTL_ExportToXml")
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1750)
})
@Messages("CTL_ExportToXml=Backup to Xml")
public final class ExportToXml implements ActionListener {

    private final IProteomicProject context;

    public ExportToXml(IProteomicProject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        ExportToXmlCallable runnable = new ExportToXmlCallable(context);
        final ProgressHandle handle = ProgressHandleFactory.createHandle(
                "Exporting Project to XML", runnable);
        runnable.setProgressHandle(handle);
        final RequestProcessor rp = new RequestProcessor(
                "Exporting Project to XML");
        Future<File> task = rp.submit(runnable);
    }

    private class ExportToXmlCallable implements Callable<File>, Cancellable, ExceptionListener {

        private final IProteomicProject context;
        private ProgressHandle handle;

        public ExportToXmlCallable(IProteomicProject context) {
            this.context = context;
        }

        public void setProgressHandle(ProgressHandle handle) {
            this.handle = handle;
        }

        @Override
        public File call() throws Exception {
            handle.start();
            handle.switchToIndeterminate();
            FileObject projectDir = context.getProjectDirectory();
            File output = new File(FileUtil.toFile(projectDir), "backup");
            output.mkdirs();
            IProject project = context.getLookup().lookup(IProject.class);
            project.toFullyRecursiveString();
            System.out.println("Project: " + project.toString());
            File outputFile = new File(output, project.getName() + ".xml");
            
            XStream xstream = new XStream(new StaxDriver());
            //xstream.registerConverter(new FileConverter());
            xstream.registerConverter(new GeneralPathConverter());
            //xstream.registerConverter(new ActivatableArrayListConverter());
            xstream.toXML(project, new BufferedOutputStream(new FileOutputStream(outputFile)));
//            
//            try {
//                XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
//                        new FileOutputStream(outputFile)));
//                encoder.setExceptionListener(this);
//                //remove any previously retrieved BeanInfo objects
//                Introspector.flushCaches();
//                for (IPersistenceDelegateRegistration registration : Lookup.getDefault().lookupAll(IPersistenceDelegateRegistration.class)) {
//                    registration.registerPersistenceDelegates(encoder);
//                }
//                encoder.writeObject(project);
//                encoder.close();
//            } catch (FileNotFoundException e) {
//            } finally {
//                handle.finish();
//            }
            handle.finish();
            return outputFile;
        }

        @Override
        public boolean cancel() {
            throw new UnsupportedOperationException("Cancellation not supported!");
        }

        @Override
        public void exceptionThrown(Exception excptn) {
            Exceptions.printStackTrace(excptn);
        }
    }
}
