package de.unibielefeld.gi.kotte.laborprogramm.spotDetailViewer.properties;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import java.beans.PropertyEditorSupport;

/**
 * Property Editor for IWell96.
 *
 * @author Konstantin Otte
 */
public class Well96PropertyEditor extends PropertyEditorSupport {
    
    @Override
    public String getAsText() {
        IWell96 well = (IWell96) getValue();
        String text;
        if (well == null) {
            text = "no Well";
        } else {
            text = well.getParent().getName() + ":" + well.getWellPosition();
        }
        return text;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

    }
    
}
