package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IPlate384OpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 * Action for opening IPlate384s.
 *
 * @author hoffmann
 */
@ActionID(
    category = "Proteus/Plate384Node",
id = "de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.actions.Plate384ViewerOpenAction")
@ActionRegistration(
    displayName = "#CTL_OpenPlate384Action")
@ActionReferences({@ActionReference(path = "Actions/Plate384Node", position = 100)})
@NbBundle.Messages("CTL_OpenPlate384Action=Open MALDI Target Plate")
public final class Plate384ViewerOpenAction implements ActionListener {

    private final IPlate384 context;

    public Plate384ViewerOpenAction(IPlate384 context) {
        assert context != null;
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        IPlate384OpenCookie igoc = Lookup.getDefault().lookup(IPlate384OpenCookie.class);
        igoc.open();
    }
}
