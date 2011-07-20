/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Dialog;
import javax.swing.JDialog;
import javax.swing.JPanel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
public class Plate96PanelTest {

    IPlate96 plate;

    public Plate96PanelTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        plate = Lookup.getDefault().lookup(IPlate96.class);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testIcons() {
        final String resourcePath = "/homes/kotte/ProteomikProjekt/";
        Icon wellEmpty = new ImageIcon(resourcePath + "Well01.jpg");
        Icon wellError = new ImageIcon(resourcePath + "Well02.jpg");
        Icon wellOkay = new ImageIcon(resourcePath + "Well03.jpg");
        JLabel labelEmpty = new JLabel(wellEmpty);
        JLabel labelError = new JLabel(wellError);
        JLabel labelOkay = new JLabel(wellOkay);
        JPanel panel = new JPanel();
        panel.add(labelEmpty);
        panel.add(labelError);
        panel.add(labelOkay);
    }

    @Test
    public void testPlate() {
        JPanel platePanel = new Plate96Panel(plate);
        Dialog dialog = new JDialog();
        dialog.add(platePanel);
        dialog.setVisible(true);
    }
}
