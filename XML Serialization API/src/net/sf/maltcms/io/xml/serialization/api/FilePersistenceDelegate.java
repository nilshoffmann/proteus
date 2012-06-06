/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.io.xml.serialization.api;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.io.File;

/**
 *
 * @author nilshoffmann
 */
public class FilePersistenceDelegate extends PersistenceDelegate {

    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        if (oldInstance instanceof File) {
            Object[] constructorArgs = new Object[]{((File) oldInstance).getPath()};
            return new Expression(oldInstance, oldInstance.getClass(), "new", constructorArgs);
        } 
        throw new RuntimeException("Can only persist instances of java.io.File!");
    }
}
