package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.project.spi.dialogs.NameDialog;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384Factory;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Lookup;

/**
 * Action to create a new 384 well microplate.
 *
 * @author kotte
 */
public class CreatePlate384Action implements Action {
    private final IProject proj;
    private PropertyChangeListener listener;

    public CreatePlate384Action(IProject proj) {
        this.proj = proj;
    }

    @Override
    public Object getValue(String key) {
        //System.out.println("CreatePlate384Action.getValue() called with key: " + key);
        if(key.equals("Name")) {
            return "Erstelle 384 Well Platte";
        }
        return null;
    }

    @Override
    public void putValue(String key, Object value) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setEnabled(boolean b) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        IPlate384 plate384 = Lookup.getDefault().lookup(IPlate384Factory.class).createPlate384();
//        this.proj.add384Plate(plate384);
//        plate384.setParent(this.proj);
//        listener.propertyChange(null);

        System.out.println("CreatePlate384Action.actionPerformed() called");
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
            IPlate384 plate384 = Lookup.getDefault().lookup(IPlate384Factory.class).createPlate384();
        this.proj.add384Plate(plate384);
        plate384.setParent(this.proj);
            plate384.setName(dialog.getNameText());
            System.out.println("Firing PropertyChangeEvent: PLATE384_CREATED");
            listener.propertyChange(new PropertyChangeEvent(this,"PLATE384_CREATED",null,plate384));
        }
    }

}
