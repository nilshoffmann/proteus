package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

import org.openide.util.Lookup;

/**
 * Factory for creating new gel spots.
 *
 * @author kotte
 */
public class SpotFactory {

    public static ISpot getDefault() {
        ISpot result = Lookup.getDefault().lookup(ISpot.class);
        return result;
    }
}
