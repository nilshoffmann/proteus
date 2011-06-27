package de.unibielefeld.gi.kotte.laborprogramm.project.api;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.io.File;
import java.util.Map;
import org.netbeans.spi.project.ProjectFactory;

/**
 *
 * @author kotte
 */
public interface IProteomicProjectFactory extends ProjectFactory {
    public IProteomicProject createProject(File projdir, IProject project);
}
