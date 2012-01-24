package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IGelOpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
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
public class Well384Button extends JButton implements MouseInputListener {

    private IWell384 well = null;
    private JPopupMenu menu = null;
    private final Plate384Panel panel;
    private final Well384Button button = this;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (menu != null) {
                menu.setVisible(false);
            }
            menu = new Well384StatusSelectMenu(well.getStatus());
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

    private class Well384Action extends AbstractAction {

        private final Well384Button button;

        public Well384Action(Well384Button button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.setActiveWellButton(button);
        }
    }

    private class Well384StatusSelectMenu extends JPopupMenu {

        public Well384StatusSelectMenu(Well384Status status) {
            if (status != Well384Status.EMPTY) {
                add(new AbstractAction(
                        "Open Gel") {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        IGelOpenCookie gelOpenCookie = Lookup.getDefault().
                                lookup(IGelOpenCookie.class);
                        panel.getInstanceContent().add(well.getWell96().getSpot().getGel());
                        gelOpenCookie.open();
                        panel.getInstanceContent().remove(
                                well.getWell96().getSpot().getGel());
                    }
                });
                add(new JSeparator(JSeparator.HORIZONTAL));
            }
            ButtonGroup group = new ButtonGroup();
            for (Well384Status s : Well384Status.values()) {
                JRadioButtonMenuItem jrbmi = new JRadioButtonMenuItem(new Well384StatusSelectRadioButtonAction(
                        s));
                jrbmi.setText(s.toString());
                jrbmi.setEnabled(StateMachine.isTransitionAllowed(status, s));
                if (s.equals(Well384Status.FILLED) && (panel.getWell96() == null)) {
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
                    String text = well.toString();
                    if (well.getWell96() != null) {
                        text = text + "\nAssigned to well: " + well.getWell96() + " on plate " + well.
                                getWell96().getParent().getName();
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

    private class Well384StatusSelectRadioButtonAction extends AbstractAction {

        private Well384Status status;

        public Well384StatusSelectRadioButtonAction(Well384Status status) {
            this.status = status;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selectStatus(status);
            menu.setVisible(false);
            boolean autoAssignState = panel.isAutoAssignWell96();
            panel.setAutoAssign96Wells(!autoAssignState);
            panel.setAutoAssign96Wells(autoAssignState);
        }
    }

    public Well384Button(IWell384 well, Plate384Panel panel) {
        this.well = well;
        this.panel = panel;
        setAction(new Well384Action(this));
        setName(well.getWellPosition());
//        setBorderPainted(false);
        setContentAreaFilled(false);
        setMinimumSize(new Dimension(5, 5));
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void selectStatus(Well384Status nextStatus) {
        //nextStatus -> EMPTY, FILLED, IDENTIFIED, MULTIPLE_IDENTIFICATIONS, UNCERTAIN, UNIDENTIFIED, ERROR
        Well384Status currentStatus = well.getStatus();
        if (!well.getStatus().equals(nextStatus)) {
            IWell96 well96 = null;
            System.out.println("Next status: " + nextStatus);
            if (StateMachine.isTransitionAllowed(currentStatus, nextStatus)) {
                switch (nextStatus) {
                    case EMPTY:
                        well96 = this.well.getWell96();
                        if (well96 != null /*&& well96.get384Wells().contains(well)*/) {
                            well96.get384Wells().remove(well);
                            //reset the well96 to FILLED status if it's last well384 was just removed
                            if (well96.get384Wells().isEmpty()) {
                                well96.setStatus(Well96Status.FILLED);
                            }
                        }
                        well.setWell96(null);
                        break;
                    case FILLED:
                        well96 = panel.getWell96();
                        //only processed wells that are filled (or processed), ignore empty or errand well96s
                        if (well96.getStatus() == Well96Status.FILLED || well96.
                                getStatus() == Well96Status.PROCESSED) {
                            well96.add384Well(well);
                            well96.setStatus(Well96Status.PROCESSED);
                            well.setWell96(well96);
                        }
                        break;
//                    case IDENTIFIED:
//                        well.setIdentification("<MOCK IDENTIFICATION>");
//                        break;
//                    case MULTIPLE_IDENTIFICATIONS:
//                        well.setIdentification("<MOCK IDENTIFICATION>,<MOCK IDENTIFICATION>,<MOCK IDENTIFICATION>");
//                        break;
//                    case UNCERTAIN:
//                        well.setIdentification("<MAYBE MOCK IDENTIFICATION>,<MAYBE MOCK IDENTIFICATION>,<MAYBE MOCK IDENTIFICATION>");
//                        break;
//                    case UNIDENTIFIED:
//                        well.setIdentification("MOCK UNIDENTIFIED");
//                        break;
                    case ERROR:
                        break;
                }
                well.setStatus(nextStatus);
                panel.setButtonForWell96Active(well96);
                repaint();
            } else {
                Exceptions.printStackTrace(
                        new IllegalStateException(
                        "Illegal transition attempted: tried to transit from " + currentStatus + " to " + nextStatus));
            }
        }
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
                if(isSelected()) {
                    g2.setColor(Color.BLACK);
                }
                g2.drawLine(0, 0, getWidth(), getHeight());
                g2.drawLine(getWidth(), 0, 0, getHeight());
                break;
            case FILLED:
                break;
            case IDENTIFIED:
                break;
            case UNIDENTIFIED:
        }
        g2.setClip(originalClip);
        g2.setColor(originalColor);
    }

    public IWell384 getWell() {
        return well;
    }
}
