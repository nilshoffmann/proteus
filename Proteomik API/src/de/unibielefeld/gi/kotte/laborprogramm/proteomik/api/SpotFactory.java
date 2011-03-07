package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
public class SpotFactory {

    public static ISpot getDefault() {
        ISpot result = Lookup.getDefault().lookup(ISpot.class);
        return result;
    }
}
