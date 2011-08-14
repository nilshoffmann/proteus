package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer;

import javax.swing.JPanel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import java.awt.GridLayout;
import javax.swing.JLabel;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * GUI Panel that visually represents a IPlate384. Offers Well96Buttons to manipulate plate wells.
 *
 * @author kotte
 */
public class Plate384Panel extends JPanel {

    private IWell96 well96 = null;
    private boolean autoAssignSpots = false;
    private int currentPlateIndex = 0;
    private IPlate384 plate = null;
    private Well384Button[] buttons = null;

    public Plate384Panel(IPlate384 plate) {
        this.plate = plate;
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
        buttons = new Well384Button[plate.getWells().length];
        int buttonCnt = 0;
        //rows
        for (char c = 'A'; c < 'A' + y; c++) {
            int rowIndex = ((int) c) - ((int) 'A');
            //columns
            for (int i = 0; i <= x; i++) {
                if (i == 0) {
                    JLabel jl = new JLabel("" + c);
                    jl.setHorizontalAlignment(JLabel.RIGHT);
                    add(jl); //add leftmost column label
                } else {
                    //create button for the current well on the plate grid
                    Well384Button button = new Well384Button(plate.getWell(c, i), this);
                    //we want the buttons to be indexed by increasing x
                    buttons[plate.posToIndex(c, i)] = button;
                    add(button); //add main grid area button
                }
            }
        }
    }

    public IWell96 getWell96() {
        return this.well96;
    }

    public void setWell96(IWell96 well96) {
        this.well96 = well96;
        if (autoAssignSpots) {
            try {
                Well384Button wb = getNextUnassignedWell();
                wb.selectStatus(Well384Status.FILLED);
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
        setButtonForWell96Active(well96);
    }

    public boolean isAutoAssignSpots() {
        return autoAssignSpots;
    }

    protected void setButtonForWell96Active(IWell96 well96) {
        if (well96 != null) {
            Well384Button[] wells = this.buttons;
            for (int i = currentPlateIndex; i < wells.length; i++) {
//                if (wells[i].getWell().getSpot() == spot) {
                    wells[i].setSelected(true);
//                } else {
//                    wells[i].setSelected(false);
//                }
            }
        }
    }

    protected Well384Button getNextUnassignedWell() {
        Well384Button[] wells = this.buttons;
        for (int i = currentPlateIndex; i < wells.length; i++) {
            if (wells[i].getWell().getStatus() == Well384Status.EMPTY) {
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
