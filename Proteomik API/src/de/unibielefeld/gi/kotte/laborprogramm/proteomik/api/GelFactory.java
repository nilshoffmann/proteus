package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
public class GelFactory {

    public static IGel getDefault() {
        IGel result = Lookup.getDefault().lookup(IGel.class);
        return result;
    }

}