package de.unibielefeld.gi.kotte.laborprogramm.pathway.spi.base;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base.ICompoundFactory;

/**
 * Factory for creating compound annotations.
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = ICompoundFactory.class)
public class CompoundFactory implements ICompoundFactory {

    @Override
    public Compound createCompoundAnnotation() {
        Compound result = new Compound();
        return result;
    }
}
