/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.nodes.SpotNode;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.IntrospectionException;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.explorer.propertysheet.PropertySheet;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * FIXME possible loss of selection? context is null when action is invoked
 * @author hoffmann
 */
public class SpotLabelEditAction implements ActionListener {

    private final ISpot context;

    public SpotLabelEditAction(ISpot context) {
        if(this.context == null) {
            throw new IllegalArgumentException("Context was null!");
        }
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            SpotNode bn = new SpotNode(context);
            PropertySheet ps = new PropertySheet();
            ps.setNodes(new Node[]{bn});
            DialogDescriptor bnd = new DialogDescriptor(ps,
                    "Edit Spot Properties");
            Object bndRet = DialogDisplayer.getDefault().notify(bnd);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
