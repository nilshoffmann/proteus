/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.pathways;

import de.unibielefeld.gi.omicsTools.biocyc.ptools.CommonName;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Compound;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.PGDB;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Protein;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kotte
 */
public class MetacycControllerTest {

    public MetacycControllerTest() {
    }

    @Test
    public void testMetacycController() {
        MetacycController mc = new MetacycController();

        List<PGDB> organisms = mc.getOrganisms();
        PGDB organism = organisms.iterator().next();

        List<Pathway> pathways = mc.getPathwaysForOrganism(organism.getOrgid());
        Pathway pathway = pathways.iterator().next();

        List<Compound> compounds = mc.getCopoundsForPathway(pathway.getOrgid(), pathway.getFrameid());
        Compound compound = compounds.iterator().next();
        System.out.println("Compound toString(): " + compound);
        System.out.println("Compound ID: " + compound.getID());
        System.out.println("Compound details: " + compound.getDetail());
        System.out.println("Compound Cml Molecule Title: " + compound.getCml().getMolecule().getTitle());

        List<Protein> proteins = mc.getEnzymesForPathway(pathway.getOrgid(), pathway.getFrameid());
        Protein protein = proteins.iterator().next();
        System.out.println("Protein toString(): " + protein);
        System.out.println("Protein ID: " + protein.getID());
        System.out.println("Protein details: " + protein.getDetail());
        for (Object obj : protein.getCatalyzes().getEnzymaticReaction().iterator().next().getCitationOrCofactorOrComment()) {
            if (obj instanceof CommonName) {
                System.out.println("Protein Catalyzes EnzymaticReaction CommonName: " + ((CommonName) obj).getContent());
            }
        }
    }
}
