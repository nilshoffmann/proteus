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
package test;

import test.eventObject.EventObjectFactory;
import test.eventObject.handler.IEventObjectHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.SwingUtilities;
import test.eventObject.IEventObject;

/**
 *
 * @author nilshoffmann
 */
public class TypeSafeEventSource implements Runnable {

    private HashMap<Class<?>, Set<IEventObjectHandler>> listeners = new HashMap<Class<?>, Set<IEventObjectHandler>>();
    private List<IEventObjectHandler> toNotify = Collections.emptyList();
    private Object o = null;
    private EventObjectFactory eof = new EventObjectFactory();

    public void addListener(IEventObjectHandler its) {
        if (this.listeners.containsKey(its.getEventObjectCargoType())) {
            listeners.get(its.getEventObjectCargoType()).add(its);
        } else {
            LinkedHashSet<IEventObjectHandler> lhs = new LinkedHashSet<IEventObjectHandler>();
            lhs.add(its);
            listeners.put(its.getEventObjectCargoType(), lhs);
        }
    }

    public void removeListener(IEventObjectHandler its) {
        if (listeners.containsKey(its.getEventObjectCargoType())) {
            Set<IEventObjectHandler> s = listeners.get(its.getEventObjectCargoType());
            s.remove(its);
        }
    }

    public <T> void notifyListeners(T t) {
        System.out.println("Retrieving listeners for class "+t.getClass());
        Set<IEventObjectHandler> s = listeners.get(t.getClass());
        if (s != null) {
            toNotify = new LinkedList<IEventObjectHandler>(s);
            System.out.println("notifying: "+toNotify);
            o = t;
            if (SwingUtilities.isEventDispatchThread()) {
                run();
            } else {
                SwingUtilities.invokeLater(this);
            }

        }else{
            System.out.println("No listeners found!");
        }
    }

    @Override
    public void run() {
        for (IEventObjectHandler tysl : toNotify) {
            IEventObject ieo = eof.create(o);
            tysl.handle(ieo);
        }
        toNotify = Collections.emptyList();
    }
}
