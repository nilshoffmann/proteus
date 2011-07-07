package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import java.util.List;

/**
 * A well on a 96 well microplate.
 *
 * @author kotte
 */
public interface IWell96 extends IPropertyChangeSource {

    public IPlate96 getParent();

    public char getRow();

    public int getColumn();

    public String getWellPosition();

    public Well96Status getStatus();

    public ISpot getSpot();

    public List<IWell384> get384Wells();

    public void set384Wells(List<IWell384> wells);

    public void add384Well(IWell384 well);

    public void setStatus(Well96Status status);

    public void setSpot(ISpot spot);

    public void setParent(IPlate96 parent);

    public void setRow(char row);

    public void setColumn(int column);
}
