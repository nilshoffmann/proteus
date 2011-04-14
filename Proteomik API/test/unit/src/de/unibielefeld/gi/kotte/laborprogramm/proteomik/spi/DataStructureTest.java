package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.*;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.*;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.*;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.*;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.*;

import org.openide.util.Lookup;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kotte
 */
public class DataStructureTest {

    IProject project;

    public DataStructureTest() {
        //set up project
        this.project = Lookup.getDefault().lookup(IProjectFactory.class).createProject();
        this.project.setName("Dummy Projekt");
        this.project.setDescription("Testprojekt zum testen der Proteomik API Datenstrukturen");
        this.project.setOwner("kotte");

        //set up logical gel group
        ILogicalGelGroup logical = Lookup.getDefault().lookup(ILogicalGelGroupFactory.class).createLogicalGelGroup();
        logical.setName("Testgruppe high level");
        logical.setDescription("Mock Objekt zum Test der Datenstruktur");
        logical.setParent(this.project);
        this.project.addGelGroup(logical);

        //set up two bio rep gel groups
        IBioRepGelGroup biorep1 = Lookup.getDefault().lookup(IBioRepGelGroupFactory.class).createBioRepGelGroup();
        biorep1.setName("Testgruppe #1 mid level");
        biorep1.setDescription("Mock Objekt zum Test der Datenstruktur");
        biorep1.setParent(logical);
        logical.addGelGroup(biorep1);
        IBioRepGelGroup biorep2 = Lookup.getDefault().lookup(IBioRepGelGroupFactory.class).createBioRepGelGroup();
        biorep2.setName("Testgruppe #2 mid level");
        biorep2.setDescription("Mock Objekt zum Test der Datenstruktur");
        biorep2.setParent(logical);
        logical.addGelGroup(biorep2);

        //set up two tech rep gel groups (one for each bio rep gel group)
        ITechRepGelGroup techrep1 = Lookup.getDefault().lookup(ITechRepGelGroupFactory.class).createTechRepGelGroupFactory();
        techrep1.setName("Testgruppe #1 low level");
        techrep1.setDescription("Mock Objekt zum Test der Datenstruktur");
        techrep1.setParent(biorep1);
        biorep1.addGelGroup(techrep1);
        ITechRepGelGroup techrep2 = Lookup.getDefault().lookup(ITechRepGelGroupFactory.class).createTechRepGelGroupFactory();
        techrep2.setName("Testgruppe #2 low level");
        techrep2.setDescription("Mock Objekt zum Test der Datenstruktur");
        techrep2.setParent(biorep2);
        biorep2.addGelGroup(techrep2);

        //set up two gels (one for each tech rep gel group)
        IGel gel1 = Lookup.getDefault().lookup(IGelFactory.class).createGel();
        gel1.setDescription("Mock Objekt zum Test der Datenstruktur");
        gel1.setName("Testgel #1");
        gel1.setParent(techrep1);
        techrep1.addGel(gel1);
        IGel gel2 = Lookup.getDefault().lookup(IGelFactory.class).createGel();
        gel2.setDescription("Mock Objekt zum Test der Datenstruktur");
        gel2.setName("Testgel #2");
        gel2.setParent(techrep2);
        techrep2.addGel(gel2);

        //set up some spots for the two gels
        ISpot spot11 = Lookup.getDefault().lookup(ISpotFactory.class).createSpot();
        spot11.setNumber(1);
        spot11.setPosX(100);
        spot11.setPosY(199);
        spot11.setLabel("Spot #1 on gel #1");
        spot11.setGel(gel1);
        gel1.addSpot(spot11);
        ISpot spot12 = Lookup.getDefault().lookup(ISpotFactory.class).createSpot();
        spot11.setNumber(2);
        spot11.setPosX(200);
        spot11.setPosY(100);
        spot11.setLabel("Spot #2 on gel #1");
        spot11.setGel(gel1);
        gel1.addSpot(spot12);
        ISpot spot21 = Lookup.getDefault().lookup(ISpotFactory.class).createSpot();
        spot11.setNumber(1);
        spot11.setPosX(99);
        spot11.setPosY(200);
        spot11.setLabel("Spot #1 on gel #2");
        spot11.setGel(gel2);
        gel2.addSpot(spot21);
        ISpot spot22 = Lookup.getDefault().lookup(ISpotFactory.class).createSpot();
        spot11.setNumber(2);
        spot11.setPosX(199);
        spot11.setPosY(99);
        spot11.setLabel("Spot #2 on gel #2");
        spot11.setGel(gel1);
        gel2.addSpot(spot22);

        //set up groups of spots
        ISpotGroup spotgroup1 = Lookup.getDefault().lookup(ISpotGroupFactory.class).createSpotGroup();
        spotgroup1.setLabel("Spot #1");
        spotgroup1.setNumber(1);
        spotgroup1.addSpot(spot11);
        spot11.setGroup(spotgroup1);
        spotgroup1.addSpot(spot21);
        spot21.setGroup(spotgroup1);
        ISpotGroup spotgroup2 = Lookup.getDefault().lookup(ISpotGroupFactory.class).createSpotGroup();
        spotgroup2.setLabel("Spot #2");
        spotgroup2.setNumber(2);
        spotgroup2.addSpot(spot12);
        spot12.setGroup(spotgroup2);
        spotgroup2.addSpot(spot22);
        spot22.setGroup(spotgroup2);

        //set up a 96 well microplate
        IPlate96 plate96 = Lookup.getDefault().lookup(IPlate96Factory.class).createPlate96();
        plate96.setName("Testplatte");
        plate96.setDescription("Mock Objekt zum Test der Datenstruktur");
        plate96.setParent(this.project);
        this.project.add96Plate(plate96);

        //collect some wells for our spots from the plates
        IWell96 wellA1 = plate96.getWell('A', 1);
        IWell96 wellA2 = plate96.getWell('A', 2);
        IWell96 wellB1 = plate96.getWell('B', 1);
        IWell96 wellB2 = plate96.getWell('B', 2);
        assertNotSame(wellA1, wellA2);
        assertNotSame(wellB1, wellB2);

        //provide picking information
        System.out.println("filling spots:");
        wellA1.setSpot(spot11);
        wellA1.setStatus(Well96Status.FILLED);
        spot11.setWell(wellA1);
        spot11.setStatus(SpotStatus.PICKED);
        wellA2.setSpot(spot12);
        wellA2.setStatus(Well96Status.FILLED);
        spot12.setWell(wellA2);
        spot12.setStatus(SpotStatus.PICKED);
        wellB1.setSpot(spot21);
        wellB1.setStatus(Well96Status.FILLED);
        spot21.setWell(wellB1);
        spot21.setStatus(SpotStatus.PICKED);
        wellB2.setSpot(spot22);
        wellB2.setStatus(Well96Status.FILLED);
        spot22.setWell(wellB2);
        spot22.setStatus(SpotStatus.PICKED);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testToString() {
        System.out.println(this.project);
    }

    @Test
    public void testTunneling() {
        ILogicalGelGroup logical = this.project.getGelGroups().iterator().next();
        IBioRepGelGroup biorep = logical.getGelGroups().iterator().next();
        ITechRepGelGroup techrep = biorep.getGelGroups().iterator().next();
        IGel gel = techrep.getGels().iterator().next();
        assertEquals(this.project, gel.getParent().getParent().getParent().getParent());

        IPlate96 plate96 = this.project.get96Plates().iterator().next();
        IWell96 well96 = plate96.getWell('A', 1);
        ISpot spot = well96.getSpot();
        assert (gel.getSpots().contains(spot));
    }
}