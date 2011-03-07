package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

/**
 * A spot on a 2D gel.
 *
 * @author kotte
 */
public interface ISpot {
    
    public String getLabel();
    public boolean isLabelDisplayed();
    public IGel getParent();
    public int getPosX();
    public int getPosY();
    public SpotStatus getStatus();
    public void setLabel(String label);
    public void setLabelDisplayed(boolean labelDisplayed);
    public void setParent(IGel parent);
    public void setPosX(int posX);
    public void setPosY(int posY);
    public void setStatus(SpotStatus status);

}
