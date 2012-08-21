package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IPlate96OpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 * Action for opening IPlate96s.
 *
 * @author hoffmann
 */
@ActionID(
    category = "Proteus/Plate96Node",
id = "de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.actions.Plate384ViewerOpenAction")
@ActionRegistration(
    displayName = "#CTL_OpenPlate96Action")
@ActionReferences({@ActionReference(path = "Actions/Plate96Node", position = 100)})
@NbBundle.Messages("CTL_OpenPlate96Action=Open Microtiter Plate")
public final class Plate96ViewerOpenAction implements ActionListener {
    private final IPlate96 context;

    public Plate96ViewerOpenAction(IPlate96 context) {
        assert context != null;
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        IPlate96OpenCookie igoc = Lookup.getDefault().lookup(IPlate96OpenCookie.class);
        igoc.open();
    }
}
