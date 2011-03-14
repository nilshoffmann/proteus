package de.unibielefeld.gi.kotte.laborprogramm.plateViewer;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPlate;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IWell;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author kotte
 */
public class PlatePanel extends JPanel {

    private class WellAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private Icon wellEmpty;
    private Icon wellError;
    private Icon wellOkay;
    private final static String resourcePath = "/homes/kotte/ProteomikProjekt/";

    public PlatePanel(IPlate plate) {
        //Icons to display wells on the plate
        wellEmpty = new ImageIcon(resourcePath + "Well01.jpg");
        wellError = new ImageIcon(resourcePath + "Well02.jpg");
        wellOkay  = new ImageIcon(resourcePath + "Well03.jpg");

        final int x = plate.getXdimension();
        final int y = plate.getYdimension();
        this.setLayout(new GridLayout(x+1, y+1));

        //filling the grid
        add(new JLabel()); //add upper left corner empty JLabel placeholder
        for(int j = 1; j <= x; j++) {
            add(new JLabel(""+j)); //add top row label
        }
        for(char c = 'A'; c < 'A'+x; c++) {
            add(new JLabel(""+c)); //add leftmost column label
            for(int i = 1; i <= y; i++) {
                //create button for the current well on the plate grid
                IWell currentWell = plate.getWell(c, i);
                assert(currentWell != null); //FIXME this assertion fails
                JButton button = new JButton();
                button.setAction(new WellAction());
                //button.setName(currentWell.getWellPosition());
                button.setSize(16, 16);
                switch(currentWell.getStatus()) {
                    case ERROR:
                        button.setIcon(wellError);
                        break;
                    case IDENTIFIED:
                        button.setIcon(wellOkay);
                        break;
                    default:
                        button.setIcon(wellEmpty);
                }
                add(button); //add main grid area button
            }
        }
    }
}
