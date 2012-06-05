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
    public static final String PROPERTY_SHOW_METHOD_NAME = "show method name";
    public static final String PROPERTY_SHOW_IDENTIFICATION = "show identification";
    public static final String PROPERTY_SHOW_GROUP_LABEL = "show group label";
    public static final String PROPERTY_FILENAME = "filename";
    public static final String PROPERTY_DIRECTORY = "directory";

    private File directory;

    public boolean isShowMethodName() {
        return methodNameBox.isSelected();
    }
    
    public boolean isShowGroupLabel() {
        return groupLabelBox.isSelected();
    }

    public boolean isShowIdentification() {
        return identificationBox.isSelected();
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
        methodNameBox = new javax.swing.JCheckBox();
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
        groupLabelBox = new javax.swing.JCheckBox();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        org.openide.awt.Mnemonics.setLocalizedText(outputHeadingLabel, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.outputHeadingLabel.text")); // NOI18N

        methodNameBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(methodNameBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.methodNameBox.text")); // NOI18N
        methodNameBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                methodNameBoxActionPerformed(evt);
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

        groupLabelBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(groupLabelBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.groupLabelBox.text")); // NOI18N
        groupLabelBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupLabelBoxActionPerformed(evt);
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
                                .addComponent(methodNameBox))
                            .addComponent(identificationBox)
                            .addComponent(groupLabelBox)
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
                .addComponent(groupLabelBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identificationBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(methodNameBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void methodNameBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_methodNameBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_METHOD_NAME, !methodNameBox.isSelected(), methodNameBox.isSelected());
    }//GEN-LAST:event_methodNameBoxActionPerformed

    private void identificationBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identificationBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENTIFICATION, !identificationBox.isSelected(), identificationBox.isSelected());
        methodNameBox.setEnabled(identificationBox.isSelected());
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

    private void groupLabelBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupLabelBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_GROUP_LABEL, !groupLabelBox.isSelected(), groupLabelBox.isSelected());
    }//GEN-LAST:event_groupLabelBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fileChooserButton;
    private javax.swing.JLabel fileDirectoryLabel;
    private javax.swing.JTextField fileDirectoryTextField;
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JTextField fileNameTextField;
    private javax.swing.JLabel filePathLabel;
    private javax.swing.JTextField filePathTextField;
    private javax.swing.JCheckBox groupLabelBox;
    private javax.swing.JCheckBox identificationBox;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JCheckBox methodNameBox;
    private javax.swing.JLabel methodsLabel;
    private javax.swing.JList methodsList;
    private javax.swing.JLabel outputHeadingLabel;
    private javax.swing.JSeparator outputSeparator;
    // End of variables declaration//GEN-END:variables
}
