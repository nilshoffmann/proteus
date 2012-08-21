package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

/**
 * Action for deleting IPlate384s.
 * 
 * @author kotte
 */
@ActionID(
    category = "Proteus/Plate384Node",
id = "de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.actions.DeleteIdentificationsAction")
@ActionRegistration(
    displayName = "#CTL_DeleteIdentificationsAction")
@ActionReferences({@ActionReference(path = "Actions/Plate384Node", position = 400)})
@Messages("CTL_DeleteIdentificationsAction=Delete Identifications")
public final class DeleteIdentificationsAction implements ActionListener {

    private final IPlate384 context;

    public DeleteIdentificationsAction(IPlate384 context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        for (IWell384 well : this.context.getWells()) {
            well.getIdentification().getMethods().clear();
            if (well.getStatus() != Well384Status.EMPTY && well.getStatus() != Well384Status.ERROR) {
                well.setStatus(Well384Status.FILLED);
            }
        }
    }
}
