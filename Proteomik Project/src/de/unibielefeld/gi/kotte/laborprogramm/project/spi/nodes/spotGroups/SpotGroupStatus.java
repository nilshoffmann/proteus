package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes.spotGroups;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;

/**
 * Status of a ISpotGrooup for disply purposes.
 *
 * @author kotte
 */
enum SpotGroupStatus {
    ALL_UNPICKED, HAS_IDENT, PARTIALLY_PROCESSED;
    
    static SpotGroupStatus getSpotGroupStatus(ISpotGroup group) {
        boolean allUnpicked = true;
        for (ISpot spot : group.getSpots()) {
            if (spot.getStatus() == SpotStatus.PICKED) {
                allUnpicked = false;
                IWell96 well96 = spot.getWell();
                if (well96.getStatus() == Well96Status.PROCESSED) {
                    for (IWell384 well384 : well96.get384Wells()) {
                        if (well384.getStatus() == Well384Status.IDENTIFIED) {
                            return HAS_IDENT;
                        }
                    }
                }
            }
        }
        if (allUnpicked) {
            return ALL_UNPICKED;
        }
        return PARTIALLY_PROCESSED;
    }
}
