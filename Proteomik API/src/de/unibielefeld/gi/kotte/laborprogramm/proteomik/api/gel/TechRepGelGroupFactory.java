package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

import org.openide.util.Lookup;

/**
 * Factory for creating new groups of technical replications of gels.
 *
 * @author kotte
 */
public class TechRepGelGroupFactory {

    public static ITechRepGelGroup getDefault() {
        ITechRepGelGroup result = Lookup.getDefault().lookup(ITechRepGelGroup.class);
        return result;
    }
}
