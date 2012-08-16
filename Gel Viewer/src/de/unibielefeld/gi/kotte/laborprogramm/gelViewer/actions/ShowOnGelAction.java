/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.GelViewerTopComponent;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

@ActionID(
    category = "SpotNode",
id = "de.unibielefeld.gi.kotte.laborprogramm.gelViewer.actions.ShowOnGelAction")
@ActionRegistration(
    displayName = "#CTL_ShowOnGelAction")
@Messages("CTL_ShowOnGelAction=Show on Gel")
public final class ShowOnGelAction implements ActionListener, LookupListener {

    private final ISpot context;

    public ShowOnGelAction(ISpot context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        IGel gel = Utilities.actionsGlobalContext().lookup(IGel.class);
        IProject ipr = Utilities.actionsGlobalContext().lookup(IProject.class);
        IProteomicProject ipp = Utilities.actionsGlobalContext().lookup(IProteomicProject.class);
        if(ipr==null || ipp == null) {
            throw new NullPointerException("Project API and SPI not in lookup!");
        }
        if (gel == null) {
            gel = context.getGel();
        }
        Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().openTopComponent(gel, GelViewerTopComponent.class);
//        GelViewerTopComponent gvtc = (GelViewerTopComponent) Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().getTopComponentFor(gel);
//        InstanceContent ic = new InstanceContent();
//        Lookup lookup = new AbstractLookup(ic);
//            Result<ISpot> lres = lookup.lookupResult(ISpot.class);
//            gvtc.getLookup().lookup(LookupResultListeners.class).


    }

    @Override
    public void resultChanged(LookupEvent le) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
