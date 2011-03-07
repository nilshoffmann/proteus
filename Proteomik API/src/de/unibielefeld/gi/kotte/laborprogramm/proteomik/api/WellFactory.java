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
public class WellFactory {

    public static IWell getDefault() {
        IWell result = Lookup.getDefault().lookup(IWell.class);
        return result;
    }
}
