package de.unibielefeld.gi.kotte.laborprogramm.project;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProjectFactory;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kotte
 */
public class ProjectTest {

    public ProjectTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testProject() {
        IProteomicProjectFactory ippf = Lookup.getDefault().lookup(IProteomicProjectFactory.class);
        assertNotNull(ippf);
        String path = "/de/unibielefeld/gi/kotte/laborprogramm/project/testDirectory";
        File dir = new File("testDirectory");
        dir.deleteOnExit();
        dir.mkdir();
        System.out.println(dir.getAbsolutePath());
        assert (dir.isDirectory());
        IProteomicProject pp = ippf.createProject(dir);
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
        assertNotNull(url);
        pp.activate(url);
    }
}
