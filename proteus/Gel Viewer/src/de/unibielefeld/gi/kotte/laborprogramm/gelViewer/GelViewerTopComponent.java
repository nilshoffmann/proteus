/*
 *  Copyright (C) 2008-2012 Nils Hoffmann
 *  Nils.Hoffmann A T CeBiTec.Uni-Bielefeld.DE
 *
 *  This file is part of Maui.
 *
 *  Maui is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Maui is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Maui.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer;

import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.actions.ExportGelAction;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.AnnotationManager;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.GelSpotAnnotations;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.dataProvider.GelSpotDataProvider;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.lookup.SpotGroupSelectionListener;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.lookup.SpotGroupSelectionListener.SyncOnSpotGroupToken;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.lookup.SpotSelectionListener;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.theme.ThemeManager;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IPlate384OpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.cookies.IPlate96OpenCookie;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.SpotStatus;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.group.ISpotGroup;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate384.IWell384;
import de.unibielefeld.gi.kotte.laborprogramm.topComponentRegistry.api.IRegistryFactory;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.FocusManager;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import net.sf.maltcms.chromaui.lookupResultListener.api.LookupResultListeners;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;
import net.sf.maltcms.ui.plot.heatmap.HeatmapPanel;
import net.sf.maltcms.ui.plot.heatmap.IAnnotation;
import net.sf.maltcms.ui.plot.heatmap.IDataProvider;
import net.sf.maltcms.ui.plot.heatmap.Tuple2D;
import net.sf.maltcms.ui.plot.heatmap.axis.Corner;
import net.sf.maltcms.ui.plot.heatmap.event.IProcessorResultListener;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEventProcessorChain;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.PointSelectionProcessor;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.ZoomProcessor;
import net.sf.maltcms.ui.plot.heatmap.painter.AnnotationPainter;
import net.sf.maltcms.ui.plot.heatmap.painter.LocationRectanglePainter;
import net.sf.maltcms.ui.plot.heatmap.painter.PainterLayerUI;
import net.sf.maltcms.ui.plot.heatmap.painter.ToolTipPainter;
import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.explorer.propertysheet.PropertySheet;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.TaskListener;
import org.openide.util.Utilities;
import org.openide.util.WeakListeners;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;

/**
 * TODO allow setting of font size property TODO allow setting of focused view
 * TODO add support for highlighting / scrolling to a specific annotation Top
 * component that displays a gel with spot annotations and allows selection of
 * associated ISpot objects.
 */
@ConvertAsProperties(dtd = "-//de.unibielefeld.gi.kotte.laborprogramm.gelViewer//GelViewer//EN",
        autostore = false)
