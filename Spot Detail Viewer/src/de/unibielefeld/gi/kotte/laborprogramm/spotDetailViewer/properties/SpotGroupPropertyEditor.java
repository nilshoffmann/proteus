package de.unibielefeld.gi.kotte.laborprogramm.spotDetailViewer.properties;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.beans.PropertyEditorSupport;

/**
 * Property Editor for ISpotGroup.
 *
 * @author hoffmann
 */
public class SpotGroupPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        ISpotGroup spotGroup = (ISpotGroup) getValue();
        if (spotGroup == null) {
            return "no spot group";
        }
        return spotGroup.getNumber()+" "+spotGroup.getLabel();
 //       return spotGroup.getLabel();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

    }

}
