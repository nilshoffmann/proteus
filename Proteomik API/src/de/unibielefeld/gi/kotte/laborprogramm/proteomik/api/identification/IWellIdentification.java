package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import java.util.List;

/**
 * Set of possible protein identifications for a Well384.
 *
 * @author hoffmann
 */
public interface IWellIdentification extends IPropertyChangeSource{
    
    public static final String PROPERTY_WELL = "well";
    public static final String PROPERTY_IDENTIFICATIONS = "identifications";
    
    public IWell384 getWell();
    
    public void setWell(IWell384 well);
    
    public List<IIdentification> getIdentifications();
    
    public void setIdentifications(List<IIdentification> identifications);
    
    public void addIdentification(IIdentification identification);
    
}
