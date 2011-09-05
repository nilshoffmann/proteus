package de.unibielefeld.gi.kotte.laborprogramm.gelViewer;

import cross.datastructures.tuple.Tuple2D;
import de.unibielefeld.gi.kotte.laborprogramm.centralLookup.CentralLookup;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.actions.ExportGelAction;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.dataProvider.GelSpotDataProvider;
import de.unibielefeld.gi.kotte.laborprogramm.gelViewer.theme.ThemeManager;
import de.unibielefeld.gi.kotte.laborprogramm.project.api.IProteomicProject;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.String;
import java.util.Collection;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import net.sf.maltcms.ui.plot.heatmap.Annotation;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;
import net.sf.maltcms.ui.plot.heatmap.HeatmapPanel;
import net.sf.maltcms.ui.plot.heatmap.IDataProvider;
import net.sf.maltcms.ui.plot.heatmap.axis.Corner;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEventProcessorChain;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.PointSelectionProcessor;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.ZoomProcessor;
import net.sf.maltcms.ui.plot.heatmap.painter.AnnotationPainter;
import net.sf.maltcms.ui.plot.heatmap.painter.PainterLayerUI;
import net.sf.maltcms.ui.plot.heatmap.painter.ToolTipPainter;
import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * TODO allow setting of font size property
 * TODO allow setting of focused view
 * TODO add support for highlighting / scrolling to a specific annotation
 * Top component that displays a gel with spot annotations and allows selection
 * of associated ISpot objects.
 */
