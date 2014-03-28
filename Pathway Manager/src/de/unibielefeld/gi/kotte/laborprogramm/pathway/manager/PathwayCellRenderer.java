package de.unibielefeld.gi.kotte.laborprogramm.pathway.manager;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.NameTools;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * ListCellRenderer for Lists of Pathways.
 *
 * @author Konstantin Otte
 */
public class PathwayCellRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Pathway pw = (Pathway) value;
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setText(NameTools.getPathwayName(pw));
        setToolTipText(PathwayOverviewTopComponent.getToolTip(pw.getCitationOrCommentOrCommonName()));
        return this;
    }
}
