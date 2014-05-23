/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.io.xml.serialization.api;

import java.beans.PersistenceDelegate;

/**
 *
 * @author hoffmann
 */
public interface IPersistenceDelegateRegistration {
    
    Class<?> appliesTo();
    
    PersistenceDelegate getPersistenceDelegate();
}
