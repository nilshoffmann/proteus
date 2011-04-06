package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

import org.openide.util.Lookup;

/**
 * Factory for creating new groups of biological replications of groups
 * of technical replications of 2D gels.
 *
 * @author kotte
 */
public class BioRepGelGroupFactory {

    public static IBioRepGelGroup getDefault() {
        IBioRepGelGroup result = Lookup.getDefault().lookup(IBioRepGelGroup.class);
        return result;
    }
}
