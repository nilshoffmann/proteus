/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kotte
 */
public class GelTest {

    public GelTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getDescription method, of class Gel.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Gel instance = new Gel();
        String expResult = "I have no description";
        String result = instance.getDescription();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}