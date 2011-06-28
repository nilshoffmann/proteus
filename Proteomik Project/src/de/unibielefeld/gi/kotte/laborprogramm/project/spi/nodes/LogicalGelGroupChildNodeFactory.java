package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.IBioRepGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author kotte
 */
class LogicalGelGroupChildNodeFactory  extends ChildFactory<Object> {

    private ILogicalGelGroup illg;

    public LogicalGelGroupChildNodeFactory(ILogicalGelGroup illg) {
        this.illg = illg;
    }

    @Override
    protected boolean createKeys(List<Object> toPopulate) {
        List<IBioRepGelGroup> ibrggs = illg.getGelGroups();
        for (IBioRepGelGroup ibrgg : ibrggs) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(ibrgg);
            }
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(Object key) {

        //TODO anpassen
        try {
            return new BeanNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return Node.EMPTY;
    }

}
