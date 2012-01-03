package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;
import org.openide.util.Utilities;

/**
 * Action to create a new 384 well microplate.
 *
 * @author kotte
 */
public class RemovePlate384Action implements ActionListener {

    private final IPlate384 context;

    public RemovePlate384Action(IPlate384 context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<IPlate384> plates = this.context.getParent().get384Plates();
        System.out.println("Number of Plates before: "+plates.size());
        plates.remove(this.context);
        System.out.println("Number of Plates after: "+plates.size());
        IProteomicProject project = Utilities.actionsGlobalContext().lookup(IProteomicProject.class);
        if(project!=null) {
            System.out.println("Firing property change for removal of IPlate384");
            project.propertyChange(new PropertyChangeEvent(this,IProject.PROPERTY_PLATES384,null,plates));
        }
    }
}
