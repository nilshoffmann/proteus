package de.unibielefeld.gi.kotte.laborprogramm.pathway.manager;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.utils.NameTools;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.PGDB;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * ListCellRenderer for Lists of PGDBs.
 *
 * @author kotte
 */
public class PGDBCellRenderer extends DefaultListCellRenderer implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        PGDB pgdb = (PGDB) value;
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setText(NameTools.getSpeciesName(pgdb));
        return this;
    }
}
