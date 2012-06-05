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
import java.util.*;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;

/**
 *
 * @author kotte
 */
public class SpotDataExporter {

    public static void export(WizardDescriptor wizardDescriptor) {
        System.out.println("Exporting Spot Annotations");

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
        boolean showMethodName = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_METHOD_NAME);
        boolean showIdentification = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENTIFICATION);
        boolean showGroupLabel = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_GROUP_LABEL);

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
                //use StringBuilder to generate output line
                StringBuilder sb = new StringBuilder();
                //skip empty lines
                boolean filled = false;

                //write leftmost column (SpotID)
                sb.append(group.getNumber());

                //write label
                if (showGroupLabel) {
                    sb.append("\t");
                    if (group.getLabel() != null && !group.getLabel().isEmpty()) {
                        sb.append(group.getLabel());
                        filled = true;
                    }
                }
                
                if (showIdentification) {
                    //get identifications for output
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
                                    //} else {
                                    //  System.out.println("Skipping well384 " + well384.toString());
                                    }
                                }
                            //} else {
                            //  System.out.println("Skipping well96 " + well96.toString());
                            }
                        //} else {
                        //  System.out.println("Skipping unpicked spot " + spot + " in group #" + group.getNumber() + ": " + group.getLabel());
                        }
                    }
                    //System.out.println("Processing " + identifications.size() + " identifications of " + spots.size() + " spots in spot group!");

                    //get set of identification name strings for each Method object
                    Map<IIdentificationMethod, Set<IIdentification>> methodToIdentification = new LinkedHashMap<IIdentificationMethod, Set<IIdentification>>();
                    for (IWellIdentification ident : identifications) {
                        for (IIdentificationMethod method : ident.getMethods()) {
                            if (methods.contains(method.getName())) {
                                Set<IIdentification> idents = methodToIdentification.get(method);
                                if (idents == null) {
                                    idents = new LinkedHashSet<IIdentification>();
                                    methodToIdentification.put(method, idents);
                                }
                                for (IIdentification identification : method.getIdentifications()) {
                                    //if (!identification.getName().isEmpty()) {
                                        idents.add(identification);
                                    //} else {
                                    //  System.out.println("Skipping empty identification for export!");
                                    //}
                                }
                            }
                        }
                    }

                    //generate method output in format:
                    //[method1:ident1|ident2][method2:ident3]
                    if (!methodToIdentification.isEmpty()) {
                        sb.append("\t");
                        for (IIdentificationMethod method : methodToIdentification.keySet()) {
                            Set<IIdentification> idents = methodToIdentification.get(method);
                            if (!idents.isEmpty()) {
                                sb.append("[");

                                //write method name
                                if (showMethodName) {
                                    sb.append(method.getName());
                                    sb.append(": ");
                                }

                                int cnt = 0;
                                for (IIdentification ident : idents) {
                                    //write identification name (always enabled)
                                    sb.append(ident.getName());

                                    if (cnt < idents.size() - 1) {
                                        sb.append("|");
                                    }
                                    cnt++;
                                }
                                sb.append("]");
                            } else {
                                System.out.println("Skipping empty method results for " + method.getName());
                            }
                        }
                        filled = true;
                    } else {
                        System.out.println("Skipping spot group " + group.getNumber() + " without annotations!");
                    }
                }
                //finish output line
                if (filled) {
                    bw.write(sb.toString());
                    bw.newLine();
                }
            }
            //finish output
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