public final class GelViewerTopComponent extends TopComponent implements
        LookupListener, PropertyChangeListener,
        IProcessorResultListener<Tuple2D<Point2D, Double>>, MouseInputListener {

    private static GelViewerTopComponent instance;
    /**
     * path to the icon used by the component and its open action
     */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "GelViewerTopComponent";
    private InstanceContent ic = new InstanceContent();
    private Tuple2D<Point2D, SpotAnnotation> annotation;
    private Result<IGel> result;
    private JXLayer<JComponent> layer;
    private LookupResultListeners lookupListeners;
    private HeatmapPanel<ISpot> jl;
    private AnnotationManager annotationManager;
    private boolean centerView = true;

    @Override
    public HelpCtx getHelpCtx() {
        return super.getHelpCtx();
    }

    public GelViewerTopComponent() {
        associateLookup(new AbstractLookup(ic));
        initComponents();
        setFocusable(true);
        setName(NbBundle.getMessage(GelViewerTopComponent.class,
                "CTL_GelViewerTopComponent"));
        setToolTipText(NbBundle.getMessage(GelViewerTopComponent.class,
                "HINT_GelViewerTopComponent"));

        lookupListeners = new LookupResultListeners();

        SpotSelectionListener all = new SpotSelectionListener(
                ISpot.class, getLookup());
        lookupListeners.add(all);
        SpotGroupSelectionListener sgsl = new SpotGroupSelectionListener(
                ISpotGroup.class, getLookup());
        lookupListeners.add(sgsl);
        ic.add(new SpotGroupSelectionListener.SyncOnSpotGroupToken());
    }

    public void setGel(IGel gel) {
        System.out.println("Setting gel " + gel);
        if (getLookup().lookup(IGel.class) == null && gel != null) {
            this.ic.add(Utilities.actionsGlobalContext().lookup(
                    IProteomicProject.class));
            this.ic.add(gel);
            //CentralLookup.getDefault().remove(gel);
            setDisplayName(gel.getName());
            addHeatmapPanel(this, gel);//deregister
        } else if (gel == null) {
            putClientProperty("print.printable", Boolean.FALSE);
            throw new IllegalStateException("Gel can not be null!");
        } else {
            putClientProperty("print.printable", Boolean.FALSE);
            System.err.println(
                    "Gel can only be set once on GelViewerTopComponent!");
        }
    }

    private String getLocalizedFilePath(String filePath) {
        if (filePath.contains("/")) {
            if (!File.separator.equals("/")) {
                return filePath.replaceAll("/", File.separator);
            }
        } else if (filePath.contains("\\")) {
            if (!File.separator.equals("\\")) {
                return filePath.replaceAll("\\\\", File.separator);
            }
        }
        return filePath;
    }

    private void addHeatmapPanel(final GelViewerTopComponent tc, final IGel gel) {
        final RequestProcessor rp = new RequestProcessor("Gel Loader", 1);
        final RequestProcessor.Task task = rp.create(new Runnable() {
            @Override
            public void run() {
                System.out.println("Loading gel " + gel.getFilename());
                IProteomicProject p = Utilities.actionsGlobalContext().lookup(
                        IProteomicProject.class);
                File f = gel.getLocation();
                File baseDir = FileUtil.toFile(p.getProjectDirectory());
                String localizedFilename = getLocalizedFilePath(gel.getFilename());
                String filePath = localizedFilename;
                if (f == null) {
                    System.out.println("Gel location is null");
                    f = new File(localizedFilename);
                    System.out.println("Setting to :" + f.getPath());
                    gel.setLocation(f);

                } else {
                    filePath = getLocalizedFilePath(f.getPath());
                }

                if (!f.isAbsolute()) {
                    f = new File(baseDir, filePath);
                }

                System.out.println("Using gel image location: " + f);
                HeatmapDataset<ISpot> hmd = null;
                hmd = new HeatmapDataset<ISpot>(new GelSpotDataProvider(
                        f,
                        gel));

                ic.add(hmd);
                jl = new HeatmapPanel<ISpot>(
                        hmd);
                hmd.addPropertyChangeListener(tc);
                annotationManager = new AnnotationManager(p, gel, hmd, jl);
                annotationManager.open();
                final IDataProvider hdp = hmd.getDataProvider();
                //create a tooltip painter for the payload type (here: List<Float>)
                ToolTipPainter<ISpot, JPanel> tooltipPainter = new ToolTipPainter<ISpot, JPanel>(
                        hmd) {
                            @Override
                            public String getStringFor(IAnnotation<ISpot> t) {
                                Point2D dataCoord = hdp.getViewToModelTransform().
                                transform(t.getPosition(), null);
                                return String.format("[x=%.2f|y=%.2f] ",
                                        dataCoord.getX(), dataCoord.getY()) + " Label: " + t.getPayload().getLabel() + " " + t.getPayload().
                                getStatus();//"x="+t.getPosition().getX()+" values: "+t.getPayload();
                            }
                        };
				//register ToolTipPainter to receive events from dataset
                //hmd.addPropertyChangeListener(tooltipPainter);
                ic.add(tooltipPainter);

                //create an annotation painter
                AnnotationPainter<ISpot, JPanel> annotationPainter = new AnnotationPainter<ISpot, JPanel>(
                        hmd) {
                        };
                annotationPainter.setSearchRadius(25.0d);
                annotationPainter.addPropertyChangeListener(tooltipPainter);
                annotationPainter.addPropertyChangeListener(tc);

                ic.add(annotationPainter);

                LocationRectanglePainter locationRectanglePainter = new LocationRectanglePainter(
                        hmd);
                ic.add(locationRectanglePainter);
                //the PointSelectionProcessor will handle selection of single points
                PointSelectionProcessor pointSelectionProcessor = new PointSelectionProcessor();
                pointSelectionProcessor.addListener(annotationPainter,
                        MouseEvent.BUTTON1_DOWN_MASK);
                ic.add(pointSelectionProcessor);
                //create a zoom processor
                ZoomProcessor zoomProcessor = new ZoomProcessor();
                //register dataset for zoom events
                zoomProcessor.addListener(hmd);
                zoomProcessor.setMinZoom(0.1d);
                zoomProcessor.setMaxZoom(4.0d);
                zoomProcessor.setZoomDelta(0.1d);
                zoomProcessor.addListener(tc);
                ic.add(zoomProcessor);

                //set up the mouse event processor chain with zoomProcessor and pointSelectionProcessor
                MouseEventProcessorChain mouseEventProcessorChain = new MouseEventProcessorChain(
                        zoomProcessor, pointSelectionProcessor);

//                ModelViewCoordinatesRenderer mvcr = new ModelViewCoordinatesRenderer(
//                        hmd);
//                pointSelectionProcessor.addListener(mvcr);
                //Compound painter and jxlayer
                CompoundPainter<JComponent> compoundPainter = new CompoundPainter<JComponent>(
                        annotationPainter,
                        tooltipPainter, locationRectanglePainter);//, mvcr);
                PainterLayerUI<JComponent> plui = new PainterLayerUI<JComponent>(
                        compoundPainter);
                layer = new JXLayer<JComponent>(jl,
                        plui);

                //register layer ui with HeatmapPanel for transformation events
                jl.addPropertyChangeListener(HeatmapDataset.PROP_TRANSFORM,
                        plui);
                //register mouse and key listeners
                jl.addMouseListener(mouseEventProcessorChain);
                jl.addMouseMotionListener(mouseEventProcessorChain);
                jl.addMouseWheelListener(mouseEventProcessorChain);
                layer.addKeyListener(pointSelectionProcessor);

                //Property change support for repaints at CompoundPainter
                compoundPainter.addPropertyChangeListener(layer);

                //Frame setup
                JScrollPane jsp = new JScrollPane(layer);//, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                jsp.getVerticalScrollBar().getAccessibleContext().
                        addPropertyChangeListener(annotationPainter);
                jsp.getHorizontalScrollBar().getAccessibleContext().
                        addPropertyChangeListener(annotationPainter);

//                Rule columnView = new Rule(hmd,Rule.HORIZONTAL, true);
//                Rule rowView = new Rule(hmd,Rule.VERTICAL, true);
////
////                hmd.addPropertyChangeListener(rowView);
////
////                hmd.addPropertyChangeListener(columnView);
//
//                columnView.setPreferredWidth(hmd.getDataBounds().getBounds().width);
//                rowView.setPreferredHeight(hmd.getDataBounds().getBounds().height);
////
//                jsp.setColumnHeaderView(columnView);
//                jsp.setRowHeaderView(rowView);
                jsp.setWheelScrollingEnabled(true);

                jsp.setComponentOrientation(
                        ComponentOrientation.LEFT_TO_RIGHT);

                jsp.setCorner(JScrollPane.UPPER_LEFT_CORNER, new Corner());
                jsp.setCorner(JScrollPane.LOWER_RIGHT_CORNER,
                        new Corner());
                jsp.setCorner(JScrollPane.LOWER_LEFT_CORNER,
                        new Corner());
                jsp.setCorner(JScrollPane.UPPER_RIGHT_CORNER,
                        new Corner());
                jl.addMouseListener(tc);

                tc.add(jsp, BorderLayout.CENTER);

//        } catch (IOException ex) {
//            Exceptions.printStackTrace(ex);
//        }
                jToolBar1.add(new ExportGelAction("Save", jl,
                        annotationPainter,
                        gel));

//                jl.requestFocusInWindow();
                ic.add(jl);
                result.removeLookupListener(tc);
                Iterator<Tuple2D<Point2D, IAnnotation<ISpot>>> iter = hmd.getAnnotationIterator();
                while (iter.hasNext()) {
                    Tuple2D<Point2D, IAnnotation<ISpot>> tple = iter.next();
                    tple.getSecond().setSelected(false);
                    WeakListeners.propertyChange(tc, tple.getSecond());
                    WeakListeners.propertyChange(tc, tple.getSecond().getPayload());
                }
                tc.putClientProperty("print.printable", Boolean.TRUE);
                tc.requestFocusInWindow(true);
                setSpotsToDraw();
            }
        });
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Loading gel " + gel.getName(), task);
        task.addTaskListener(new TaskListener() {
            @Override
            public void taskFinished(org.openide.util.Task task) {
				//make sure that we get rid of the ProgressHandle
                //when the task is finished
                ph.finish();
            }
        });

        //start the progresshandle the progress UI will show 500s after
        ph.start();

        //this actually start the task
        task.schedule(0);

//        repaint();
    }

    @Override
    public void print(Graphics g) {
        if (layer != null) {
            layer.print(g);
        }
    }

    @Override
    public void printAll(Graphics g) {
        if (layer != null) {
            layer.printAll(g);
        }
    }

    @Override
    protected void printBorder(Graphics g) {
//        super.printBorder(g);
    }

    @Override
    protected void printChildren(Graphics g) {
//        super.printChildren(g);
    }

    @Override
    protected void printComponent(Graphics g) {
//        super.printComponent(g);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        labeledSpotsCheckBox = new javax.swing.JCheckBoxMenuItem();
        unlabeledSpotsCheckBox = new javax.swing.JCheckBoxMenuItem();
        pickedSpotsCheckBox = new javax.swing.JCheckBoxMenuItem();
        unpickedSpotsCheckBox = new javax.swing.JCheckBoxMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        crossHairCheckBox = new javax.swing.JCheckBoxMenuItem();
        tooltipsCheckBox = new javax.swing.JCheckBoxMenuItem();
        centerCheckBox = new javax.swing.JCheckBoxMenuItem();
        syncCheckBox = new javax.swing.JCheckBoxMenuItem();
        locationIndicatorCheckBox = new javax.swing.JCheckBoxMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        zoomDisplay = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        viewSettingsButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();

        labeledSpotsCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(labeledSpotsCheckBox, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.labeledSpotsCheckBox.text")); // NOI18N
        labeledSpotsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labeledSpotsCheckBoxActionPerformed(evt);
            }
        });
        jPopupMenu1.add(labeledSpotsCheckBox);

        org.openide.awt.Mnemonics.setLocalizedText(unlabeledSpotsCheckBox, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.unlabeledSpotsCheckBox.text")); // NOI18N
        unlabeledSpotsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unlabeledSpotsCheckBoxActionPerformed(evt);
            }
        });
        jPopupMenu1.add(unlabeledSpotsCheckBox);

        pickedSpotsCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(pickedSpotsCheckBox, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.pickedSpotsCheckBox.text")); // NOI18N
        pickedSpotsCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.pickedSpotsCheckBox.toolTipText")); // NOI18N
        pickedSpotsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pickedSpotsCheckBoxActionPerformed(evt);
            }
        });
        jPopupMenu1.add(pickedSpotsCheckBox);

        unpickedSpotsCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(unpickedSpotsCheckBox, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.unpickedSpotsCheckBox.text")); // NOI18N
        unpickedSpotsCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.unpickedSpotsCheckBox.toolTipText")); // NOI18N
        unpickedSpotsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unpickedSpotsCheckBoxActionPerformed(evt);
            }
        });
        jPopupMenu1.add(unpickedSpotsCheckBox);
        jPopupMenu1.add(jSeparator2);

        crossHairCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(crossHairCheckBox, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.crossHairCheckBox.text")); // NOI18N
        crossHairCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.crossHairCheckBox.toolTipText")); // NOI18N
        crossHairCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crossHairCheckBoxActionPerformed(evt);
            }
        });
        jPopupMenu1.add(crossHairCheckBox);

        tooltipsCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(tooltipsCheckBox, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.tooltipsCheckBox.text")); // NOI18N
        tooltipsCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.tooltipsCheckBox.toolTipText")); // NOI18N
        tooltipsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tooltipsCheckBoxActionPerformed(evt);
            }
        });
        jPopupMenu1.add(tooltipsCheckBox);

        centerCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(centerCheckBox, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.centerCheckBox.text")); // NOI18N
        centerCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.centerCheckBox.toolTipText")); // NOI18N
        centerCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerCheckBoxActionPerformed(evt);
            }
        });
        jPopupMenu1.add(centerCheckBox);

        syncCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(syncCheckBox, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.syncCheckBox.text")); // NOI18N
        syncCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.syncCheckBox.toolTipText")); // NOI18N
        syncCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syncCheckBoxActionPerformed(evt);
            }
        });
        jPopupMenu1.add(syncCheckBox);

        locationIndicatorCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(locationIndicatorCheckBox, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.locationIndicatorCheckBox.text")); // NOI18N
        locationIndicatorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationIndicatorCheckBoxActionPerformed(evt);
            }
        });
        jPopupMenu1.add(locationIndicatorCheckBox);

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.jButton1.text_1")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        zoomDisplay.setColumns(4);
        zoomDisplay.setText(org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.zoomDisplay.text")); // NOI18N
        zoomDisplay.setEnabled(false);
        zoomDisplay.setFocusable(false);
        zoomDisplay.setMaximumSize(new java.awt.Dimension(40, 50));
        zoomDisplay.setMinimumSize(new java.awt.Dimension(40, 20));
        jToolBar1.add(zoomDisplay);
        jToolBar1.add(jSeparator1);

        org.openide.awt.Mnemonics.setLocalizedText(viewSettingsButton, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.viewSettingsButton.text")); // NOI18N
        viewSettingsButton.setFocusable(false);
        viewSettingsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        viewSettingsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        viewSettingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSettingsButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(viewSettingsButton);
        jToolBar1.add(jSeparator3);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //zoom in
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Rectangle2D dataBounds = getLookup().lookup(HeatmapDataset.class).
                        getDataBounds();
                getLookup().lookup(ZoomProcessor.class).zoomIn(new Point2D.Double(dataBounds.getCenterX(), dataBounds.getCenterY()));
                zoomDisplay.setText(String.format("%1.2f", getLookup().lookup(
                        HeatmapDataset.class).getZoom().getSecond()));
            }
        };
        SwingUtilities.invokeLater(r);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //zoom out
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Rectangle2D dataBounds = getLookup().lookup(HeatmapDataset.class).
                        getDataBounds();

                getLookup().lookup(ZoomProcessor.class).zoomOut(
                        new Point2D.Double(dataBounds.getCenterX(), dataBounds.getCenterY()));
                zoomDisplay.setText(String.format("%1.2f", getLookup().lookup(
                        HeatmapDataset.class).getZoom().getSecond()));
            }
        };
        SwingUtilities.invokeLater(r);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void pickedSpotsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pickedSpotsCheckBoxActionPerformed
