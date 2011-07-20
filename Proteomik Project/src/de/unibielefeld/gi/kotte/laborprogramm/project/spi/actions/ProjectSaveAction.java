/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import org.openide.cookies.SaveCookie;
import org.openide.util.Exceptions;

public final class ProjectSaveAction implements ActionListener {

    private final SaveCookie context;

    public ProjectSaveAction(SaveCookie context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            context.save();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
