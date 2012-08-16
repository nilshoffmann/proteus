/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.WindowManager;

@ActionID(
    category = "Properties",
id = "de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions.OpenPropertiesViewAction")
@ActionRegistration(
    displayName = "#CTL_OpenPropertiesViewAction")
@Messages("CTL_OpenPropertiesViewAction=Properties")
public final class OpenPropertiesViewAction implements ActionListener {

//    private final Object context;
//
//    public OpenPropertiesViewAction() {
//        this.context = context;
//    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        WindowManager.getDefault().findTopComponent("properties").open();
    }
}
