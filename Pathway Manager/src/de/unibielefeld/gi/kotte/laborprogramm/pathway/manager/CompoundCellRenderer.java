package de.unibielefeld.gi.kotte.laborprogramm.pathway.manager;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.NameTools;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Compound;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * ListCellRenderer for Lists of Compounds.
 *
 * @author kotte
 */
public class CompoundCellRenderer extends DefaultListCellRenderer implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Compound cmp = (Compound) value;
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setText(NameTools.getCompoundName(cmp));
        setToolTipText(PathwayOverviewTopComponent.getToolTip(cmp.getAbbrevNameOrAppearsInLeftSideOfOrAppearsInRightSideOf()));
        return this;
    }
}
