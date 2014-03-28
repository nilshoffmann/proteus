package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.dialogs.NameDialog;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96Factory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

/**
 * Action for creating IPlate96s.
 *
 * @author Konstantin Otte
 */
@ActionID(
    category = "Proteus/ProteomicProject",
id = "de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer.actions.CreatePlate96Action")
@ActionRegistration(
    displayName = "#CTL_CreatePlate96Action")
@ActionReferences({@ActionReference(path = "Actions/ProteomicProject", position = 100)})
@Messages("CTL_CreatePlate96Action=Create New Microtiter Plate")
public final class CreatePlate96Action implements ActionListener {

    private final IProject context;

    public CreatePlate96Action(IProject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // Create a custom NotifyDescriptor, specify the panel instance as a parameter + other params
        NameDialog dialog = new NameDialog();
        NotifyDescriptor nd = new NotifyDescriptor(
                dialog, // instance of your panel
                "Enter name for 96 Well Plate", // title of the dialog
                NotifyDescriptor.OK_CANCEL_OPTION, // it is Yes/No dialog ...
                NotifyDescriptor.PLAIN_MESSAGE, // ... of a question type => a question mark icon
                null, // we have specified YES_NO_OPTION => can be null, options specified by L&F,
                // otherwise specify options as:
                //     new Object[] { NotifyDescriptor.YES_OPTION, ... etc. },
                NotifyDescriptor.OK_OPTION // default option is "Yes"
                );

        // let's display the dialog now...
        if (DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.OK_OPTION) {
            // user clicked yes, do something here, for example:
            //     System.out.println(myPanel.getNameFieldValue());
            IPlate96 plate96 = Lookup.getDefault().lookup(IPlate96Factory.class).createPlate96();
            this.context.add96Plate(plate96);
            plate96.setParent(this.context);
            plate96.setName(dialog.getNameText());
        }
    }
}
