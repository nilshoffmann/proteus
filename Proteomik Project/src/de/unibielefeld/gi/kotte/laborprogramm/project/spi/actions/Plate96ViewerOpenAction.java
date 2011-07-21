/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.centralLookup.CentralLookup;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IPlate96OpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.util.Lookup;

/**
 *
 * @author hoffmann
 */
public final class Plate96ViewerOpenAction implements ActionListener {

//    private final OpenCookie context;
    private final IPlate96 context;

    public Plate96ViewerOpenAction(IPlate96 context) {
        assert context != null;
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
//        CentralLookup.getDefault().add(this.context);
        IPlate96OpenCookie igoc = Lookup.getDefault().lookup(IPlate96OpenCookie.class);
        igoc.open();
    }
}