//        showPickedSpots = pickedSpotsCheckBox.isSelected();
        setSpotsToDraw();
    }//GEN-LAST:event_pickedSpotsCheckBoxActionPerformed

    private void tooltipsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tooltipsCheckBoxActionPerformed
        getLookup().lookup(
                net.sf.maltcms.ui.plot.heatmap.painter.ToolTipPainter.class).
                setDrawLabels(tooltipsCheckBox.isSelected());
    }//GEN-LAST:event_tooltipsCheckBoxActionPerformed

    private void centerCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerCheckBoxActionPerformed
        centerView = centerCheckBox.isSelected();
    }//GEN-LAST:event_centerCheckBoxActionPerformed

    private void syncCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncCheckBoxActionPerformed
        getLookup().lookup(SyncOnSpotGroupToken.class).setSyncOnSpotGroup(
                syncCheckBox.isSelected());
    }//GEN-LAST:event_syncCheckBoxActionPerformed

    private void viewSettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewSettingsButtonActionPerformed
        jPopupMenu1.show(this,
                viewSettingsButton.getX(), viewSettingsButton.getY());
    }//GEN-LAST:event_viewSettingsButtonActionPerformed

    private void unpickedSpotsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unpickedSpotsCheckBoxActionPerformed
//        showUnpickedSpots = unpickedSpotsCheckBox.isSelected();
        setSpotsToDraw();
    }//GEN-LAST:event_unpickedSpotsCheckBoxActionPerformed

    private void locationIndicatorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationIndicatorCheckBoxActionPerformed
        getLookup().lookup(LocationRectanglePainter.class).
                setDrawLocationIndicator(locationIndicatorCheckBox.isSelected());
    }//GEN-LAST:event_locationIndicatorCheckBoxActionPerformed

    private void unlabeledSpotsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unlabeledSpotsCheckBoxActionPerformed
