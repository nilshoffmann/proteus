/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;

/**
 *
 * @author hoffmann
 */
public class StateMachine {

    public static boolean isTransitionAllowed(Well96Status start, Well96Status end) {
        if(start==Well96Status.EMPTY && end==Well96Status.FILLED) {
            return true;
        }
        if(start==Well96Status.EMPTY && end==Well96Status.ERROR) {
            return true;
        }
        if(start==Well96Status.FILLED && end!=Well96Status.FILLED) {
            return true;
        }
        if(start==Well96Status.PROCESSED && end==Well96Status.ERROR) {
            return true;
        }
        if(start==Well96Status.ERROR && end==Well96Status.EMPTY) {
            return true;
        }
        return false;
    }

}
