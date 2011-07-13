package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96Factory;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
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
        if(key.equals("Name")) {
            return "erstelle 96 Well Platte";
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
        IPlate96 plate96 = Lookup.getDefault().lookup(IPlate96Factory.class).createPlate96();
        this.proj.add96Plate(plate96);
        plate96.setParent(this.proj);
        listener.propertyChange(null);
    }

}
