package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IPlate96;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.WeakListeners;

/**
 * Factory for creating various nodes as Children of a ProjectNode.
 *
 * @author kotte
 */
public class ProjectChildNodeFactory extends ChildFactory<Object> implements PropertyChangeListener {

    private IProteomicProject ipp;

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        System.out.println("ProjectChildNodeFactory: Received property change event: " + pce);
        refresh(true);
    }

    public ProjectChildNodeFactory(IProteomicProject ipp) {
        this.ipp = ipp;
        ipp.addPropertyChangeListener(WeakListeners.propertyChange(this,ipp));
    }

    @Override
    protected boolean createKeys(List<Object> toPopulate) {
        for (ILogicalGelGroup ilgg : ipp.getLookup().lookup(IProject.class).getGelGroups()) {
            if (Thread.interrupted()) {
                return false;
            } else {
                toPopulate.add(ilgg);
            }
        }
        for (IPlate384 ilgg : ipp.getLookup().lookup(IProject.class).get384Plates()) {
            if (Thread.interrupted()) {
                return false;
            } else {
                toPopulate.add(ilgg);
            }
        }
        for (IPlate96 ilgg : ipp.getLookup().lookup(IProject.class).get96Plates()) {
            if (Thread.interrupted()) {
                return false;
            } else {
                toPopulate.add(ilgg);
            }
        }
        List<ISpotGroup> spotGroups = ipp.getLookup().lookup(IProject.class).getSpotGroups();
        if (!spotGroups.isEmpty()) {
            toPopulate.add(spotGroups);
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(Object key) {
        Object keyVal = key;
        Node node = Node.EMPTY;
        if (keyVal instanceof ILogicalGelGroup) {
            node = new LogicalGelGroupNode((ILogicalGelGroup) keyVal, ipp.getLookup());
        } else if (keyVal instanceof IPlate384) {
            node = new Plate384Node((IPlate384) keyVal, ipp.getLookup());
        } else if (keyVal instanceof IPlate96) {
            node = new Plate96Node((IPlate96) keyVal, ipp.getLookup());
        } else if (keyVal instanceof List) {
            List<?> keyValList = (List) keyVal;
            if (!keyValList.isEmpty()) {
                node = new SpotGroupFolderNode((List<ISpotGroup>) keyVal, ipp.getLookup());
            }
        }
        node.addPropertyChangeListener(WeakListeners.propertyChange(this,node));
        return node;
    }
}
