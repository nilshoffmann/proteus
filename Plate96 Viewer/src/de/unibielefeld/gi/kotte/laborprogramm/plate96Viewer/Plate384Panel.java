package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author kotte
 */
public class Plate384Panel extends JPanel {

    private class Well384Action extends AbstractAction {

        IWell384 well;
        JButton button;

        public Well384Action(IWell384 well, JButton button) {
            this.well = well;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO aktuellen Well im LookUp anmelden
            well.setStatus(Well384Status.FILLED);
            button.setIcon(wellOkay);
        }
    }
    private Icon wellEmpty;
    private Icon wellError;
    private Icon wellOkay;
    //private final static String resourcePath = "resources/";
    //de/unibielefeld/gi/kotte/laborprogramm/plateViewer/

    public Plate384Panel(IPlate384 plate) {
        //Icons to display wells on the plate
        URL emptyURL = Plate96Panel.class.getResource("resources/Well01.jpg");
        URL errorURL = Plate96Panel.class.getResource("resources/Well02.jpg");
        URL okayURL = Plate96Panel.class.getResource("resources/Well03.jpg");
        wellEmpty = new ImageIcon(emptyURL);//resourcePath + "Well01.jpg");
        wellError = new ImageIcon(errorURL);//resourcePath + "Well02.jpg");
        wellOkay  = new ImageIcon(okayURL); //resourcePath + "Well03.jpg");

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

        for (char c = 'A'; c < 'A' + y; c++) {
            JLabel jl = new JLabel("" + c);
            jl.setHorizontalAlignment(JLabel.RIGHT);
            add(jl); //add leftmost column label

            Dimension dim16 = new Dimension(16, 16);
            for (int i = 1; i <= x; i++) {
                //create button for the current well on the plate grid
                IWell384 currentWell = plate.getWell(c, i);
                JButton button = new JButton();
                button.setAction(new Well384Action(currentWell, button));
                button.setName(currentWell.getWellPosition());
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                button.setPreferredSize(dim16);
                button.setMinimumSize(dim16);
                switch (currentWell.getStatus()) {
                    //TODO Farben für weitere Zustände einbinden
                    case ERROR:
                        button.setIcon(wellError);
                        break;
                    case FILLED:
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
