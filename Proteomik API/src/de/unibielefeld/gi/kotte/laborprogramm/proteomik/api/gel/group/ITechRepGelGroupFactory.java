package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group;

/**
 * Factory for creating new groups of technical replications of gels.
 *
 * @author Konstantin Otte
 */
public interface ITechRepGelGroupFactory {

    public ITechRepGelGroup createTechRepGelGroup();
}
