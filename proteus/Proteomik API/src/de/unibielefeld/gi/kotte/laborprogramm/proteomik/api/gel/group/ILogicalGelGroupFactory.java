package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group;

/**
 * Factory for creating new groups of logically different gel groups.
 *
 * @author Konstantin Otte
 */
public interface ILogicalGelGroupFactory {

    public ILogicalGelGroup createLogicalGelGroup();
}
