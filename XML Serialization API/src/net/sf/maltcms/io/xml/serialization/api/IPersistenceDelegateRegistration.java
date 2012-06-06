/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.io.xml.serialization.api;

import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;

/**
 *
 * @author nilshoffmann
 */
public interface IPersistenceDelegateRegistration {
    
    PersistenceDelegate getPreferredDelegate(Class<?> clazz, String...constructorArgumentProperties);
    
    void registerPersistenceDelegates(XMLEncoder encoder);
    
}
