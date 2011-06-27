/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.project.spi.nodes;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ILogicalGelGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
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
public class ProjectChildNodeFactory extends ChildFactory<Object>{

    private IProteomicProject ipp;

    public ProjectChildNodeFactory(IProteomicProject ipp) {
        this.ipp = ipp;
    }

    @Override
    protected boolean createKeys(List<Object> toPopulate) {
        List<ILogicalGelGroup> ilgg = ipp.getGelGroups();
        for (ILogicalGelGroup ic : ilgg) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(ic);
            }
        }

        List<IPlate384> ip = ipp.get384Plates();
        for (IPlate384 ic : ip) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(ic);
            }
        }

        List<ISpotGroup> isg = ipp.getSpotGroups();
        for (ISpotGroup ic : isg) {
            if (Thread.interrupted()) {
                return true;
            } else {
                toPopulate.add(ic);
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
