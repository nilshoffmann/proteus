/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.project.spi.actions;

import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IIdentificationMethod;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.identification.IWellIdentification;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.Well384Status;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.Well96Status;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

public final class ExportSpotTableAction implements ActionListener {

    private final IProteomicProject context;
    private Set<String> methods = new LinkedHashSet<String>(Arrays.asList(new String[]{
                "Lift MSMS_XCC_B100_C_5ident"}));

    public ExportSpotTableAction(IProteomicProject context) {
        System.out.println("ExportSpotTableAction initialized with " + context);
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        System.out.println("Exporting spot annotations");
        File basedir = FileUtil.toFile(context.getProjectDirectory());
        File exportDir = new File(basedir,"export");
        File spotTableExport = new File(exportDir,"spotTables");
        spotTableExport.mkdirs();
        File outFile = new File(spotTableExport,
                FileUtil.findFreeFileName(context.getProjectDirectory(),
                "spotGroupAnnotationExport", "csv") + ".csv");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
            List<ISpotGroup> spotGroups = this.context.getLookup().lookup(
                    IProject.class).getSpotGroups();
            Collections.sort(spotGroups, new Comparator<ISpotGroup>() {

                @Override
                public int compare(ISpotGroup t, ISpotGroup t1) {
                    return t.getNumber() - t1.getNumber();
                }
            });
            for (ISpotGroup group : spotGroups) {
                String label = group.getNumber() + "";
                List<ISpot> spots = group.getSpots();
                Set<IWellIdentification> identifications = new LinkedHashSet<IWellIdentification>();
                for (ISpot spot : spots) {
                    if (spot.getStatus() == SpotStatus.PICKED) {
                        IWell96 well96 = spot.getWell();
                        if (well96.getStatus() == Well96Status.PROCESSED) {
                            for (IWell384 well384 : well96.get384Wells()) {
                                if (well384.getStatus() == Well384Status.IDENTIFIED) {
                                    IWellIdentification ident = well384.
                                            getIdentification();
                                    identifications.add(ident);
                                } else {
                                    System.out.println("Skipping well384 " + well384.
                                            toString());
                                }
                            }
                        } else {
                            System.out.println(
                                    "Skipping well96 " + well96.toString());
                        }
                    } else {
                        System.out.println("Skipping unpicked spot " + spot + " in group #" + group.
                                getNumber() + ": " + group.getLabel());
                    }
                }
                System.out.println("Processing " + identifications.size() + " identifications of " + spots.
                        size() + " spots in spot group!");
                Map<IIdentificationMethod, Set<String>> methodToNames = new LinkedHashMap<IIdentificationMethod, Set<String>>();
                for (IWellIdentification ident : identifications) {
                    for (IIdentificationMethod method : ident.getMethods()) {
                        if (methods.contains(method.getName())) {
                            Set<String> names = methodToNames.get(method);
                            if (names == null) {
                                names = new LinkedHashSet<String>();
                            }
                            for (IIdentification identification : method.
                                    getIdentifications()) {
                                String id = identification.getName();
                                if(!id.isEmpty()) {
                                    names.add(id);
                                }else{
                                    System.out.println("Skipping empty identification for export!");
                                }
                            }
                            if(!names.isEmpty()) {
                                methodToNames.put(method, names);
                            }
                        }
                    }
                }
                if (!methodToNames.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (IIdentificationMethod method : methodToNames.keySet()) {
                        Set<String> names = methodToNames.get(method);
                        if(!names.isEmpty()) {
                            sb.append("[");
                            sb.append(method.getName());
                            sb.append(": ");
                            int cnt = 0;
                            for (String name : names) {
                                sb.append(name);
                                if (cnt < names.size() - 1) {
                                    sb.append("|");
                                }
                                cnt++;
                            }
                            sb.append("]");
                        }else{
                            System.out.println("Skipping empty method results for "+method.getName());
                        }
                    }
                    String ident = sb.toString();
                    bw.write(label + "\t" + ident);
                    bw.newLine();
                } else {
                    System.out.println(
                            "Skipping spot group " + label + " without annotations!");
                }
            }
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

    }
}
