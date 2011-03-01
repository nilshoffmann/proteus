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
public class GelFactory {

    public static IGel getDefault() {
        IGel result = Lookup.getDefault().lookup(IGel.class);
        return result;
    }

}
