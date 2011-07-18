package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kotte
 */
public class Plate96ViewerTopComponentTest {

    public Plate96ViewerTopComponentTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getDefault method, of class Plate96ViewerTopComponent.
     */
    @Test
    public void testGetDefault() {
        System.out.println("getDefault");
        Plate96ViewerTopComponent result = Plate96ViewerTopComponent.getDefault();
        assertNotNull(result);
    }

    /**
     * Test of findInstance method, of class Plate96ViewerTopComponent.
     */
    @Test
    public void testFindInstance() {
        System.out.println("findInstance");
        Plate96ViewerTopComponent result = Plate96ViewerTopComponent.findInstance();
        assertNotNull(result);
    }

}