package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;

/**
 * A well on a microplate for MS identification experiments.
 *
 * @author kotte
 */
public interface IWell96 {

    public IPlate96 getParent();

    public char getRow();

    public int getColumn();

    public String getWellPosition();

    public Well96Status getStatus();

    public ISpot getSpot();

    public void setStatus(Well96Status status);

    public void setSpot(ISpot spot);

    public void setParent(IPlate96 parent);

    public void setRow(char row);

    public void setColumn(int column);
}
