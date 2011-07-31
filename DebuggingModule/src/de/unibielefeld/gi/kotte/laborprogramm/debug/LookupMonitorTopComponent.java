/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.debug;

import de.unibielefeld.gi.kotte.laborprogramm.centralLookup.CentralLookup;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
dtd = "-//de.unibielefeld.gi.kotte.laborprogramm.debug//LookupMonitor//EN",
autostore = false)
public final class LookupMonitorTopComponent extends TopComponent implements
        LookupListener {

    private static LookupMonitorTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "LookupMonitorTopComponent";
    private Result<Object> r = null;
    private Result<Object> centralLookupResult = null;
    private DefaultListModel dlm = new DefaultListModel();
    private DefaultListModel dlm2 = new DefaultListModel();

    public LookupMonitorTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(LookupMonitorTopComponent.class,
                "CTL_LookupMonitorTopComponent"));
        setToolTipText(NbBundle.getMessage(LookupMonitorTopComponent.class,
                "HINT_LookupMonitorTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(LookupMonitorTopComponent.class, "LookupMonitorTopComponent.jLabel1.text")); // NOI18N

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(LookupMonitorTopComponent.class, "LookupMonitorTopComponent.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized LookupMonitorTopComponent getDefault() {
        if (instance == null) {
            instance = new LookupMonitorTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the LookupMonitorTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized LookupMonitorTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(
                PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(LookupMonitorTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof LookupMonitorTopComponent) {
            return (LookupMonitorTopComponent) win;
        }
        Logger.getLogger(LookupMonitorTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        r = Utilities.actionsGlobalContext().lookupResult(Object.class);
        r.addLookupListener(this);
        centralLookupResult = CentralLookup.getDefault().lookupResult(
                Object.class);
        centralLookupResult.addLookupListener(this);
    }

    @Override
    public void componentClosed() {
        if(r!=null) {
            r.removeLookupListener(this);
        }
        if(centralLookupResult!=null) {
            centralLookupResult.removeLookupListener(this);
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
        if (r != null) {
            dlm = new DefaultListModel();
            for (Object obj : r.allInstances()) {
                dlm.addElement(obj);
            }
            jList1.setModel(dlm);
        }
        if (centralLookupResult != null) {
            dlm2 = new DefaultListModel();
            for (Object obj : centralLookupResult.allInstances()) {
                dlm2.addElement(obj);
            }
            jList2.setModel(dlm2);
        }
    }
}
