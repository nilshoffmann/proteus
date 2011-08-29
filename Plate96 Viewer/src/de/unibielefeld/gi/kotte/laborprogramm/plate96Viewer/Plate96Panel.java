package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer;

import javax.swing.JPanel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
import java.awt.GridLayout;
import javax.swing.JLabel;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.lookup.InstanceContent;

/**
 * GUI Panel that visually represents a IPlate96. Offers Well96Buttons to manipulate plate wells.
 *
 * @author kotte
 */
public class Plate96Panel extends JPanel {

    private ISpot spot = null;
    private boolean autoAssignSpots = false;
    private int currentPlateIndex = 0;
//    private IPlate96 plate = null;
    private Well96Button[] buttons = null;
    private InstanceContent instanceContent = null;
    private Well96Button activeButton = null;

    public Plate96Panel(IPlate96 plate, InstanceContent instanceContent) {
//        this.plate = plate;
        this.instanceContent = instanceContent;
        final int x = plate.getXdimension();
        final int y = plate.getYdimension();
        this.setLayout(new GridLayout(y + 1, x + 1));
        setName(plate.getName());
        add(new JLabel()); //add upper left corner empty JLabel placeholder

        //FIXME button initialization needs to be done differently
        //TODO implement auto assignment of next free well, also handle resetting of already assigned wells
        //assignment should proceed from left top to bottom, column by column (A1, B1, C1 ..., A2, B2, C2 ...)
        for (int j = 1; j <= x; j++) {
            JLabel jl = new JLabel("" + j);
            jl.setHorizontalAlignment(JLabel.CENTER);
            add(jl); //add top row label
        }
        buttons = new Well96Button[plate.getWells().length];
        //rows
        for (char c = 'A'; c < 'A' + y; c++) {
            //columns
            for (int i = 0; i <= x; i++) {
                if (i == 0) {
                    JLabel jl = new JLabel("" + c);
                    jl.setHorizontalAlignment(JLabel.RIGHT);
                    add(jl); //add leftmost column label
                } else {
                    //create button for the current well on the plate grid
                    Well96Button button = new Well96Button(plate.getWell(c, i), this);
                    //we want the buttons to be indexed by increasing x
                    buttons[plate.posToIndex(c, i)] = button;
                    add(button); //add main grid area button
                }
            }
        }
    }

    public void setActiveWellButton(Well96Button wellButton) {
        if (activeButton != null) {
            IWell96 oldWell = activeButton.getWell();
            instanceContent.remove(oldWell);
            activeButton.setSelected(false);
        }
        wellButton.setSelected(true);
        instanceContent.add(wellButton.getWell());
        activeButton = wellButton;
    }

    public ISpot getSpot() {
        return spot;
    }

    public void setSpot(ISpot spot) {
        this.spot = spot;
        if (autoAssignSpots) {
            try {
                Well96Button wb = getNextUnassignedWell();
                wb.selectStatus(Well96Status.FILLED);
            } catch (IllegalStateException ise) {
                NotifyDescriptor nd = new NotifyDescriptor(
                        "No empty wells left on plate!", // instance of your panel
                        "No more wells", // title of the dialog
                        NotifyDescriptor.DEFAULT_OPTION, // it is Yes/No dialog ...
                        NotifyDescriptor.WARNING_MESSAGE, // ... of a question type => a question mark icon
                        null, // we have specified YES_NO_OPTION => can be null, options specified by L&F,
                        // otherwise specify options as:
                        //     new Object[] { NotifyDescriptor.YES_OPTION, ... etc. },
                        NotifyDescriptor.OK_OPTION // default option is "Yes"
                        );

                // let's display the dialog now...
                DialogDisplayer.getDefault().notify(nd);
//                Exceptions.printStackTrace(ise);
            }
        }
        setButtonForSpotActive(spot);
    }

    public boolean isAutoAssignSpots() {
        return autoAssignSpots;
    }

    protected void setButtonForSpotActive(ISpot spot) {
        if (spot != null) {
            Well96Button[] wells = this.buttons;
            for (int i = currentPlateIndex; i < wells.length; i++) {
                if (wells[i].getWell().getSpot() == spot) {
                    wells[i].setSelected(true);
                } else {
                    wells[i].setSelected(false);
                }
            }
        }
    }

    protected Well96Button getNextUnassignedWell() {
        Well96Button[] wells = this.buttons;
        for (int i = currentPlateIndex; i < wells.length; i++) {
            if (wells[i].getWell().getStatus() == Well96Status.EMPTY) {
                currentPlateIndex = i + 1;
                return wells[i];
            }
        }
        throw new IllegalStateException("No empty wells left on plate!");
    }

    public void setAutoAssignSpots(boolean autoAssignSpots) {
        if (this.autoAssignSpots != autoAssignSpots) {
            currentPlateIndex = 0;
        }
        this.autoAssignSpots = autoAssignSpots;
    }
}
