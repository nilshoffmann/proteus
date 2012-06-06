/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.proteomik.api;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IUniqueObject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.Plate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.Plate96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.Project;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.Well384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.Well96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel.*;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.IdentificationMethod;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.WellIdentification;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.beans.Introspector;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.File;
import net.sf.maltcms.io.xml.serialization.api.CustomPersistenceDelegate;
import net.sf.maltcms.io.xml.serialization.api.FilePersistenceDelegate;
import net.sf.maltcms.io.xml.serialization.api.GeneralPathPersistenceDelegate;
import net.sf.maltcms.io.xml.serialization.api.IPersistenceDelegateRegistration;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author nilshoffmann
 */
@ServiceProvider(service=IPersistenceDelegateRegistration.class)
public class XmlPersistenceRegistration implements IPersistenceDelegateRegistration {

    @Override
    public void registerPersistenceDelegates(XMLEncoder encoder) {
        encoder.setPersistenceDelegate(IProject.class, getPreferredDelegate(IProject.class));
        encoder.setPersistenceDelegate(IPropertyChangeSource.class, getPreferredDelegate(IPropertyChangeSource.class));
        encoder.setPersistenceDelegate(IUniqueObject.class, getPreferredDelegate(IUniqueObject.class));
        encoder.setPersistenceDelegate(IGel.class, getPreferredDelegate(IGel.class));
        encoder.setPersistenceDelegate(ISpot.class, getPreferredDelegate(ISpot.class));
        encoder.setPersistenceDelegate(SpotStatus.class, getPreferredDelegate(SpotStatus.class));
        encoder.setPersistenceDelegate(IBioRepGelGroup.class, getPreferredDelegate(IBioRepGelGroup.class));
        encoder.setPersistenceDelegate(ILogicalGelGroup.class, getPreferredDelegate(ILogicalGelGroup.class));
        encoder.setPersistenceDelegate(ITechRepGelGroup.class, getPreferredDelegate(ITechRepGelGroup.class));
        encoder.setPersistenceDelegate(ISpotGroup.class, getPreferredDelegate(ISpotGroup.class));
        encoder.setPersistenceDelegate(IIdentification.class, getPreferredDelegate(IIdentification.class));
        encoder.setPersistenceDelegate(IIdentificationMethod.class, getPreferredDelegate(IIdentificationMethod.class));
        encoder.setPersistenceDelegate(IWellIdentification.class, getPreferredDelegate(IWellIdentification.class));
        encoder.setPersistenceDelegate(IPlate384.class, getPreferredDelegate(IPlate384.class));
        encoder.setPersistenceDelegate(IWell384.class, getPreferredDelegate(IWell384.class));
        encoder.setPersistenceDelegate(Well384Status.class, getPreferredDelegate(Well384Status.class));
        encoder.setPersistenceDelegate(IPlate96.class, getPreferredDelegate(IPlate96.class));
        encoder.setPersistenceDelegate(IWell96.class, getPreferredDelegate(IWell96.class));
        encoder.setPersistenceDelegate(Well96Status.class, getPreferredDelegate(Well96Status.class));
        encoder.setPersistenceDelegate(Project.class, getPreferredDelegate(Project.class));
        encoder.setPersistenceDelegate(Gel.class, getPreferredDelegate(Gel.class));
        encoder.setPersistenceDelegate(Spot.class, getPreferredDelegate(Spot.class));
        encoder.setPersistenceDelegate(BioRepGelGroup.class, getPreferredDelegate(BioRepGelGroup.class));
        encoder.setPersistenceDelegate(LogicalGelGroup.class, getPreferredDelegate(LogicalGelGroup.class));
        encoder.setPersistenceDelegate(TechRepGelGroup.class, getPreferredDelegate(TechRepGelGroup.class));
        encoder.setPersistenceDelegate(SpotGroup.class, getPreferredDelegate(SpotGroup.class));
        encoder.setPersistenceDelegate(Identification.class, getPreferredDelegate(Identification.class));
        encoder.setPersistenceDelegate(IdentificationMethod.class, getPreferredDelegate(IdentificationMethod.class));
        encoder.setPersistenceDelegate(WellIdentification.class, getPreferredDelegate(WellIdentification.class));
        encoder.setPersistenceDelegate(Plate384.class, getPreferredDelegate(Plate384.class));
        encoder.setPersistenceDelegate(Well384.class, getPreferredDelegate(Well384.class));
        encoder.setPersistenceDelegate(Plate96.class, getPreferredDelegate(Plate96.class));
        encoder.setPersistenceDelegate(Well96.class, getPreferredDelegate(Well96.class));
        encoder.setPersistenceDelegate(File.class, new FilePersistenceDelegate());
        encoder.setPersistenceDelegate(Shape.class, getPreferredDelegate(Shape.class));
        encoder.setPersistenceDelegate(GeneralPath.class, new GeneralPathPersistenceDelegate());
    }

    @Override
        public PersistenceDelegate getPreferredDelegate(Class<?> clazz, String...constructorArgumentProperties) {
        return new CustomPersistenceDelegate(Introspector.IGNORE_ALL_BEANINFO);
    }
}
