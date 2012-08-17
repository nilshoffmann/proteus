package de.unibielefeld.gi.kotte.laborprogramm.spotDetailViewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.spotDetailViewer.properties.GelPropertyEditor;
import de.unibielefeld.gi.kotte.laborprogramm.spotDetailViewer.properties.SpotGroupPropertyEditor;
import de.unibielefeld.gi.kotte.laborprogramm.spotDetailViewer.properties.Well96PropertyEditor;
import java.beans.PropertyEditorManager;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
         PropertyEditorManager.registerEditor(IGel.class, GelPropertyEditor.class);
         PropertyEditorManager.registerEditor(ISpotGroup.class, SpotGroupPropertyEditor.class);
         PropertyEditorManager.registerEditor(IWell96.class, Well96PropertyEditor.class);
    }
}
