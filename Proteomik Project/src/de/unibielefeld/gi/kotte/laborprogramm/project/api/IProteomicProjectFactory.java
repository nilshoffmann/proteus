package de.unibielefeld.gi.kotte.laborprogramm.project.api;

import java.io.File;
import java.util.Map;
import org.netbeans.spi.project.ProjectFactory;

/**
 *
 * @author kotte
 */
public interface IProteomicProjectFactory extends ProjectFactory {
    public IProteomicProject createProject(File projdir);
    public IProteomicProject createProject(Map<String, Object> props, File projdir);
}
