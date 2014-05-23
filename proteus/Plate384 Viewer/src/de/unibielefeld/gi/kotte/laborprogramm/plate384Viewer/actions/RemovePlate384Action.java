package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
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
 * Action for deleting IPlate384s.
 *
 * @author Konstantin Otte
 */
@ActionID(
    category = "Proteus/Plate384Node",
id = "de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.actions.RemovePlate384Action")
@ActionRegistration(
    displayName = "#CTL_RemovePlate384Action")
@ActionReferences({@ActionReference(path = "Actions/Plate384Node", position = 410)})
@Messages("CTL_RemovePlate384Action=Delete")
public final class RemovePlate384Action implements ActionListener {

    private final IPlate384 context;

    public RemovePlate384Action(IPlate384 context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().closeTopComponentsFor(this.context);
        this.context.getParent().remove384Plate(context);
    }
}
