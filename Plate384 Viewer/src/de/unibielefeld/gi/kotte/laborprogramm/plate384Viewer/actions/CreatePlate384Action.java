package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.dialogs.NameDialog;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384Factory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

/**
 * Action for creating IPlate384s.
 *
 * @author kotte
 */
@ActionID(
    category = "ProteomicProject",
id = "de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.actions.CreatePlate384Action")
@ActionRegistration(
    displayName = "#CTL_CreatePlate384Action")
@Messages("CTL_CreatePlate384Action=Create New MALDI Target Plate")
public final class CreatePlate384Action implements ActionListener {

    private final IProject context;

    public CreatePlate384Action(IProject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // Create a custom NotifyDescriptor, specify the panel instance as a parameter + other params
        NameDialog dialog = new NameDialog();
        NotifyDescriptor nd = new NotifyDescriptor(
                dialog, // instance of your panel
                "Enter name for 384 Well Plate", // title of the dialog
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
            IPlate384 plate384 = Lookup.getDefault().lookup(
                    IPlate384Factory.class).createPlate384();
            this.context.add384Plate(plate384);
            plate384.setParent(this.context);
            plate384.setName(dialog.getNameText());
        }
    }
}
