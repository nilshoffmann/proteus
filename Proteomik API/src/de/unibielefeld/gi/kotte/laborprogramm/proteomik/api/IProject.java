package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import java.util.List;

/**
 * Proteomic porject containing 2D gels and microplates.
 *
 * @author kotte
 */
public interface IProject {

    public List<IGel> getGels();

    public void setGels(List<IGel> gels);

    public String getName();

    public void setName(String name);

    public String getOwner();

    public void setOwner(String owner);

    public String getDescription();

    public void setDescription(String description);

    public List<IPlate> getPlates();

    public void setPlates(List<IPlate> plates);
}
