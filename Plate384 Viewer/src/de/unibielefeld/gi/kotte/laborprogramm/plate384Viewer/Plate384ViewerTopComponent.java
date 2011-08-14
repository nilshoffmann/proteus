package de.unibielefeld.gi.kotte.laborprogramm.plate384Viewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import java.awt.BorderLayout;
import java.util.Iterator;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

/**
 * Top component for the Plate384 Viewer.
 *
 * @author kotte
 */
@ConvertAsProperties(dtd = "-//de.unibielefeld.gi.kotte.laborprogramm.plateViewer//Plate96Viewer//EN",
autostore = false)
public final class Plate384ViewerTopComponent extends TopComponent implements LookupListener {

    private static Plate384ViewerTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "Plate384ViewerTopComponentTopComponent";
    private Result<IPlate384> result = null;
    private Result<IWell96> well96Result = null;
    private IWell96 well96 = null;
    private InstanceContent instanceContent = new InstanceContent();

    public Plate384ViewerTopComponent() {
        associateLookup(new AbstractLookup(instanceContent));
        result = Utilities.actionsGlobalContext().lookupResult(IPlate384.class);
        well96Result = Utilities.actionsGlobalContext().lookupResult(IWell96.class);
        initComponents();
        setName(NbBundle.getMessage(Plate384ViewerTopComponent.class, "CTL_Plate384ViewerTopComponent"));
        setToolTipText(NbBundle.getMessage(Plate384ViewerTopComponent.class, "HINT_Plate384ViewerTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

        IPlate384 plate = Utilities.actionsGlobalContext().lookup(IPlate384.class);
        if (plate != null) {
            initPlateComponent(plate);
        }
        WindowManager mgr = WindowManager.getDefault();
        Mode mode = mgr.findMode("explorer");
        mode.dockInto(this); 
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        autoAssignSpots = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        activeSpotLabel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(autoAssignSpots, org.openide.util.NbBundle.getMessage(Plate384ViewerTopComponent.class, "Plate384ViewerTopComponent.autoAssignSpots.text")); // NOI18N
        autoAssignSpots.setToolTipText(org.openide.util.NbBundle.getMessage(Plate384ViewerTopComponent.class, "Plate384ViewerTopComponent.autoAssignSpots.toolTipText")); // NOI18N
        autoAssignSpots.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoAssignSpotsActionPerformed(evt);
            }
        });
        jToolBar1.add(autoAssignSpots);
        jToolBar1.add(jSeparator1);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(Plate384ViewerTopComponent.class, "Plate384ViewerTopComponent.jLabel1.text")); // NOI18N
        jToolBar1.add(jLabel1);

        org.openide.awt.Mnemonics.setLocalizedText(activeSpotLabel, org.openide.util.NbBundle.getMessage(Plate384ViewerTopComponent.class, "Plate384ViewerTopComponent.activeSpotLabel.text")); // NOI18N
        jToolBar1.add(activeSpotLabel);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void autoAssignSpotsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoAssignSpotsActionPerformed
        platePanel.setAutoAssignSpots(autoAssignSpots.isSelected());
    }//GEN-LAST:event_autoAssignSpotsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activeSpotLabel;
    private javax.swing.JToggleButton autoAssignSpots;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    private Plate384Panel platePanel;

    private void initPlateComponent(IPlate384 plate) {
        if (platePanel == null) {
//            remove(platePanel);
//        }
            instanceContent.add(plate);
            platePanel = new Plate384Panel(plate);
            setDisplayName("96 Well Plate: " + plate.getName());
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
    }

    @Override
    public void componentClosed() {
        if (result != null) {
            result.removeLookupListener(this);
        }
        if (well96Result != null) {
            well96Result.removeLookupListener(this);
        }
        IPlate384 plate = getLookup().lookup(IPlate384.class);
        if (plate != null) {
            Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().closeTopComponent(plate);
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
            if(well96instance!=this.well96) {
                activeSpotLabel.setText(well96instance.toString());
                this.well96 = well96instance;
                platePanel.setWell96(this.well96);
            }
        }
    }
}