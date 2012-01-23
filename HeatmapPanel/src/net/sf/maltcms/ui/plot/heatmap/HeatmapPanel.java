/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.ui.plot.heatmap;

import cross.datastructures.tuple.Tuple2D;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.RepaintManager;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import net.sf.maltcms.ui.plot.heatmap.actions.AddAnnotation;
import net.sf.maltcms.ui.plot.heatmap.actions.RemoveAnnotation;
import net.sf.maltcms.ui.plot.heatmap.axis.Corner;
import net.sf.maltcms.ui.plot.heatmap.event.IProcessorResultListener;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEventProcessorChain;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEventType;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.PointSelectionProcessor;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.ZoomProcessor;
import net.sf.maltcms.ui.plot.heatmap.painter.AnnotationPainter;
import net.sf.maltcms.ui.plot.heatmap.painter.PainterLayerUI;
import net.sf.maltcms.ui.plot.heatmap.painter.ToolTipPainter;
import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.painter.CompoundPainter;

/**
 * @author nilshoffmann
 */
public class HeatmapPanel<T> extends JComponent implements
        PropertyChangeListener, IProcessorResultListener<Point2D>, Scrollable {

    private final BufferedImage bi;
    private final HeatmapDataset<T> hd;
    private AffineTransform at;
    private Dimension preferredViewportSize = new Dimension(800, 600);

    public HeatmapPanel(HeatmapDataset<T> hd) {
        this.bi = hd.getImage();
        this.hd = hd;
        this.hd.addPropertyChangeListener(this);
        setPreferredSize(new Dimension(this.bi.getWidth(), this.bi.getHeight()));
        setAutoscrolls(true);
//        addMouseListener(this);
//        addMouseMotionListener(this);
//        addMouseWheelListener(this);
        setPreferredScrollableViewportSize(
                new Dimension(Math.min(preferredViewportSize.width,
                getPreferredSize().width), Math.min(preferredViewportSize.height,
                getPreferredSize().height)));
    }
    
    public BufferedImage getImage() {
        return bi;
    }

    @Override
    public void print(Graphics g) {
        System.out.println("Print called with clip: "+g.getClip());
        Rectangle clipRect = new Rectangle(getPreferredSize().width,getPreferredSize().height);
        g.setClip(clipRect);
        g.drawImage(bi,0,0,null);
    }

    @Override
    public void printAll(Graphics g) {
        print(g);
    }

    @Override
    protected void printBorder(Graphics g) {
        print(g);
    }

    @Override
    protected void printChildren(Graphics g) {
        print(g);
    }

    @Override
    protected void printComponent(Graphics g) {
        print(g);
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.drawImage(bi, at, null);
        g2.dispose();
    }

    @Override
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i3);
        hd.setRegionOfInterest(new Rectangle2D.Double(i, i1, i2, i3));
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if (pce.getPropertyName().equals(HeatmapDataset.PROP_TRANSFORM)) {
            this.at = (AffineTransform) pce.getNewValue();
            firePropertyChange(HeatmapDataset.PROP_TRANSFORM, pce.getOldValue(),
                    pce.getNewValue());
        } else if (pce.getPropertyName().equals(HeatmapDataset.PROP_ZOOM)) {
            System.out.println("Received PROP_ZOOM change event");
            Tuple2D<Point2D, Double> t = (Tuple2D<Point2D, Double>) pce.getNewValue();
            double zoom = t.getSecond();
            Dimension newPreferredSize = new Dimension((int) (hd.getDataBounds().
                    getWidth() * zoom),
                    (int) (hd.getDataBounds().getHeight() * zoom));
            setPreferredSize(newPreferredSize);
            this.at = hd.getTransform();
            revalidate();
            RepaintManager.currentManager(this).markCompletelyDirty(this);
            RepaintManager.currentManager(this).paintDirtyRegions();
            System.out.println("Zoom occurred around "+t.getFirst());
            scrollRectToVisible(new Rectangle2D.Double(
                    t.getFirst().getX() - getPreferredSize().width / 2,
                    t.getFirst().getY() - getPreferredSize().height / 2,
                    getPreferredSize().width, getPreferredSize().height).getBounds());
        } else {
            revalidate();
//            RepaintManager.currentManager(this).markCompletelyDirty(this);
//            RepaintManager.currentManager(this).paintDirtyRegions();
        }
    }

    @Override
    public void listen(Point2D t,
            net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent et) {
        if (et.getMet() == MouseEventType.DRAGGED) {
            scrollRectToVisible(new Rectangle((int) t.getX(), (int) t.getY(), 1,
                    1));
        }
    }

    public void setPreferredScrollableViewportSize(Dimension d) {
        this.preferredViewportSize = d;
        revalidate();
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return this.preferredViewportSize;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle rctngl, int i, int i1) {
        return Math.min(1, Math.max(getVisibleRect().width,
                getVisibleRect().height) / 100);
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle rctngl, int i, int i1) {
        return Math.min(10, Math.max(getVisibleRect().width,
                getVisibleRect().height) / 10);
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    public static void main(String[] args) {
        String[] files = {"/resources/80547_255_75_101.jpeg"};//"/resources/100414_Standard_FAME_1_50Hz.png"
        int file = 0;
        for (String s : files) {
            try {
                final JXFrame jf = new JXFrame("HeatmapDemo", true);
                HeatmapDataset<List<Integer>> hmd = null;
                BufferedImage bi = null;
                if (file == 0) {
                    bi = ImageIO.read(HeatmapPanel.class.getResource(s));
                    hmd = new HeatmapDataset<List<Integer>>(new HeatmapDataProvider(
                            bi, new Point2D.Double(0, bi.getHeight())));
                } else {
                    bi = ImageIO.read(HeatmapPanel.class.getResource(s));
                    hmd = new HeatmapDataset<List<Integer>>(new HeatmapDataProvider(
                            bi));
                }
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
                annotationPainter.setSearchRadius(20.0d);

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
                zoomProcessor.setMinZoom(0.5d);
                zoomProcessor.setMaxZoom(2.0d);
                zoomProcessor.setZoomDelta(0.5d);

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

                jf.getContentPane().add(jsp, BorderLayout.CENTER);

                //lets see, how good we can handle annotations!
                int maxAnnotations = (int) (0.0001 * hmd.getDataBounds().
                        getWidth() * hmd.getDataBounds().getHeight());
                System.out.println("Adding " + maxAnnotations + " annotations!");

                for (int i = 0; i < maxAnnotations; i++) {
                    Point2D p2 = new Point2D.Double((int) (Math.random() * (hmd.getDataBounds().getWidth() - 1)),
                            (int) (Math.random() * (hmd.getDataBounds().
                            getHeight() - 1)));
                    annotationPainter.setActivePoint(p2);
                    //invoke addAnnotation action
                    annotationPainter.addAnnotation();
                }

                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        jf.setVisible(true);
                        jf.pack();
                        //import, otherwise, no key events are forwarded
                        layer.requestFocus();
                    }
                });
                file++;
            } catch (IOException ex) {
                Logger.getLogger(HeatmapPanel.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }
}
