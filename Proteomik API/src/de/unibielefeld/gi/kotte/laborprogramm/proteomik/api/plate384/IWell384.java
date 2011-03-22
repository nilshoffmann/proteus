package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;

/**
 * A well on a 384 well microplate.
 *
 * @author kotte
 */
public interface IWell384 {

    public IPlate384 getParent();

    public char getRow();

    public int getColumn();

    public String getWellPosition();

    public Well384Status getStatus();

    public IWell96 getWell96();

    public void setParent(IPlate384 parent);

    public void setRow(char row);

    public void setColumn(int column);

    public void setStatus(Well384Status status);

    public void setWell96(IWell96 well96);
}
