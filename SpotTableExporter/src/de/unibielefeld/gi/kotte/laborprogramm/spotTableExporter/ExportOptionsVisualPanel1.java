package de.unibielefeld.gi.kotte.laborprogramm.spotTableExporter;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * Wizard Panel for the Spot Data Export Options.
 *
 * @author kotte
 */
public final class ExportOptionsVisualPanel1 extends JPanel {

    public static final String PROPERTY_PROJECT = "project";
    public static final String PROPERTY_METHODS = "methods";
    public static final String PROPERTY_FILENAME = "filename";
    public static final String PROPERTY_DIRECTORY = "directory";
    
    public static final String PROPERTY_SHOW_HEADER= "add row header";
    public static final String PROPERTY_SHOW_GROUP_NUMBER = "show spot group number";
    public static final String PROPERTY_SHOW_GROUP_LABEL = "show spot group label";
    public static final String PROPERTY_SHOW_IDENTIFICATION = "show spot identifications";
    
    public static final String PROPERTY_SHOW_IDENT_METHOD_NAME = "show identification method name";
    public static final String PROPERTY_SHOW_IDENT_NAME = "show identification name";
    public static final String PROPERTY_SHOW_IDENT_PLATE96_POSITION = "show identification plate96 position";
    public static final String PROPERTY_SHOW_IDENT_PLATE384_POSITION = "show identification plate384 position";
    public static final String PROPERTY_SHOW_IDENT_GEL_NAME = "show identification gel name";
    public static final String PROPERTY_SHOW_IDENT_ABBREVIATION = "show identification abbreviation";
    public static final String PROPERTY_SHOW_IDENT_ACCESSION = "show identification accession number";
    public static final String PROPERTY_SHOW_IDENT_KEGG_NUMBERS = "show identification EC numbers";
    public static final String PROPERTY_SHOW_IDENT_COVERAGE = "show identification MS Coverage";
    public static final String PROPERTY_SHOW_IDENT_PI_VALUE = "show identification pI value";
    public static final String PROPERTY_SHOW_IDENT_SCORE = "show identification mascot score";
    public static final String PROPERTY_SHOW_IDENT_WEIGHT = "show identification molecular weight";
    
    private File directory;
    
    public boolean isShowHeader() {
        return headerBox.isSelected();
    }
    
    public boolean isShowGroupNumber() {
        return spotGroupIDBox.isSelected();
    }
    
    public boolean isShowGroupLabel() {
        return spotGroupLabelBox.isSelected();
    }

    public boolean isShowIdentification() {
        return identificationBox.isSelected();
    }

    public boolean isShowIdentMethodName() {
        return identMethodNameBox.isSelected();
    }

    public boolean isShowIdentName() {
        return identNameBox.isSelected();
    }

    public boolean isShowIdentPlate96Position() {
        return identPlate96Box.isSelected();
    }

    public boolean isShowIdentPlate384Position() {
        return identPlate384Box.isSelected();
    }
    
    public boolean isShowIdentGelName() {
        return identGelNameBox.isSelected();
    }

    public boolean isShowIdentAbbreviation() {
        return identAbbreviationBox.isSelected();
    }

    public boolean isShowIdentAccession() {
        return identAccessionBox.isSelected();
    }

    public boolean isShowIdentEcNumbers() {
        return identEcNumbersBox.isSelected();
    }

    public boolean isShowIdentCoverage() {
        return identCoverageBox.isSelected();
    }

    public boolean isShowIdentPIValue() {
        return identPIValueBox.isSelected();
    }

    public boolean isShowIdentScore() {
        return identScoreBox.isSelected();
    }

    public boolean isShowIdentWeight() {
        return identWeightBox.isSelected();
    }

    public String getFileName() {
        return fileNameTextField.getText();
    }

    public File getDirectory() {
        return this.directory;
    }

    protected void setDirectory(File directory) {
        this.directory = directory;
        fileDirectoryTextField.setText(directory.getAbsolutePath());
        filePathTextField.setText(directory.getAbsolutePath() + File.separator + fileNameTextField.getText() + ".csv");
    }

