package de.unibielefeld.gi.kotte.laborprogramm.spotTableExporter;

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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;

/**
 *
 * @author kotte
 */
public class SpotDataExporter {

    public static void export(WizardDescriptor wizardDescriptor) {
        System.out.println("Exporting spot annotations");

        //get project
        IProteomicProject context = (IProteomicProject) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_PROJECT);

        //get output file
        File directory = (File) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_DIRECTORY);
        directory.mkdirs();
        String filename = (String) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_FILENAME);
        File outFile = new File(directory, filename + ".csv");

        //get methods
        Set<String> methods = (Set<String>) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_METHODS);

        //get output parameters
        boolean showUserDefinedLabels = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_USER_DEFINED_LABEL);
        boolean showIdentificationName = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_IDENTIFICATION_NAME);

        //get sorted list of spotGroups
        List<ISpotGroup> spotGroups = context.getLookup().lookup(IProject.class).getSpotGroups();
        Collections.sort(spotGroups, new Comparator<ISpotGroup>() {

            @Override
            public int compare(ISpotGroup t, ISpotGroup t1) {
                return t.getNumber() - t1.getNumber();
            }
        });

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
            for (ISpotGroup group : spotGroups) {
                String groupNumber = group.getNumber() + "";
                List<ISpot> spots = group.getSpots();
                Set<IWellIdentification> identifications = new LinkedHashSet<IWellIdentification>();
                for (ISpot spot : spots) {
                    if (spot.getStatus() == SpotStatus.PICKED) {
                        IWell96 well96 = spot.getWell();
                        if (well96.getStatus() == Well96Status.PROCESSED) {
                            for (IWell384 well384 : well96.get384Wells()) {
                                if (well384.getStatus() == Well384Status.IDENTIFIED) {
                                    IWellIdentification ident = well384.getIdentification();
                                    identifications.add(ident);
//                                } else {
//                                    System.out.println("Skipping well384 " + well384.toString());
                                }
                            }
//                        } else {
//                            System.out.println("Skipping well96 " + well96.toString());
                        }
//                    } else {
//                        System.out.println("Skipping unpicked spot " + spot + " in group #" + group.getNumber() + ": " + group.getLabel());
                    }
                }
                System.out.println("Processing " + identifications.size() + " identifications of " + spots.size() + " spots in spot group!");
                Map<IIdentificationMethod, Set<String>> methodToNames = new LinkedHashMap<IIdentificationMethod, Set<String>>();
                for (IWellIdentification ident : identifications) {
                    for (IIdentificationMethod method : ident.getMethods()) {
                        if (methods.contains(method.getName())) {
                            Set<String> names = methodToNames.get(method);
                            if (names == null) {
                                names = new LinkedHashSet<String>();
                            }
                            for (IIdentification identification : method.getIdentifications()) {
                                String id = identification.getName();
                                if (!id.isEmpty()) {
                                    names.add(id);
                                } else {
                                    System.out.println("Skipping empty identification for export!");
                                }
                            }
                            if (!names.isEmpty()) {
                                methodToNames.put(method, names);
                            }
                        }
                    }
                }

                if (!methodToNames.isEmpty()) {
                    //write group number
                    bw.write(groupNumber);
                    //write identification name
                    if (showIdentificationName) {
                        StringBuilder sb = new StringBuilder();
                        for (IIdentificationMethod method : methodToNames.keySet()) {
                            Set<String> names = methodToNames.get(method);
                            if (!names.isEmpty()) {
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
                            } else {
                                System.out.println("Skipping empty method results for " + method.getName());
                            }
                        }
                        String ident = sb.toString();
                        bw.write("\t" + ident);
                    }
                    //write user defined label
                    if (showUserDefinedLabels && group.getLabel() != null && !group.getLabel().isEmpty()) {
                        bw.write("\t" + group.getLabel());
                    }
                    //end line
                    bw.newLine();
                } else {
                    System.out.println(
                            "Skipping spot group " + groupNumber + " without annotations!");
                }
            }
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
