/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import java.beans.PropertyChangeListener;

/**
 *
 * @author hoffmann
 */
public interface IPropertyChangeSource {
    public void addPropertyChangeListener(PropertyChangeListener pcl);

    public void removePropertyChangeListener(PropertyChangeListener pcl);
}
