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
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
        PtoolsXml organisms = getStuff(DATABASE_URL + "xmlquery?dbs");
        if (organisms != null) {
            return organisms.getMetadata().getPGDB();
        }
        return new ArrayList<PGDB>();
    }

    public List<Pathway> getPathwaysForOrganism(String organismID) {
        List<Pathway> list = new ArrayList<Pathway>();
        PtoolsXml pathways = getStuff(DATABASE_URL + "xmlquery?[x:x<-" + organismID + "^^pathways]");
        List<Object> children = pathways.getCompoundOrGOTermOrGene();
        if (children != null) {
            for (Object obj : children) {
                if (obj instanceof Pathway) {
                    list.add((Pathway) obj);
                }
            }
        }
        return list;
    }

    public List<Protein> getEnzymesForPathway(String organismID, String pathwayID) {
        List<Protein> list = new ArrayList<Protein>();
        PtoolsXml enzymes = getStuff(DATABASE_URL + "xmlquery?[x:y:=" + organismID + '~' + pathwayID + ",x<-(enzymes-of-pathway y)]");
        List<Object> children = enzymes.getCompoundOrGOTermOrGene();
        if (children != null) {
            for (Object obj : children) {
                if (obj instanceof Enzyme) {
                    Protein protein = ((Enzyme) obj).getProtein();
                    list.add(protein);
                }
            }
        }
        return list;
    }

    public List<Compound> getCopoundsForPathway(String organismID, String pathwayID) {
        List<Compound> list = new ArrayList<Compound>();
        PtoolsXml compounds = getStuff(DATABASE_URL + "xmlquery?[x:y:=" + organismID + '~' + pathwayID + ",x<-(compounds-of-pathway y)]");
        List<Object> children = compounds.getCompoundOrGOTermOrGene();
        if (children != null) {
            for (Object obj : children) {
                if (obj instanceof Compound) {
                    list.add((Compound) obj);
                }
            }
        }
        return list;
    }

//    public PtoolsXml getEntity(String id) {
//        PtoolsXml entity = getStuff(DATABASE_URL + "getxml?" + id);
//        return entity;
//    }

    private PtoolsXml getStuff(String address) {
        PtoolsXml stuff = null;
        try {
            File tmpFile = File.createTempFile("biocycquery", ".xml");
            JAXBContext jc = JAXBContext.newInstance("de.unibielefeld.gi.omicsTools.biocyc.ptools");
            Unmarshaller u = jc.createUnmarshaller();
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
            stuff = (PtoolsXml) u.unmarshal(tmpFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
        return stuff;
    }
}
