package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
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
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;

/**
 * Button to be placed on a Plate96Panel. Represents a IWell96 and offers actions to set the wells status.
 *
 * @author hoffmann, kotte
 */
public class Well384Button extends JButton {

    private IWell384 well = null;
    private JPopupMenu menu = null;
    private final Plate384Panel panel;
    private final Well384Button button = this;

    private class Well384Action extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
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

    private class Well96StatusSelectMenu extends JPopupMenu {

        public Well96StatusSelectMenu(Well384Status status) {
            ButtonGroup group = new ButtonGroup();
            for (Well384Status s : Well384Status.values()) {
                JRadioButtonMenuItem jrbmi = new JRadioButtonMenuItem(new Well384StatusSelectRadioButtonAction(s));
                jrbmi.setText(s.toString());
                jrbmi.setEnabled(StateMachine.isTransitionAllowed(status, s));
                if (s.equals(Well384Status.FILLED) && (panel.getWell96() == null)) {
                    jrbmi.setEnabled(false);
                }
//                } else if (s == Well384Status.PROCESSED) {
//                    if ((panel.getSpot() == null) && (well.getSpot() == null)) {
//                        jrbmi.setEnabled(false);
//                    }
//                }
                jrbmi.setSelected(s == status);
                group.add(jrbmi);
                add(jrbmi);
            }
            add(new JSeparator(JSeparator.HORIZONTAL));
            add(new AbstractAction("Details") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = well.toString() + "\n";

//                    if (well.getSpot() != null) {
//                        text += well.getSpot().toString();
//                    }
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
                        // user clicked yes, do something here, for example:
                        //     System.out.println(myPanel.getNameFieldValue());
//                        IPlate96 plate96 = Lookup.getDefault().lookup(IPlate96Factory.class).createPlate96();
//                        this.proj.add96Plate(plate96);
//                        plate96.setParent(this.proj);
//                        plate96.setName(dialog.getNameText());
//                        System.out.println("Firing PropertyChangeEvent: PLATE96_CREATED");
//                        listener.propertyChange(new PropertyChangeEvent(this, "PLATE96_CREATED", null, plate96));
                    }
                }
            });
            //TODO see GelViewerTopComponent
//            if(well.getSpot()!=null) {
//                add(new AbstractAction());
//            }
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
            boolean autoAssignState = panel.isAutoAssignSpots();
            panel.setAutoAssignSpots(!autoAssignState);
            panel.setAutoAssignSpots(autoAssignState);
        }
    }

    public Well384Button(IWell384 well, Plate384Panel panel) {
        this.well = well;
        this.panel = panel;
        setAction(new Well384Action());
        setName(well.getWellPosition());
//        setBorderPainted(false);
        setContentAreaFilled(false);
        setMinimumSize(new Dimension(5, 5));
    }

    public void selectStatus(Well384Status nextStatus) {
        //nextStatus -> EMPTY, FILLED, PROCESSED, ERROR
        Well384Status currentStatus = well.getStatus();
        if (!well.getStatus().equals(nextStatus)) {
            IWell96 well96 = null;
            if (StateMachine.isTransitionAllowed(currentStatus, nextStatus)) {
                switch (nextStatus) {
                    case ERROR:
                        well.setStatus(nextStatus);
                        break;
                }
                panel.setButtonForWell96Active(well96);
                repaint();
            } else {
                Exceptions.printStackTrace(new IllegalStateException("Illegal transition attempted: tried to transit from " + currentStatus + " to " + nextStatus));
            }
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
        if (isSelected()) {
            g2.fillOval(2, 2, getWidth() - 4, getHeight() - 4);//getInsets().left, getInsets().top, width, height);
        } else {
            g2.drawOval(2, 2, getWidth() - 4, getHeight() - 4);//getInsets().left, getInsets().top, width, height);
        }
        g2.setColor(originalColor);
    }

    public IWell384 getWell() {
        return well;
    }
}