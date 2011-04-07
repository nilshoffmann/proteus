package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import org.openide.util.Lookup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ITechRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
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
        this.project = Lookup.getDefault().lookup(IProject.class);
        this.project.setName("Dummy Projekt");
        this.project.setDescription("Testprojekt zum testen der Proteomik API Datenstrukturen");
        this.project.setOwner("kotte");

        //set up logical gel group
        ILogicalGelGroup logical = Lookup.getDefault().lookup(ILogicalGelGroup.class);
        logical.setName("Testgruppe high level");
        logical.setDescription("Mock Objekt zum Test der Datenstruktur");
        logical.setParent(this.project);
        this.project.addGelGroup(logical);

        //set up two bio rep gel groups
        IBioRepGelGroup biorep1 = Lookup.getDefault().lookup(IBioRepGelGroup.class);
        biorep1.setName("Testgruppe #1 mid level");
        biorep1.setDescription("Mock Objekt zum Test der Datenstruktur");
        biorep1.setParent(logical);
        logical.addGelGroup(biorep1);
        IBioRepGelGroup biorep2 = Lookup.getDefault().lookup(IBioRepGelGroup.class);
        biorep2.setName("Testgruppe #2 mid level");
        biorep2.setDescription("Mock Objekt zum Test der Datenstruktur");
        biorep2.setParent(logical);
        logical.addGelGroup(biorep2);

        //set up two tech rep gel groups (one for each bio rep gel group)
        ITechRepGelGroup techrep1 = Lookup.getDefault().lookup(ITechRepGelGroup.class);
        techrep1.setName("Testgruppe #1 low level");
        techrep1.setDescription("Mock Objekt zum Test der Datenstruktur");
        techrep1.setParent(biorep1);
        biorep1.addGelGroup(techrep1);
        ITechRepGelGroup techrep2 = Lookup.getDefault().lookup(ITechRepGelGroup.class);
        techrep2.setName("Testgruppe #2 low level");
        techrep2.setDescription("Mock Objekt zum Test der Datenstruktur");
        techrep2.setParent(biorep2);
        biorep2.addGelGroup(techrep2);

        //set up two gels (one for each tech rep gel group)
        IGel gel1 = Lookup.getDefault().lookup(IGel.class);
        gel1.setDescription("Mock Objekt zum Test der Datenstruktur");
        gel1.setName("Testgel #1");
        gel1.setParent(techrep1);
        techrep1.addGel(gel1);
        IGel gel2 = Lookup.getDefault().lookup(IGel.class);
        gel2.setDescription("Mock Objekt zum Test der Datenstruktur");
        gel2.setName("Testgel #2");
        gel2.setParent(techrep2);
        techrep2.addGel(gel2);

        //set up some spots for the two gels
        ISpot spot11 = Lookup.getDefault().lookup(ISpot.class);
        spot11.setNumber(1);
        spot11.setPosX(100);
        spot11.setPosY(199);
        spot11.setLabel("Spot #1 on gel #1");
        spot11.setGel(gel1);
        gel1.addSpot(spot11);
        ISpot spot12 = Lookup.getDefault().lookup(ISpot.class);
        spot11.setNumber(2);
        spot11.setPosX(200);
        spot11.setPosY(100);
        spot11.setLabel("Spot #2 on gel #1");
        spot11.setGel(gel1);
        gel1.addSpot(spot12);
        ISpot spot21 = Lookup.getDefault().lookup(ISpot.class);
        spot11.setNumber(1);
        spot11.setPosX(99);
        spot11.setPosY(200);
        spot11.setLabel("Spot #1 on gel #2");
        spot11.setGel(gel2);
        gel2.addSpot(spot21);
        ISpot spot22 = Lookup.getDefault().lookup(ISpot.class);
        spot11.setNumber(2);
        spot11.setPosX(199);
        spot11.setPosY(99);
        spot11.setLabel("Spot #2 on gel #2");
        spot11.setGel(gel1);
        gel2.addSpot(spot22);

        //set up groups of spots
        ISpotGroup spotgroup1 = Lookup.getDefault().lookup(ISpotGroup.class);
        spotgroup1.setLabel("Spot #1");
        spotgroup1.setNumber(1);
        spotgroup1.addSpot(spot11);
        spot11.setGroup(spotgroup1);
        spotgroup1.addSpot(spot21);
        spot21.setGroup(spotgroup1);
        ISpotGroup spotgroup2 = Lookup.getDefault().lookup(ISpotGroup.class);
        spotgroup2.setLabel("Spot #2");
        spotgroup2.setNumber(2);
        spotgroup2.addSpot(spot12);
        spot12.setGroup(spotgroup2);
        spotgroup2.addSpot(spot22);
        spot22.setGroup(spotgroup2);

        //set up a 96 well microplate
        IPlate96 plate96 = Lookup.getDefault().lookup(IPlate96.class);
        plate96.setName("Testplatte");
        plate96.setDescription("Mock Objekt zum Test der Datenstruktur");
        plate96.setParent(this.project);
        this.project.add96Plate(plate96);

        //set up some wells for our spots
        IWell96 well11 = Lookup.getDefault().lookup(IWell96.class);
        well11.setColumn(1);
        well11.setRow('A');
        well11.setParent(plate96);
        plate96.setWell(well11, 'A', 1);
        IWell96 well12 = Lookup.getDefault().lookup(IWell96.class);
        well12.setColumn(2);
        well12.setRow('A');
        well12.setParent(plate96);
        plate96.setWell(well12, 'A', 2);
        //...or the short way:
        IWell96 well21 = plate96.getWell('B', 1);
        IWell96 well22 = plate96.getWell('B', 2);

        //provide picking information
        System.out.println("filling spots:");
        well11.setSpot(spot11);
        well11.setStatus(Well96Status.FILLED);
        spot11.setWell(well11);
        spot11.setStatus(SpotStatus.PICKED);
        well12.setSpot(spot12);
        well12.setStatus(Well96Status.FILLED);
        spot12.setWell(well12);
        spot12.setStatus(SpotStatus.PICKED);
        well21.setSpot(spot21);
        well21.setStatus(Well96Status.FILLED);
        spot21.setWell(well21);
        spot21.setStatus(SpotStatus.PICKED);
        well22.setSpot(spot22);
        well22.setStatus(Well96Status.FILLED);
        spot22.setWell(well22);
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
        assert(gel.getSpots().contains(spot));
    }
}
