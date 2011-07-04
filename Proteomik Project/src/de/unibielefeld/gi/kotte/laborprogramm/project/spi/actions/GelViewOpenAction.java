/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IGelOpenCookie;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public final class GelViewOpenAction implements ActionListener {

    private final IGelOpenCookie context;

    public GelViewOpenAction(IGelOpenCookie context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        this.context.open();
    }
}
