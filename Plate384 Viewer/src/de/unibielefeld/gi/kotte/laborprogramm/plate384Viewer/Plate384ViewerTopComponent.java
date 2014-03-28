package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer;

import de.unibielefeld.gi.kotte.laborprogramm.picking.api.Picker;
import de.unibielefeld.gi.kotte.laborprogramm.picking.api.PickingRegistry;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Lookup.Result;
import org.openide.util.*;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component for the Plate384 Viewer.
 *
 * @author Konstantin Otte
 */
@ConvertAsProperties(dtd = "-//de.unibielefeld.gi.kotte.laborprogramm.plateViewer//Plate384Viewer//EN",
autostore = false)
public final class Plate384ViewerTopComponent extends TopComponent implements LookupListener, Picker {

    private static Plate384ViewerTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "Plate384ViewerTopComponentTopComponent";
    private Result<IPlate384> result = null;
    private Result<IWell96> well96Result = null;
    private Result<ISpot> spotResult = null;
    private Result<IPlate96> plate96Result = null;
    private IWell96 well96 = null;
    private IPlate96 autoPickedPlate = null;
    private InstanceContent instanceContent = new InstanceContent();

    public Plate384ViewerTopComponent() {
        associateLookup(new AbstractLookup(instanceContent));
        result = Utilities.actionsGlobalContext().lookupResult(IPlate384.class);
        well96Result = Utilities.actionsGlobalContext().lookupResult(IWell96.class);
        plate96Result = Utilities.actionsGlobalContext().lookupResult(IPlate96.class);
        spotResult = Utilities.actionsGlobalContext().lookupResult(ISpot.class);
        IProteomicProject project = Utilities.actionsGlobalContext().lookup(IProteomicProject.class);
        if(project==null) {
            throw new IllegalArgumentException("Instance of IProteomicProject must not be null!");
        }
        instanceContent.add(project);
        initComponents();
        setName(NbBundle.getMessage(Plate384ViewerTopComponent.class, "CTL_Plate384ViewerTopComponent"));
        setToolTipText(NbBundle.getMessage(Plate384ViewerTopComponent.class, "HINT_Plate384ViewerTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

        IPlate384 plate = Utilities.actionsGlobalContext().lookup(IPlate384.class);
        if (plate != null) {
            initPlateComponent(plate);
        }
        WindowManager mgr = WindowManager.getDefault();
        Mode mode = mgr.findMode("output");
        mode.dockInto(this);
        resultChanged(new LookupEvent(result));
        resultChanged(new LookupEvent(spotResult));
        resultChanged(new LookupEvent(plate96Result));
        resultChanged(new LookupEvent(well96Result));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        autoAssignSpotsButton = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        autoPickPlateButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton1 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        activeSpotLabel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(autoAssignSpotsButton, org.openide.util.NbBundle.getMessage(Plate384ViewerTopComponent.class, "Plate384ViewerTopComponent.autoAssignSpotsButton.text")); // NOI18N
        autoAssignSpotsButton.setToolTipText(org.openide.util.NbBundle.getMessage(Plate384ViewerTopComponent.class, "Plate384ViewerTopComponent.autoAssignSpotsButton.toolTipText")); // NOI18N
        autoAssignSpotsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoAssignSpotsButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(autoAssignSpotsButton);
        jToolBar1.add(jSeparator1);

        org.openide.awt.Mnemonics.setLocalizedText(autoPickPlateButton, org.openide.util.NbBundle.getMessage(Plate384ViewerTopComponent.class, "Plate384ViewerTopComponent.autoPickPlateButton.text")); // NOI18N
        autoPickPlateButton.setFocusable(false);
        autoPickPlateButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        autoPickPlateButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        autoPickPlateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoPickPlateButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(autoPickPlateButton);
        jToolBar1.add(jSeparator2);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(Plate384ViewerTopComponent.class, "Plate384ViewerTopComponent.jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);
        jToolBar1.add(jSeparator3);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(Plate384ViewerTopComponent.class, "Plate384ViewerTopComponent.jLabel1.text")); // NOI18N
        jToolBar1.add(jLabel1);

        org.openide.awt.Mnemonics.setLocalizedText(activeSpotLabel, org.openide.util.NbBundle.getMessage(Plate384ViewerTopComponent.class, "Plate384ViewerTopComponent.activeSpotLabel.text")); // NOI18N
        jToolBar1.add(activeSpotLabel);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void autoAssignSpotsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoAssignSpotsButtonActionPerformed
        if (autoAssignSpotsButton.isSelected()) {
            PickingRegistry.register(this);
        } else {
            PickingRegistry.unregister(this);
        }
        platePanel.setAutoAssign96Wells(autoAssignSpotsButton.isSelected());
    }//GEN-LAST:event_autoAssignSpotsButtonActionPerformed

    private void autoPickPlateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoPickPlateButtonActionPerformed
        platePanel.autoPickPlate(autoPickedPlate);
    }//GEN-LAST:event_autoPickPlateButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        platePanel.clear();
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activeSpotLabel;
    private javax.swing.JToggleButton autoAssignSpotsButton;
    private javax.swing.JButton autoPickPlateButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    private Plate384Panel platePanel;

    private void initPlateComponent(IPlate384 plate) {
        if (platePanel == null) {
//            remove(platePanel);
//        }
            instanceContent.add(plate);
            platePanel = new Plate384Panel(plate, instanceContent, getLookup());
            setDisplayName("384 Well Plate: " + plate.getName());
            add(platePanel, BorderLayout.CENTER);
//            CentralLookup.getDefault().remove(plate);
        }
    }

//    /**
//     * Gets default instance. Do not use directly: reserved for *.settings files only,
//     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
//     * To obtain the singleton instance, use {@link #findInstance}.
//     */
//    public static synchronized Plate96ViewerTopComponent getDefault() {
//        if (instance == null) {
//            instance = new Plate96ViewerTopComponent();
//        }
//        return instance;
//    }
//
//    /**
//     * Obtain the Plate96ViewerTopComponent instance. Never call {@link #getDefault} directly!
//     */
//    public static synchronized Plate96ViewerTopComponent findInstance() {
//        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
//        if (win == null) {
//            Logger.getLogger(Plate96ViewerTopComponent.class.getName()).warning(
//                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
//            return getDefault();
//        }
//        if (win instanceof Plate96ViewerTopComponent) {
//            return (Plate96ViewerTopComponent) win;
//        }
//        Logger.getLogger(Plate96ViewerTopComponent.class.getName()).warning(
//                "There seem to be multiple components with the '" + PREFERRED_ID
//                + "' ID. That is a potential source of errors and unexpected behavior.");
//        return getDefault();
//    }
    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    public void componentOpened() {
        if (result != null) {
            result.addLookupListener(this);
        }
        if (well96Result != null) {
            well96Result.addLookupListener(this);
        }
        if (plate96Result != null) {
            plate96Result.addLookupListener(this);
        }
        if (spotResult != null) {
            spotResult.addLookupListener(this);
        }
    }

    @Override
    public void componentClosed() {
        if (result != null) {
            result.removeLookupListener(this);
        }
        if (well96Result != null) {
            well96Result.removeLookupListener(this);
        }
        if (plate96Result != null) {
            plate96Result.removeLookupListener(this);
        }
        if (spotResult != null) {
            spotResult.removeLookupListener(this);
        }
        IPlate384 plate = getLookup().lookup(IPlate384.class);
        if (plate != null) {
            Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().closeTopComponentsFor(plate);
//            CentralLookup.getDefault().remove(plate);
        }
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        //set plate
        Iterator<? extends IPlate384> instances = result.allInstances().iterator();
        if (instances.hasNext()) {
            initPlateComponent(instances.next());
        }

        Iterator<? extends IWell96> well96Instances = well96Result.allInstances().iterator();
        if (well96Instances.hasNext()) {
            IWell96 well96instance = well96Instances.next();
            if (well96instance != this.well96) {
                activeSpotLabel.setText(well96instance.getWellPosition());
                this.well96 = well96instance;
                platePanel.setWell96(this.well96);
            }
        }

        Iterator<? extends IPlate96> plate96Instances = plate96Result.allInstances().iterator();
        if (plate96Instances.hasNext()) {
            autoPickedPlate = plate96Instances.next();
        }

        Iterator<? extends ISpot> spotInstances = spotResult.allInstances().iterator();
        while (spotInstances.hasNext()) {
            ISpot spot = spotInstances.next();
            IWell96 well96 = spot.getWell();
            if (well96 != null) {
                List<IWell384> wells = well96.get384Wells();
                if (wells != null) {
                    for (IWell384 well384 : wells) {
                        if(well384.getParent()==getLookup().lookup(IPlate384.class)) {
                            platePanel.setButtonForWell96Active(well96);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void resetPicking() {
        autoAssignSpotsButton.setSelected(false);
        platePanel.setAutoAssign96Wells(false);
    }
}
