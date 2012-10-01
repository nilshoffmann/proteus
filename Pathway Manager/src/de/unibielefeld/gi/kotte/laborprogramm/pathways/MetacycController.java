package de.unibielefeld.gi.kotte.laborprogramm.pathways;

import de.unibielefeld.gi.omicsTools.biocyc.ptools.Compound;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Enzyme;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.PGDB;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Protein;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.PtoolsXml;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.openide.util.Exceptions;

/**
 * TODO JAvaDocs
 *
 * @author kotte
 */
public class MetacycController {

    static final public String DATABASE_URL = "http://biocyc.org/";

    /**
     * @return a Map (orgID -> organism common name with added strain name) or
     * null
     */
    public List<PGDB> getOrganisms() {
        List<PGDB> list = Collections.emptyList();
        URI uri;
        try {
            uri = new URI("http", null, "biocyc.org", 80, "/xmlquery",
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
            uri = new URI("http", null, "biocyc.org", 80, "/xmlquery", "[x:x<-" + organismID + "^^pathways]", null);
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
            uri = new URI("http", null, "biocyc.org", 80, "/xmlquery", "[x:y:=" + organismID + '~' + pathwayID + ",x<-(enzymes-of-pathway y)]", null);
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
            uri = new URI("http", null, "biocyc.org", 80, "/xmlquery", "[x:y:=" + organismID + '~' + pathwayID + ",x<-(compounds-of-pathway y)]", null);
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

//    public PtoolsXml getEntity(String id) {
//        PtoolsXml entity = getStuff(DATABASE_URL + "getxml?" + id);
//        return entity;
//    }
    private PtoolsXml getStuff(URI uri) {
        try {
            String request = uri.toASCIIString();
            System.out.println("Database query with address: " + request);
            File tmpFile = File.createTempFile("biocycquery", ".xml");
            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.omicsTools.biocyc.ptools");
            Unmarshaller u = jc.createUnmarshaller();
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            //String encoding = new sun.misc.BASE64Encoder().encode("username assword".getBytes());
            //conn.setRequestProperty("Authorization", "Basic " + encoding);
            conn.setRequestMethod("GET");

            conn.connect();
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("iso-8859-1")));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile));
            String line;
            while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
                bw.write(line);
            }
            conn.disconnect();
            reader.close();
            bw.flush();
            bw.close();
            return (PtoolsXml) u.unmarshal(tmpFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
