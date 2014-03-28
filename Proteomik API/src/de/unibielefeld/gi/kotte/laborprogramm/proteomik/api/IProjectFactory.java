package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import java.io.File;
import java.util.Map;

/**
 * Factory for creating proteomic projects.
 *
 * @author Konstantin Otte
 */
public interface IProjectFactory {

    public IProject createEmptyProject();
}
