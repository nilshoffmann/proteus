package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
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
public class ProjectChildNodeFactory extends ChildFactory<Object> {

    private IProteomicProject ipp;

    public ProjectChildNodeFactory(IProteomicProject ipp) {
        this.ipp = ipp;
    }

    @Override
    protected boolean createKeys(List<Object> toPopulate) {
        List<ILogicalGelGroup> ilggs = ipp.getGelGroups();
        for (ILogicalGelGroup illg : ilggs) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(illg);
            }
        }

        List<IPlate384> ips_384 = ipp.get384Plates();
        for (IPlate384 ip_384 : ips_384) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(ip_384);
            }
        }

        List<IPlate96> ips_96 = ipp.get96Plates();
        for (IPlate96 ip_96 : ips_96) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(ip_96);
            }
        }

        List<ISpotGroup> isgs = ipp.getSpotGroups();
        for (ISpotGroup isg : isgs) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(isg);
            }
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(Object key) {

        //TODO anpassen fuer z.B. IGel
        //--> custom AbstractNode mit eigener ChildFactory
        try {
            return new BeanNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return Node.EMPTY;
    }
}
