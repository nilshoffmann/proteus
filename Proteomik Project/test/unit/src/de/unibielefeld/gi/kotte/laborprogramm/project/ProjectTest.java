package de.unibielefeld.gi.kotte.laborprogramm.project;

import org.netbeans.spi.project.ProjectFactory;
import java.util.Arrays;
import java.net.MalformedURLException;
import net.sf.maltcms.chromaui.db.api.ICrudProviderFactory;
import net.sf.maltcms.chromaui.db.api.ICrudProvider;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.maltcms.chromaui.db.api.ICrudSession;
import net.sf.maltcms.chromaui.db.spi.db4o.DB4oCrudProvider;
import junit.framework.TestCase;
import java.io.IOException;
import org.netbeans.junit.NbModuleSuite;
import org.openide.filesystems.FileUtil;
import org.netbeans.spi.project.ProjectState;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGelFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpotFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroupFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroupFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroupFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroupFactory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384Factory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96Factory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.project.spi.factory.ProteomicProjectFactory2;
import de.unibielefeld.gi.kotte.laborprogramm.project.spi.factory.ProteomikProjectFactory;
import java.io.File;
import net.sf.maltcms.chromaui.db.api.NoAuthCredentials;
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
public class ProjectTest extends TestCase {

    private File tmpDir;
    private File tmpProjectDir;

    public ProjectTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Override
    protected void setUp() throws Exception {
        tmpDir = new File(System.getProperty("java.io.tmpdir"));
        tmpProjectDir = new File(tmpDir, "testDB4oProject");
    }

    @Override
    protected void tearDown() throws Exception {
        deleteChildren(tmpProjectDir);
        tmpProjectDir.delete();
    }

    public static junit.framework.Test suite() {
        return NbModuleSuite.create(NbModuleSuite.createConfiguration(
                ProjectTest.class));
    }

