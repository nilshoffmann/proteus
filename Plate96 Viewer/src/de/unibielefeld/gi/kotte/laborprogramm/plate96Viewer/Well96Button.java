package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Button to be placed on a Plate96Panel. Represents a IWell96 and offers actions to set the wells status.
 *
 * @author hoffmann, kotte
 */
public class Well96Button extends JButton {

    private IWell96 well = null;

    private class Well96Action extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dia = new Well96StatusSelectDialog(well.getStatus());
            dia.setVisible(true);
        }
    }

    private class Well96StatusSelectDialog extends JDialog {

        public Well96StatusSelectDialog(Well96Status status) {
            JPanel panel = new JPanel();
            panel.add(new JLabel("choose status:"));
            ButtonGroup group = new ButtonGroup();
            for (Well96Status s : Well96Status.values()) {
                JRadioButton jrb = new JRadioButton(s.toString());
                if (s == status) {
                    jrb.setSelected(true);
                }
                jrb.setAction(new Well96StatusSelectRadioButtonAction(s));
                group.add(jrb);
                panel.add(jrb);
            }
            add(panel);
        }
    }

    private class Well96StatusSelectRadioButtonAction extends AbstractAction {

        private Well96Status status;

        public Well96StatusSelectRadioButtonAction(Well96Status status) {
            this.status = status;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selectStatus(status);
        }
    }

    public Well96Button(IWell96 well) {
        this.well = well;
        setAction(new Well96Action());
        setName(well.getWellPosition());
        setBorderPainted(false);
        setContentAreaFilled(false);
    }

//    public IWell96 getWell96() {
//        return well96;
//    }
//
//    public void setWell96(IWell96 well96) {
//        this.well96 = well96;
//    }

    private void selectStatus(Well96Status status) {
        if(!well.getStatus().equals(status)){
            if(status.equals(Well96Status.FILLED)) {
                //TODO get Spot from lookup
                //spot.setWell(well96)
                //well96.setSpot(spot);
            }
            if(status.equals(Well96Status.EMPTY) && well.getStatus().equals(Well96Status.FILLED)) {
                ISpot spot = well.getSpot();
                well.setSpot(null);
                spot.setWell(null);
            }
            well.setStatus(status);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Color originalColor = g2.getColor();
        g2.setColor(well.getStatus().getColor());
        int width = getWidth() - (getInsets().left + getInsets().right);
        int height = getHeight() - (getInsets().top + getInsets().bottom);
        g2.fillOval(getInsets().left, getInsets().top, width, height);
        g2.setColor(originalColor);
    }
}
