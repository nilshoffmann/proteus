package de.unibielefeld.gi.kotte.laborprogramm.spotDetailViewer.properties;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.beans.PropertyEditorSupport;

/**
 * Property Editor for IGel.
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
