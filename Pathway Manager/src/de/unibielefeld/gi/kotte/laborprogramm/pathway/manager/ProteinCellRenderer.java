package de.unibielefeld.gi.kotte.laborprogramm.pathway.manager;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.NameTools;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Protein;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * ListCellRenderer for Lists of Proteins.
 *
 * @author kotte
 */
public class ProteinCellRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Protein prot = (Protein) value;
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setText(NameTools.getProteinName(prot));
        setToolTipText(PathwayOverviewTopComponent.getToolTip(prot.getCatalyzes().getEnzymaticReaction().iterator().next().getCitationOrCofactorOrComment()));
        return this;
    }
}
