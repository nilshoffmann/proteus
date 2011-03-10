package de.unibielefeld.gi.kotte.laborprogramm.firsttestmodule;

import java.io.IOException;
import java.util.logging.Logger;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Lookup;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IGel;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//de.unibielefeld.gi.kotte.laborprogramm.firsttestmodule//firsttestmodule//EN",
autostore = false)
public final class firsttestmoduleTopComponent extends TopComponent {

    private static firsttestmoduleTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "firsttestmoduleTopComponent";

    public firsttestmoduleTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(firsttestmoduleTopComponent.class, "CTL_firsttestmoduleTopComponent"));
        setToolTipText(NbBundle.getMessage(firsttestmoduleTopComponent.class, "HINT_firsttestmoduleTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTest = new javax.swing.JLabel();
        jButtonTest = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();

        org.openide.awt.Mnemonics.setLocalizedText(jLabelTest, org.openide.util.NbBundle.getMessage(firsttestmoduleTopComponent.class, "firsttestmoduleTopComponent.jLabelTest.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButtonTest, org.openide.util.NbBundle.getMessage(firsttestmoduleTopComponent.class, "firsttestmoduleTopComponent.jButtonTest.text")); // NOI18N
        jButtonTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(251, Short.MAX_VALUE)
                .addComponent(jLabelTest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonTest))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonTest)
                    .addComponent(jLabelTest)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestActionPerformed
        //jLabelTest.setText(Lookup.getDefault().lookup(IGel.class).getDescription());
        IGel testGel = Lookup.getDefault().lookup(IGel.class);
        testGel.setFilename("/homes/kotte/ProteomikProjekt/b100/80547_255_75_101.jpeg");
        java.net.URL imgURL = null;

        File f = new File(testGel.getFilename());
        if (!f.exists()) {
            throw new RuntimeException("File specified " + f.getAbsolutePath() + " does not exist!");
        }else{
            try {
                //        if (imgURL != null) {
                BufferedImage myPicture = ImageIO.read(f);
                ImageIcon icon = new ImageIcon(myPicture);
                JFrame jf = new JFrame();
                JLabel jl = new JLabel(icon);
                jl.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
                //remove(jLabelTest);
                jf.add(new JScrollPane(jl));
                jf.setVisible(true);
                jf.pack();
//                jScrollPane1.add(jl);
                //            jLabelTest.setIcon(icon);
                //            jLabelTest.setText("kein Gelbild");
                //            jLabelTest.setText("kein Gelbild");
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }//GEN-LAST:event_jButtonTestActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonTest;
    private javax.swing.JLabel jLabelTest;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized firsttestmoduleTopComponent getDefault() {
        if (instance == null) {
            instance = new firsttestmoduleTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the firsttestmoduleTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized firsttestmoduleTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(firsttestmoduleTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof firsttestmoduleTopComponent) {
            return (firsttestmoduleTopComponent) win;
        }
        Logger.getLogger(firsttestmoduleTopComponent.class.getName()).warning(
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
