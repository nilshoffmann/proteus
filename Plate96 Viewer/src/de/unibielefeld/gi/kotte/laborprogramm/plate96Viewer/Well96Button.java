package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

/**
 * Button to be placed on a Plate96Panel. Represents a IWell96 and offers actions to set the wells status.
 *
 * @author hoffmann, kotte
 */
public class Well96Button extends JButton {

    private IWell96 well = null;
    private JPopupMenu menu = null;
    private final Plate96Panel panel;
    private final Well96Button button = this;

    private class Well96Action extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(menu!=null) {
                menu.setVisible(false);
            }
            menu = new Well96StatusSelectMenu(well.getStatus());
            menu.setInvoker(panel);
            Point p = new Point(getMousePosition());
            SwingUtilities.convertPointToScreen(p, button);
            menu.setLocation(p);
            menu.setVisible(true);
        }
    }

    private class Well96StatusSelectMenu extends JPopupMenu {

        public Well96StatusSelectMenu(Well96Status status) {
            ButtonGroup group = new ButtonGroup();
            for (Well96Status s : Well96Status.values()) {
                JRadioButtonMenuItem jrbmi = new JRadioButtonMenuItem(new Well96StatusSelectRadioButtonAction(s));
                jrbmi.setText(s.toString());
                if(s.equals(Well96Status.FILLED) && (panel.getSpot() == null)) {
                    jrbmi.setEnabled(false);
                }
                jrbmi.setSelected(s == status);
                group.add(jrbmi);
                add(jrbmi);
            }
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
            menu.setVisible(false);
        }
    }

    public Well96Button(IWell96 well, Plate96Panel panel) {
        this.well = well;
        this.panel = panel;
        setAction(new Well96Action());
        setName(well.getWellPosition());
//        setBorderPainted(false);
        setContentAreaFilled(false);
        setMinimumSize(new Dimension(5,5));
    }

    private void selectStatus(Well96Status status) {
        if(!well.getStatus().equals(status)){
            if(status.equals(Well96Status.FILLED)) {
                ISpot spot = panel.getSpot(); // get spot from lookup
                spot.setWell(this.well);
                this.well.setSpot(spot);
            }
            if(status.equals(Well96Status.EMPTY) && well.getStatus().equals(Well96Status.FILLED)) {
                ISpot spot = this.well.getSpot();
                this.well.setSpot(null);
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
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color originalColor = g2.getColor();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(well.getStatus().getColor());
//        int width = getWidth() - (getInsets().left + getInsets().right);
//        int height = getHeight() - (getInsets().top + getInsets().bottom);
        g2.fillOval(2,2,getWidth()-4,getHeight()-4);//getInsets().left, getInsets().top, width, height);
        g2.setColor(originalColor);
    }
}
