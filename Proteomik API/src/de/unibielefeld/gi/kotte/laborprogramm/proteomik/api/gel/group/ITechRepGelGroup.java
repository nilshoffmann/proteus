package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.util.List;

/**
 * A group containing technical replications of 2D gels.
 *
 * @author kotte
 */
public interface ITechRepGelGroup extends IPropertyChangeSource {

    public static final String PROPERTY_PARENT = "parent";

    public static final String PROPERTY_NAME = "name";

    public static final String PROPERTY_DESCRIPTION = "description";

    public static final String PROPERTY_GELS = "gels";

    public IBioRepGelGroup getParent();

    public void setParent(IBioRepGelGroup parent);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public List<IGel> getGels();

    public void addGel(IGel gel);

    public void setGels(List<IGel> gels);

    public String toFullyRecursiveString();
}
