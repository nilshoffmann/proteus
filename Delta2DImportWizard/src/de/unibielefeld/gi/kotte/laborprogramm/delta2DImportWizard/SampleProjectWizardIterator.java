package de.unibielefeld.gi.kotte.laborprogramm.delta2DImportWizard;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;


/**
 * Descriptor for the Sample Project Wizard.
 *
 * @author Konstantin Otte
 */
public class SampleProjectWizardIterator implements WizardDescriptor.ProgressInstantiatingIterator<FileObject> {

    private int index;
    private WizardDescriptor.Panel[] panels;
    private WizardDescriptor wiz;
    private static final ProjectImportWizardAction iwa = new ProjectImportWizardAction();

    public static SampleProjectWizardIterator createIterator() {
        return new SampleProjectWizardIterator();
    }

    @Override
    public Set<FileObject> instantiate(ProgressHandle handle) throws IOException {
        handle.start();
        handle.switchToIndeterminate();
        Set<FileObject> resultSet = new LinkedHashSet<FileObject>();
        File parentFile = iwa.createProject(wiz);
        if(parentFile!=null) {
            FileObject fobj = FileUtil.toFileObject(parentFile);
            resultSet.add(fobj);
        }
        handle.finish();
        return resultSet;
    }
    
    @Override
    public Set<FileObject> instantiate() throws IOException {
        return instantiate(ProgressHandleFactory.createHandle("Delta2D Project Import"));
    }

    @Override
    public void initialize(WizardDescriptor wiz) {
        this.wiz = wiz;
        index = 0;
        panels = iwa.getPanels();
    }

    @Override
    public void uninitialize(WizardDescriptor wiz) {
        this.wiz.putProperty("projdir", null);
        this.wiz.putProperty("name", null);
        this.wiz = null;
        panels = null;
    }

    @Override
    public String name() {
        return MessageFormat.format("{0} of {1}",
                new Object[]{new Integer(index + 1), new Integer(panels.length)});
    }

    @Override
    public boolean hasNext() {
        return index < panels.length - 1;
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public void nextPanel() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        index++;
    }

    @Override
    public void previousPanel() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        index--;
    }

    @Override
    public WizardDescriptor.Panel current() {
        return panels[index];
    }

    @Override
    public final void addChangeListener(ChangeListener l) {
    }

    @Override
    public final void removeChangeListener(ChangeListener l) {
    }

}
