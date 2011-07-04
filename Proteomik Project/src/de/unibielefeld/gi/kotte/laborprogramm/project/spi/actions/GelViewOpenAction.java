/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IGelOpenCookie;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.openide.util.Utilities;

public final class GelViewOpenAction implements ActionListener {

//    private final IGel context;
//
//    public GelViewOpenAction(IGel context) {
//        this.context = context;
//    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        IGelOpenCookie igoc = Utilities.actionsGlobalContext().lookup(IGelOpenCookie.class);
        if(igoc!=null) {
            igoc.open();
        }
    }
}
