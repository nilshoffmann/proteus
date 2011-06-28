package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ITechRepGelGroup;
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
class TechRepGelGroupChildNodeFactory  extends ChildFactory<Object> {

    private ITechRepGelGroup ibrgg;

    public TechRepGelGroupChildNodeFactory(ITechRepGelGroup ibrgg) {
        this.ibrgg = ibrgg;
    }

    @Override
    protected boolean createKeys(List<Object> toPopulate) {
        List<IGel> igs = ibrgg.getGels();
        for (IGel ig : igs) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(ig);
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
