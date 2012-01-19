package de.unibielefeld.gi.kotte.laborprogramm.spotTableExporter;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * Wizard Panel for the Spot Data Export Options.
 *
 * @author kotte
 */
public final class ExportOptionsVisualPanel1 extends JPanel {

    public static final String PROPERTY_PROJECT = "project";
    public static final String PROPERTY_USER_DEFINED_LABEL = "user defined label";
    public static final String PROPERTY_IDENTIFICATION_NAME = "identification name";
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

    public void setDirectory(File directory) {
        this.directory = directory;
        fileDirectoryTextField.setText(directory.getAbsolutePath());
        filePathTextField.setText(directory.getAbsolutePath() + File.separator + fileNameTextField.getText() + ".csv");
    }

    /** Creates new form ExportOptionsVisualPanel1 */
    public ExportOptionsVisualPanel1() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Ausgabeoptionen fuer Spot Data Export";
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(userDefinedLabelBox)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileNameLabel)
                        .addContainerGap(242, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                        .addGap(93, 93, 93))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileDirectoryLabel)
                        .addContainerGap(219, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileDirectoryTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileChooserButton)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(filePathLabel)
                        .addContainerGap(303, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(filePathTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(headingLabel)
                        .addContainerGap(161, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(identificationNameBox)
                        .addContainerGap(245, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userDefinedLabelBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(identificationNameBox)
                .addContainerGap(99, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void userDefinedLabelBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userDefinedLabelBoxActionPerformed
        firePropertyChange(PROPERTY_USER_DEFINED_LABEL, !userDefinedLabelBox.isSelected(), userDefinedLabelBox.isSelected());
    }//GEN-LAST:event_userDefinedLabelBoxActionPerformed

    private void identificationNameBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identificationNameBoxActionPerformed
        firePropertyChange(PROPERTY_IDENTIFICATION_NAME, !identificationNameBox.isSelected(), identificationNameBox.isSelected());
    }//GEN-LAST:event_identificationNameBoxActionPerformed

    private void fileChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserButtonActionPerformed
        JFileChooser jfc = new JFileChooser(directory);
        jfc.setDialogTitle("Verzeichnis für Spotgruppen Export auswaehlen");
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
    private javax.swing.JCheckBox userDefinedLabelBox;
    // End of variables declaration//GEN-END:variables
}
