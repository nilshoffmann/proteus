package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes.ProjectChildNodeFactory.NodeGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;

/**
 *
 * @author kotte
 */
public class ProjectChildNodeFactory extends ChildFactory<NodeGroup> implements PropertyChangeListener {

    private IProteomicProject ipp;

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        System.out.println("ProjectChildNodeFactory: Received property change event: "+pce);
        //ipp.propertyChange(pce);
        refresh(true);
    }

    public enum NodeGroup {

        GELGROUPS, PLATES384, PLATES96, SPOTGROUPS
    };

    public ProjectChildNodeFactory(IProteomicProject ipp) {
        this.ipp = ipp;
        this.ipp.addPropertyChangeListener(this);
    }

    @Override
    protected boolean createKeys(List<NodeGroup> toPopulate) {
        for (NodeGroup s : NodeGroup.values()) {
            if (Thread.interrupted()) {
                return false;
            } else {
                toPopulate.add(s);
            }
        }
        return true;
    }

    @Override
    protected Node[] createNodesForKey(NodeGroup key) {
        NodeGroup keyVal = key;
        Node[] nodes = null;
        int i = 0;
        switch (keyVal) {
            case GELGROUPS:
                List<ILogicalGelGroup> illgs = ipp.getLookup().lookup(IProject.class).getGelGroups();
                nodes = new Node[illgs.size()];
                i = 0;
                for (ILogicalGelGroup ilgg : illgs) {
                    ilgg.addPropertyChangeListener(this);
                    ilgg.addPropertyChangeListener(ipp);
                    nodes[i++] = new LogicalGelGroupNode(ilgg,ipp.getLookup());
                }
                return nodes;
            case PLATES384: {
                List<IPlate384> plates384 = ipp.getLookup().lookup(IProject.class).get384Plates();
                nodes = new Node[plates384.size()];
                i = 0;
                for (IPlate384 plate : plates384) {
                    plate.addPropertyChangeListener(this);
                    nodes[i++] = new Plate384Node(plate,ipp.getLookup());
                }
                return nodes;
            }
            case PLATES96:
                List<IPlate96> plates96 = ipp.getLookup().lookup(IProject.class).get96Plates();
                nodes = new Node[plates96.size()];
                i = 0;
                for (IPlate96 plate : plates96) {
                    System.out.println("Adding plate96: "+plate);
                    plate.addPropertyChangeListener(this);
                        Plate96Node plateNode = new Plate96Node(plate, ipp.getLookup());
                        plateNode.addPropertyChangeListener(this);
                        nodes[i++] = plateNode;
                }
                return nodes;
            case SPOTGROUPS:
                return new Node[]{new SpotGroupFolderNode(ipp.getLookup().lookup(IProject.class).getSpotGroups(),ipp.getLookup())};
        }
        return new Node[]{Node.EMPTY};
    }
}
