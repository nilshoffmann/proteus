package de.unibielefeld.gi.kotte.laborprogramm.pathways;

import de.unibielefeld.gi.omicsTools.biocyc.ptools.Pathway;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author kotte
 */
public class PathwayCellRenderer extends DefaultListCellRenderer implements ListCellRenderer {

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
            setText(PathwayOverviewTopComponent.getPathwayName(pw));
            setToolTipText(PathwayOverviewTopComponent.getToolTip(pw.getCitationOrCommentOrCommonName()));
            return this;
        }
    }
