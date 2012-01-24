package de.unibielefeld.gi.kotte.laborprogramm.plate96Viewer;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IGelOpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 * Button to be placed on a Plate96Panel. Represents a IWell96 and offers actions to set the wells status.
 *
 * @author hoffmann, kotte
 */
public class Well96Button extends JButton implements MouseInputListener {

    private IWell96 well = null;
    private JPopupMenu menu = null;
    private final Plate96Panel panel;
    private final Well96Button button = this;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (menu != null) {
                menu.setVisible(false);
            }
            menu = new Well96StatusSelectMenu(well.getStatus());
            menu.setInvoker(panel);
            if (getMousePosition() != null) {
                Point p = new Point(getMousePosition());
                SwingUtilities.convertPointToScreen(p, button);
                menu.setLocation(p);
                menu.setVisible(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private class Well96Action extends AbstractAction {

        private final Well96Button button;

        public Well96Action(Well96Button button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.setActiveWellButton(button);
        }
    }

    private class Well96StatusSelectMenu extends JPopupMenu {

        public Well96StatusSelectMenu(Well96Status status) {
            if (status != Well96Status.EMPTY) {
                add(new AbstractAction(
                        "Open Gel") {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        IGelOpenCookie gelOpenCookie = Lookup.getDefault().
                                lookup(IGelOpenCookie.class);
                        panel.getInstanceContent().add(well.getSpot().getGel());
                        gelOpenCookie.open();
                        panel.getInstanceContent().remove(
                                well.getSpot().getGel());
                    }
                });
                add(new JSeparator(JSeparator.HORIZONTAL));
            }

            ButtonGroup group = new ButtonGroup();
            for (Well96Status s : Well96Status.values()) {
                JRadioButtonMenuItem jrbmi = new JRadioButtonMenuItem(new Well96StatusSelectRadioButtonAction(
                        s));
                jrbmi.setText(s.toString());
                jrbmi.setEnabled(StateMachine.isTransitionAllowed(status, s));
                if (s.equals(Well96Status.FILLED) && (panel.getSpot() == null)) {
                    jrbmi.setEnabled(false);
                }
                jrbmi.setSelected(s == status);
                group.add(jrbmi);
                add(jrbmi);
            }
            add(new JSeparator(JSeparator.HORIZONTAL));
            add(new AbstractAction("Details") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = well.toString() + "\n";

                    if (well.getSpot() != null) {
                        text += well.getSpot().toString();
                    }
                    JTextArea jl = new JTextArea(text);
                    NotifyDescriptor nd = new NotifyDescriptor(
                            jl, // instance of your panel
                            "Details for Well", // title of the dialog
                            NotifyDescriptor.OK_CANCEL_OPTION, // it is Yes/No dialog ...
                            NotifyDescriptor.PLAIN_MESSAGE, // ... of a question type => a question mark icon
                            null, // we have specified YES_NO_OPTION => can be null, options specified by L&F,
                            // otherwise specify options as:
                            //     new Object[] { NotifyDescriptor.YES_OPTION, ... etc. },
                            NotifyDescriptor.OK_OPTION // default option is "Yes"
                            );
                    // let's display the dialog now...
                    if (DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.OK_OPTION) {
                    }
                }
            });
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
            boolean autoAssignState = panel.isAutoAssignSpots();
            panel.setAutoAssignSpots(!autoAssignState);
            panel.setAutoAssignSpots(autoAssignState);
        }
    }

    public Well96Button(IWell96 well, Plate96Panel panel) {
        this.well = well;
        this.panel = panel;
        setAction(new Well96Action(this));
        setName(well.getWellPosition());
//        setBorderPainted(false);
        setContentAreaFilled(false);
        setMinimumSize(new Dimension(5, 5));
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void selectStatus(Well96Status nextStatus) {
        //FIXME 
//        if (spotInstance.getGel().isVirtual()) {
//            NotifyDescriptor nd = new NotifyDescriptor.Message("Can not pick spots from virtual gel " + spotInstance.getGel().getName(), NotifyDescriptor.WARNING_MESSAGE);
//            DialogDisplayer.getDefault().notify(nd);
//        } else {
        //nextStatus -> EMPTY, FILLED, PROCESSED, ERROR
        Well96Status currentStatus = well.getStatus();
        if (!well.getStatus().equals(nextStatus)) {
            ISpot spot = null;
            if (StateMachine.isTransitionAllowed(currentStatus, nextStatus)) {
                switch (nextStatus) {
                    case EMPTY:
                        spot = this.well.getSpot();
                        if (spot != null) {
                            this.well.setSpot(null);
                            spot.setStatus(SpotStatus.UNPICKED);
                            spot.setWell(null);
                        }
                        well.setStatus(nextStatus);
                        break;
                    case FILLED:
                        spot = panel.getSpot(); // get spot from lookup
                        if (spot.getGel().isVirtual()) {
                            NotifyDescriptor nd = new NotifyDescriptor.Message(
                                    "Can not add a spot from a virtual gel!",
                                    NotifyDescriptor.INFORMATION_MESSAGE);
                            DialogDisplayer dd = DialogDisplayer.getDefault();
                            dd.notify(nd);
                        } else {
//                        if (spot.getStatus() == SpotStatus.PICKED) {
                            if (reassignWell(spot)) {
                                spot.setWell(this.well);
                                spot.setStatus(SpotStatus.PICKED);
                                this.well.setSpot(spot);
                                well.setStatus(nextStatus);
                            }
                        }
                        break;
                    case PROCESSED:
                        if (this.well.getSpot() != null) {
                            well.setStatus(nextStatus);
                        }
                        break;
                    case ERROR:
                        well.setStatus(nextStatus);
                        break;
                }
                panel.setButtonForSpotActive(spot);
                repaint();
            } else {
                Exceptions.printStackTrace(
                        new IllegalStateException(
                        "Illegal transition attempted: tried to transit from " + currentStatus + " to " + nextStatus));
            }
        }
//        }
    }

    /**
     * Checks if it is okay to reassign the button's IWell96 to a new spot
     * and if so removes the old spot-well-connection.
     *
     * @param lookupSpot the current spot in the Lookup
     * @return true if the well is to be reassigned, false elsewise
     */
    private boolean reassignWell(ISpot lookupSpot) {
//        ISpot wellSpot = well.getSpot();
        IWell96 spotWell = lookupSpot.getWell();
        if (spotWell != null) {
            if (spotWell.getStatus() == Well96Status.PROCESSED) {
                NotifyDescriptor nd = new NotifyDescriptor(
                        "Spot is already assigned to well " + well.
                        getWellPosition() + " on plate " + well.getParent().
                        getName() + " and processed.", // instance of your panel
                        "Spot already processed", // title of the dialog
                        NotifyDescriptor.DEFAULT_OPTION, // it is Yes/No dialog ...
                        NotifyDescriptor.INFORMATION_MESSAGE, // ... of a question type => a question mark icon
                        null, // we have specified YES_NO_OPTION => can be null, options specified by L&F,
                        // otherwise specify options as:
                        //     new Object[] { NotifyDescriptor.YES_OPTION, ... etc. },
                        NotifyDescriptor.OK_OPTION // default option is "Yes"
                        );
                // let's display the dialog now...
                DialogDisplayer.getDefault().notify(nd);
                return false;
            }
            if (spotWell != well) {
                NotifyDescriptor nd = new NotifyDescriptor(
                        "Spot is already assigned to well " + well.
                        getWellPosition() + " on plate " + well.getParent().
                        getName() + ". Reassign?", // instance of your panel
                        "Reassign spot?", // title of the dialog
                        NotifyDescriptor.YES_NO_OPTION, // it is Yes/No dialog ...
                        NotifyDescriptor.PLAIN_MESSAGE, // ... of a question type => a question mark icon
                        null, // we have specified YES_NO_OPTION => can be null, options specified by L&F,
                        // otherwise specify options as:
                        //     new Object[] { NotifyDescriptor.YES_OPTION, ... etc. },
                        NotifyDescriptor.YES_OPTION // default option is "Yes"
                        );
                // let's display the dialog now...
                if (DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.NO_OPTION) {
                    return false;
                } else {
                    spotWell.setStatus(Well96Status.EMPTY);
                    spotWell.setSpot(null);
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Color originalColor = g2.getColor();
        Shape originalClip = g2.getClip();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(well.getStatus().getColor());
//        int width = getWidth() - (getInsets().left + getInsets().right);
//        int height = getHeight() - (getInsets().top + getInsets().bottom);
//        if (isSelected()) {
//            g2.fillOval(2, 2, getWidth() - 4, getHeight() - 4);//getInsets().left, getInsets().top, width, height);
//        } else {
//            g2.drawOval(2, 2, getWidth() - 4, getHeight() - 4);//getInsets().left, getInsets().top, width, height);
//        }
        Ellipse2D.Double e2d = new Ellipse2D.Double(2, 2, getWidth() - 4,
                getHeight() - 4);
        if (isSelected()) {
            g2.fill(e2d);
            g2.setColor(Color.BLACK);
            g2.draw(e2d);
            //g2.fillOval(2, 2, getWidth() - 4, getHeight() - 4);//getInsets().left, getInsets().top, width, height);
        } else {
            g2.draw(e2d);
            //g2.drawOval(2, 2, getWidth() - 4, getHeight() - 4);//getInsets().left, getInsets().top, width, height);
        }
        switch (well.getStatus()) {
            case EMPTY:
//        int width = getWidth() - (getInsets().left + getInsets().right);
//        int height = getHeight() - (getInsets().top + getInsets().bottom);

                break;
            case ERROR:
                g2.setClip(e2d);
                if (isSelected()) {
                    g2.setColor(Color.BLACK);
                }
                g2.drawLine(0, 0, getWidth(), getHeight());
                g2.drawLine(getWidth(), 0, 0, getHeight());
                break;
            case FILLED:
                break;
            case PROCESSED:
                break;
        }
        g2.setClip(originalClip);
        g2.setColor(originalColor);
    }

    public IWell96 getWell() {
        return well;
    }
}
