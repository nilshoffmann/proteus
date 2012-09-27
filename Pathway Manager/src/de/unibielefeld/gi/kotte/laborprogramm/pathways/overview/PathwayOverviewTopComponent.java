package de.unibielefeld.gi.kotte.laborprogramm.pathways.overview;

import de.unibielefeld.gi.kotte.laborprogramm.pathways.visualization.PathwayExplorerTopComponent;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.xml.stream.XMLStreamException;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.xml.stax.SBMLReader;

/**
 * Top component which provides an overview over pathways.
 */
@ConvertAsProperties(
    dtd = "-//de.unibielefeld.gi.kotte.laborprogramm.pathways.overview//PathwayOverview//EN",
autostore = false)
@TopComponent.Description(
    preferredID = "PathwayOverviewTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "navigator", openAtStartup = false)
@ActionID(category = "Window", id = "de.unibielefeld.gi.kotte.laborprogramm.pathways.overview.PathwayOverviewTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_PathwayOverviewAction",
preferredID = "PathwayOverviewTopComponent")
@Messages({
    "CTL_PathwayOverviewAction=Pathway Overview",
    "CTL_PathwayOverviewTopComponent=Pathway Overview Window",
    "HINT_PathwayOverviewTopComponent=This is a Pathway Overview window"
})
public final class PathwayOverviewTopComponent extends TopComponent {

    public PathwayOverviewTopComponent() {
        initComponents();
        setName(Bundle.CTL_PathwayOverviewTopComponent());
        setToolTipText(Bundle.HINT_PathwayOverviewTopComponent());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sbmlLabel = new javax.swing.JLabel();
        sbmlButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(sbmlLabel, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.sbmlLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(sbmlButton, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.sbmlButton.text")); // NOI18N
        sbmlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sbmlButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sbmlLabel)
                    .addComponent(sbmlButton))
                .addContainerGap(279, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sbmlLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sbmlButton)
                .addContainerGap(246, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sbmlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sbmlButtonActionPerformed
        FileChooserBuilder fcb = new FileChooserBuilder(getClass());
        fcb.setTitle("Choose SBML File");
        JFileChooser jfc = fcb.createFileChooser();
        int status = jfc.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
//            SBMLReader reader = new SBMLReader();
//            SBMLDocument document = null;
//            try {
//                document = reader.readSBML(jfc.getSelectedFile());
//            } catch (IOException ex) {
//                Exceptions.printStackTrace(ex);
//            } catch (XMLStreamException ex) {
//                Exceptions.printStackTrace(ex);
//            }
//            if (document != null) {
//                Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().openTopComponentFor(document,PathwayExplorerTopComponent.class);
                PathwayExplorerTopComponent petc = new PathwayExplorerTopComponent();
                petc.openFile(jfc.getSelectedFile());
                petc.open();
//            } else {
//                "SBMLReader konnte die ausgewählte Datei nicht einlesen."
//            }
        }
    }//GEN-LAST:event_sbmlButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton sbmlButton;
    private javax.swing.JLabel sbmlLabel;
    // End of variables declaration//GEN-END:variables
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

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
