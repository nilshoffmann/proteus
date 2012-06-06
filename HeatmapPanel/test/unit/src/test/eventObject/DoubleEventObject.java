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

/**
 *
 * @author nilshoffmann
 */
public class DoubleEventObject implements IEventObject<Double> {

    private final Double cargo;
    private final long when;
    
    public DoubleEventObject(Double s) {
        cargo = s;
        when = System.nanoTime();
    }
   
    @Override
    public long getWhen() {
        return when;
    }

    @Override
    public int compareTo(IEventObject t) {
        return Long.valueOf(when).compareTo(Long.valueOf(t.getWhen()));
    }

    @Override
    public Double getCargo() {
        return this.cargo;
    }

    @Override
    public Class<? extends Double> getCargoType() {
        return this.cargo.getClass();
    }
    
    public static DoubleEventObject create(Double d) {
        if(d instanceof Double) {
            return new DoubleEventObject(d);
        }
        return null;
    }

}
