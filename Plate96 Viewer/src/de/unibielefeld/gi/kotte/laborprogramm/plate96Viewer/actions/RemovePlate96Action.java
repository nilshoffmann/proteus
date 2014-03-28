package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.actions;

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

/**
 * Action for deleting IPlate96s.
 * 
 * @author Konstantin Otte
 */
@ActionID(
    category = "Proteus/Plate96Node",
id = "de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.actions.RemovePlate96Action")
@ActionRegistration(
    displayName = "#CTL_RemovePlate96Action")
@ActionReferences({@ActionReference(path = "Actions/Plate96Node", position = 400)})
@Messages("CTL_RemovePlate96Action=Delete")
public final class RemovePlate96Action implements ActionListener {

    private final IPlate96 context;

    public RemovePlate96Action(IPlate96 context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().closeTopComponentsFor(this.context);
        this.context.getParent().remove96Plate(context);
    }
}
