package de.unibielefeld.gi.kotte.laborprogramm.gelViewer;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.sf.maltcms.ui.plot.heatmap.Annotation;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataProvider;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;
import net.sf.maltcms.ui.plot.heatmap.HeatmapPanel;
import net.sf.maltcms.ui.plot.heatmap.IDataProvider;
import net.sf.maltcms.ui.plot.heatmap.actions.AddAnnotation;
import net.sf.maltcms.ui.plot.heatmap.actions.RemoveAnnotation;
import net.sf.maltcms.ui.plot.heatmap.axis.Corner;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEventProcessorChain;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.PointSelectionProcessor;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.ZoomProcessor;
import net.sf.maltcms.ui.plot.heatmap.painter.AnnotationPainter;
import net.sf.maltcms.ui.plot.heatmap.painter.PainterLayerUI;
import net.sf.maltcms.ui.plot.heatmap.painter.ToolTipPainter;
import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.openide.util.Exceptions;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
//import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.windows.CloneableTopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//de.unibielefeld.gi.kotte.laborprogramm.gelViewer//GelViewer//EN",
autostore = false)
public final class GelViewerTopComponent extends CloneableTopComponent implements LookupListener {

    private static GelViewerTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "GelViewerTopComponent";
    private IGel gel = null;
    private Result<IGel> result = null;