@ConvertAsProperties(dtd = "-//de.unibielefeld.gi.kotte.laborprogramm.gelViewer//GelViewer//EN",
autostore = false)
public final class GelViewerTopComponent extends TopComponent implements
        LookupListener, PropertyChangeListener {

    private static GelViewerTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "GelViewerTopComponent";
    private InstanceContent ic = new InstanceContent();
    private Tuple2D<Point2D, Annotation<ISpot>> annotation;
    private Result<IGel> result;
    private JXLayer<JComponent> layer;

    public GelViewerTopComponent() {
        associateLookup(new AbstractLookup(ic));
        initComponents();
        setFocusable(true);
        setName(NbBundle.getMessage(GelViewerTopComponent.class,
                "CTL_GelViewerTopComponent"));
        setToolTipText(NbBundle.getMessage(GelViewerTopComponent.class,
                "HINT_GelViewerTopComponent"));

        IGel gel = CentralLookup.getDefault().lookup(IGel.class);
        if (gel != null) {
            setGel(gel);
        }
    }

    public void setGel(IGel gel) {
        System.out.println("Setting gel " + gel);
        if (getLookup().lookup(IGel.class) == null && gel != null) {
            this.ic.add(gel);
            CentralLookup.getDefault().remove(gel);
            setDisplayName(gel.getName());
            addHeatmapPanel(gel);//deregister
            result.removeLookupListener(this);
            putClientProperty("print.printable", Boolean.TRUE);
        } else if (gel == null) {
            putClientProperty("print.printable", Boolean.FALSE);
            throw new IllegalStateException("Gel can not be null!");
        } else {
            putClientProperty("print.printable", Boolean.FALSE);
            throw new IllegalStateException("Gel can only be set once on GelViewerTopComponent!");
        }
    }

    private void addHeatmapPanel(IGel gel) {
        System.out.println("Loading gel " + gel.getFilename());
        File file = new File(gel.getFilename());
        if (file.isAbsolute()) {
            if (!file.exists()) {
                Exceptions.printStackTrace(new FileNotFoundException("Could not find referenced file " + file.getPath() + " for gel " + gel.getName()));
                return;
            }
        } else {//resolve relative gel path
            System.out.println("Resolving gel path agains project basedir!");
            IProteomicProject p = Utilities.actionsGlobalContext().lookup(IProteomicProject.class);
            System.out.println("Using project: " + p);
            file = new File(FileUtil.toFile(p.getProjectDirectory()), file.getPath());
        }
        HeatmapDataset<ISpot> hmd = null;
        hmd = new HeatmapDataset<ISpot>(new GelSpotDataProvider(file, gel));

        ic.add(hmd);
        final HeatmapPanel<ISpot> jl = new HeatmapPanel<ISpot>(
                hmd);
        hmd.addPropertyChangeListener(this);

        ThemeManager tm = ThemeManager.getInstance();
        for (ISpot spot : gel.getSpots()) {
            spot.addPropertyChangeListener(jl);
            Annotation<ISpot> ann = new SpotAnnotation(new Point2D.Double(spot.getPosX(), spot.getPosY()), spot);
            
            hmd.addAnnotation(new Point2D.Double(spot.getPosX(), spot.getPosY()),
                    ann);
        }
        final IDataProvider hdp = hmd.getDataProvider();
        //create a tooltip painter for the payload type (here: List<Float>)
        ToolTipPainter<ISpot, JPanel> tooltipPainter = new ToolTipPainter<ISpot, JPanel>() {

            @Override
            public String getStringFor(Annotation<ISpot> t) {
                Point2D dataCoord = hdp.getViewToModelTransform().
                        transform(t.getPosition(), null);
                return String.format("[x=%.2f|y=%.2f] ",
                        dataCoord.getX(), dataCoord.getY()) + " Label: " + t.getPayload().getLabel() + " " + t.getPayload().getStatus();//"x="+t.getPosition().getX()+" values: "+t.getPayload();
            }
        };
        //register ToolTipPainter to receive events from dataset
        hmd.addPropertyChangeListener(tooltipPainter);
        ic.add(tooltipPainter);

        //create an annotation painter
        AnnotationPainter<ISpot, JPanel> annotationPainter = new AnnotationPainter<ISpot, JPanel>(
                hmd) {
        };
        annotationPainter.setSearchRadius(10.0d);

        //wire tooltip painter to annotation painter, to display selected annotations
//                tooltipPainter.addPropertyChangeListener(annotationPainter);
        annotationPainter.addPropertyChangeListener(tooltipPainter);
        annotationPainter.addPropertyChangeListener(this);

        ic.add(annotationPainter);
        
        //the PointSelectionProcessor will handle selection of single points
        PointSelectionProcessor pointSelectionProcessor = new PointSelectionProcessor();
        //register tooltip painter to receive events only, if shift is down
        pointSelectionProcessor.addListener(tooltipPainter,
                MouseEvent.BUTTON1_DOWN_MASK | MouseEvent.SHIFT_DOWN_MASK);
//        pointSelectionProcessor.addListener(tooltipPainter,
//                );
        //register annotation painter to receive all events
        pointSelectionProcessor.addListener(annotationPainter,
                MouseEvent.BUTTON1_DOWN_MASK);
//        pointSelectionProcessor.addListener(annotationPainter,
//                MouseEvent.BUTTON1_DOWN_MASK);

        ic.add(pointSelectionProcessor);

//        //create actions
//        //add an annotation at active position
//        AddAnnotation aa = new AddAnnotation();
//        aa.setAnnotationPainter(annotationPainter);
//        //invoke on ctrl+left mouse
//        pointSelectionProcessor.addListener(aa,
//                MouseEvent.CTRL_DOWN_MASK | MouseEvent.BUTTON1_DOWN_MASK);
//
//        //remove an annotation at active position
//        RemoveAnnotation ra = new RemoveAnnotation();
//        ra.setAnnotationPainter(annotationPainter);
//        //invoke on ctrl+right mouse
//        pointSelectionProcessor.addListener(ra,
//                MouseEvent.CTRL_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK);

//        SelectionRectanglePainter srp = new SelectionRectanglePainter();
//        RectangularSelectionProcessor rsp = new RectangularSelectionProcessor();
//        rsp.addListener(srp);

        //create a zoom processor
        ZoomProcessor zoomProcessor = new ZoomProcessor();
        //register dataset for zoom events
        zoomProcessor.addListener(hmd);
        zoomProcessor.setMinZoom(0.25d);
        zoomProcessor.setMaxZoom(4.0d);
        zoomProcessor.setZoomDelta(0.25d);
        ic.add(zoomProcessor);

        //set up the mouse event processor chain with zoomProcessor and pointSelectionProcessor
        MouseEventProcessorChain mouseEventProcessorChain = new MouseEventProcessorChain(
                zoomProcessor, pointSelectionProcessor);

        //Compound painter and jxlayer
        CompoundPainter<JComponent> compoundPainter = new CompoundPainter<JComponent>(
                annotationPainter, tooltipPainter);
        PainterLayerUI<JComponent> plui = new PainterLayerUI<JComponent>(compoundPainter);
        layer = new JXLayer<JComponent>(jl,
                plui);

        //register layer ui with HeatmapPanel for transformation events
        jl.addPropertyChangeListener(HeatmapDataset.PROP_TRANSFORM, plui);
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

        jsp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        jsp.setCorner(JScrollPane.UPPER_LEFT_CORNER, new Corner());
        jsp.setCorner(JScrollPane.LOWER_RIGHT_CORNER,
                new Corner());
        jsp.setCorner(JScrollPane.LOWER_LEFT_CORNER,
                new Corner());
        jsp.setCorner(JScrollPane.UPPER_RIGHT_CORNER,
                new Corner());
        
        add(jsp, BorderLayout.CENTER);

//        } catch (IOException ex) {
//            Exceptions.printStackTrace(ex);
//        }
        jToolBar1.add(new ExportGelAction("Save as image",jl,annotationPainter,gel));
        requestFocusInWindow(true);
        jl.requestFocusInWindow();
        revalidate();
//        repaint();
    }

    @Override
    public void print(Graphics g) {
        if(layer!=null) {
            layer.print(g);
        }
    }

    @Override
    public void printAll(Graphics g) {
        if(layer!=null) {
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
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        zoomDisplay = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();

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

        jCheckBox1.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(GelViewerTopComponent.class, "GelViewerTopComponent.jCheckBox1.text")); // NOI18N
        jCheckBox1.setFocusable(false);
        jCheckBox1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jCheckBox1);

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

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        getLookup().lookup(net.sf.maltcms.ui.plot.heatmap.painter.ToolTipPainter.class).setDrawLabels(jCheckBox1.isSelected());
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField zoomDisplay;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized GelViewerTopComponent getDefault() {
        if (instance == null) {
            instance = new GelViewerTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the GelViewerTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized GelViewerTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(
                PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(GelViewerTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof GelViewerTopComponent) {
            return (GelViewerTopComponent) win;
        }
        Logger.getLogger(GelViewerTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    public void componentOpened() {
        result = Utilities.actionsGlobalContext().lookupResult(IGel.class);
        result.addLookupListener(this);
        resultChanged(new LookupEvent(result));
        requestActive();
    }

    @Override
    public void componentClosed() {
        if (result != null) {
            result.removeLookupListener(this);
        }
        // TODO add custom code on component closing
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
            Tuple2D<Point2D, Annotation<ISpot>> newAnnotation = (Tuple2D<Point2D, Annotation<ISpot>>) evt.getNewValue();
            //reset current selection
            if (newAnnotation == null) {
                if (annotation != null) {
                    ic.remove(annotation.getSecond().getPayload());
                }
            } else {
                if (annotation != null) {
                    ic.remove(annotation.getSecond().getPayload());
                    System.out.println("Setting new annotation");
                    ic.add(newAnnotation.getSecond().getPayload());
                } else {
                    System.out.println("Setting new annotation");
                    ic.add(newAnnotation.getSecond().getPayload());
                }
                annotation = newAnnotation;
            }
        } else {
            repaint();
        }
    }
}
