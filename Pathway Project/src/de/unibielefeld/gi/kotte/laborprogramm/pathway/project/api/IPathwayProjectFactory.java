package de.unibielefeld.gi.kotte.laborprogramm.pathway.project.api;

import java.io.File;

/**
 *
 * @author kotte
 */
public interface IPathwayProjectFactory {
    public IPathwayProject createProject(File projdir);
}
