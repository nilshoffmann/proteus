/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
public class PlateFactory {

    public static IPlate getDefault() {
        IPlate result = Lookup.getDefault().lookup(IPlate.class);
        return result;
    }
}
