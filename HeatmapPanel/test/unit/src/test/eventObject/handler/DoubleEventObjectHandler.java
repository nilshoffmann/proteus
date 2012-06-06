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
package test.eventObject.handler;

import test.eventObject.IEventObject;

/**
 *
 * @author nilshoffmann
 */
public class DoubleEventObjectHandler implements IEventObjectHandler<Double, IEventObject<Double>> {

    @Override
    public void handle(IEventObject<? super Double> ivo) {
        System.out.println("Received Double: "+ivo.getCargo());
    }

    @Override
    public Class<? extends Double> getEventObjectCargoType() {
        return Double.class;
    }

}
