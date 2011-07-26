/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