    /**
     * Test plain db4o persistence. No Netbeans related APIs used here.
     *
     */
    @Test
    public void testDB4oPersistence() {

        if (tmpProjectDir.exists()) {
            deleteChildren(tmpProjectDir);
        }
        File databaseFile = new File(tmpProjectDir, "plopp.ppr");
        tmpProjectDir.mkdirs();
        ICrudProvider icp;
        IProject ipr1 = null;
        //create seesion
        try {
            icp = Lookup.getDefault().lookup(ICrudProviderFactory.class).
                    getCrudProvider(databaseFile.toURI().toURL(),
                    new NoAuthCredentials(), Lookup.getDefault().lookup(
                    ClassLoader.class));
            icp.open();
            ICrudSession ics = icp.createSession();
            ics.open();
            ipr1 = createTestProject();
            //create object in database
            ics.create(Arrays.asList(ipr1));
            //test retrieval in same session
            Collection<IProject> coll = ics.retrieve(IProject.class);
            assertEquals(1, coll.size());
            IProject ipr = coll.iterator().next();
            assertNotNull(ipr);
            //close session
            ics.close();
            icp.close();
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }

        //test retrieval in new session
        try {
            icp = Lookup.getDefault().lookup(ICrudProviderFactory.class).
                    getCrudProvider(databaseFile.toURI().toURL(),
                    new NoAuthCredentials(), Thread.currentThread().
                    getContextClassLoader());
            icp.open();
            ICrudSession ics = icp.createSession();
            ics.open();
            IProject ipr2 = ics.retrieve(IProject.class).iterator().next();
            assertNotNull(ipr2);
            assertEquals(ipr1.toString(), ipr2.toString());
            ics.close();
            icp.close();
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Test modification after first persistence to db was performed.
     */
    @Test
    public void testProjectCRUDCapability() {
        if (tmpProjectDir.exists()) {
            deleteChildren(tmpProjectDir);
        }
        File databaseFile = new File(tmpProjectDir, "plopp.ppr");
        //tmpProjectDir.deleteOnExit();
        tmpProjectDir.mkdirs();
        ICrudProvider icp;
        IProject ipr1 = null;
        try {
            icp = Lookup.getDefault().lookup(ICrudProviderFactory.class).
                    getCrudProvider(databaseFile.toURI().toURL(),
                    new NoAuthCredentials(), Thread.currentThread().
                    getContextClassLoader());
            icp.open();
            ICrudSession ics = icp.createSession();
            ics.open();
            ipr1 = createTestProject();
            ics.create(Arrays.asList(ipr1));
            //test retrieval in same session
            Collection<IProject> coll = ics.retrieve(IProject.class);
            assertEquals(1, coll.size());
            IProject ipr = coll.iterator().next();
            assertNotNull(ipr);

            //test update of original object

            ipr = addSpotsAndWells(ipr);
            ics.update(ipr);
            ics.close();
            icp.close();
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }

        //test retrieval in new session
        try {
            icp = Lookup.getDefault().lookup(ICrudProviderFactory.class).
                    getCrudProvider(databaseFile.toURI().toURL(),
                    new NoAuthCredentials(), Thread.currentThread().
                    getContextClassLoader());
            icp.open();
            ICrudSession ics = icp.createSession();
            ics.open();
            IProject ipr2 = ics.retrieve(IProject.class).iterator().next();
            assertNotNull(ipr2);
            assertFalse(ipr2.get96Plates().isEmpty());
            System.out.println("IProject has the following plates: "+ipr2.get96Plates());
            ics.close();
            icp.close();
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Test
    public void testProject() {
        //set up project in test directory
        //IProteomicProjectFactory ippf = Lookup.getDefault().lookup(IProteomicProjectFactory.class);
        ProjectFactory ippf = new ProteomikProjectFactory();
        assertNotNull(ippf);
        IProject testProject = createTestProject();
        if (tmpProjectDir.exists()) {
            deleteChildren(tmpProjectDir);
        }
        //tmpProjectDir.deleteOnExit();
        tmpProjectDir.mkdirs();
        IProteomicProjectFactory ippf2 = new ProteomicProjectFactory2();
        IProteomicProject ipp = ippf2.createProject(tmpProjectDir, testProject);
        String str1 = null;
        try {
            ipp = (IProteomicProject) ippf.loadProject(FileUtil.toFileObject(
                    tmpProjectDir), new ProjectState() {

                @Override
                public void markModified() {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void notifyDeleted() throws IllegalStateException {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }
            });
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
//        try {
//            ipp.activate(new File(tmpProjectDir, ProteomikProjectFactory.PROJECT_FILE).toURI().toURL());
        str1 = ipp.toString();
        ipp.close();
//        } catch (MalformedURLException ex) {
//            Exceptions.printStackTrace(ex);
//        }

        //test retrieval from database

        DB4oCrudProvider instance = new DB4oCrudProvider(new File(tmpProjectDir,
                ProteomikProjectFactory.PROJECT_FILE), new NoAuthCredentials(), Lookup.
                getDefault().lookup(ClassLoader.class));
        instance.open();
        ICrudSession ics = instance.createSession();
        ics.open();
        Collection<IProject> projects = ics.retrieve(IProject.class);
        Logger.getLogger(getClass().getName()).log(Level.INFO,
                "Retrieved {0} projects", projects.size());
        assertEquals(1, projects.size());
        for (IProject proj : projects) {
            Logger.getLogger(getClass().getName()).log(Level.INFO,
                    "Retrieved project {0}", proj);
        }
        ics.close();
        instance.close();

        //retrieve from database and compare to previous version
        try {
            IProteomicProject ipp2 = (IProteomicProject) ippf.loadProject(FileUtil.
                    toFileObject(tmpProjectDir), new ProjectState() {

                @Override
                public void markModified() {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void notifyDeleted() throws IllegalStateException {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }
            });
            System.err.println("Str1: " + str1);
            String str2 = ipp2.toString();
            System.err.println("Str2: " + str2);
            assertEquals(str1, str2);
            ipp2.close();
            //System.out.println("Project2: "+ipp2.toString());
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        //IProteomicProject pp = ippf.createProject(dir);
    }

    private void deleteChildren(File f) {
        if (f != null) {
            File[] files = f.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteChildren(f);
                } else {
                    file.delete();
                }
            }
        }
    }

    private IProject createTestProject() {
        IProject project = null;
        project = Lookup.getDefault().lookup(IProjectFactory.class).
                createEmptyProject();
        project.setName("Dummy Projekt");
        project.setDescription(
                "Testprojekt zum testen der Proteomik API Datenstrukturen");
        project.setOwner("kotte");

        //set up logical gel group
        ILogicalGelGroup logical = Lookup.getDefault().lookup(
                ILogicalGelGroupFactory.class).createLogicalGelGroup();
        logical.setName("Testgruppe high level");
        logical.setDescription("Mock Objekt zum Test der Datenstruktur");
        logical.setParent(project);
        project.addGelGroup(logical);

        //set up two bio rep gel groups
        IBioRepGelGroup biorep1 = Lookup.getDefault().lookup(
                IBioRepGelGroupFactory.class).createBioRepGelGroup();
        biorep1.setName("Testgruppe #1 mid level");
        biorep1.setDescription("Mock Objekt zum Test der Datenstruktur");
        biorep1.setParent(logical);
        logical.addGelGroup(biorep1);

        IBioRepGelGroup biorep2 = Lookup.getDefault().lookup(
                IBioRepGelGroupFactory.class).createBioRepGelGroup();
        biorep2.setName("Testgruppe #2 mid level");
        biorep2.setDescription("Mock Objekt zum Test der Datenstruktur");
        biorep2.setParent(logical);
        logical.addGelGroup(biorep2);

        //set up two tech rep gel groups (one for each bio rep gel group)
        ITechRepGelGroup techrep1 = Lookup.getDefault().lookup(
                ITechRepGelGroupFactory.class).createTechRepGelGroup();
        techrep1.setName("Testgruppe #1 low level");
        techrep1.setDescription("Mock Objekt zum Test der Datenstruktur");
        techrep1.setParent(biorep1);
        biorep1.addGelGroup(techrep1);

        ITechRepGelGroup techrep2 = Lookup.getDefault().lookup(
                ITechRepGelGroupFactory.class).createTechRepGelGroup();
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

        return project;
    }

    private IProject addSpotsAndWells(IProject project) {
        //set up a 96 well microplate
        IPlate96 plate96 = Lookup.getDefault().lookup(IPlate96Factory.class).
                createPlate96();
        plate96.setName("Testplatte");
        plate96.setDescription("Mock Objekt zum Test der Datenstruktur");
        plate96.setParent(project);
        project.add96Plate(plate96);

        //set up some spots for the two gels
        ISpot spot11 = Lookup.getDefault().lookup(ISpotFactory.class).createSpot();
        spot11.setNumber(1);
        spot11.setPosX(100);
        spot11.setPosY(199);
        spot11.setLabel("Spot #1 on gel #1");
        IGel gel1 = project.getGelGroups().get(0).getGelGroups().get(0).
                getGelGroups().get(0).getGels().get(0);
        spot11.setGel(gel1);
        gel1.addSpot(spot11);
        ISpot spot12 = Lookup.getDefault().lookup(ISpotFactory.class).createSpot();
        spot12.setNumber(2);
        spot12.setPosX(200);
        spot12.setPosY(100);
        spot12.setLabel("Spot #2 on gel #1");
        spot12.setGel(gel1);
        gel1.addSpot(spot12);

        ISpot spot21 = Lookup.getDefault().lookup(ISpotFactory.class).createSpot();
        spot21.setNumber(1);
        spot21.setPosX(99);
        spot21.setPosY(200);
        spot21.setLabel("Spot #1 on gel #2");
        IGel gel2 = project.getGelGroups().get(0).getGelGroups().get(0).
                getGelGroups().get(0).getGels().get(0);
        spot21.setGel(gel2);
        gel2.addSpot(spot21);
        ISpot spot22 = Lookup.getDefault().lookup(ISpotFactory.class).createSpot();
        spot22.setNumber(2);
        spot22.setPosX(199);
        spot22.setPosY(99);
        spot22.setLabel("Spot #2 on gel #2");
        spot22.setGel(gel1);
        gel2.addSpot(spot22);

        //set up groups of spots
        ISpotGroup spotgroup1 = Lookup.getDefault().lookup(
                ISpotGroupFactory.class).createSpotGroup();
        spotgroup1.setLabel("Spot #1");
        spotgroup1.setNumber(1);
        spotgroup1.addSpot(spot11);
        spot11.setGroup(spotgroup1);
        spotgroup1.addSpot(spot21);
        spot21.setGroup(spotgroup1);
        project.addSpotGroup(spotgroup1);
        ISpotGroup spotgroup2 = Lookup.getDefault().lookup(
                ISpotGroupFactory.class).createSpotGroup();
        spotgroup2.setLabel("Spot #2");
        spotgroup2.setNumber(2);
        spotgroup2.addSpot(spot12);
        spot12.setGroup(spotgroup2);
        spotgroup2.addSpot(spot22);
        spot22.setGroup(spotgroup2);
        project.addSpotGroup(spotgroup2);

        //collect some wells for our spots from the plates
        IWell96 wellA1 = plate96.getWell('A', 1);
        IWell96 wellA2 = plate96.getWell('A', 2);
        IWell96 wellB1 = plate96.getWell('B', 1);
        IWell96 wellB2 = plate96.getWell('B', 2);

        //provide picking information
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

        //create 384 well plate
        IPlate384 plate384 = Lookup.getDefault().lookup(IPlate384Factory.class).
                createPlate384();
        plate384.setParent(project);
        project.add384Plate(plate384);
        plate384.setName("grosse Testplatte");
        plate384.setDescription("Mock Objekt zum Test der Datenstruktur");

        //provide processing information
        IWell384 well384_1 = plate384.getWell('F', 10);
        wellA1.add384Well(well384_1);
        wellA1.setStatus(Well96Status.PROCESSED);
        well384_1.setWell96(wellA1);
        well384_1.setStatus(Well384Status.FILLED);
        return project;
    }
}
