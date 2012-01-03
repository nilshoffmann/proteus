/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.wellIdentificationViewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import java.awt.BorderLayout;
import java.awt.Component;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.explorer.propertysheet.PropertySheet;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//de.unibielefeld.gi.kotte.laborprogramm.wellIdentificationViewer//WellIdentificationViewer//EN",
autostore = false)
public final class WellIdentificationViewerTopComponent extends TopComponent
        implements LookupListener {

    private static WellIdentificationViewerTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "WellIdentificationViewerTopComponent";
    private Result<IWell384> result;
    private DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
    private List<WellIdentificationNode> nodeList = new ArrayList<WellIdentificationNode>();
    private IIdentificationComboBoxRenderer renderer = new IIdentificationComboBoxRenderer(IIdentification.class);
    private IWellIdentification wellIdentification = null;

    private abstract class TypedComboBoxRenderer<T> extends JLabel
            implements ListCellRenderer {

        private Class<? extends T> typeClass;

        public TypedComboBoxRenderer(Class<? extends T> typeClass) {
            setOpaque(true);
            this.typeClass = typeClass;
        }

        @Override
        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            //Get the selected index. (The index param isn't
            //always valid, so just use the value.)
            int selectedIndex = index;

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            if (value != null) {
                try {
                    T t = typeClass.cast(value);

                    String name = getStringForComponent(t);
                    if (name != null) {
                        setText(name);
                    } else {
                        setText("N.N.");
                    }
                    ImageIcon img = getImageIconForComponent(t);
                    if (img != null) {
                        setIcon(img);
                    }
                } catch (ClassCastException cce) {
                }
            }else{
                setText("");
                setIcon(null);
            }
            return this;
        }

        public abstract String getStringForComponent(T t);

        public abstract ImageIcon getImageIconForComponent(T t);
    }

    private class IIdentificationComboBoxRenderer extends TypedComboBoxRenderer<IIdentification> {

        public IIdentificationComboBoxRenderer(Class<? extends IIdentification> typeClass) {
            super(typeClass);
        }

        @Override
        public String getStringForComponent(IIdentification t) {
            return t.getName();
        }

        @Override
        public ImageIcon getImageIconForComponent(IIdentification t) {
            return null;
        }
    }

    public WellIdentificationViewerTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(WellIdentificationViewerTopComponent.class,
                "CTL_WellIdentificationViewerTopComponent"));
        setToolTipText(NbBundle.getMessage(
                WellIdentificationViewerTopComponent.class,
                "HINT_WellIdentificationViewerTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        result = Utilities.actionsGlobalContext().lookupResult(IWell384.class);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        clearButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        propertySheet1 = new org.openide.explorer.propertysheet.PropertySheet();

        setLayout(new java.awt.BorderLayout());

        jComboBox1.setModel(dcbm);
        jComboBox1.setRenderer(renderer);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(clearButton, org.openide.util.NbBundle.getMessage(WellIdentificationViewerTopComponent.class, "WellIdentificationViewerTopComponent.clearButton.text")); // NOI18N
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(deleteButton, org.openide.util.NbBundle.getMessage(WellIdentificationViewerTopComponent.class, "WellIdentificationViewerTopComponent.deleteButton.text")); // NOI18N
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jComboBox1, 0, 279, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {clearButton, deleteButton});

        add(jPanel1, java.awt.BorderLayout.NORTH);
        add(propertySheet1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        IIdentification selectedIdent = (IIdentification) jComboBox1.getSelectedItem();
        setActiveNode(selectedIdent);
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        dcbm.removeAllElements();
        jComboBox1.setModel(dcbm);
        setActiveNode(null);
    }//GEN-LAST:event_clearButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        Object obj = jComboBox1.getSelectedItem();
        if (obj != null && wellIdentification != null) {
            IIdentification identif = (IIdentification) obj;
            IIdentificationMethod method = identif.getMethod();
            List<IIdentification> idents = wellIdentification.getMethodByName(method.getName()).getIdentifications();
            idents.remove(identif);
            wellIdentification.getMethodByName(method.getName()).setIdentifications(idents);
            setData(wellIdentification);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel1;
    private org.openide.explorer.propertysheet.PropertySheet propertySheet1;
    // End of variables declaration//GEN-END:variables

    private void setData(IWellIdentification wellIdentification) {
        dcbm.removeAllElements();
        jComboBox1.setModel(dcbm);
        this.wellIdentification = wellIdentification;
        if (wellIdentification != null) {
            for (IIdentificationMethod method : wellIdentification.getMethods()) {
                for (IIdentification identification : method.getIdentifications()) {
                    dcbm.addElement(identification);
                }
            }
            setActiveNode((IIdentification)dcbm.getElementAt(0));
        }else{
            setActiveNode(null);
        }
    }

    private void setActiveNode(IIdentification selectedIdent) {
        if (selectedIdent != null) {
            try {
                propertySheet1.setNodes(new Node[]{new WellIdentificationNode(selectedIdent)});
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
        }else{
             propertySheet1.setNodes(new Node[]{});
        }
    }

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized WellIdentificationViewerTopComponent getDefault() {
        if (instance == null) {
            instance = new WellIdentificationViewerTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the WellIdentificationViewerTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized WellIdentificationViewerTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(
                PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(
                    WellIdentificationViewerTopComponent.class.getName()).
                    warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof WellIdentificationViewerTopComponent) {
            return (WellIdentificationViewerTopComponent) win;
        }
        Logger.getLogger(WellIdentificationViewerTopComponent.class.getName()).
                warning(
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
        result.addLookupListener(this);
    }

    @Override
    public void componentClosed() {
        result.removeLookupListener(this);
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
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
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        for (IWell384 spot : result.allInstances()) {
//            nodeList.clear();
            setData(spot.getIdentification());
            return;
        }
//        if (!nodeList.isEmpty()) {
//
//        }
    }
}
