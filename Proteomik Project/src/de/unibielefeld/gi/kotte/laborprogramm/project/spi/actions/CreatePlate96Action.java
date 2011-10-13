package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.project.spi.dialogs.NameDialog;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96Factory;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Lookup;

/**
 *
 * @author kotte
 */
public class CreatePlate96Action implements Action {

    private final IProject proj;
    private PropertyChangeListener listener;

    public CreatePlate96Action(IProject proj) {
        this.proj = proj;
    }

    @Override
    public Object getValue(String key) {
        System.out.println("CreatePlate96Action.getValue() called with key: " + key);
        if (key.equals("Name")) {
            return "Erstelle 96 Well Platte";
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
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("CreatePlate96Action.actionPerformed() called");
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
            this.proj.add96Plate(plate96);
            plate96.setParent(this.proj);
            plate96.setName(dialog.getNameText());
            System.out.println("Firing PropertyChangeEvent: PLATE96_CREATED");
            listener.propertyChange(new PropertyChangeEvent(this,"PLATE96_CREATED",null,plate96));
        }

    }
}
