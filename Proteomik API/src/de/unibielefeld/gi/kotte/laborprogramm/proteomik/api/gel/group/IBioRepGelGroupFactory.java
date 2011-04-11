package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group;

/**
 * Factory for creating new groups of biological replications of groups
 * of technical replications of 2D gels.
 *
 * @author kotte
 */
public interface IBioRepGelGroupFactory {

    public IBioRepGelGroup createBioRepGelGroup();
}
