package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification;

import java.beans.*;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Konstantin Otte
 */
public class KeggNumbersPropertyEditor extends PropertyEditorSupport {

    public KeggNumbersPropertyEditor() {
    }

    @Override
    public String getAsText() {
        List<String> keggNumbers = (List<String>) getValue();
        String str = "";
        Iterator<String> it = keggNumbers.iterator();
        if(it.hasNext()) {
            str = it.next();
        }
        for (; it.hasNext();) {
            str += ", " + it.next();
        }
        return str;
    }
}
