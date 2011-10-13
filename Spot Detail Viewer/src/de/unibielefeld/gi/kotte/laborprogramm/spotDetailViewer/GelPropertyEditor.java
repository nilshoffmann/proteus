/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.spotDetailViewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author hoffmann
 */
public class GelPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        IGel gel = (IGel) getValue();
        return gel.getName();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

    }

}
