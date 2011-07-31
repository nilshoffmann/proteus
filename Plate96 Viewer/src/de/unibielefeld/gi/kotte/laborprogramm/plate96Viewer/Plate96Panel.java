package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer;

import javax.swing.JPanel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * GUI Panel that visually represents a IPlate96. Offers Well96Buttons to manipulate plate wells.
 *
 * @author kotte
 */
public class Plate96Panel extends JPanel {

    private ISpot spot = null;

    public Plate96Panel(IPlate96 plate) {
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

        for (char c = 'A'; c < 'A' + y; c++) {
            JLabel jl = new JLabel("" + c);
            jl.setHorizontalAlignment(JLabel.RIGHT);
            add(jl); //add leftmost column label

            for (int i = 1; i <= x; i++) {
                //create button for the current well on the plate grid
                JButton button = new Well96Button(plate.getWell(c, i), this);
                add(button); //add main grid area button
            }
        }
    }

    public ISpot getSpot() {
        return spot;
    }

    public void setSpot(ISpot spot) {
        this.spot = spot;
    }
}
