/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.plateViewer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kotte
 */
public class PlateViewerTopComponentTest {

    public PlateViewerTopComponentTest() {
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