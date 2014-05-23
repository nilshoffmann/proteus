package de.unibielefeld.gi.kotte.laborprogramm.picking.api;

/**
 * The Picking Registry allows Picker classes to register as the active Picker.
 *
 * @author Konstantin Otte
 */
public class PickingRegistry {
    
    private static Picker activePicker = null;

    public static void register(Picker picker) {
        if (activePicker != null) {
            activePicker.resetPicking();
        }
        activePicker = picker;
    }

    public static void unregister(Picker picker) {
        if (picker == activePicker) {
            activePicker = null;
        }
    }
}
