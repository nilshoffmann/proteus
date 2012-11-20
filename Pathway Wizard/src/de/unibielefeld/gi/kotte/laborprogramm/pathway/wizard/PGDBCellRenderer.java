package de.unibielefeld.gi.kotte.laborprogramm.pathway.wizard;

import de.unibielefeld.gi.omicsTools.biocyc.ptools.PGDB;
import de.unibielefeld.gi.omicsTools.biocyc.ptools.Strain;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
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
        setText(getSpeciesName(pgdb));
        return this;
    }

    static String getSpeciesName(PGDB pgdb) {
        StringBuilder builder = new StringBuilder((String) pgdb.getSpecies().getContent().iterator().next());
        Strain strain = pgdb.getStrain();
        if (strain != null) {
            builder.append(' ').append(strain.getContent());
        }
        return builder.toString();
    }
}