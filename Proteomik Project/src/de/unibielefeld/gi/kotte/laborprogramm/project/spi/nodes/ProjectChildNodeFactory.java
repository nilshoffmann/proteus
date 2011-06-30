package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
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

    private enum NodeGroup {

        GELGROUPS, PLATES384, PLATES96, SPOTGROUPS
    };

    public ProjectChildNodeFactory(IProteomicProject ipp) {
        this.ipp = ipp;
    }

    @Override
    protected boolean createKeys(List<Object> toPopulate) {
        for (NodeGroup s : NodeGroup.values()) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(s);
            }
        }
        return true;
    }

    @Override
    protected Node[] createNodesForKey(Object key) {
        NodeGroup keyVal = (NodeGroup) key;
        Node[] nodes = null;
        int i = 0;
        switch (keyVal) {
            case GELGROUPS:
                List<ILogicalGelGroup> gels = ipp.getGelGroups();
                nodes = new Node[gels.size()];
                i = 0;
                for (ILogicalGelGroup ilgg : gels) {
                    nodes[i++] = new LogicalGelGroupNode(ilgg);
                }
                return nodes;
            case PLATES384: {
                List<IPlate384> plates384 = ipp.get384Plates();
                nodes = new Node[plates384.size()];
                i = 0;
                for (IPlate384 ilgg : plates384) {
                    try {
                        nodes[i++] = new BeanNode(ilgg);
                    } catch (IntrospectionException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                return nodes;
            }
            case PLATES96:
                List<IPlate96> plates96 = ipp.get96Plates();
                nodes = new Node[plates96.size()];
                i = 0;
                for (IPlate96 ilgg : plates96) {
                    try {
                        nodes[i++] = new BeanNode(ilgg);
                    } catch (IntrospectionException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                return nodes;
            case SPOTGROUPS:
                return new Node[]{new SpotGroupFolderNode(ipp.getSpotGroups())};
        }
        return new Node[]{Node.EMPTY};
    }
//    @Override
//    protected Node createNodeForKey(Object key) {
//        NodeGroup keyVal = (NodeGroup)key;
//        switch(keyVal) {
//            case GELGROUPS:
//                break;
//            case PLATES384:
//                break;
//            case PLATES96:
//                break;
//            case SPOTGROUPS:
//                return new SpotGroupFolderNode(ipp.getSpotGroups());
//        }
//
//
//        if (key instanceof ILogicalGelGroup) {
//            return new LogicalGelGroupNode((ILogicalGelGroup) key);
////        } else if (key instanceof IPlate384) {
////            //return new Plate384Node((IPlate384)key);
////        } else if (key instanceof IPlate96) {
////
////        } else if (key instanceof ISpotGroup) {
//
//        } else {
//            //TODO anpassen fuer z.B. IGel
//            //--> custom AbstractNode mit eigener ChildFactory
//            try {
//                return new BeanNode(key);
//            } catch (IntrospectionException ex) {
//                Exceptions.printStackTrace(ex);
//            }
//        }
//        return Node.EMPTY;
//    }
}
