package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

/**
 * A well on a microplate for MS identification experiments.
 *
 * @author kotte
 */
public interface IWell {

    public IPlate getParent();

    public char getRow();

    public int getColumn();

    public String getWellPosition();

    public IdentificationStatus getStatus();

    public ISpot getSpot();

    public void setParent(IPlate parent);

    public void setRow(char row);

    public void setColumn(int column);

    public void setStatus(IdentificationStatus status);

    public void setSpot(ISpot spot);
}
