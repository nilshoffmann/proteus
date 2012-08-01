/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.io.xml.serialization.api;

import java.awt.Shape;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.PersistenceDelegate;
import org.openide.util.lookup.ServiceProvider;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * FIXME reimplement
 * @author hoffmann
 */
//@ServiceProvider(service=IPersistenceDelegateRegistration.class)
public class ShapePersistenceDelegate extends DefaultPersistenceDelegate implements IPersistenceDelegateRegistration {

    @Override
    protected void initialize(Class type, Object oldInstance, Object newInstance, Encoder out ) {
//        Shape path = (Shape)oldInstance;
//        GeneralPathDescriptor descriptor = new GeneralPathDescriptor(path);
//        descriptor.generate(out,path);
        throw new NotImplementedException();
    }

    @Override
    public Class<?> appliesTo() {
        return Shape.class;
    }

    @Override
    public PersistenceDelegate getPersistenceDelegate() {
        return this;
    }
}
