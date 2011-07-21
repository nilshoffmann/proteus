/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api;

import org.openide.windows.TopComponent;

/**
 *
 * @author hoffmann
 */
public interface IRegistry {
    public void openTopComponent(Object object, Class<? extends TopComponent> topComponentClass);
    public void closeTopComponent(Object object);
}
