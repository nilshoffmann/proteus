package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

import org.openide.util.Lookup;

/**
 * Factory for creating 2D gels.
 *
 * @author kotte
 */
public class GelFactory {

    public static IGel getDefault() {
        IGel result = Lookup.getDefault().lookup(IGel.class);
        return result;
    }
}
