package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.Plate96ViewerTopComponent;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

@ActionID(
    category = "Proteus/SpotNode",
id = "de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.actions.ShowSpotOnPlateAction")
@ActionRegistration(
    displayName = "#CTL_ShowSpotOnPlateAction")
@ActionReferences({@ActionReference(path = "Actions/SpotNode", position = 110)})
@Messages("CTL_ShowSpotOnPlateAction=Show on Plate")
public final class ShowSpotOnPlateAction implements ActionListener {

    private final ISpot context;

    //TODO für den Konstrukeor einer kontextsensitiven Aktion:
    //putValue(Action.NAME, NbBundle.getMessage(ShowSpotOnPlateAction.class,"CTL_ShowSpotOnPlateAction"));
    
    //TODO Bedingung für condionally enabled Action:
    //setEnabled(context.getStatus == SpotStatus.PICKED);
    
    public ShowSpotOnPlateAction(ISpot context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        IPlate96 plate = context.getWell().getParent();
        Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().openTopComponentFor(plate, Plate96ViewerTopComponent.class);
    }
}
