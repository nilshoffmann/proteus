package de.unibielefeld.gi.kotte.laborprogramm.pathways;

import de.unibielefeld.gi.kotte.laborprogramm.pathways.sbml.PathwayExplorerTopComponent;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.CommonName;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Compound;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.PGDB;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Protein;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Strain;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Synonym;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.Exceptions;
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
    TypedListModel<PGDB> organismListModel = new TypedListModel<PGDB>();
    TypedListModel<Pathway> pathwaysListModel = new TypedListModel<Pathway>();
    TypedListModel<Protein> enzymesListModel = new TypedListModel<Protein>();
    TypedListModel<Compound> compoundsListModel = new TypedListModel<Compound>();

    public PathwayOverviewTopComponent() {
        initComponents();
        organismList.addListSelectionListener(new OrganismListListener());
        organismList.setCellRenderer(new PGDBCellRenderer());
        organismList.setModel(organismListModel);
        pathwaysList.addListSelectionListener(new PathwayListListener());
        pathwaysList.setCellRenderer(new PathwayCellRenderer());
        pathwaysList.setModel(pathwaysListModel);
        enzymesList.setCellRenderer(new ProteinCellRenderer());
        enzymesList.setModel(enzymesListModel);
        compoundsList.setCellRenderer(new CompoundCellRenderer());
        compoundsList.setModel(compoundsListModel);
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
            PathwayExplorerTopComponent petc = new PathwayExplorerTopComponent();
            petc.openFile(jfc.getSelectedFile());
        }
    }//GEN-LAST:event_sbmlButtonActionPerformed

    private void organismsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organismsButtonActionPerformed
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Fetching organism list");
        RequestProcessor rp = new RequestProcessor(PathwayOverviewTopComponent.class);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    ph.start();
                    organismList.setVisible(false);
                    ph.progress("Querying database");
                    List<PGDB> organisms = mc.getOrganisms();
                    ph.progress("Sorting results");
                    Collections.sort(organisms, new Comparator<PGDB>() {
                        @Override
                        public int compare(PGDB o1, PGDB o2) {
                            String name1 = getSpeciesName(o1);
                            String name2 = getSpeciesName(o2);
                            return name1.compareTo(name2);
                        }
                    });
                    ph.progress("Adding results to the list");
                    organismListModel.setList(organisms);
                    organismList.setVisible(true);
                } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        } finally {
                    ph.finish();
                }
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
                try {
                    ph.start();
                    pathwaysList.setVisible(false);
                    ph.progress("Querying database");
                    List<Pathway> pathways = mc.getPathwaysForOrganism(organism.getOrgid());
                    ph.progress("Sorting results");
                    Collections.sort(pathways, new Comparator<Pathway>() {
                        @Override
                        public int compare(Pathway o1, Pathway o2) {
                            String name1 = getPathwayName(o1);
                            String name2 = getPathwayName(o2);
                            return name1.compareTo(name2);
                        }
                    });
                    ph.progress("Adding results to the list");
                    pathwaysListModel.setList(pathways);
                    pathwaysList.setVisible(true);
                } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        } finally {
                    ph.finish();
                }
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
                try {
                    ph.start();
                    enzymesList.setVisible(false);
                    ph.progress("Querying database");
                    List<Protein> proteins = mc.getEnzymesForPathway(pathway.getOrgid(), pathway.getFrameid());
                    ph.progress("Sorting results");
                    Collections.sort(proteins, new Comparator<Protein>() {
                        @Override
                        public int compare(Protein o1, Protein o2) {
                            String name1 = getProteinName(o1);
                            String name2 = getProteinName(o2);
                            return name1.compareTo(name2);
                        }
                    });
                    ph.progress("Adding results to the list");
                    enzymesListModel.setList(proteins);
                    enzymesList.setVisible(true);
                } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        } finally {
                    ph.finish();
                }
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
                try {
                    ph.start();
                    compoundsList.setVisible(false);
                    ph.progress("Querying database");
                    List<Compound> compounds = mc.getCompoundsForPathway(pathway.getOrgid(), pathway.getFrameid());
                    ph.progress("Sorting results");
                    Collections.sort(compounds, new Comparator<Compound>() {
                        @Override
                        public int compare(Compound o1, Compound o2) {
                            String name1 = getCompoundName(o1);
                            String name2 = getCompoundName(o2);
                            return name1.compareTo(name2);
                        }
                    });
                    ph.progress("Adding results to the list");
                    compoundsListModel.setList(compounds);
                    compoundsList.setVisible(true);
                } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        } finally {
                    ph.finish();
                }
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

    static String getSpeciesName(PGDB pgdb) {
        StringBuilder builder = new StringBuilder((String) pgdb.getSpecies().getContent().iterator().next());
        Strain strain = pgdb.getStrain();
        if (strain != null) {
            builder.append(' ').append(strain.getContent());
        }
        return builder.toString();
    }

    static String getPathwayName(Pathway pw) {
        for (Object obj : pw.getCitationOrCommentOrCommonName()) {
            if (obj instanceof CommonName) {
                return (new StringBuilder("<html>")).append(((CommonName) obj).getContent()).append("</html>").toString();
            }
        }
        return pw.getFrameid();
    }

    static String getProteinName(Protein prot) {
        for (Object obj : prot.getCatalyzes().getEnzymaticReaction().iterator().next().getCitationOrCofactorOrComment()) {
            if (obj instanceof CommonName) {
                return (new StringBuilder("<html>")).append(((CommonName) obj).getContent()).append("</html>").toString();
            }
        }
        return prot.getFrameid();
    }

    static String getCompoundName(Compound cmp) {
        for (Object obj : cmp.getAbbrevNameOrAppearsInLeftSideOfOrAppearsInRightSideOf()) {
            if (obj instanceof CommonName) {
                return (new StringBuilder("<html>")).append(((CommonName) obj).getContent()).append("</html>").toString();
            }
        }
        return cmp.getFrameid();
    }

    static String getToolTip(List<Object> l) {
        StringBuilder tooltip = new StringBuilder("<html>");
        boolean empty = true;
//        try {
            for (Object obj : l) {
                if (obj instanceof CommonName) {
                    if (!empty) {
                        tooltip.append("<br>");
                    }
                    CommonName name = (CommonName) obj;
                    tooltip.append(name.getContent());
                    empty = false;
                } else if (obj instanceof Synonym) {
                    if (!empty) {
                        tooltip.append("<br>");
                    }
                    Synonym synonym = (Synonym) obj;
                    tooltip.append(synonym.getContent());
                    empty = false;
                }
            }
//        } catch (Exception ex) {
//            Exceptions.printStackTrace(ex);
//        }
        tooltip.append("</html>");
        return tooltip.toString();
    }
}
