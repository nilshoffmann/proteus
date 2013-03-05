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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
	TypedListModel<PGDB> selectedOrganismListModel = new TypedListModel<PGDB>();
    TypedListModel<Pathway> selectedPathwaysListModel = new TypedListModel<Pathway>();
    TypedListModel<Protein> enzymesListModel = new TypedListModel<Protein>();
    TypedListModel<Compound> compoundsListModel = new TypedListModel<Compound>();

    public PathwayOverviewTopComponent() {
        initComponents();
		organismsFilter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				filterOrganisms(organismListModel);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filterOrganisms(organismListModel);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				
			}
		});
        organismList.addListSelectionListener(new OrganismListListener());
        organismList.setCellRenderer(new PGDBCellRenderer());
        organismList.setModel(organismListModel);
		pathwaysFilter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				filterPathways(pathwaysListModel);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filterPathways(pathwaysListModel);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				
			}
		});
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
	
	public void filterOrganisms(TypedListModel<PGDB> organism) {
		selectedOrganismListModel = new TypedListModel<PGDB>();
		if(organismsFilter.getText().isEmpty()) {
			selectedOrganismListModel.setList(organism.getList());
		}else{
			String filterText = organismsFilter.getText().toLowerCase();
			selectedOrganismListModel = new TypedListModel<PGDB>();
			ArrayList<PGDB> data = new ArrayList<PGDB>();
			for(PGDB p : organism.getList()) {
				if(NameTools.getSpeciesName(p).toLowerCase().startsWith(filterText) || NameTools.getSpeciesName(p).toLowerCase().contains(filterText)) {
					data.add(p);
				}
			}
			selectedOrganismListModel.setList(data);
		}
		organismList.setModel(selectedOrganismListModel);
	}
	
	public void filterPathways(TypedListModel<Pathway> pathways) {
		selectedPathwaysListModel = new TypedListModel<Pathway>();
		if(pathwaysFilter.getText().isEmpty()) {
			selectedPathwaysListModel.setList(pathways.getList());
		}else{
			String filterText = pathwaysFilter.getText().toLowerCase();
			selectedPathwaysListModel = new TypedListModel<Pathway>();
			ArrayList<Pathway> data = new ArrayList<Pathway>();
			for(Pathway p : pathways.getList()) {
				if(NameTools.getPathwayName(p).toLowerCase().startsWith(filterText) || NameTools.getPathwayName(p).toLowerCase().contains(filterText)) {
					data.add(p);
				}
			}
			selectedPathwaysListModel.setList(data);
		}
		pathwaysList.setModel(selectedPathwaysListModel);
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
        organismFixedLabel = new javax.swing.JLabel();
        pathwayFixedLabel = new javax.swing.JLabel();
        organismLabel = new javax.swing.JLabel();
        pathwayLabel = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        organismsButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        organismList = new javax.swing.JList();
        organismsFilter = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        pathwaysButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        pathwaysList = new javax.swing.JList();
        pathwaysFilter = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        enzymesList = new javax.swing.JList();
        enzymesButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        compoundsButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        compoundsList = new javax.swing.JList();

        org.openide.awt.Mnemonics.setLocalizedText(metacycLabel, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.metacycLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(organismFixedLabel, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.organismFixedLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(pathwayFixedLabel, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.pathwayFixedLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(organismLabel, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.organismLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(pathwayLabel, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.pathwayLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(organismsButton, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.organismsButton.text")); // NOI18N
        organismsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organismsButtonActionPerformed(evt);
            }
        });

        organismList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(organismList);

        organismsFilter.setText(org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.organismsFilter.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(organismsButton)
                    .addComponent(jScrollPane1)
                    .addComponent(organismsFilter))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(organismsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(organismsFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(pathwaysButton, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.pathwaysButton.text")); // NOI18N
        pathwaysButton.setEnabled(false);
        pathwaysButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathwaysButtonActionPerformed(evt);
            }
        });

        pathwaysList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(pathwaysList);

        pathwaysFilter.setText(org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.pathwaysFilter.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pathwaysButton)
                    .addComponent(pathwaysFilter)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pathwaysButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pathwaysFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        enzymesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(enzymesList);

        org.openide.awt.Mnemonics.setLocalizedText(enzymesButton, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.enzymesButton.text")); // NOI18N
        enzymesButton.setEnabled(false);
        enzymesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enzymesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(enzymesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(enzymesButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(compoundsButton, org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.compoundsButton.text")); // NOI18N
        compoundsButton.setEnabled(false);
        compoundsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compoundsButtonActionPerformed(evt);
            }
        });

        compoundsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(compoundsList);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(compoundsButton)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(compoundsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(PathwayOverviewTopComponent.class, "PathwayOverviewTopComponent.jPanel4.TabConstraints.tabTitle"), jPanel4); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(metacycLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(organismFixedLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(organismLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pathwayFixedLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pathwayLabel)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void organismsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organismsButtonActionPerformed
        CancellableRunnable<List<PGDB>> cr = new CancellableRunnable<List<PGDB>>() {
            @Override
            public void body() {
                organismList.setEnabled(false);
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
                organismList.setEnabled(true);
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
                pathwaysList.setEnabled(false);
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
                pathwaysList.setEnabled(true);
            }
        });

        final ProgressHandle ph = ProgressHandleFactory.createHandle("Fetching pathway list", cr);
        cr.setHandle(ph);
        RequestProcessor rp = new RequestProcessor(PathwayOverviewTopComponent.class);
        rp.post(cr);
    }//GEN-LAST:event_pathwaysButtonActionPerformed

    private void enzymesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enzymesButtonActionPerformed
        CancellableRunnable<List<Protein>> cr = new CancellableRunnable<List<Protein>>() {
            @Override
            public void body() {
                enzymesList.setEnabled(false);
				enzymesListModel.setList(new LinkedList<Protein>());
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
                enzymesList.setEnabled(true);
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
                compoundsList.setEnabled(false);
				compoundsListModel.setList(new LinkedList<Compound>());
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
                compoundsList.setEnabled(true);
            }
        });

        final ProgressHandle ph = ProgressHandleFactory.createHandle("Fetching compound list", cr);
        cr.setHandle(ph);
        RequestProcessor rp = new RequestProcessor(PathwayOverviewTopComponent.class);
        rp.post(cr);
    }//GEN-LAST:event_compoundsButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton compoundsButton;
    private javax.swing.JList compoundsList;
    private javax.swing.JButton enzymesButton;
    private javax.swing.JList enzymesList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel metacycLabel;
    private javax.swing.JLabel organismFixedLabel;
    private javax.swing.JLabel organismLabel;
    private javax.swing.JList organismList;
    private javax.swing.JButton organismsButton;
    private javax.swing.JTextField organismsFilter;
    private javax.swing.JLabel pathwayFixedLabel;
    private javax.swing.JLabel pathwayLabel;
    private javax.swing.JButton pathwaysButton;
    private javax.swing.JTextField pathwaysFilter;
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
