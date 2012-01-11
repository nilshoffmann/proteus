package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.util.Lookup;

/**
 * Action to create a new 384 well microplate.
 *
 * @author kotte
 */
public class RemovePlate96Action implements ActionListener {

    private final IPlate96 context;

    public RemovePlate96Action(IPlate96 context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().closeTopComponent(this.context);
        this.context.getParent().remove96Plate(context);
    }
}
