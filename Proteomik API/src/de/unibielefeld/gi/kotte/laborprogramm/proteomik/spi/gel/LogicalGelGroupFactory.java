package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.gel;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroupFactory;

/**
 * Factory for creating new groups of logically different gel groups.
 *
 * @author Konstantin Otte
 */
@org.openide.util.lookup.ServiceProvider(service = ILogicalGelGroupFactory.class)
public class LogicalGelGroupFactory implements ILogicalGelGroupFactory {

    @Override
    public ILogicalGelGroup createLogicalGelGroup() {
        ILogicalGelGroup result = new LogicalGelGroup();
        return result;
    }
}
