package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;

/**
 * State Machine for Well384Status status changes. Defines legal transitions between states.
 *
 * @author kotte
 */
public class StateMachine {

    public static boolean isTransitionAllowed(Well384Status start, Well384Status end) {
        if(end==Well384Status.ERROR) {
            return true;
        }
        if(start==Well384Status.EMPTY && end==Well384Status.FILLED) {
            return true;
        }
        if(start==Well384Status.FILLED) {
            return true;
        }
        return false;
    }

}