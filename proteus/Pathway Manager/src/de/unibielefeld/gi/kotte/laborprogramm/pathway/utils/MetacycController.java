package de.unibielefeld.gi.kotte.laborprogramm.pathway.utils;

import de.unibielefeld.gi.omicsTools.biocyc.ptools.Compound;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Enzyme;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.PGDB;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Protein;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.PtoolsXml;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Reaction;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.prefs.Preferences;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.biopax.paxtools.io.BioPAXIOHandler;
import org.biopax.paxtools.io.SimpleIOHandler;
import org.biopax.paxtools.model.Model;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

/**
 * Provides access to Metacyc Database Informations to other classes.
 *
 * @author Konstantin Otte
 */
public class MetacycController {

    public static final String defaultBiocycLocation = "http://biocyc.org:80";
//    static final public String DATABASE_URL = "http://biocyc.org/";

    public URI getBaseURI() {
        String serverLocation = NbPreferences.forModule(MetacycController.class).get("server.location", MetacycController.defaultBiocycLocation);
        URL u;
        try {
            u = new URL(serverLocation);
            return u.toURI();
        } catch (MalformedURLException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<PGDB> getOrganisms() {
        List<PGDB> list = Collections.emptyList();
        URI uri;
        try {
            URI baseUri = getBaseURI();
            uri = new URI(baseUri.getScheme(), null, baseUri.getHost(), baseUri.getPort(), "/xmlquery",
                    "dbs", null);
            PtoolsXml organisms = getStuff(uri);
            if (organisms != null) {
                return organisms.getMetadata().getPGDB();
            }
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
        return list;
    }

    public List<Pathway> getPathwaysForOrganism(String organismID) {
        List<Pathway> list = Collections.emptyList();
        URI uri;
        try {
            URI baseUri = getBaseURI();
            uri = new URI(baseUri.getScheme(), null, baseUri.getHost(), baseUri.getPort(), "/xmlquery", "[x:x<-" + organismID + "^^pathways]", null);
            PtoolsXml pathways = getStuff(uri);
            List<Object> children = pathways.getCompoundOrGOTermOrGene();
            if (children != null) {
                //FIXME eventuell children.size() weg
                list = new ArrayList<Pathway>(children.size());
                for (Object obj : children) {
                    if (obj instanceof Pathway) {
                        list.add((Pathway) obj);
                    }
                }
            }
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
        return list;
    }

    public List<Protein> getEnzymesForPathway(String organismID, String pathwayID) {
        List<Protein> list = Collections.emptyList();
        URI uri;
        try {
            URI baseUri = getBaseURI();
            uri = new URI(baseUri.getScheme(), null, baseUri.getHost(), baseUri.getPort(), "/xmlquery", "[x:y:=" + organismID + '~' + pathwayID + ",x<-(enzymes-of-pathway y)]", null);
            PtoolsXml enzymes = getStuff(uri);
            List<Object> children = enzymes.getCompoundOrGOTermOrGene();
            if (children != null) {
                //FIXME eventuell children.size() weg
                list = new ArrayList<Protein>(children.size());
                for (Object obj : children) {
                    if (obj instanceof Protein) {
                        System.out.println("Found Protein:");
                        Protein protein = ((Protein) obj);
                        System.out.println(protein);
                        list.add(protein);
                    } else if (obj instanceof Enzyme) {
                        System.out.println("Found Enzyme:");
                        Protein protein = ((Enzyme) obj).getProtein();
                        System.out.println(protein);
                        list.add(protein);
                    }
                }
            }
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
        return list;
    }

    public List<Compound> getCompoundsForPathway(String organismID, String pathwayID) {
        List<Compound> list = Collections.emptyList();
        URI uri;
        try {
            URI baseUri = getBaseURI();
            uri = new URI(baseUri.getScheme(), null, baseUri.getHost(), baseUri.getPort(), "/xmlquery", "[x:y:=" + organismID + '~' + pathwayID + ",x<-(compounds-of-pathway y)]", null);
            PtoolsXml compounds = getStuff(uri);
            List<Object> children = compounds.getCompoundOrGOTermOrGene();
            if (children != null) {
                //FIXME eventuell children.size() weg
                list = new ArrayList<Compound>(children.size());
                for (Object obj : children) {
                    if (obj instanceof Compound) {
                        list.add((Compound) obj);
                    }
                }
            }
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
        return list;
    }

    public List<Compound> getCompoundsForOrganism(String organismID) {
        List<Compound> list = Collections.emptyList();
        URI uri;
        try {
            URI baseUri = getBaseURI();
            uri = new URI(baseUri.getScheme(), null, baseUri.getHost(), baseUri.getPort(), "/xmlquery", "[x:x<-" + organismID + "^^compounds]", null);
            PtoolsXml compounds = getStuff(uri);
            List<Object> children = compounds.getCompoundOrGOTermOrGene();
            if (children != null) {
                //FIXME eventuell children.size() weg
                list = new ArrayList<Compound>(children.size());
                for (Object obj : children) {
                    if (obj instanceof Compound) {
                        list.add((Compound) obj);
                    }
                }
            }
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
        return list;
    }

    public List<org.biopax.paxtools.model.level3.Pathway> getBiopaxPathway(String organismID, String pathwayID) {
        try {
            URI baseUri = getBaseURI();
            URI uri = new URI(baseUri.getScheme(), null, "websvc." + baseUri.getHost(), baseUri.getPort(), "/" + organismID + "/pathway-biopax", "type=3&object=" + pathwayID, null);
            Model m = getStuffBioPax(uri);
            if (m == null) {
                return Collections.emptyList();
            }
            Set<org.biopax.paxtools.model.level3.Pathway> pathways = m.getObjects(org.biopax.paxtools.model.level3.Pathway.class);
            List<org.biopax.paxtools.model.level3.Pathway> l = new ArrayList<org.biopax.paxtools.model.level3.Pathway>(pathways);
            return l;
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
        return Collections.emptyList();
    }

    public List<Pathway> getPathwaysForCompound(String organismID, String compoundID) {
        List<Pathway> list = Collections.emptyList();
        URI uri;
        try {
            URI baseUri = getBaseURI();
            uri = new URI(baseUri.getScheme(), null, baseUri.getHost(), baseUri.getPort(), "/xmlquery", "[x:y:=" + organismID + '~' + compoundID + ",x<-(pathways-of-compound y)]", null);
            PtoolsXml pathways = getStuff(uri);
            List<Object> children = pathways.getCompoundOrGOTermOrGene();
            if (children != null) {
                //FIXME eventuell children.size() weg
                list = new ArrayList<Pathway>(children.size());
                for (Object obj : children) {
                    if (obj instanceof Pathway) {
                        list.add((Pathway) obj);
                    }
                }
            }
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
        return list;
    }

    public PtoolsXml getEntity(String id) {
        try {
            URI baseUri = getBaseURI();
            URI uri = new URI(baseUri.getScheme(), null, baseUri.getHost(), baseUri.getPort(), "/getxml", id, null);
            return getStuff(uri);
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    public List<Protein> getEnzymesByECNumber(String organismID, String ecNumber) {
        List<Protein> list = Collections.emptyList();
        URI uri;
        try {
            URI baseUri = getBaseURI();
            uri = new URI(baseUri.getScheme(), null, baseUri.getHost(), baseUri.getPort(), "/xmlquery", "[x:x<-" + organismID + "^^reactions,x^ec-number=\"" + ecNumber + "\"]", null);
            PtoolsXml enzymes = getStuff(uri);
            List<Object> children = enzymes.getCompoundOrGOTermOrGene();
            if (children != null) {
                //FIXME eventuell children.size() weg
                list = new ArrayList<Protein>(children.size());
                for (Object obj : children) {
                    if (obj instanceof Reaction) {
                        Reaction rea = (Reaction) obj;
                        for (Object obj2 : rea.getReactionList().getPathwayOrReaction()) {
                            if (obj2 instanceof Protein) {
                                System.out.println("Found Protein:");
                                Protein protein = ((Protein) obj2);
                                System.out.println(protein);
                                list.add(protein);
                            } else if (obj2 instanceof Enzyme) {
                                System.out.println("Found Enzyme:");
                                Protein protein = ((Enzyme) obj2).getProtein();
                                System.out.println(protein);
                                list.add(protein);
                            }
                        }
                    }
                }
            }
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
        return list;
    }

    /**
     * Gets a PtoolsXml object with the queried data.
     *
     * @param uri the query String
     * @return the XML data Object
     */
    private PtoolsXml getStuff(URI uri) {
        try {
            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.omicsTools.biocyc.ptools");
            Unmarshaller u = jc.createUnmarshaller();
            return (PtoolsXml) u.unmarshal(getFileForURI(uri));
        } catch (JAXBException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    /**
     * Gets a biopax Model object with the queried data.
     *
     * @param uri the query String
     * @return the XML data Object
     */
    private Model getStuffBioPax(URI uri) {
        BioPAXIOHandler handler = new SimpleIOHandler(); // auto-detects Level
        FileInputStream fis = null;
        try {
            File f = getFileForURI(uri);
            fis = new FileInputStream(f);
            Model model = handler.convertFromOWL(fis);
            return model;
        } catch (org.biopax.paxtools.util.BioPaxIOException e) {
            System.err.println("File may be empty!");
        } catch (FileNotFoundException e) {
            Exceptions.printStackTrace(e);
        } catch (IOException e) {
            Exceptions.printStackTrace(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    Exceptions.printStackTrace(e);
                }
            }
        }
        return null;
    }

    /**
     * Gets a xml file with queried data from the system. If it is not there it
     * is created using createFileFromURI(uri, file).
     *
     * @param uri the query String to get the file for
     * @return the xml file with the data
     */
    private File getFileForURI(URI uri) throws IOException {
        String request = uri.toASCIIString();
        System.out.println("Looking for File for request: " + request);
        List<String> lines = Collections.emptyList();
        //get properties file
        FileObject propFO = FileUtil.getConfigFile("proteus/biocycqueries.properties");
        if (propFO == null) { //if no properties file exists, create it
            FileObject configFolder = FileUtil.getConfigFile("proteus");
            if (configFolder == null) {
                configFolder = FileUtil.getConfigRoot().createFolder("proteus");
            }
            propFO = FileUtil.createData(configFolder, "biocycqueries.properties");
        } else { //if there is already a properties file, check it for our query
            lines = propFO.asLines();
            for (String line : lines) {
                String[] split = line.split("\t");
                if (split.length != 2) {
                    throw new IllegalArgumentException("Properties file in unsuitable format. Check tabs.");
                }
                if (split[0].equals(request)) { //we found the file we looked for in the system
                    System.out.println("Request string found in properties file! Name: " + split[1]);
                    File parentFolder = FileUtil.toFile(FileUtil.getConfigFile("proteus"));
                    File f = new File(parentFolder, split[1]);
                    if (Preferences.userRoot().getBoolean("check cache age", false)) { //we are supposed to check for age
                        int max_age_days = Preferences.userRoot().getInt("cache time to live", 14);
                        long lastModified = f.lastModified();
                        Date date = new Date();
                        long currentTime = date.getTime();
                        if ((lastModified + max_age_days * 86400000) < currentTime) { //the file is outdated
                            System.out.println("Deleting outdated cache file " + f.getName());
                            f.delete();
                            break;
                        } else { //the file is valid
                            return f;
                        }
                    } else { //no need to check age
                        return f;
                    }
                }
            }
        }
        System.out.println("Request not found in properties file (or outdated cache file).");
        //create a new entry in the properties file
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(propFO.getOutputStream()));
        //first, write all old entries again, because propFO.getOutputStream() overrides
        for (String line : lines) {
            bw.write(line + '\n');
        }
        //now create the new entry
        StringBuilder sb = new StringBuilder(request);
        UUID requestUUID = UUID.randomUUID();
        String filename = requestUUID.toString() + ".xml";
        sb.append('\t').append(filename);
        //write the new entry and close the file
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        //create and return the new File
        File parentFolder = FileUtil.toFile(FileUtil.getConfigFile("proteus"));
        File qryFile = new File(parentFolder, filename);
        System.out.println("Creating file: " + qryFile.getAbsolutePath());
        qryFile.createNewFile();
        writeFileFromURI(uri, qryFile);
        return qryFile;
    }

    /**
     * Fills a file in the filesystem with data from a http connection.
     *
     * @param uri the address from where to get the data
     * @param file the File object to store the data to
     * @throws IOException
     */
    private void writeFileFromURI(URI uri, File file) throws IOException {
        System.out.println("Database query with address: " + uri.toASCIIString());
        try {
            //set up connection, reader and writer
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() >= 400 || conn.getResponseCode() >= 500) {
                //catch client errors: 4xx and server errors: 5xx
                throw new IOException("Server returned an error: HTTP response code=" + conn.getResponseCode() + "; Message: " + conn.getResponseMessage());
            }
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("iso-8859-1")));
            String line;
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            //copy data from reader to writer
            while ((line = reader.readLine()) != null) {
                bw.write(line);
            }
            //clean up
            conn.disconnect();
            reader.close();
            bw.flush();
            bw.close();
        } catch (UnknownHostException ex) {
            System.err.println("No network connection!");
            file.delete();
        } catch (IOException ex) {
            file.delete();
            //rethrow so we can catch the exception further upstream
            throw ex;
        }
    }
}