//        showUnlabeledSpots = unlabeledSpotsCheckBox.isSelected();
        setSpotsToDraw();
    }//GEN-LAST:event_unlabeledSpotsCheckBoxActionPerformed

    private void labeledSpotsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labeledSpotsCheckBoxActionPerformed
//        showLabeledSpots = labeledSpotsCheckBox.isSelected();
        setSpotsToDraw();
    }//GEN-LAST:event_labeledSpotsCheckBoxActionPerformed

    private void crossHairCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crossHairCheckBoxActionPerformed
        getLookup().lookup(
                net.sf.maltcms.ui.plot.heatmap.painter.ToolTipPainter.class).
                setDrawCrossHair(crossHairCheckBox.isSelected());
    }//GEN-LAST:event_crossHairCheckBoxActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem centerCheckBox;
    private javax.swing.JCheckBoxMenuItem crossHairCheckBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JCheckBoxMenuItem labeledSpotsCheckBox;
    private javax.swing.JCheckBoxMenuItem locationIndicatorCheckBox;
    private javax.swing.JCheckBoxMenuItem pickedSpotsCheckBox;
    private javax.swing.JCheckBoxMenuItem syncCheckBox;
    private javax.swing.JCheckBoxMenuItem tooltipsCheckBox;
    private javax.swing.JCheckBoxMenuItem unlabeledSpotsCheckBox;
    private javax.swing.JCheckBoxMenuItem unpickedSpotsCheckBox;
    private javax.swing.JButton viewSettingsButton;
    private javax.swing.JTextField zoomDisplay;
    // End of variables declaration//GEN-END:variables

    private void setSpotsToDraw() {
        HeatmapDataset ds = getLookup().lookup(HeatmapDataset.class);
        if (ds != null) {
            Iterator<?> iter2 = ds.getAnnotationIterator();
            while (iter2.hasNext()) {
                Tuple2D<Point2D, SpotAnnotation> tple = (Tuple2D<Point2D, SpotAnnotation>) iter2.next();
                SpotAnnotation spot = (SpotAnnotation) tple.getSecond();
                boolean drawSpot = false;
                boolean isUnlabeled = spot.getPayload().getLabel() == null || spot.getPayload().getLabel().isEmpty();
                if (unlabeledSpotsCheckBox.isSelected() && isUnlabeled) {
                    drawSpot = true;
                }
                if (labeledSpotsCheckBox.isSelected() && !isUnlabeled) {
                    drawSpot = true;
                }
                if (drawSpot) {
                    drawSpot = false;
                    boolean isPicked = (spot.getPayload().getStatus() == SpotStatus.PICKED);
                    if (pickedSpotsCheckBox.isSelected() && isPicked) {
                        drawSpot = true;
                    }
                    if (unpickedSpotsCheckBox.isSelected() && !isPicked) {
                        drawSpot = true;
                    }
                }
                spot.setDrawSpotBox(drawSpot);
                spot.setDrawSpotId(drawSpot);
            }
            Component c = FocusManager.getCurrentManager().getFocusOwner();
            requestFocusInWindow(true);
            //layer.repaint();
            revalidate();
            repaint();
//            FocusManager.getCurrentManager().focusNextComponent(c);
        }
    }
