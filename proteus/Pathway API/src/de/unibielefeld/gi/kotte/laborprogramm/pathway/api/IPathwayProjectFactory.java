package de.unibielefeld.gi.kotte.laborprogramm.pathway.api;

/**
 * Factory for creating pathway projects.
 *
 * @author Konstantin Otte
 */
public interface IPathwayProjectFactory {

    IPathwayProject createProject(String name);
}
