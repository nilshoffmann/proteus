package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification;

import java.beans.*;
import java.util.List;

/**
 *
 * @author kotte
 */
public class KeggNumbersPropertyEditor extends PropertyEditorSupport {

    public KeggNumbersPropertyEditor() {
    }

    @Override
    public String getAsText() {
        List<String> keggNumbers = (List<String>) getValue();
        String str = "";
        for (String number : keggNumbers) {
            str += number;
        }
        return str;
    }
}
