package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification;

/**
 * Factory for creating WellIdentifications.
 *
 * @author kotte
 */
public interface IWellIdentificationFactory {
    
    public IWellIdentification createWellIdentification();
    
}
