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

import test.eventObject.handler.StringEventObjectHandler;
import test.eventObject.handler.DoubleEventObjectHandler;

/**
 *
 * @author nilshoffmann
 */
public class EventGenerator {

    public static void main(String[] args) {
        TypeSafeEventSource tses = new TypeSafeEventSource();
        tses.addListener(new StringEventObjectHandler());
        tses.addListener(new DoubleEventObjectHandler());
        
        Object[] obj = new Object[]{"abc",0.3541d,"asdkjl",213d,9013,"iju",90138,"iojbn"};
        for(Object o:obj) {
            tses.notifyListeners(o);
        }
    }
    
}
