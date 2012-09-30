package de.unibielefeld.gi.kotte.laborprogramm.pathways;

import de.unibielefeld.gi.kotte.laborprogramm.pathways.sbml.PathwayExplorerTopComponent;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.CommonName;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Compound;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.PGDB;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Protein;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.PtoolsXml;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Strain;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Synonym;
import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.windows.TopComponent;

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

    PGDB organism = null;
    Pathway pathway = null;
    MetacycController mc = new MetacycController();

    public PathwayOverviewTopComponent() {
        initComponents();
        organismList.addListSelectionListener(new OrganismListListener());
        organismList.setCellRenderer(new PGDBCellRenderer());
        pathwaysList.addListSelectionListener(new PathwayListListener());
        pathwaysList.setCellRenderer(new PathwayCellRenderer());
        enzymesList.setCellRenderer(new ProteinCellRenderer());
        compoundsList.setCellRenderer(new CompoundCellRenderer());
        setName(Bundle.CTL_PathwayOverviewTopComponent());
        setToolTipText(Bundle.HINT_PathwayOverviewTopComponent());

    }

    class OrganismListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (organismList.getSelectedIndex() != -1) {
                organism = (PGDB) organismList.getSelectedValue();
                String text = (String) organism.getSpecies().getContent().iterator().next();
                Strain strain = organism.getStrain();
                if (strain != null) {
                    text += ' ' + strain.getContent();
                }
                organismLabel.setText(text);
                pathwaysButton.setEnabled(true);
            } else {
                pathwaysButton.setEnabled(false);
                enzymesButton.setEnabled(false);
                compoundsButton.setEnabled(false);
            }
        }
    }

    class PathwayListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (pathwaysList.getSelectedIndex() != -1) {
                pathway = (Pathway) pathwaysList.getSelectedValue();
                for (Object obj : pathway.getCitationOrCommentOrCommonName()) {
                    if (obj instanceof CommonName) {
                        CommonName name = (CommonName) obj;
                        pathwayLabel.setText("<html>" + name.getContent() + "</html>");
                        break;
                    }
                }
                enzymesButton.setEnabled(true);
                compoundsButton.setEnabled(true);
            } else {
                enzymesButton.setEnabled(false);
                compoundsButton.setEnabled(false);
            }
        }
    }

    class PGDBCellRenderer extends DefaultListCellRenderer implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            PGDB pgdb = (PGDB) value;
            List<Object> content = pgdb.getSpecies().getContent();
            if (content.size() != 1) {
                System.err.println("Species list entry with not exactly one species: " + content);
            }
            String text = (String) content.iterator().next();
            Strain strain = pgdb.getStrain();
            if (strain != null) {
                text += ' ' + strain.getContent();
            }
            setText(text);
            return this;
        }
    }

    class PathwayCellRenderer extends DefaultListCellRenderer implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Pathway pw = (Pathway) value;
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            boolean empty = true;
            for (Object obj : pw.getCitationOrCommentOrCommonName()) {
                if (obj instanceof CommonName) {
                    CommonName name = (CommonName) obj;
                    setText("<html>" + name.getContent() + "</html>");
                    sb.append(name.getContent());
                } else if (obj instanceof Synonym) {
                    Synonym synonym = (Synonym) obj;
                    sb.append(synonym.getContent());
                }
                if (!empty) {
                    sb.append("<br>");
                }
                empty = false;
            }
            sb.append("</html>");
            setToolTipText(sb.toString());
            return this;
        }
    }

    class ProteinCellRenderer extends DefaultListCellRenderer implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Protein prot = (Protein) value;
            for (Object obj : prot.getCatalyzes().getEnzymaticReaction().iterator().next().getCitationOrCofactorOrComment()) {
                if (obj instanceof CommonName) {
                    setText(((CommonName) obj).getContent());
                    break;
                }
            }
            return this;
        }
    }

    class CompoundCellRenderer extends DefaultListCellRenderer implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Compound cmp = (Compound) value;
            setText(cmp.getCml().getMolecule().getTitle());
            return this;
        }
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
        sbmlSeparator = new javax.swing.JSeparator();
        metacycLabel = new javax.swing.JLabel();
        organismsButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        organismList = new javax.swing.JList();
        pathwaysButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        pathwaysList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        compoundsList = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        enzymesList = new javax.swing.JList();
        enzymesButton = new javax.swing.JButton();
        compoundsButton = new javax.swing.JButton();
        organismFixedLabel = new javax.swing.JLabel();
        pathwayFixedLabel = new javax.swing.JLabel();
        organismLabel = new javax.swing.JLabel();
        pathwayLabel = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(sbmlLabel, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.sbmlLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(sbmlButton, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.sbmlButton.text")); // NOI18N
        sbmlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sbmlButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(metacycLabel, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.metacycLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(organismsButton, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.organismsButton.text")); // NOI18N
        organismsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organismsButtonActionPerformed(evt);
            }
        });

        organismList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(organismList);

        org.openide.awt.Mnemonics.setLocalizedText(pathwaysButton, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.pathwaysButton.text")); // NOI18N
        pathwaysButton.setEnabled(false);
        pathwaysButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathwaysButtonActionPerformed(evt);
            }
        });

        pathwaysList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(pathwaysList);

        compoundsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(compoundsList);

        enzymesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(enzymesList);

        org.openide.awt.Mnemonics.setLocalizedText(enzymesButton, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.enzymesButton.text")); // NOI18N
        enzymesButton.setEnabled(false);
        enzymesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enzymesButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(compoundsButton, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.compoundsButton.text")); // NOI18N
        compoundsButton.setEnabled(false);
        compoundsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compoundsButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(organismFixedLabel, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.organismFixedLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(pathwayFixedLabel, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.pathwayFixedLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(organismLabel, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.organismLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(pathwayLabel, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.pathwayLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sbmlSeparator, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sbmlLabel)
                    .addComponent(sbmlButton)
                    .addComponent(metacycLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(organismsButton)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pathwaysButton))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(enzymesButton))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(compoundsButton)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(organismFixedLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(organismLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pathwayFixedLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pathwayLabel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sbmlLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sbmlButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sbmlSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(metacycLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(organismFixedLabel)
                    .addComponent(organismLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pathwayFixedLabel)
                    .addComponent(pathwayLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(organismsButton)
                    .addComponent(pathwaysButton)
                    .addComponent(enzymesButton)
                    .addComponent(compoundsButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane1))
                .addContainerGap())
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

    private void organismsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organismsButtonActionPerformed
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Fetching organism list");
        RequestProcessor rp = new RequestProcessor(PathwayOverviewTopComponent.class);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ph.start();
                ph.progress("Querying database");
                List<PGDB> organisms = mc.getOrganisms();
                organismList.setListData(organisms.toArray());
                ph.finish();
            }
        };
        rp.post(r);
    }//GEN-LAST:event_organismsButtonActionPerformed

    private void pathwaysButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathwaysButtonActionPerformed
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Fetching pathway list");
        RequestProcessor rp = new RequestProcessor(PathwayOverviewTopComponent.class);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ph.start();
                ph.progress("Querying database");
                List<Pathway> pathways = mc.getPathwaysForOrganism(organism.getOrgid());
                pathwaysList.setListData(pathways.toArray());
                ph.finish();
            }
        };
        rp.post(r);
    }//GEN-LAST:event_pathwaysButtonActionPerformed

    private void enzymesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enzymesButtonActionPerformed
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Fetching enzymes list");
        RequestProcessor rp = new RequestProcessor(PathwayOverviewTopComponent.class);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ph.start();
                ph.progress("Querying database");
                List<Protein> proteins = mc.getEnzymesForPathway(pathway.getOrgid(), pathway.getFrameid());
                enzymesList.setListData(proteins.toArray());
                ph.finish();
            }
        };
        rp.post(r);
    }//GEN-LAST:event_enzymesButtonActionPerformed

    private void compoundsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compoundsButtonActionPerformed
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Fetching compounds list");
        RequestProcessor rp = new RequestProcessor(PathwayOverviewTopComponent.class);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ph.start();
                ph.progress("Querying database");
                List<Compound> compounds = mc.getCopoundsForPathway(pathway.getOrgid(), pathway.getFrameid());
                compoundsList.setListData(compounds.toArray());
                ph.finish();
            }
        };
        rp.post(r);
    }//GEN-LAST:event_compoundsButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton compoundsButton;
    private javax.swing.JList compoundsList;
    private javax.swing.JButton enzymesButton;
    private javax.swing.JList enzymesList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel metacycLabel;
    private javax.swing.JLabel organismFixedLabel;
    private javax.swing.JLabel organismLabel;
    private javax.swing.JList organismList;
    private javax.swing.JButton organismsButton;
    private javax.swing.JLabel pathwayFixedLabel;
    private javax.swing.JLabel pathwayLabel;
    private javax.swing.JButton pathwaysButton;
    private javax.swing.JList pathwaysList;
    private javax.swing.JButton sbmlButton;
    private javax.swing.JLabel sbmlLabel;
    private javax.swing.JSeparator sbmlSeparator;
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
