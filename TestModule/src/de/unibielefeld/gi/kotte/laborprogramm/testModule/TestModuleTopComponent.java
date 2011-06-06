package de.unibielefeld.gi.kotte.laborprogramm.testModule;

import de.unibielefeld.gi.kotte.laborprogramm.ImportWizard.ImportWizardAction;
import de.unibielefeld.gi.kotte.laborprogramm.plateViewer.Plate384Panel;
import de.unibielefeld.gi.kotte.laborprogramm.plateViewer.Plate96Panel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384Factory;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96Factory;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Lookup;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//de.unibielefeld.gi.kotte.laborprogramm.testModule//TestModule//EN",
autostore = false)
public final class TestModuleTopComponent extends TopComponent {

    private static TestModuleTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "TestModuleTopComponent";

    public TestModuleTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(TestModuleTopComponent.class, "CTL_TestModuleTopComponent"));
        setToolTipText(NbBundle.getMessage(TestModuleTopComponent.class, "HINT_TestModuleTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        plate96TestButton = new javax.swing.JButton();
        plate384TestButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(plate96TestButton, org.openide.util.NbBundle.getMessage(TestModuleTopComponent.class, "TestModuleTopComponent.plate96TestButton.text")); // NOI18N
        plate96TestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plate96TestButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(plate384TestButton, org.openide.util.NbBundle.getMessage(TestModuleTopComponent.class, "TestModuleTopComponent.plate384TestButton.text")); // NOI18N
        plate384TestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plate384TestButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(TestModuleTopComponent.class, "TestModuleTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plate96TestButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plate384TestButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(261, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(plate96TestButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plate384TestButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(206, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void plate96TestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plate96TestButtonActionPerformed
        IPlate96 plate = Lookup.getDefault().lookup(IPlate96Factory.class).createPlate96();
        JPanel panel = new Plate96Panel(plate);
        JDialog dialog = new JDialog();
        dialog.add(panel);
        dialog.pack();
        dialog.setVisible(true);
    }//GEN-LAST:event_plate96TestButtonActionPerformed

    private void plate384TestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plate384TestButtonActionPerformed
        IPlate384 plate = Lookup.getDefault().lookup(IPlate384Factory.class).createPlate384();
        JPanel panel = new Plate384Panel(plate);
        JDialog dialog = new JDialog();
        dialog.add(panel);
        dialog.pack();
        dialog.setVisible(true);
    }//GEN-LAST:event_plate384TestButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new ImportWizardAction().performAction();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton plate384TestButton;
    private javax.swing.JButton plate96TestButton;
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized TestModuleTopComponent getDefault() {
        if (instance == null) {
            instance = new TestModuleTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the TestModuleTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized TestModuleTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(TestModuleTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof TestModuleTopComponent) {
            return (TestModuleTopComponent) win;
        }
        Logger.getLogger(TestModuleTopComponent.class.getName()).warning(
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
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
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
}
