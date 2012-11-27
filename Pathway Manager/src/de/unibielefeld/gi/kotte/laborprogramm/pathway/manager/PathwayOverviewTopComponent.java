package de.unibielefeld.gi.kotte.laborprogramm.pathway.manager;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.CancellableRunnable;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.MetacycController;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.NameTools;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.ResultListener;
import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.TypedListModel;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.windows.TopComponent;

/**
 * Top component which provides an overview over Metacyc Database contents.
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
    List<Pathway> pathways = Collections.emptyList();
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
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
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void organismsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organismsButtonActionPerformed
        CancellableRunnable<List<PGDB>> cr = new CancellableRunnable<List<PGDB>>() {
            @Override
            public void body() {
                organismList.setVisible(false);
                this.handle.progress("Querying database");
                List<PGDB> organisms = mc.getOrganisms();
                this.handle.progress("Sorting results");
                Collections.sort(organisms, new Comparator<PGDB>() {
                    @Override
                    public int compare(PGDB o1, PGDB o2) {
                        String name1 = NameTools.getSpeciesName(o1);
                        String name2 = NameTools.getSpeciesName(o2);
                        return name1.compareTo(name2);
                    }
                });
                this.handle.progress("Adding results to the list");
                notifyListeners(organisms);
            }
        };
        cr.addResultListener(new ResultListener<List<PGDB>>() {
            @Override
            public void listen(List<PGDB> organisms) {
                System.out.println("Received organisms result list!");
                organismListModel.setList(organisms);
                organismList.setVisible(true);
            }
        });

        final ProgressHandle ph = ProgressHandleFactory.createHandle("Fetching organism list", cr);
        cr.setHandle(ph);
        RequestProcessor rp = new RequestProcessor(PathwayOverviewTopComponent.class);
        rp.post(cr);
    }//GEN-LAST:event_organismsButtonActionPerformed

    private void pathwaysButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathwaysButtonActionPerformed
        CancellableRunnable<List<Pathway>> cr = new CancellableRunnable<List<Pathway>>(true) {
            @Override
            public void body() {
                pathwaysList.setVisible(false);
                this.handle.progress("Querying database");
//                    List<Pathway> pathways;
                List<Pathway> pathways = mc.getPathwaysForOrganism(organism.getOrgid());
                this.handle.progress("Sorting results");
                Collections.sort(pathways, new Comparator<Pathway>() {
                    @Override
                    public int compare(Pathway o1, Pathway o2) {
                        String name1 = NameTools.getPathwayName(o1);
                        String name2 = NameTools.getPathwayName(o2);
                        return name1.compareTo(name2);
                    }
                });
                this.handle.progress("Adding results to the list");
                notifyListeners(pathways);
            }
        };
        cr.addResultListener(new ResultListener<List<Pathway>>() {
            @Override
            public void listen(List<Pathway> r) {
                System.out.println("Received pathway result list!");
                pathways = r;
                pathwaysListModel.setList(pathways);
                pathwaysList.setVisible(true);
            }
        });

        final ProgressHandle ph = ProgressHandleFactory.createHandle("Fetching organism list", cr);
        cr.setHandle(ph);
        RequestProcessor rp = new RequestProcessor(PathwayOverviewTopComponent.class);
        rp.post(cr);
    }//GEN-LAST:event_pathwaysButtonActionPerformed

    private void enzymesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enzymesButtonActionPerformed
        CancellableRunnable<List<Protein>> cr = new CancellableRunnable<List<Protein>>() {
            @Override
            public void body() {
                enzymesList.setVisible(false);
                this.handle.progress("Querying database");
                List<Protein> proteins = mc.getEnzymesForPathway(pathway.getOrgid(), pathway.getFrameid());
                this.handle.progress("Sorting results");
                Collections.sort(proteins, new Comparator<Protein>() {
                    @Override
                    public int compare(Protein o1, Protein o2) {
                        String name1 = NameTools.getProteinName(o1);
                        String name2 = NameTools.getProteinName(o2);
                        return name1.compareTo(name2);
                    }
                });
                this.handle.progress("Adding results to the list");
                notifyListeners(proteins);
            }
        };
        cr.addResultListener(new ResultListener<List<Protein>>() {
            @Override
            public void listen(List<Protein> proteins) {
                System.out.println("Received proteins result list!");
                enzymesListModel.setList(proteins);
                enzymesList.setVisible(true);
            }
        });

        final ProgressHandle ph = ProgressHandleFactory.createHandle("Fetching enzymes list", cr);
        cr.setHandle(ph);
        RequestProcessor rp = new RequestProcessor(PathwayOverviewTopComponent.class);
        rp.post(cr);
    }//GEN-LAST:event_enzymesButtonActionPerformed

    private void compoundsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compoundsButtonActionPerformed
        CancellableRunnable<List<Compound>> cr = new CancellableRunnable<List<Compound>>() {
            @Override
            public void body() {
                compoundsList.setVisible(false);
                this.handle.progress("Querying database");
                List<Compound> compounds = mc.getCompoundsForPathway(pathway.getOrgid(), pathway.getFrameid());
                this.handle.progress("Sorting results");
                Collections.sort(compounds, new Comparator<Compound>() {
                    @Override
                    public int compare(Compound o1, Compound o2) {
                        String name1 = NameTools.getCompoundName(o1);
                        String name2 = NameTools.getCompoundName(o2);
                        return name1.compareTo(name2);
                    }
                });
                this.handle.progress("Adding results to the list");
                notifyListeners(compounds);
            }
        };
        cr.addResultListener(new ResultListener<List<Compound>>() {
            @Override
            public void listen(List<Compound> compounds) {
                System.out.println("Received proteins result list!");
                compoundsListModel.setList(compounds);
                compoundsList.setVisible(true);
            }
        });

        final ProgressHandle ph = ProgressHandleFactory.createHandle("Fetching compoun list", cr);
        cr.setHandle(ph);
        RequestProcessor rp = new RequestProcessor(PathwayOverviewTopComponent.class);
        rp.post(cr);
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
