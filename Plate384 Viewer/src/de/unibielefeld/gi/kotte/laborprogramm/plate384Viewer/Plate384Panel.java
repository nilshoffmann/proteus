package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import java.beans.PropertyChangeEvent;
import javax.swing.JPanel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
import java.awt.GridLayout;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import javax.swing.JLabel;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

/**
 * GUI Panel that visually represents a IPlate384. Offers Well96Buttons to manipulate plate wells.
 *
 * @author kotte
 */
public class Plate384Panel extends JPanel implements PropertyChangeListener {

    private IWell96 well96 = null;
    private boolean autoAssign96Wells = false;
    private int currentPlateIndex = 0;
//    private IPlate384 plate = null;
    private Well384Button[] buttons = null;
    private InstanceContent instanceContent = null;
    private Lookup lookup = null;
    private Well384Button activeButton = null;

    public Plate384Panel(IPlate384 plate, InstanceContent instanceContent,
            Lookup lookup) {
//        this.plate = plate;
        this.instanceContent = instanceContent;
        this.lookup = lookup;
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
        buttons = new Well384Button[plate.getWells().length];
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
                    IWell384 well = plate.getWell(c, i);
                    well.addPropertyChangeListener(this);
                    Well384Button button = new Well384Button(well,
                            this);
                    //we want the buttons to be indexed by increasing x
                    buttons[plate.posToIndex(c, i)] = button;
                    add(button); //add main grid area button
                }
            }
        }
    }

    public void clear() {
        for (Well384Button button : buttons) {
            IWell96 well96 = button.getWell().getWell96();
            if (well96 != null) {
                button.getWell().setWell96(null);
                well96.get384Wells().remove(button.getWell());
                if (well96.get384Wells().isEmpty()) {
                    well96.setStatus(Well96Status.FILLED);
                }
            }
            for (Iterator<IIdentificationMethod> it = button.getWell().getIdentification().getMethods().iterator(); it.hasNext();) {
                IIdentificationMethod method = it.next();
                method.getIdentifications().clear();
            }
            button.getWell().getIdentification().getMethods().clear();
            button.getWell().setStatus(Well384Status.EMPTY);
        }
        currentPlateIndex = 0;
        repaint();
    }

    public void setActiveWellButton(Well384Button wellButton) {
        if (activeButton != null) {
            ISpot spot = lookup.lookup(ISpot.class);
            try {
                instanceContent.remove(spot);
            } catch (Exception e) {
            }
            IWell384 oldWell = activeButton.getWell();
            instanceContent.remove(oldWell);
            activeButton.setSelected(false);
            for (Well384Button button : buttons) {
                button.setSelected(false);
            }
            IWell96 oldWell96 = oldWell.getWell96();
            try {
                instanceContent.remove(oldWell96);
            } catch (Exception e) {
            }
        }
        wellButton.setSelected(true);
        instanceContent.add(wellButton.getWell());
        if (wellButton.getWell().getWell96() != null) {
            instanceContent.add(wellButton.getWell().getWell96());
            if (wellButton.getWell().getWell96().getSpot() != null) {
                instanceContent.add(wellButton.getWell().getWell96().getSpot());
            }
        }

        activeButton = wellButton;

//        if (activeButton != null) {
//            ISpot spot = lookup.lookup(ISpot.class);
//            try {
//                instanceContent.remove(spot);
//            } catch (Exception e) {
//            }
//            IWell96 oldWell = activeButton.getWell();
//            instanceContent.remove(oldWell);
//            activeButton.setSelected(false);
//            for (Well96Button button : buttons) {
//                button.setSelected(false);
//            }
//        }
//        wellButton.setSelected(true);
//        instanceContent.add(wellButton.getWell());
//        if (wellButton.getWell().getSpot() != null) {
//            instanceContent.add(wellButton.getWell().getSpot());
//        }
//        activeButton = wellButton;

    }

    public IWell96 getWell96() {
        return this.well96;
    }

    public void setWell96(IWell96 well96) {
        this.well96 = well96;
        if (autoAssign96Wells) {
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

    public boolean isAutoAssignWell96() {
        return autoAssign96Wells;
    }

    public void setButtonForWell96Active(IWell96 well96) {
        if (well96 != null) {
            for (int i = 0; i < 384; i++) {
                if (this.buttons[i].getWell().getWell96() == well96) {
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

    public Well384Button getNextUnassignedWell() {
        for (int i = currentPlateIndex; i < 384; i++) {
            if (this.buttons[i].getWell().getStatus() == Well384Status.EMPTY) {
                currentPlateIndex = i + 1;
                return this.buttons[i];
            }
        }
        throw new IllegalStateException("No empty wells left on plate!");
    }

    public void setAutoAssign96Wells(boolean autoAssign96Wells) {
        if (this.autoAssign96Wells != autoAssign96Wells) {
            currentPlateIndex = 0;
        }
        this.autoAssign96Wells = autoAssign96Wells;
    }

    public void autoPickPlate(IPlate96 plate96) {
        if (plate96 != null) {
            //increase currentPlateIndex until it references to a button in the top row
            while (!(this.buttons[currentPlateIndex].getWell().getRow() == 'A')) {
                currentPlateIndex++;
            }
            for (IWell96 well96 : plate96.getWells()) {
                if (well96.getStatus() == Well96Status.FILLED) {
                    //set current Well96
                    this.well96 = well96;
                    //connect the two wells
                    this.buttons[currentPlateIndex].selectStatus(
                            Well384Status.FILLED);
                }
                currentPlateIndex++;
                if (currentPlateIndex >= 384) {
                    throw new IllegalStateException(
                            "No empty wells left on plate!");
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }
}
