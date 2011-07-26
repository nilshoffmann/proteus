/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.eventObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;
import test.eventObject.factory.IEventObjectFactory;

/**
 *
 * @author nilshoffmann
 */
public class EventObjectFactory {

    private HashMap<Class<?>, IEventObjectFactory> typeToFactory = new HashMap<Class<?>, IEventObjectFactory>();

    public EventObjectFactory() {
        ServiceLoader<IEventObjectFactory> l = ServiceLoader.load(IEventObjectFactory.class);
        Iterator<IEventObjectFactory> iter = l.iterator();
        while (iter.hasNext()) {
            IEventObjectFactory ieof = iter.next();
            if (typeToFactory.containsKey(ieof.getType())) {
                System.err.println("Warning: Already assigned a factory for type " + ieof.getType() + "! Not adding factory: " + ieof.getClass());
            } else {
                typeToFactory.put(ieof.getType(), ieof);
            }
        }

    }

    public <U> IEventObject<U> create(U u) {
        if(typeToFactory.containsKey(u.getClass())) {
            return typeToFactory.get(u.getClass()).create(u);
        }
        return null;
    }
}
