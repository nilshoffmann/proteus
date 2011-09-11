package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384Factory;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
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
            return "erstelle 384 Well Platte";
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
        IPlate384 plate384 = Lookup.getDefault().lookup(IPlate384Factory.class).createPlate384();
        this.proj.add384Plate(plate384);
        plate384.setParent(this.proj);
        listener.propertyChange(null);
    }

}