    protected void setAvailableMethods(Set<String> methods) {
        //put methods in JList
        methodsList.setListData(methods.toArray());
        //select all JList entries
        int[] indices = new int[methods.size()];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }
        methodsList.setSelectedIndices(indices);
    }

    public Set<String> getSelectedMethods() {
        Object[] methods = (Object[]) methodsList.getSelectedValues();
        LinkedHashSet<String> methodsSet = new LinkedHashSet<String>();
        for(Object obj:methods) {
            methodsSet.add(obj.toString());
        }
        return methodsSet;
    }

    /** Creates new form ExportOptionsVisualPanel1 */
    public ExportOptionsVisualPanel1() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Output Options for Spot Data Export";
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
        outputHeadingLabel = new javax.swing.JLabel();
        identMethodNameBox = new javax.swing.JCheckBox();
        identificationBox = new javax.swing.JCheckBox();
        fileDirectoryTextField = new javax.swing.JTextField();
        fileDirectoryLabel = new javax.swing.JLabel();
        fileChooserButton = new javax.swing.JButton();
        filePathLabel = new javax.swing.JLabel();
        filePathTextField = new javax.swing.JTextField();
        fileNameTextField = new javax.swing.JTextField();
        fileNameLabel = new javax.swing.JLabel();
        methodsLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        methodsList = new javax.swing.JList();
        outputSeparator = new javax.swing.JSeparator();
        spotGroupLabelBox = new javax.swing.JCheckBox();
        identNameBox = new javax.swing.JCheckBox();
        identPlate96Box = new javax.swing.JCheckBox();
        identPlate384Box = new javax.swing.JCheckBox();
        spotGroupIDBox = new javax.swing.JCheckBox();
        identAbbreviationBox = new javax.swing.JCheckBox();
        identAccessionBox = new javax.swing.JCheckBox();
        identCoverageBox = new javax.swing.JCheckBox();
        identEcNumbersBox = new javax.swing.JCheckBox();
        identPIValueBox = new javax.swing.JCheckBox();
        identScoreBox = new javax.swing.JCheckBox();
        identWeightBox = new javax.swing.JCheckBox();
        headerBox = new javax.swing.JCheckBox();
        identGelNameBox = new javax.swing.JCheckBox();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        org.openide.awt.Mnemonics.setLocalizedText(outputHeadingLabel, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.outputHeadingLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(identMethodNameBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identMethodNameBox.text")); // NOI18N
        identMethodNameBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identMethodNameBoxActionPerformed(evt);
            }
        });

        identificationBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(identificationBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identificationBox.text")); // NOI18N
        identificationBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identificationBoxActionPerformed(evt);
            }
        });

        fileDirectoryTextField.setText(org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.fileDirectoryTextField.text")); // NOI18N
        fileDirectoryTextField.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(fileDirectoryLabel, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.fileDirectoryLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(fileChooserButton, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.fileChooserButton.text")); // NOI18N
        fileChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(filePathLabel, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.filePathLabel.text")); // NOI18N

        filePathTextField.setText(org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.filePathTextField.text")); // NOI18N
        filePathTextField.setEnabled(false);

        fileNameTextField.setText(org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.fileNameTextField.text")); // NOI18N
        fileNameTextField.setPreferredSize(new java.awt.Dimension(200, 20));
        fileNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileNameTextFieldActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(fileNameLabel, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.fileNameLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(methodsLabel, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.methodsLabel.text")); // NOI18N

        methodsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(methodsList);

        spotGroupLabelBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(spotGroupLabelBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.spotGroupLabelBox.text")); // NOI18N
        spotGroupLabelBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spotGroupLabelBoxActionPerformed(evt);
            }
        });

        identNameBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(identNameBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identNameBox.text")); // NOI18N
        identNameBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identNameBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(identPlate96Box, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identPlate96Box.text")); // NOI18N
        identPlate96Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identPlate96BoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(identPlate384Box, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identPlate384Box.text")); // NOI18N
        identPlate384Box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identPlate384BoxActionPerformed(evt);
            }
        });

        spotGroupIDBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(spotGroupIDBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.spotGroupIDBox.text")); // NOI18N
        spotGroupIDBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spotGroupIDBoxActionPerformed(evt);
            }
        });

        identAbbreviationBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(identAbbreviationBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identAbbreviationBox.text")); // NOI18N
        identAbbreviationBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identAbbreviationBoxActionPerformed(evt);
            }
        });

        identAccessionBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(identAccessionBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identAccessionBox.text")); // NOI18N
        identAccessionBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identAccessionBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(identCoverageBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identCoverageBox.text")); // NOI18N
        identCoverageBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identCoverageBoxActionPerformed(evt);
            }
        });

        identEcNumbersBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(identEcNumbersBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identEcNumbersBox.text")); // NOI18N
        identEcNumbersBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identEcNumbersBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(identPIValueBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identPIValueBox.text")); // NOI18N
        identPIValueBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identPIValueBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(identScoreBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identScoreBox.text")); // NOI18N
        identScoreBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identScoreBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(identWeightBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identWeightBox.text")); // NOI18N
        identWeightBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identWeightBoxActionPerformed(evt);
            }
        });

        headerBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(headerBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.headerBox.text")); // NOI18N
        headerBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headerBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(identGelNameBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identGelNameBox.text")); // NOI18N
        identGelNameBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identGelNameBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(outputSeparator)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fileNameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fileDirectoryTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileChooserButton))
                    .addComponent(filePathTextField)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(identMethodNameBox)
                                    .addComponent(identNameBox)
                                    .addComponent(identPlate96Box)
                                    .addComponent(identPlate384Box)
                                    .addComponent(identGelNameBox)
                                    .addComponent(identAbbreviationBox)
                                    .addComponent(identAccessionBox)
                                    .addComponent(identCoverageBox)
                                    .addComponent(identEcNumbersBox)
                                    .addComponent(identPIValueBox)
                                    .addComponent(identScoreBox)
                                    .addComponent(identWeightBox)))
                            .addComponent(headerBox)
                            .addComponent(spotGroupIDBox)
                            .addComponent(identificationBox)
                            .addComponent(spotGroupLabelBox)
                            .addComponent(fileNameLabel)
                            .addComponent(fileDirectoryLabel)
                            .addComponent(filePathLabel)
                            .addComponent(methodsLabel)
                            .addComponent(outputHeadingLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fileDirectoryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileChooserButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filePathLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filePathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(outputSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(methodsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(outputHeadingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spotGroupIDBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spotGroupLabelBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identificationBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identMethodNameBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identNameBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identPlate96Box)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identPlate384Box)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identGelNameBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identAbbreviationBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identAccessionBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identEcNumbersBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identCoverageBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identPIValueBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identScoreBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identWeightBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void identMethodNameBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identMethodNameBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENT_METHOD_NAME, !identMethodNameBox.isSelected(), identMethodNameBox.isSelected());
    }//GEN-LAST:event_identMethodNameBoxActionPerformed

    private void identificationBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identificationBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENTIFICATION, !identificationBox.isSelected(), identificationBox.isSelected());
        identMethodNameBox.setEnabled(identificationBox.isSelected());
        identAbbreviationBox.setEnabled(identificationBox.isSelected());
        identAccessionBox.setEnabled(identificationBox.isSelected());
        identCoverageBox.setEnabled(identificationBox.isSelected());
        identEcNumbersBox.setEnabled(identificationBox.isSelected());
        identMethodNameBox.setEnabled(identificationBox.isSelected());
        identNameBox.setEnabled(identificationBox.isSelected());
        identPIValueBox.setEnabled(identificationBox.isSelected());
        identPlate384Box.setEnabled(identificationBox.isSelected());
        identPlate96Box.setEnabled(identificationBox.isSelected());
        identScoreBox.setEnabled(identificationBox.isSelected());
        identWeightBox.setEnabled(identificationBox.isSelected());
        identGelNameBox.setEnabled(identificationBox.isSelected());
    }//GEN-LAST:event_identificationBoxActionPerformed

    private void fileChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserButtonActionPerformed
        JFileChooser jfc = new JFileChooser(directory);
        jfc.setDialogTitle("Choose Directory for Spotgroup Export");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setAcceptAllFileFilterUsed(false);
        int status = jfc.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            File oldFile = directory;
            directory = jfc.getSelectedFile();
            fileDirectoryTextField.setText(directory.getAbsolutePath());
            filePathTextField.setText(directory.getAbsolutePath() + File.separator + fileNameTextField.getText() + ".csv");
            firePropertyChange(PROPERTY_DIRECTORY, oldFile, directory);
        }
}//GEN-LAST:event_fileChooserButtonActionPerformed

    private void fileNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileNameTextFieldActionPerformed
        filePathTextField.setText(directory.getAbsolutePath() + File.separator + fileNameTextField.getText() + ".csv");
        firePropertyChange(PROPERTY_FILENAME, null, fileNameTextField.getText());
    }//GEN-LAST:event_fileNameTextFieldActionPerformed

    private void spotGroupLabelBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spotGroupLabelBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_GROUP_LABEL, !spotGroupLabelBox.isSelected(), spotGroupLabelBox.isSelected());
    }//GEN-LAST:event_spotGroupLabelBoxActionPerformed

    private void identNameBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identNameBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENT_NAME, !identNameBox.isSelected(), identNameBox.isSelected());
    }//GEN-LAST:event_identNameBoxActionPerformed

    private void identPlate96BoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identPlate96BoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENT_PLATE96_POSITION, !identPlate96Box.isSelected(), identPlate96Box.isSelected());
    }//GEN-LAST:event_identPlate96BoxActionPerformed

    private void identPlate384BoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identPlate384BoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENT_PLATE384_POSITION, !identPlate384Box.isSelected(), identPlate384Box.isSelected());
    }//GEN-LAST:event_identPlate384BoxActionPerformed

    private void spotGroupIDBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spotGroupIDBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_GROUP_NUMBER, !spotGroupIDBox.isSelected(), spotGroupIDBox.isSelected());
    }//GEN-LAST:event_spotGroupIDBoxActionPerformed

    private void identAbbreviationBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identAbbreviationBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENT_ABBREVIATION, !identAbbreviationBox.isSelected(), identAbbreviationBox.isSelected());
    }//GEN-LAST:event_identAbbreviationBoxActionPerformed

    private void identAccessionBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identAccessionBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENT_ACCESSION, !identAccessionBox.isSelected(), identAccessionBox.isSelected());
    }//GEN-LAST:event_identAccessionBoxActionPerformed

    private void identCoverageBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identCoverageBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENT_COVERAGE, !identCoverageBox.isSelected(), identCoverageBox.isSelected());
    }//GEN-LAST:event_identCoverageBoxActionPerformed

    private void identEcNumbersBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identEcNumbersBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENT_KEGG_NUMBERS, !identEcNumbersBox.isSelected(), identEcNumbersBox.isSelected());
    }//GEN-LAST:event_identEcNumbersBoxActionPerformed

    private void identPIValueBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identPIValueBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENT_PI_VALUE, !identPIValueBox.isSelected(), identPIValueBox.isSelected());
    }//GEN-LAST:event_identPIValueBoxActionPerformed

    private void identScoreBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identScoreBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENT_SCORE, !identScoreBox.isSelected(), identScoreBox.isSelected());
    }//GEN-LAST:event_identScoreBoxActionPerformed

    private void identWeightBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identWeightBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENT_WEIGHT, !identWeightBox.isSelected(), identWeightBox.isSelected());
    }//GEN-LAST:event_identWeightBoxActionPerformed

    private void headerBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_headerBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_HEADER, !headerBox.isSelected(), headerBox.isSelected());
    }//GEN-LAST:event_headerBoxActionPerformed

    private void identGelNameBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identGelNameBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENT_GEL_NAME, !identGelNameBox.isSelected(), identGelNameBox.isSelected());
    }//GEN-LAST:event_identGelNameBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fileChooserButton;
    private javax.swing.JLabel fileDirectoryLabel;
    private javax.swing.JTextField fileDirectoryTextField;
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JTextField fileNameTextField;
    private javax.swing.JLabel filePathLabel;
    private javax.swing.JTextField filePathTextField;
    private javax.swing.JCheckBox headerBox;
    private javax.swing.JCheckBox identAbbreviationBox;
    private javax.swing.JCheckBox identAccessionBox;
    private javax.swing.JCheckBox identCoverageBox;
    private javax.swing.JCheckBox identGelNameBox;
    private javax.swing.JCheckBox identEcNumbersBox;
    private javax.swing.JCheckBox identMethodNameBox;
    private javax.swing.JCheckBox identNameBox;
    private javax.swing.JCheckBox identPIValueBox;
    private javax.swing.JCheckBox identPlate384Box;
    private javax.swing.JCheckBox identPlate96Box;
    private javax.swing.JCheckBox identScoreBox;
    private javax.swing.JCheckBox identWeightBox;
    private javax.swing.JCheckBox identificationBox;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel methodsLabel;
    private javax.swing.JList methodsList;
    private javax.swing.JLabel outputHeadingLabel;
    private javax.swing.JSeparator outputSeparator;
    private javax.swing.JCheckBox spotGroupIDBox;
    private javax.swing.JCheckBox spotGroupLabelBox;
    // End of variables declaration//GEN-END:variables
}
