package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IUniqueObject;
import java.util.List;

/**
 * Set of possible protein identifications for a specific identification method
 *
 * @author Konstantin Otte
 */
public interface IIdentificationMethod extends IPropertyChangeSource, IUniqueObject {

    public static final String PROPERTY_PARENT = "parent";

    public static final String PROPERTY_IDENTIFICATIONS = "identifications";

    public static final String PROPERTY_NAME = "method name";

    public IWellIdentification getParent();

    public void setParent(IWellIdentification parent);

    public String getName();

    public void setName(String name);

    public List<IIdentification> getIdentifications();

    public void setIdentifications(List<IIdentification> identifications);

    public void addIdentification(IIdentification identification);
}
