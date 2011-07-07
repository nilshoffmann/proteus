package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author kotte
 */
public class ProjectChildNodeFactory extends ChildFactory<Object> implements PropertyChangeListener {

    private IProteomicProject ipp;

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        refresh(true);
    }

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
                List<ILogicalGelGroup> illgs = ipp.getGelGroups();
                nodes = new Node[illgs.size()];
                i = 0;
                for (ILogicalGelGroup ilgg : illgs) {
                    ilgg.addPropertyChangeListener(this);
                    ilgg.addPropertyChangeListener(ipp);
                    nodes[i++] = new LogicalGelGroupNode(ilgg);
                }
                return nodes;
            case PLATES384: {
                List<IPlate384> plates384 = ipp.get384Plates();
                nodes = new Node[plates384.size()];
                i = 0;
                for (IPlate384 plate : plates384) {
                    plate.addPropertyChangeListener(this);
                    try {
                        nodes[i++] = new BeanNode(plate);
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
                for (IPlate96 plate : plates96) {
                    plate.addPropertyChangeListener(this);
                    try {
                        nodes[i++] = new BeanNode(plate);
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
}
