package de.unibielefeld.gi.kotte.laborprogramm.pathway.api;

import java.beans.PropertyChangeListener;

/**
 *
 * @author hoffmann
 */
public interface IPropertyChangeSource {
    public void addPropertyChangeListener(PropertyChangeListener pcl);

    public void removePropertyChangeListener(PropertyChangeListener pcl);
}
