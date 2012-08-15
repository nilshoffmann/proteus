package de.unibielefeld.gi.kotte.laborprogramm.spotExport;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
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
//        System.out.println("Exporting Spot Annotations with Spot Information Exporter.");

        //get list of spots
        List<ISpot> spotList = (List<ISpot>) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SPOTLIST);

        //get output file
        File directory = (File) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_DIRECTORY);
        directory.mkdirs();
        String filename = (String) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_FILENAME);
        File outFile = new File(directory, filename + ".csv");

        //get method sets
        Set<String> methods = (Set<String>) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_METHODS);

        //get output parameters
        boolean showSpotLabel = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_LABEL);
        boolean showIdentification = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENTIFICATION);
        boolean showMethodName = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_METHOD_NAME);
        boolean showIdentName = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_NAME);
        boolean showIdentPlate96Position = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_PLATE96_POSITION);
        boolean showIdentPlate384Position = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_PLATE384_POSITION);
        boolean showIdentGelName = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_GEL_NAME);
        boolean showIdentAbbreviation = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_ABBREVIATION);
        boolean showIdentAccession = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_ACCESSION);
        boolean showIdentEcNumbers = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_ABBREVIATION);
        boolean showIdentCoverage = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_COVERAGE);
        boolean showIdentPIValue = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_PI_VALUE);
        boolean showIdentScore = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_SCORE);
        boolean showIdentWeight = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_WEIGHT);

        //get filters
        boolean filterMascotUsed = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_FILTER_MASCOT_USAGE);
        float filterMascotValue = (Float) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_FILTER_MASCOT_VALUE);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
            for (ISpot spot : spotList) {
                //use StringBuilder to generate output line
                StringBuilder globalStringBuilder = new StringBuilder();
                //always start with "ID" followed by the group number and a tab
                globalStringBuilder.append("ID");
                globalStringBuilder.append(spot.getNumber());
                globalStringBuilder.append('\t');
                //skip empty lines
                boolean lineFilled = false;

                //write label
                if (showSpotLabel) {

                    if (spot.getLabel() != null && !spot.getLabel().isEmpty()) {
                        globalStringBuilder.append(spot.getLabel());
                        lineFilled = true;
                    } //else {
                    //globalStringBuilder.append("unlabeled");
                    //lineFilled = true;
                    //}
                }

                if (showIdentification) {
                    //get set of identification name strings for each Method object
                    Map<IIdentificationMethod, Set<IIdentification>> methodToIdentification = new LinkedHashMap<IIdentificationMethod, Set<IIdentification>>();
                    if (spot.getStatus() == SpotStatus.PICKED) {
                        IWell96 well96 = spot.getWell();
                        if (well96.getStatus() == Well96Status.PROCESSED) {
                            for (IWell384 well384 : well96.get384Wells()) {
                                if (well384.getStatus() == Well384Status.IDENTIFIED) {
                                    IWellIdentification ident = well384.getIdentification();
                                    for (IIdentificationMethod method : ident.getMethods()) {
                                        if (methods.contains(method.getName())) {
                                            Set<IIdentification> idents = new LinkedHashSet<IIdentification>();
                                            for (IIdentification identification : method.getIdentifications()) {
                                                //apply filters
                                                if (filterMascotUsed) {
                                                    if (identification.getScore() >= filterMascotValue) {
                                                        idents.add(identification);
                                                    }
                                                } else {
                                                    idents.add(identification);
                                                }
                                            }
                                            methodToIdentification.put(method, idents);
                                        }
                                    }
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

                    if (!methodToIdentification.isEmpty()) {
                        for (IIdentificationMethod method : methodToIdentification.keySet()) {
                            StringBuilder methodStringBuilder = new StringBuilder();
                            boolean methodFilled = false;
                            //only use [] brackets if there are multiple methods
                            if (methods.size() > 1) {
                                methodStringBuilder.append('[');
                            } else if (lineFilled && showMethodName) {
                                //placeholder between label and method name
                                methodStringBuilder.append(' ');
                            }
                            if (showMethodName) {
                                methodStringBuilder.append(method.getName());
                            }

                            Set<IIdentification> idents = methodToIdentification.get(method);
                            if (!idents.isEmpty()) {
                                for (IIdentification ident : idents) {
                                    //initialize identification output
                                    StringBuilder identStringBuilder = new StringBuilder();
                                    identStringBuilder.append('(');
                                    boolean identFilled = false;
                                    if (showIdentName) {
                                        identStringBuilder.append(ident.getName());
                                        if (!ident.getName().isEmpty()) {
                                            identFilled = true;
                                        }
                                    }
                                    //get remote objects
                                    IWell384 well384 = method.getParent().getParent();
                                    IWell96 well96 = well384.getWell96();
                                    IGel gel = well96.getSpot().getGel();
                                    if (showIdentPlate96Position) {
                                        identStringBuilder.append('|');
                                        identStringBuilder.append(well96.getParent().getName());
                                        identStringBuilder.append(':');
                                        identStringBuilder.append(well96.getWellPosition());
                                        identFilled = true;
                                    }
                                    if (showIdentPlate384Position) {
                                        identStringBuilder.append('|');
                                        identStringBuilder.append(well384.getParent().getName());
                                        identStringBuilder.append(':');
                                        identStringBuilder.append(well384.getWellPosition());
                                        identFilled = true;
                                    }
                                    if (showIdentGelName) {
                                        identStringBuilder.append('|');
                                        identStringBuilder.append(gel.getName());
                                        identFilled = true;
                                    }
                                    if (showIdentAbbreviation) {
                                        identStringBuilder.append('|');
                                        identStringBuilder.append(ident.getAbbreviation());
                                        if (!ident.getAbbreviation().isEmpty()) {
                                            identFilled = true;
                                        }
                                    }
                                    if (showIdentAccession) {
                                        identStringBuilder.append('|');
                                        identStringBuilder.append(ident.getAccession());
                                        if (!ident.getAccession().isEmpty()) {
                                            identFilled = true;
                                        }
                                    }
                                    if (showIdentEcNumbers) {
                                        identStringBuilder.append('|');
                                        List<String> ecNumbers = ident.getEcNumbers();
                                        int i = 0;
                                        for (String en : ecNumbers) {
                                            identStringBuilder.append(en);
                                            if (i < ecNumbers.size() - 1) {
                                                identStringBuilder.append(", ");
                                                i++;
                                            }
                                            if (!en.isEmpty()) {
                                                identFilled = true;
                                            }
                                        }
                                    }
                                    if (showIdentCoverage) {
                                        identStringBuilder.append('|');
                                        identStringBuilder.append(ident.getCoverage());
                                        if (ident.getCoverage() != -1) {
                                            identFilled = true;
                                        }
                                    }
                                    if (showIdentPIValue) {
                                        identStringBuilder.append('|');
                                        identStringBuilder.append(ident.getPiValue());
                                        if (ident.getPiValue() != 0.0) {
                                            identFilled = true;
                                        }
                                    }
                                    if (showIdentScore) {
                                        identStringBuilder.append('|');
                                        identStringBuilder.append(ident.getScore());
                                        if (ident.getScore() != -1.0) {
                                            identFilled = true;
                                        }
                                    }
                                    if (showIdentWeight) {
                                        identStringBuilder.append('|');
                                        identStringBuilder.append(ident.getProteinMolecularWeight());
                                        if (ident.getProteinMolecularWeight() != 0.0) {
                                            identFilled = true;
                                        }
                                    }
                                    //finish ident output
                                    if (identFilled) {
                                        identStringBuilder.append(')');
                                        if (identStringBuilder.charAt(1) == '|') {
                                            identStringBuilder.delete(1, 2);
                                        }
                                        methodStringBuilder.append(identStringBuilder);
                                        methodFilled = true;
                                    } else {
                                        System.out.println("Skipping empty identification output: " + identStringBuilder.toString());
                                    }
                                }
                            } else {
                                System.out.println("Skipping empty method results for " + method.getName());
                            }

                            //finish method output
                            if (methodFilled) {
                                //only use [] brackets if there are multiple methods
                                if (methods.size() > 1) {
                                    methodStringBuilder.append(']');
                                }
                                globalStringBuilder.append(methodStringBuilder);
                                lineFilled = true;
                            } else {
                                System.out.println("Skipping empty method information: " + methodStringBuilder.toString());
                            }
                        }
                    } else {
                        System.out.println("Skipping spot " + spot.getNumber() + " without annotations!");
                    }
                }
                //finish output line
                if (lineFilled) {
                    bw.write(globalStringBuilder.toString());
                    bw.newLine();
                } else {
                    System.out.println("Skipping empty line: " + globalStringBuilder.toString());
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
