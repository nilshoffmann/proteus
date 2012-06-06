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
package net.sf.maltcms.ui.plot.heatmap.event.mouse;

/**
 *
 * @author nilshoffmann
 */
public class MouseEvent {

    private final MouseEventType met;
    private final java.awt.event.MouseEvent me;
    
    public MouseEvent(java.awt.event.MouseEvent me, MouseEventType met) {
        this.me = me;
        this.met = met;
    }

    public java.awt.event.MouseEvent getMe() {
        return me;
    }

    public MouseEventType getMet() {
        return met;
    }
}
