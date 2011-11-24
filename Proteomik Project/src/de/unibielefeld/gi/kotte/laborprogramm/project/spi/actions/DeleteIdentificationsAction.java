/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IPlate384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public final class DeleteIdentificationsAction implements ActionListener {

    private final List<IPlate384> context;

    public DeleteIdentificationsAction(List<IPlate384> context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        for (IPlate384 iPlate384 : context) {
            for (IWell384 well : iPlate384.getWells()) {
                well.getIdentification().getIdentifications().clear();
                if (well.getStatus() != Well384Status.EMPTY && well.getStatus() != Well384Status.ERROR) {
                    well.setStatus(Well384Status.FILLED);
                }
            }
        }
    }
}
