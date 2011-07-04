package de.unibielefeld.gi.kotte.laborprogramm.ImportWizard;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

public class SampleProjectWizardIterator implements WizardDescriptor./*Progress*/InstantiatingIterator {

    private int index;
    private WizardDescriptor.Panel[] panels;
    private WizardDescriptor wiz;
    private static final ImportWizardAction iwa = new ImportWizardAction();

//    public SampleProjectWizardIterator() {
//        iwa = new ImportWizardAction();
//    }

    public static SampleProjectWizardIterator createIterator() {
        return new SampleProjectWizardIterator();
    }

//    private WizardDescriptor.Panel[] createPanels() {
//        return iwa.getPanels();//new WizardDescriptor.Panel[]{
////                    new SampleProjectWizardPanel(),};
//    }

//    private String[] createSteps() {
//        return new String[]{
//                    NbBundle.getMessage(SampleProjectWizardIterator.class, "LBL_CreateProjectStep")
//                };
//    }

    @Override
    public Set/*<FileObject>*/ instantiate(/*ProgressHandle handle*/) throws IOException {
        Set<FileObject> resultSet = new LinkedHashSet<FileObject>();
//        File dirF = FileUtil.normalizeFile((File) wiz.getProperty("projdir"));
//        dirF.mkdirs();
//
//        FileObject template = Templates.getTemplate(wiz);
//        FileObject dir = FileUtil.toFileObject(dirF);
//        unZipFile(template.getInputStream(), dir);
//
//        // Always open top dir as a project:
//        resultSet.add(dir);
//        // Look for nested projects to open as well:
//        Enumeration<? extends FileObject> e = dir.getFolders(true);
//        while (e.hasMoreElements()) {
//            FileObject subfolder = e.nextElement();
//            if (ProjectManager.getDefault().isProject(subfolder)) {
//                resultSet.add(subfolder);
//            }
//        }
//
//        File parent = dirF.getParentFile();
//        if (parent != null && parent.exists()) {
//            ProjectChooser.setProjectsFolder(parent);
//        }

        File parentFile = iwa.createProject(wiz);
        if(parentFile!=null) {
            resultSet.add(FileUtil.toFileObject(parentFile));
        }

        return resultSet;
    }

    @Override
    public void initialize(WizardDescriptor wiz) {
        this.wiz = wiz;
        index = 0;
        panels = iwa.getPanels();//createPanels();
        // Make sure list of steps is accurate.
//        String[] steps = createSteps();
//        for (int i = 0; i < panels.length; i++) {
//            Component c = panels[i].getComponent();
//            if (steps[i] == null) {
//                // Default step name to component name of panel.
//                // Mainly useful for getting the name of the target
//                // chooser to appear in the list of steps.
//                steps[i] = c.getName();
//            }
//            if (c instanceof JComponent) { // assume Swing components
//                JComponent jc = (JComponent) c;
//                // Step #.
//                // TODO if using org.openide.dialogs >= 7.8, can use WizardDescriptor.PROP_*:
//                jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(i));
//                // Step name (actually the whole list for reference).
//                jc.putClientProperty("WizardPanel_contentData", steps);
//            }
//        }
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

    // If nothing unusual changes in the middle of the wizard, simply:
    @Override
    public final void addChangeListener(ChangeListener l) {
    }

    @Override
    public final void removeChangeListener(ChangeListener l) {
    }

//    private static void unZipFile(InputStream source, FileObject projectRoot) throws IOException {
//        try {
//            ZipInputStream str = new ZipInputStream(source);
//            ZipEntry entry;
//            while ((entry = str.getNextEntry()) != null) {
//                if (entry.isDirectory()) {
//                    FileUtil.createFolder(projectRoot, entry.getName());
//                } else {
//                    FileObject fo = FileUtil.createData(projectRoot, entry.getName());
//                    if ("nbproject/project.xml".equals(entry.getName())) {
//                        // Special handling for setting name of Ant-based projects; customize as needed:
//                        filterProjectXML(fo, str, projectRoot.getName());
//                    } else {
//                        writeFile(str, fo);
//                    }
//                }
//            }
//        } finally {
//            source.close();
//        }
//    }
//
//    private static void writeFile(ZipInputStream str, FileObject fo) throws IOException {
//        OutputStream out = fo.getOutputStream();
//        try {
//            FileUtil.copy(str, out);
//        } finally {
//            out.close();
//        }
//    }
//
//    private static void filterProjectXML(FileObject fo, ZipInputStream str, String name) throws IOException {
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            FileUtil.copy(str, baos);
//            Document doc = XMLUtil.parse(new InputSource(new ByteArrayInputStream(baos.toByteArray())), false, false, null, null);
//            NodeList nl = doc.getDocumentElement().getElementsByTagName("name");
//            if (nl != null) {
//                for (int i = 0; i < nl.getLength(); i++) {
//                    Element el = (Element) nl.item(i);
//                    if (el.getParentNode() != null && "data".equals(el.getParentNode().getNodeName())) {
//                        NodeList nl2 = el.getChildNodes();
//                        if (nl2.getLength() > 0) {
//                            nl2.item(0).setNodeValue(name);
//                        }
//                        break;
//                    }
//                }
//            }
//            OutputStream out = fo.getOutputStream();
//            try {
//                XMLUtil.write(doc, out, "UTF-8");
//            } finally {
//                out.close();
//            }
//        } catch (Exception ex) {
//            Exceptions.printStackTrace(ex);
//            writeFile(str, fo);
//        }
//
//    }
}