//    /**
//     * Gets default instance. Do not use directly: reserved for *.settings files only,
//     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
//     * To obtain the singleton instance, use {@link #findInstance}.
//     */
//    public static synchronized GelViewerTopComponent getDefault() {
//        if (instance == null) {
//            instance = new GelViewerTopComponent();
//        }
//        return instance;
//    }
//
//    /**
//     * Obtain the GelViewerTopComponent instance. Never call {@link #getDefault} directly!
//     */
//    public static synchronized GelViewerTopComponent findInstance() {
//        TopComponent win = WindowManager.getDefault().findTopComponent(
//                PREFERRED_ID);
//        if (win == null) {
//            Logger.getLogger(GelViewerTopComponent.class.getName()).warning(
//                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
//            return getDefault();
//        }
//        if (win instanceof GelViewerTopComponent) {
//            return (GelViewerTopComponent) win;
//        }
//        Logger.getLogger(GelViewerTopComponent.class.getName()).warning(
//                "There seem to be multiple components with the '" + PREFERRED_ID
//                + "' ID. That is a potential source of errors and unexpected behavior.");
//        return getDefault();
//    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    protected void componentActivated() {
        super.componentActivated();
        requestFocusInWindow();
    }

    @Override
    public void componentOpened() {
        super.componentOpened();
        result = Utilities.actionsGlobalContext().lookupResult(IGel.class);
        result.addLookupListener(this);
        lookupListeners.register(Utilities.actionsGlobalContext());
        resultChanged(new LookupEvent(result));
        lookupListeners.resultChanged(new LookupEvent(Utilities.actionsGlobalContext().lookupResult(ISpot.class)));
        lookupListeners.resultChanged(new LookupEvent(Utilities.actionsGlobalContext().lookupResult(ISpotGroup.class)));
        requestActive();
    }

    @Override
    public void componentClosed() {
        super.componentClosed();
        if (result != null) {
            result.removeLookupListener(this);
        }
        lookupListeners.deregister();
        IGel gel = getLookup().lookup(IGel.class);
        if (gel != null) {
            Lookup.getDefault().lookup(IRegistryFactory.class).getDefault().
                    closeTopComponentsFor(gel);
        }
    }

    void writeProperties(java.util.Properties p) {
		// better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        if (result != null) {
            Collection<? extends IGel> c = result.allInstances();
            if (c.size() > 1) {
                throw new IllegalArgumentException(
                        "Found more than one instance of IGel in lookup!");
            } else if (!c.isEmpty()) {
                IGel gel = c.iterator().next();
                setGel(gel);
            }
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("annotationPointSelection".equals(evt.getPropertyName())) {
            System.out.println("GelViewer for gel " + getLookup().lookup(
                    IGel.class) + " received annotationPointSelection: " + evt.getNewValue());
            Tuple2D<Point2D, SpotAnnotation> newAnnotation = (Tuple2D<Point2D, SpotAnnotation>) evt.getNewValue();
            Tuple2D<Point2D, SpotAnnotation> oldAnnotation = (Tuple2D<Point2D, SpotAnnotation>) evt.getOldValue();
            if (oldAnnotation != null) {
                removeAnnotation(oldAnnotation.getSecond());
            }
            //adjust current selection
            setSelection(newAnnotation);
//            repaint();
        } else {
//            repaint();
        }
        setSpotsToDraw();
        jl.revalidate();
        jl.repaint();
    }

    protected void setSelection(
            Tuple2D<Point2D, SpotAnnotation> newAnnotation) {
        Point2D startPoint = null;
        if (annotation != null) {
            startPoint = getLookup().lookup(HeatmapDataset.class).toViewPoint(
                    annotation.getFirst());
        }
        if (newAnnotation == null) {
            resetSelection();
        } else {
            if (annotation != newAnnotation) {
//            if (annotation != null) {
                resetSelection();
//            }
                SpotAnnotation sa = newAnnotation.getSecond();
                if (sa != null) {
                    ic.add(sa);
                    ISpot spot = newAnnotation.getSecond().getPayload();
                    if (spot != null) {
                        ic.add(spot);
                        ISpotGroup group = spot.getGroup();
                        if (group != null) {
                            ic.add(group);
                        }
                    }
                    annotation = newAnnotation;
                    annotation.getSecond().setSelected(true);
                    //sa.addPropertyChangeListener(this);
                    if (centerView) {
                        HeatmapDataset hd = getLookup().lookup(
                                HeatmapDataset.class);
                        Point2D transformedCenter;
                        transformedCenter = hd.toViewPoint(
                                annotation.getFirst());
                        Rectangle target = new Rectangle((int) (transformedCenter.getX() - jl.getVisibleRect().width / 2), (int) (transformedCenter.getY() - jl.getVisibleRect().height / 2), jl.getVisibleRect().width,
                                jl.getVisibleRect().height);
                        System.out.println("Scrolling to rectangle " + target);
                        jl.scrollRectToVisible(target);

//                    transformedCenter = transform.transform(
//                            annotation.getFirst(), null);
                    }
                }
            }
        }

    }

    protected void removeAnnotation(SpotAnnotation sa) {
        if (sa != null) {
            //sa.removePropertyChangeListener(this);
            ic.remove(sa);
            ISpot oldSpot = sa.getPayload();
            if (oldSpot != null) {
                ic.remove(oldSpot);
                ISpotGroup group = oldSpot.getGroup();
                if (group != null) {
                    ic.remove(group);
                }
            }
        }
    }

    protected void resetSelection() {
        if (annotation != null) {
            annotation.getSecond().setSelected(false);
            removeAnnotation(annotation.getSecond());
        }
        annotation = null;
    }

    @Override
    public void listen(Tuple2D<Point2D, Double> t,
            net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent me) {
        zoomDisplay.setText(String.format("%.2f", t.getSecond()));
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.isPopupTrigger() || me.getButton() == MouseEvent.BUTTON3) {
            JPopupMenu popup = new JPopupMenu();
            if (annotation != null) {

                final ISpot spot = annotation.getSecond().getPayload();
                if (spot.getWell() != null) {
                    popup.add(new AbstractAction(
                            "Open 96 Well Plate") {
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                                    IPlate96OpenCookie plate96OpenCookie = Lookup.getDefault().
                                    lookup(IPlate96OpenCookie.class);
                                    ic.add(spot.getWell().getParent());
                                    plate96OpenCookie.open();
                                    ic.remove(spot.getWell().getParent());
                                }
                            });
                    if (spot.getWell().get384Wells() != null && !spot.getWell().
                            get384Wells().isEmpty()) {
                        popup.add(new AbstractAction(
                                "Open 384 Well Plates") {
                                    @Override
                                    public void actionPerformed(ActionEvent ae) {
                                        for (IWell384 well384 : spot.getWell().
                                        get384Wells()) {
                                            IPlate384OpenCookie plate384OpenCookie = Lookup.getDefault().
                                            lookup(IPlate384OpenCookie.class);
                                            ic.add(well384.getParent());
                                            //TODO add project to lookup
                                            plate384OpenCookie.open();
                                            ic.remove(well384.getParent());
                                        }
                                    }
                                });
                    }
                    popup.add(new JSeparator());
                }

                popup.add(new AbstractAction("Properties") {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        ThemeManager tm = ThemeManager.getInstance();
                        HeatmapDataset ds = getLookup().lookup(
                                HeatmapDataset.class);
                        if (ds != null && annotation != null) {
                            SpotAnnotation sa = (SpotAnnotation) annotation.getSecond();
                            PropertySheet ps = new PropertySheet();
                            try {
                                ps.setNodes(new Node[]{new BeanNode(sa)});
                            } catch (IntrospectionException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                            JPanel jp = new JPanel();
                            BoxLayout bl = new BoxLayout(jp, BoxLayout.Y_AXIS);
                            jp.setLayout(bl);
                            jp.add(ps);
                            ButtonGroup bg = new ButtonGroup();
                            final JRadioButton applyToCurrent = new JRadioButton(
                                    "Apply Settings to Active Spot", true);
                            final JRadioButton applyToAllOnGel = new JRadioButton(
                                    "Apply Settings to All Spots On Gel", false);
                            final JRadioButton applyToSpotsInGroup = new JRadioButton(
                                    "Apply Settings to Spot Group", false);
                            final JRadioButton applyToAll = new JRadioButton(
                                    "Apply Settings to All Spots", false);
                            bg.add(applyToCurrent);
                            bg.add(applyToSpotsInGroup);
                            bg.add(applyToAllOnGel);
                            bg.add(applyToAll);

                            JPanel radioPanel = new JPanel(new GridLayout(0, 1));
                            radioPanel.add(applyToCurrent);
                            radioPanel.add(applyToSpotsInGroup);
                            radioPanel.add(applyToAllOnGel);
                            radioPanel.add(applyToAll);
                            jp.add(radioPanel);
                            NotifyDescriptor nd = new NotifyDescriptor.Confirmation(
                                    jp,
                                    NotifyDescriptor.OK_CANCEL_OPTION,
                                    NotifyDescriptor.PLAIN_MESSAGE);
                            Object obj = DialogDisplayer.getDefault().notify(nd);
                            if (obj == NotifyDescriptor.OK_OPTION) {
                                if (applyToSpotsInGroup.isSelected()) {
                                    ISpot spot = annotation.getSecond().
                                            getPayload();
                                    SpotAnnotation templateSpotAnnotation = (SpotAnnotation) annotation.getSecond();
                                    ISpotGroup spotGroup = spot.getGroup();
                                    if (spotGroup != null) {
                                        for (ISpot other : spotGroup.getSpots()) {
                                            if (other != spot) {

                                                SpotAnnotation otherSpotAnnotation = AnnotationManager.getSpotAnnotation(getLookup().
                                                        lookup(
                                                                IProteomicProject.class),
                                                        other);
                                                if (otherSpotAnnotation != null) {
                                                    setSpotProperties(
                                                            otherSpotAnnotation,
                                                            templateSpotAnnotation);
                                                }
                                            }
                                        }
                                    }
                                } else if (applyToAllOnGel.isSelected()) {
                                    Iterator<?> iter2 = ds.getAnnotationIterator();
                                    while (iter2.hasNext()) {
                                        Tuple2D<Point2D, SpotAnnotation> tple = (Tuple2D<Point2D, SpotAnnotation>) iter2.next();
                                        SpotAnnotation spot = (SpotAnnotation) tple.getSecond();
                                        setSpotProperties(spot, sa);
                                    }
                                } else if (applyToAll.isSelected()) {
                                    SpotAnnotation templateSpotAnnotation = (SpotAnnotation) annotation.getSecond();
                                    for (GelSpotAnnotations gsa : AnnotationManager.getAnnotations(getLookup().
                                            lookup(
                                                    IProteomicProject.class))) {
                                        for (SpotAnnotation sann : gsa.getSpotAnnotations()) {
                                            setSpotProperties(
                                                    sann,
                                                    templateSpotAnnotation);
                                        }
                                    }
                                }
                                repaint();
                            }
                        }
                    }

                    private void setSpotProperties(SpotAnnotation spot,
                            SpotAnnotation template) {
                        spot.setFillColor(template.getFillColor());
                        spot.setLineColor(template.getLineColor());
                        spot.setFont(template.getFont());
                        spot.setSelectedFillColor(
                                template.getSelectedFillColor());
                        spot.setSelectedStrokeColor(template.getSelectedStrokeColor());
                        spot.setSelectionCrossColor(template.getSelectionCrossColor());
                        spot.setStrokeColor(template.getStrokeColor());
                        spot.setTextColor(template.getTextColor());
                        spot.setDrawSpotBox(template.isDrawSpotBox());
                        spot.setDrawSpotId(template.isDrawSpotId());
                        spot.setDisplacementX(template.getDisplacementX());
                        spot.setDisplacementY(template.getDisplacementY());
                        spot.setFillAlpha(template.getFillAlpha());
                        spot.setStrokeAlpha(template.getStrokeAlpha());
                    }
                });
            }

            popup.show(me.getComponent(), me.getX(), me.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }
}