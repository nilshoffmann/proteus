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

    public IBioRepGelGroup getParent();

    public void setParent(IBioRepGelGroup parent);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public List<IGel> getGels();

    public void addGel(IGel gel);

    public void setGels(List<IGel> gels);
}
