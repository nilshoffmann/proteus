package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

import org.openide.util.Lookup;

/**
 * Factory for creating new groups of logically different gel groups.
 *
 * @author kotte
 */
public class LogicalGelGroupFactory {

    public static ILogicalGelGroup getDefault() {
        ILogicalGelGroup result = Lookup.getDefault().lookup(ILogicalGelGroup.class);
        return result;
    }
}
