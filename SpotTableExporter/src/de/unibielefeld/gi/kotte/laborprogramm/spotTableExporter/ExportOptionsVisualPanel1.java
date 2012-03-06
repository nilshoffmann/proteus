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
    public static final String PROPERTY_SHOW_IDENTIFICATION_NAME = "show identification name";
    public static final String PROPERTY_FILENAME = "filename";
    public static final String PROPERTY_DIRECTORY = "directory";

    private File directory;

    public boolean isUserDefinedLabel() {
        return userDefinedLabelBox.isSelected();
    }

    public boolean isIdentificationName() {
        return identificationNameBox.isSelected();
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
        return "Output options for Spot Data Export";
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
        headingLabel = new javax.swing.JLabel();
        userDefinedLabelBox = new javax.swing.JCheckBox();
        identificationNameBox = new javax.swing.JCheckBox();
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

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        org.openide.awt.Mnemonics.setLocalizedText(headingLabel, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.headingLabel.text")); // NOI18N

        userDefinedLabelBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(userDefinedLabelBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.userDefinedLabelBox.text")); // NOI18N
        userDefinedLabelBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userDefinedLabelBoxActionPerformed(evt);
            }
        });

        identificationNameBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(identificationNameBox, org.openide.util.NbBundle.getMessage(ExportOptionsVisualPanel1.class, "ExportOptionsVisualPanel1.identificationNameBox.text")); // NOI18N
        identificationNameBox.setEnabled(false);
        identificationNameBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identificationNameBoxActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fileNameLabel)
                    .addComponent(fileDirectoryLabel)
                    .addComponent(filePathLabel)
                    .addComponent(headingLabel)
                    .addComponent(methodsLabel)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                    .addComponent(filePathTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fileNameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                            .addComponent(fileDirectoryTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileChooserButton))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(userDefinedLabelBox)
                        .addComponent(identificationNameBox)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filePathLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filePathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(headingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(identificationNameBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userDefinedLabelBox)
                .addGap(13, 13, 13)
                .addComponent(methodsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void userDefinedLabelBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userDefinedLabelBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_METHOD_NAME, !userDefinedLabelBox.isSelected(), userDefinedLabelBox.isSelected());
    }//GEN-LAST:event_userDefinedLabelBoxActionPerformed

    private void identificationNameBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identificationNameBoxActionPerformed
        firePropertyChange(PROPERTY_SHOW_IDENTIFICATION_NAME, !identificationNameBox.isSelected(), identificationNameBox.isSelected());
    }//GEN-LAST:event_identificationNameBoxActionPerformed

    private void fileChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserButtonActionPerformed
        JFileChooser jfc = new JFileChooser(directory);
        jfc.setDialogTitle("Choose directory for spotgroup export");
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fileChooserButton;
    private javax.swing.JLabel fileDirectoryLabel;
    private javax.swing.JTextField fileDirectoryTextField;
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JTextField fileNameTextField;
    private javax.swing.JLabel filePathLabel;
    private javax.swing.JTextField filePathTextField;
    private javax.swing.JLabel headingLabel;
    private javax.swing.JCheckBox identificationNameBox;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel methodsLabel;
    private javax.swing.JList methodsList;
    private javax.swing.JCheckBox userDefinedLabelBox;
    // End of variables declaration//GEN-END:variables
}
