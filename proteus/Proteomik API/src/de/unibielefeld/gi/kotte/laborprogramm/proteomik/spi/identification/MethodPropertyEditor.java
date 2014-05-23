package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import java.beans.*;

/**
 *
 * @author Konstantin Otte
 */
public class MethodPropertyEditor extends PropertyEditorSupport {

    public MethodPropertyEditor() {
    }

    @Override
    public String getAsText() {
        IIdentificationMethod method = (IIdentificationMethod) getValue();
        return method.getName();
    }
}
