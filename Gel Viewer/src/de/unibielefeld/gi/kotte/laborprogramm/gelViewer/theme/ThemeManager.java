/*
 *  Copyright (C) 2008-2012 Nils Hoffmann
 *  Nils.Hoffmann A T CeBiTec.Uni-Bielefeld.DE
 *
 *  This file is part of Maui.
 *
 *  Maui is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Maui is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Maui.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.theme;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

/**
 *
 * @author nils
 */
public class ThemeManager {

    private Font font;

    private HashMap<String,Color> typeToColor = new HashMap<String,Color>();

    private static ThemeManager tm = null;

    public static ThemeManager getInstance() {
        if(tm==null) {
            tm = new ThemeManager();
            String prefix = "de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.";
            tm.setColor(prefix+"fillColor",new Color(255, 255, 255, 64));
            tm.setColor(prefix+"strokeColor",Color.BLUE);
            tm.setColor(prefix+"selectedFillColor",Color.BLUE.brighter());
            tm.setColor(prefix+"selectedStrokeColor",Color.ORANGE);
            tm.setColor(prefix+"selectionCrossColor",Color.BLACK);
            tm.setColor(prefix+"textColor",Color.BLACK);
            tm.setColor(prefix+"lineColor",Color.BLACK);
        }
        return tm;
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font f) {
        this.font = f;
    }

    public void setColor(String name, Color c) {
        typeToColor.put(name,c);
    }

    public Color getColor(String name) {
        if(typeToColor.containsKey(name)) {
            typeToColor.get(name);
        }
        throw new IllegalArgumentException("No color for key "+name);
    }



}
