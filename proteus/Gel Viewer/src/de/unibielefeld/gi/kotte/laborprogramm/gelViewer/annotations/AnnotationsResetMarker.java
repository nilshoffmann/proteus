/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations;

/**
 * Marker class for Annotation Format Update.
 * @author hoffmann
 */
public class AnnotationsResetMarker {

    private boolean isUpdated = false;
    
    public void setUpdated(boolean b) {
        this.isUpdated = b;
    }
    
    public boolean isUpdated() {
        return isUpdated;
    }
    
}
