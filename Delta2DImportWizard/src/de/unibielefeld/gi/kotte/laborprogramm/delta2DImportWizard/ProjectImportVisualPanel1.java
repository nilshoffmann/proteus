package de.unibielefeld.gi.kotte.laborprogramm.delta2DImportWizard;

import java.io.File;
import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import org.openide.filesystems.FileChooserBuilder;

/**
 * Visual Panel for the Delta2D project import.
 * 
 * @author kotte
 */
public final class ProjectImportVisualPanel1 extends JPanel {

    public static final String PROPERTY_PROJECT_DIRECTORY = "Delta2D project directory";
    public static final String PROPERTY_PROJECT_PARENT_DIRECTORY = "Proteus project parent directory";
    public static final String PROPERTY_PROJECT_NAME = "Proteus project name";
    public static final String PROPERTY_SELECTED_PROJECT_NAME = "selected Delta2D project";
    
    private File projectDirectory;
    private File projectParentDirectoryFile;

    /** Creates new form ProjectImportVisualPanel1 */
    public ProjectImportVisualPanel1() {
        initComponents();
        projectList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    public String getName() {
        return "Import Delta2D project";
    }

    public File getProjectDirectory() {
        return projectDirectory;
    }
    
    public String getProjectName() {
        return projectNameTextField.getText();
    }
    
    public File getProjectParentDirectoryFile() {
        return projectParentDirectoryFile;
    }

    protected void setProjectNamesList(List<String> names) {
        projectList.setListData(names.toArray());
        projectList.setSelectedIndex(0);
    }
    
    public String getSelectedProject() {
        return (String) projectList.getSelectedValue();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        projectLabel = new javax.swing.JLabel();
        projectTextField = new javax.swing.JTextField();
        projectButton = new javax.swing.JButton();
        listLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        projectList = new javax.swing.JList();
        projectParentDirectoryTextField = new javax.swing.JTextField();
        projectParentDirectoryButton = new javax.swing.JButton();
        projectDirectoryLabel = new javax.swing.JLabel();
        projectDirectoryTextField = new javax.swing.JTextField();
        projectNameTextField = new javax.swing.JTextField();
        projectParentDirectoryLabel = new javax.swing.JLabel();
        projectNameLabel = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(projectLabel, org.openide.util.NbBundle.getMessage(ProjectImportVisualPanel1.class, "ProjectImportVisualPanel1.projectLabel.text")); // NOI18N

        projectTextField.setText(org.openide.util.NbBundle.getMessage(ProjectImportVisualPanel1.class, "ProjectImportVisualPanel1.projectTextField.text")); // NOI18N
        projectTextField.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(projectButton, org.openide.util.NbBundle.getMessage(ProjectImportVisualPanel1.class, "ProjectImportVisualPanel1.projectButton.text")); // NOI18N
        projectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(listLabel, org.openide.util.NbBundle.getMessage(ProjectImportVisualPanel1.class, "ProjectImportVisualPanel1.listLabel.text")); // NOI18N

        jScrollPane1.setViewportView(projectList);

        projectParentDirectoryTextField.setText(org.openide.util.NbBundle.getMessage(ProjectImportVisualPanel1.class, "ProjectImportVisualPanel1.projectParentDirectoryTextField.text")); // NOI18N
        projectParentDirectoryTextField.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(projectParentDirectoryButton, org.openide.util.NbBundle.getMessage(ProjectImportVisualPanel1.class, "ProjectImportVisualPanel1.projectParentDirectoryButton.text")); // NOI18N
        projectParentDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectParentDirectoryButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(projectDirectoryLabel, org.openide.util.NbBundle.getMessage(ProjectImportVisualPanel1.class, "ProjectImportVisualPanel1.projectDirectoryLabel.text")); // NOI18N

        projectDirectoryTextField.setText(org.openide.util.NbBundle.getMessage(ProjectImportVisualPanel1.class, "ProjectImportVisualPanel1.projectDirectoryTextField.text")); // NOI18N
        projectDirectoryTextField.setEnabled(false);

        projectNameTextField.setText(org.openide.util.NbBundle.getMessage(ProjectImportVisualPanel1.class, "ProjectImportVisualPanel1.projectNameTextField.text")); // NOI18N
        projectNameTextField.setPreferredSize(new java.awt.Dimension(200, 20));

        org.openide.awt.Mnemonics.setLocalizedText(projectParentDirectoryLabel, org.openide.util.NbBundle.getMessage(ProjectImportVisualPanel1.class, "ProjectImportVisualPanel1.projectParentDirectoryLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(projectNameLabel, org.openide.util.NbBundle.getMessage(ProjectImportVisualPanel1.class, "ProjectImportVisualPanel1.projectNameLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(projectLabel)
                            .addComponent(listLabel)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(projectTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(projectButton)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(projectNameLabel)
                            .addComponent(projectParentDirectoryLabel)
                            .addComponent(projectDirectoryLabel)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(projectNameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                                    .addComponent(projectDirectoryTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                                    .addComponent(projectParentDirectoryTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(projectParentDirectoryButton)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(projectLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(projectButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(projectNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(projectNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(projectParentDirectoryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectParentDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(projectParentDirectoryButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(projectDirectoryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(projectDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void projectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectButtonActionPerformed
        FileChooserBuilder fcb = new FileChooserBuilder(getClass());
        fcb.setTitle("Choose project directory");
        fcb.setDirectoriesOnly(true);
        JFileChooser jfc = fcb.createFileChooser();
        int status = jfc.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File oldFile = projectDirectory;
            projectDirectory = jfc.getSelectedFile();
            projectTextField.setText(projectDirectory.getAbsolutePath());
            firePropertyChange(PROPERTY_PROJECT_DIRECTORY, oldFile, projectDirectory);
        }
	}//GEN-LAST:event_projectButtonActionPerformed

    private void projectParentDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectParentDirectoryButtonActionPerformed

        FileChooserBuilder fcb = new FileChooserBuilder(getClass());         fcb.setTitle("Choose project directory");         fcb.setDirectoriesOnly(true);         JFileChooser jfc = fcb.createFileChooser();         int status = jfc.showOpenDialog(this);         if (status == JFileChooser.APPROVE_OPTION) {             File oldFile = projectParentDirectoryFile;             projectParentDirectoryFile = jfc.getSelectedFile();             projectParentDirectoryTextField.setText(projectParentDirectoryFile.getAbsolutePath());             projectDirectoryTextField.setText(projectParentDirectoryFile.getAbsolutePath() + File.separator + projectNameTextField.getText());             firePropertyChange(PROPERTY_PROJECT_PARENT_DIRECTORY, oldFile,                     projectParentDirectoryFile);         }     }//GEN-LAST:event_projectParentDirectoryButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel listLabel;
    private javax.swing.JButton projectButton;
    private javax.swing.JLabel projectDirectoryLabel;
    private javax.swing.JTextField projectDirectoryTextField;
    private javax.swing.JLabel projectLabel;
    private javax.swing.JList projectList;
    private javax.swing.JLabel projectNameLabel;
    private javax.swing.JTextField projectNameTextField;
    private javax.swing.JButton projectParentDirectoryButton;
    private javax.swing.JLabel projectParentDirectoryLabel;
    private javax.swing.JTextField projectParentDirectoryTextField;
    private javax.swing.JTextField projectTextField;
    // End of variables declaration//GEN-END:variables
}
