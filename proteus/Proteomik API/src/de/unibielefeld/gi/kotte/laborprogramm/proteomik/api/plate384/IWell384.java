package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IUniqueObject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;

/**
 * A well on a 384 well microplate.
 *
 * @author Konstantin Otte
 */
public interface IWell384 extends IPropertyChangeSource, IUniqueObject {

    public static final String PROPERTY_PARENT = "parent";

    public static final String PROPERTY_STATUS = "status";

    public static final String PROPERTY_IDENTIFICATION = "identification";

    public static final String PROPERTY_WELL96 = "plate96 well";

    public static final String PROPERTY_POSITION = "position";

    public IPlate384 getParent();

    public char getRow();

    public int getColumn();

    public String getWellPosition();

    public Well384Status getStatus();

    public IWell96 getWell96();

    public IWellIdentification getIdentification();

    public void setIdentification(IWellIdentification identification);

    public void setParent(IPlate384 parent);

    public void setRow(char row);

    public void setColumn(int column);

    public void setStatus(Well384Status status);

    public void setWell96(IWell96 well96);

    public String toFullyRecursiveString();
}