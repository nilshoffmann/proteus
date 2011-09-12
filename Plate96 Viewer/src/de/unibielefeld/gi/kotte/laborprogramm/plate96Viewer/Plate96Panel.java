package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer;

import javax.swing.JPanel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
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
    private IWell384 well384 = null;
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

    public void setWell384(IWell384 well384) {
        this.well384 = well384;
        setButtonForWell384Active(well384);
    }

    public boolean isAutoAssignSpots() {
        return autoAssignSpots;
    }

    protected void setButtonForSpotActive(ISpot spot) {
        if (spot != null) {
            for (int i = 0; i < 96; i++) {
                if (this.buttons[i].getWell().getSpot() == spot) {
                    this.buttons[i].setSelected(true);
                } else {
                    this.buttons[i].setSelected(false);
                }
            }
        } else {
            for (int i = 0; i < 96; i++) {
                this.buttons[i].setSelected(false);
            }
        }
    }

    protected void setButtonForWell384Active(IWell384 well384) {
        if (well384 != null) {
            for (int i = 0; i < 96; i++) {
                if (this.buttons[i].getWell().get384Wells().contains(well384)) {
                    this.buttons[i].setSelected(true);
                } else {
                    this.buttons[i].setSelected(false);
                }
            }
        } else {
            for (int i = 0; i < 96; i++) {
                this.buttons[i].setSelected(false);
            }
        }
    }

    protected Well96Button getNextUnassignedWell() {
        for (int i = currentPlateIndex; i < 96; i++) {
            if (this.buttons[i].getWell().getStatus() == Well96Status.EMPTY) {
                currentPlateIndex = i + 1;
                return this.buttons[i];
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
