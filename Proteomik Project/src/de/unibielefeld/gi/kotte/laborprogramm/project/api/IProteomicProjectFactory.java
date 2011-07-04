package de.unibielefeld.gi.kotte.laborprogramm.project.api;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.io.File;

/**
 *
 * @author kotte
 */
public interface IProteomicProjectFactory {
    public IProteomicProject createProject(File projdir, IProject project);
}
