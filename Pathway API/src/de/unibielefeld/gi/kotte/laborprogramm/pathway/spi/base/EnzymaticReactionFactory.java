package de.unibielefeld.gi.kotte.laborprogramm.pathway.spi.base;

import de.unibielefeld.gi.kotte.laborprogramm.pathway.api.base.IEnzymaticReactionFactory;

/**
 * Factory for creating reaction annotations.
 *
 * @author kotte
 */
@org.openide.util.lookup.ServiceProvider(service = IEnzymaticReactionFactory.class)
public class EnzymaticReactionFactory implements IEnzymaticReactionFactory {

    @Override
    public EnzymaticReaction createReactionAnnotation() {
        EnzymaticReaction result = new EnzymaticReaction();
        return result;
    }
}
