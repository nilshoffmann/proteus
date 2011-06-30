package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.List;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author kotte
 */
class SpotGroupFolderChildNodeFactory extends ChildFactory<ISpotGroup> {

    private Collection<ISpotGroup> illg;

    public SpotGroupFolderChildNodeFactory(Collection<ISpotGroup> illg) {
        this.illg = illg;
    }

    @Override
    protected boolean createKeys(List<ISpotGroup> toPopulate) {
        for (ISpotGroup isg : illg) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(isg);
            }
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(ISpotGroup key) {

        //TODO anpassen
        try {
            return new BeanNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return Node.EMPTY;
    }
}
