/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.spotDetailViewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import java.beans.PropertyEditorManager;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
         PropertyEditorManager.registerEditor(IGel.class, GelPropertyEditor.class);
         PropertyEditorManager.registerEditor(ISpotGroup.class, SpotGroupPropertyEditor.class);
    }
}
