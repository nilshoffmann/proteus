package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import org.openide.util.Lookup;

/**
 * Factory for creating proteomic projects.
 *
 * @author kotte
 */
public class ProjectFactory {

    public static IProject getDefault() {
        IProject result = Lookup.getDefault().lookup(IProject.class);
        return result;
    }
}
