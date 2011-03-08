package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import java.util.List;

/**
 * A 2D protein gel containing gel spots.
 *
 * @author kotte
 */
public interface IGel {

    public IProject getParent();
    public void setParent(IProject parent);
    public String getDescription();
    public void setDescription(String description);
    public String getFilename();
    public void setFilename(String filename);
    public String getName();
    public void setName(String name);
    public List<ISpot> getSpots();
    public void setSpots(List<ISpot> spots);
}
