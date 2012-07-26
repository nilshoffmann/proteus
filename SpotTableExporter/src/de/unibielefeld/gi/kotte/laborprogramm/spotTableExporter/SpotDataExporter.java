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
        boolean showHeader = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_HEADER);
        boolean showGroupNumber = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_GROUP_NUMBER);
        boolean showGroupLabel = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_GROUP_LABEL);
        boolean showIdentification = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENTIFICATION);
        boolean showIdentMethodName = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_METHOD_NAME);
        boolean showIdentName = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_NAME);
        boolean showIdentPlate96Position = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_PLATE96_POSITION);
        boolean showIdentPlate384Position = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_PLATE384_POSITION);
        boolean showIdentAbbreviation = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_ABBREVIATION);
        boolean showIdentAccession = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_ACCESSION);
        boolean showIdentEcNumbers = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_ABBREVIATION);
        boolean showIdentCoverage = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_COVERAGE);
        boolean showIdentPIValue = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_PI_VALUE);
        boolean showIdentScore = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_SCORE);
        boolean showIdentWeight = (Boolean) wizardDescriptor.getProperty(ExportOptionsVisualPanel1.PROPERTY_SHOW_IDENT_WEIGHT);

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
            
            //add row headers
            if (showHeader) {
                StringBuilder header = new StringBuilder();
                if (showGroupNumber) {
                    header.append("Group Number");
                }
                if (showGroupLabel) {
                    header.append('\t');
                    header.append("Group Label");
                }
                if (showIdentMethodName) {
                    header.append('\t');
                    header.append("Method Name");
                }
                if (showIdentName) {
                    header.append('\t');
                    header.append("Name");
                }
                if (showIdentPlate96Position) {
                    header.append('\t');
                    header.append("96 Well Plate");
                }
                if (showIdentPlate384Position) {
                    header.append('\t');
                    header.append("384 Well Plate");
                }
                if (showIdentAbbreviation) {
                    header.append('\t');
                    header.append("Abbbreviation");
                }
                if (showIdentAccession) {
                    header.append('\t');
                    header.append("Accession Number");
                }
                if (showIdentEcNumbers) {
                    header.append('\t');
                    header.append("EC Numbers");
                }
                if (showIdentCoverage) {
                    header.append('\t');
                    header.append("MS Coverage");
                }
                if (showIdentPIValue) {
                    header.append('\t');
                    header.append("pI Value");
                }
                if (showIdentScore) {
                    header.append('\t');
                    header.append("Mascot Score");
                }
                if (showIdentWeight) {
                    header.append('\t');
                    header.append("Molecular Weight");
                }
                bw.write(header.toString());
                bw.newLine();
            }
            
            for (ISpotGroup group : spotGroups) {
                //use StringBuilder to generate output line
                StringBuilder sb = new StringBuilder();
                
                //skip empty lines
                boolean filled = false;

                //write leftmost column (SpotID)
                if (showGroupNumber) {
                    sb.append(group.getNumber());
                }

                //write label
                if (showGroupLabel) {
                    sb.append('\t');
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

                    if (!methodToIdentification.isEmpty()) {
                        for (IIdentificationMethod method : methodToIdentification.keySet()) {
                            Set<IIdentification> idents = methodToIdentification.get(method);
                            if (!idents.isEmpty()) {
                                //// OUTPUT GENERATION (for identification) ////
                                int cnt = 0;
                                for (IIdentification ident : idents) {
                                    if (showIdentMethodName) {
                                        sb.append('\t');
                                        sb.append(method.getName());
                                        if (!method.getName().isEmpty()) {
                                            filled = true;
                                        }
                                    }
                                    if (showIdentName) {
                                        sb.append('\t');
                                        sb.append(ident.getName());
                                        if (!ident.getName().isEmpty()) {
                                            filled = true;
                                        }
                                    }
                                    if (showIdentPlate96Position) {
                                        sb.append('\t');
                                        IWell96 well96 = method.getParent().getParent().getWell96();
                                        sb.append(well96.getParent().getName());
                                        sb.append(':');
                                        sb.append(well96.getWellPosition());
                                        filled = true;
                                    }
                                    if (showIdentPlate384Position) {
                                        sb.append('\t');
                                        IWell384 well384 = method.getParent().getParent();
                                        sb.append(well384.getParent().getName());
                                        sb.append(':');
                                        sb.append(well384.getWellPosition());
                                        filled = true;
                                    }
                                    if (showIdentAbbreviation) {
                                        sb.append('\t');
                                        sb.append(ident.getAbbreviation());
                                        if (!ident.getAbbreviation().isEmpty()) {
                                            filled = true;
                                        }
                                    }
                                    if (showIdentAccession) {
                                        sb.append('\t');
                                        sb.append(ident.getAccession());
                                        if (!ident.getAccession().isEmpty()) {
                                            filled = true;
                                        }
                                    }
                                    if (showIdentEcNumbers) {
                                        sb.append('\t');
                                        List<String> ecNumbers = ident.getEcNumbers();
                                        int i = 0;
                                        for (String kn : ecNumbers) {
                                            sb.append(kn);
                                            if (i < ecNumbers.size() -1) {
                                                sb.append(", ");
                                                i++;
                                            }
                                            if (!kn.isEmpty()) {
                                                filled = true;
                                            }
                                        }
                                    }
                                    if (showIdentCoverage) {
                                        sb.append('\t');
                                        sb.append(ident.getCoverage());
                                        //TODO check value validity
                                        //filled = true;
                                    }
                                    if (showIdentPIValue) {
                                        sb.append('\t');
                                        sb.append(ident.getPiValue());
                                        //TODO check value validity
                                        //filled = true;
                                    }
                                    if (showIdentScore) {
                                        sb.append('\t');
                                        sb.append(ident.getScore());
                                        //TODO check value validity
                                        //filled = true;
                                    }
                                    if (showIdentWeight) {
                                        sb.append('\t');
                                        sb.append(ident.getProteinMolecularWeight());
                                        //TODO check value validity
                                        //filled = true;
                                    }
                                    if (cnt < idents.size() - 1) {
                                        //new line, tabbed to start of identification output
                                        sb.append('\n');
                                        if (showGroupLabel) {
                                            sb.append('\t');
                                        }
                                    }
                                    cnt++;
                                }
                                ////////////////////////////////////////////////
                            } else {
                                System.out.println("Skipping empty method results for " + method.getName());
                            }
                        }
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
