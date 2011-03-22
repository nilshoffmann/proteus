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
     * Test of getDefault method, of class PlateViewerTopComponent.
     */
    @Test
    public void testGetDefault() {
        System.out.println("getDefault");
        PlateViewerTopComponent result = PlateViewerTopComponent.getDefault();
        assertNotNull(result);
    }

    /**
     * Test of findInstance method, of class PlateViewerTopComponent.
     */
    @Test
    public void testFindInstance() {
        System.out.println("findInstance");
        PlateViewerTopComponent result = PlateViewerTopComponent.findInstance();
        assertNotNull(result);
    }

}