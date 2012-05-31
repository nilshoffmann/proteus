package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IPlate384OpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.util.Lookup;

/**
 *
 * @author hoffmann
 */
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
