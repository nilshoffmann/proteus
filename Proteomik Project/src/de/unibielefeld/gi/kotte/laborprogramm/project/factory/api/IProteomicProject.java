/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.project.factory.api;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import java.net.URL;
import org.netbeans.api.project.Project;

/**
 *
 * @author kotte
 */
public interface IProteomicProject extends Project, IProject{

    public void activate(URL url);
    
}
