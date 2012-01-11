package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IUniqueObject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;

/**
 * A spot on a 2D gel.
 *
 * @author kotte
 */
public interface ISpot extends IPropertyChangeSource, IUniqueObject {

    public static final String PROPERTY_LABEL = "label";

    public static final String PROPERTY_NUMBER = "spot number";

    public static final String PROPERTY_GROUP = "spot group";

    public static final String PROPERTY_POS_X = "X coordinate";

    public static final String PROPERTY_POS_Y = "Y coordinate";

    public static final String PROPERTY_STATUS = "spot status";

    public static final String PROPERTY_WELL = "plate96 well";

    public static final String PROPERTY_GEL = "gel";

    public String getLabel();

    public int getNumber();

    public IGel getGel();

    public ISpotGroup getGroup();

    public double getPosX();

    public double getPosY();

    public SpotStatus getStatus();

    public IWell96 getWell();

    public void setWell(IWell96 well);

    public void setLabel(String label);

    public void setNumber(int number);

    public void setGel(IGel gel);

    public void setGroup(ISpotGroup group);

    public void setPosX(double posX);

    public void setPosY(double posY);

    public void setStatus(SpotStatus status);
}
