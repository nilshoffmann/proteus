package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

import org.openide.util.Lookup;

/**
 * Factory for creating new spot groups.
 *
 * @author kotte
 */
public class SpotGroupFactory {

    public static ISpotGroup getDefault() {
        ISpotGroup result = Lookup.getDefault().lookup(ISpotGroup.class);
        return result;
    }
}
