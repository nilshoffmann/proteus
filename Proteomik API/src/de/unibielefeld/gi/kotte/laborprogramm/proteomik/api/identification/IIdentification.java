/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;

/**
 *
 * @author hoffmann
 */
public interface IIdentification extends IPropertyChangeSource {

    public static final String PROPERTY_METHOD = "method";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_DESCRIPTION = "description";

    public String getMethod();

    public void setMethod(String method);

    public String getName();

    public void setName(String name);

    public String getId();

    public void setId(String id);

    public String getDescription();

    public void setDescription(String description);
}