    public GelViewerTopComponent() {
        setGel(Utilities.actionsGlobalContext().lookup(IGel.class));
        //setGel(CentralLookup.getDefault().lookup(IGel.class));
//        Template<IGel> gelTemplate = new Template<IGel>(IGel.class);
//        result = CentralLookup.getDefault().lookup(gelTemplate);
//        result.addLookupListener(this);

        initComponents();

        setName(NbBundle.getMessage(GelViewerTopComponent.class, "CTL_GelViewerTopComponent"));
        setToolTipText(NbBundle.getMessage(GelViewerTopComponent.class, "HINT_GelViewerTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

    }

    private void setGel(IGel gel) {
        if (this.gel != null) {
            result.removeLookupListener(this);
            result = null;
            return;
        }
        if (this.gel == null && gel != null) {
            this.gel = gel;
            //CentralLookup.getDefault().remove(gel);
            //String name = "gel: " + gel.getName() + " of experiment " + gel.getParent().getParent().getParent().getParent().getName();
//            Logger.getLogger(GelViewerTopComponent.class.getName()).info(
//                    "Opening " + name);
//            JLabel jl = new JLabel(gel.getName());
//            setLayout(new BorderLayout());
//            add(jl, BorderLayout.NORTH);
            setDisplayName(this.gel.getName());
            validate();
            addHeatmapPanel(this.gel);
        }
    }

    private void addHeatmapPanel(IGel gel) {
        //TODO fixme
//        File file = new File(gel.getFilename());
        File file = new File("/vol/maltcms/kotte/ProteomikLaborProgramm/Sample/Delta 2D/Konstantin/gelImages/1284023623027");
        HeatmapDataset<List<Integer>> hmd = null;
        BufferedImage bi = null;
//        try {
            //        if (file == 0) {
            //            bi = ImageIO.read(HeatmapPanel.class.getResource(s));
            //            hmd = new HeatmapDataset<List<Integer>>(new HeatmapDataProvider(
            //                    bi, new Point2D.Double(0, bi.getHeight())));
            //        } else {
            //
            //        }
            PlanarImage im = null;
            im = JAI.create("fileload", file.getAbsolutePath());
            bi = im.getAsBufferedImage();
//            bi = ImageIO.read(file);
            hmd = new HeatmapDataset<List<Integer>>(new HeatmapDataProvider(
                    bi));
            final HeatmapPanel<List<Integer>> jl = new HeatmapPanel<List<Integer>>(
                    hmd);
            hmd.addPropertyChangeListener(jl);
            final IDataProvider hdp = hmd.getDataProvider();
            //create a tooltip painter for the payload type (here: List<Float>)
            ToolTipPainter<List<Integer>, JPanel> tooltipPainter = new ToolTipPainter<List<Integer>, JPanel>() {

                @Override
                public String getStringFor(Annotation<List<Integer>> t) {
                    Point2D dataCoord = hdp.getViewToModelTransform().
                            transform(t.getPosition(), null);
                    return String.format("x=%.2f, y=%.2f; values=",
                            dataCoord.getX(), dataCoord.getY()) + t.getPayload();//"x="+t.getPosition().getX()+" values: "+t.getPayload();
                }
            };
            //register ToolTipPainter to receive events from dataset
            hmd.addPropertyChangeListener(tooltipPainter);

            //create an annotation painter
            AnnotationPainter<List<Integer>, JPanel> annotationPainter = new AnnotationPainter<List<Integer>, JPanel>(
                    hmd);
            annotationPainter.setSearchRadius(5.0d);

            //wire tooltip painter to annotation painter, to display selected annotations
//                tooltipPainter.addPropertyChangeListener(annotationPainter);
            annotationPainter.addPropertyChangeListener(tooltipPainter);

            //the PointSelectionProcessor will handle selection of single points
            PointSelectionProcessor pointSelectionProcessor = new PointSelectionProcessor();
            //register tooltip painter to receive events only, if shift is down
            pointSelectionProcessor.addListener(tooltipPainter,
                    MouseEvent.SHIFT_DOWN_MASK);
            pointSelectionProcessor.addListener(tooltipPainter,
                    MouseEvent.BUTTON1_DOWN_MASK);
            //register annotation painter to receive all events
            pointSelectionProcessor.addListener(annotationPainter,
                    MouseEvent.SHIFT_DOWN_MASK);
            pointSelectionProcessor.addListener(annotationPainter,
                    MouseEvent.BUTTON1_DOWN_MASK);

            //create actions
            //add an annotation at active position
            AddAnnotation aa = new AddAnnotation();
            aa.setAnnotationPainter(annotationPainter);
            //invoke on ctrl+left mouse
            pointSelectionProcessor.addListener(aa,
                    MouseEvent.CTRL_DOWN_MASK | MouseEvent.BUTTON1_DOWN_MASK);

            //remove an annotation at active position
            RemoveAnnotation ra = new RemoveAnnotation();
            ra.setAnnotationPainter(annotationPainter);
            //invoke on ctrl+right mouse
            pointSelectionProcessor.addListener(ra,
                    MouseEvent.CTRL_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK);

            //SelectionRectanglePainter srp = new SelectionRectanglePainter();
            //RectangularSelectionProcessor rsp = new RectangularSelectionProcessor();
//                rsp.addListener(annotationPainter,MouseEvent.BUTTON1_DOWN_MASK);
            //rsp.addListener(srp);

            //create a zoom processor
            ZoomProcessor zoomProcessor = new ZoomProcessor();
            //register dataset for zoom events
            zoomProcessor.addListener(hmd);
            zoomProcessor.setMinZoom(0.25d);
            zoomProcessor.setMaxZoom(4.0d);
            zoomProcessor.setZoomDelta(0.25d);

            //set up the mouse event processor chain with zoomProcessor and pointSelectionProcessor
            MouseEventProcessorChain mouseEventProcessorChain = new MouseEventProcessorChain(
                    zoomProcessor, pointSelectionProcessor);

            //Compound painter and jxlayer
            CompoundPainter<JComponent> compoundPainter = new CompoundPainter<JComponent>(
                    annotationPainter, tooltipPainter);
            PainterLayerUI plui = new PainterLayerUI(compoundPainter);
            final JXLayer<JComponent> layer = new JXLayer<JComponent>(jl,
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

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
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
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        result = Utilities.actionsGlobalContext().lookupResult(IGel.class);
        result.addLookupListener(this);
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
        Collection<? extends IGel> c = result.allInstances();
        if (c.size() > 1) {
            throw new IllegalArgumentException("Found more than one instance of IGel in lookup!");
        }
        if (c.size() == 1) {
            System.out.println("Setting gel!");
            setGel(c.iterator().next());
        }
    }
}
