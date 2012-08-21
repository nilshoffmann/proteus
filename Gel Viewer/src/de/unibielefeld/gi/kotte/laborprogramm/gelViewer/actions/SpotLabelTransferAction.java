package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Proteus/GelNode",
id = "de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions.SpotLabelTransferAction")
@ActionRegistration(displayName = "#CTL_SpotLabelTransferAction")
@ActionReferences({@ActionReference(path = "Actions/GelNode", position = 200)})
@Messages("CTL_SpotLabelTransferAction=Transfer Spot Labels")
/**
 * Action that transfers the labels of a gel to all other gels of its project.
 * 
 * @author kotte
 */
public final class SpotLabelTransferAction implements ActionListener {

    private final IGel context;

    public SpotLabelTransferAction(IGel context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // fetch non-empty labels in a SpotID->Label Hash
        HashMap<Integer, String> labels = new HashMap<Integer, String>();
        for (ISpot spot : context.getSpots()) {
            if (spot.getLabel() != null && !spot.getLabel().isEmpty()) {
                labels.put(spot.getNumber(), spot.getLabel());
            }
        }
        
        // iterate over each spot of the project and override its label
        // iff there is a label in the hash for this spot's ID
        IProject proj = context.getParent().getParent().getParent().getParent();
        for (ILogicalGelGroup lgg : proj.getGelGroups()) {
            for (IBioRepGelGroup brgg : lgg.getGelGroups()) {
                for (ITechRepGelGroup trgg : brgg.getGelGroups()) {
                    for (IGel gel : trgg.getGels()) {
                        for (ISpot spot : gel.getSpots()) {
                            if(labels.containsKey(spot.getNumber())) {
                                spot.setLabel(labels.get(spot.getNumber()));
                            }
                        }
                    }
                }
            }
        }
    }
}
