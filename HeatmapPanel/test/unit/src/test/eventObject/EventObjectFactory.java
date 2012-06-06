/*
 *  Copyright (C) 2008-2012 Nils Hoffmann
 *  Nils.Hoffmann A T CeBiTec.Uni-Bielefeld.DE
 *
 *  This file is part of Maui.
 *
 * Maui is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * Maui is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Maui.  If not, see <http://www.gnu.org/licenses/>.
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
