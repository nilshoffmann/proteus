package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IUniqueObject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import java.util.List;

/**
 * Set of possible protein identifications for a Well384.
 *
 * @author hoffmann, kotte
 */
public interface IWellIdentification extends IPropertyChangeSource, IUniqueObject {

    public static final String PROPERTY_PARENT = "parent";

    public static final String PROPERTY_METHODS = "methods";
    
    public IWell384 getParent();
    
    public void setParent(IWell384 well);
    
    public List<IIdentificationMethod> getMethods();

    public IIdentificationMethod getMethodByName(String name);
    
    public void setMethods(List<IIdentificationMethod> method);
    
    public void addMethod(IIdentificationMethod method);
    
}
