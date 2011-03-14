/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.plateViewer;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Dialog;
import javax.swing.JDialog;
import javax.swing.JPanel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPlate;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.PlateFactory;
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
public class PlatePanelTest {

    IPlate plate;

    public PlatePanelTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        plate = PlateFactory.get96WellPlate();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testIcons() {
        final String resourcePath = "/homes/kotte/ProteomikProjekt/";
        Icon wellEmpty = new ImageIcon(resourcePath + "Well01.jpg");
        Icon wellError = new ImageIcon(resourcePath + "Well02.jpg");
        Icon wellOkay  = new ImageIcon(resourcePath + "Well03.jpg");
        JLabel labelEmpty = new JLabel(wellEmpty);
        JLabel labelError = new JLabel(wellEmpty);
        JLabel labelOkay = new JLabel(wellEmpty);
        JPanel panel = new JPanel();
        panel.add(labelEmpty);
        panel.add(labelError);
        panel.add(labelOkay);
    }

    @Test
    public void testPlate() {
        JPanel platePanel = new PlatePanel(plate);
        Dialog dialog = new JDialog();
        dialog.add(platePanel);
        dialog.setVisible(true);
    }

}