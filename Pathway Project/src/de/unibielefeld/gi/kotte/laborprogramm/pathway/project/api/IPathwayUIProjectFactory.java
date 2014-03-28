package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;

/**
 * Factory for creating new PathwayUIProjects.
 *
 * @author Konstantin Otte
 */
public interface IPathwayUIProjectFactory {

    public IPathwayUIProject createProject(IProteomicProject parent, String name);
}
