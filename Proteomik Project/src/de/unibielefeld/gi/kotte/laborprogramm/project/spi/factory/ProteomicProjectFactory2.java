/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.project.spi.factory;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProjectFactory;
import de.unibielefeld.gi.kotte.laborprogramm.project.spi.ProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.io.File;
import java.net.MalformedURLException;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author hoffmann
 */
@ServiceProvider(service=IProteomicProjectFactory.class)
public class ProteomicProjectFactory2 implements IProteomicProjectFactory{
    @Override
    public IProteomicProject createProject(File projdir, IProject project) {
        ProteomicProject proproject = null;
        try {
            proproject = new ProteomicProject();
            proproject.activate(new File(projdir, ProteomikProjectFactory.PROJECT_FILE).toURI().toURL());
            proproject.setProjectData(project);
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return proproject;
    }
}
