/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.ta.Activatable;
import cross.datastructures.tuple.Tuple2D;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.IPropertyChangeSource;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import net.sf.maltcms.ui.plot.heatmap.Annotation;
import net.sf.maltcms.ui.plot.heatmap.painter.PainterTools;

/**
 *
 * @author nils
 */
public class SpotAnnotation extends Annotation<ISpot> implements Activatable {

//    /**
//     * PropertyChangeSupport ala JavaBeans(tm)
//     * Not persisted!
//     */
//    private transient PropertyChangeSupport pcs = null;
//
//    @Override
//    public synchronized void removePropertyChangeListener(
//            PropertyChangeListener listener) {
//        getPropertyChangeSupport().removePropertyChangeListener(listener);
//    }
//
//    @Override
//    public synchronized void addPropertyChangeListener(
//            PropertyChangeListener listener) {
//        getPropertyChangeSupport().addPropertyChangeListener(listener);
//    }
//
//    private PropertyChangeSupport getPropertyChangeSupport() {
//        if (this.pcs == null) {
//            this.pcs = new PropertyChangeSupport(this);
//        }
//        return this.pcs;
//    }

    private transient Activator activator;

    @Override
    public void bind(Activator activator) {
        if (this.activator == activator) {
            return;
        }
        if (activator != null && null != this.activator) {
            throw new IllegalStateException(
                    "Object can only be bound to one activator");
        }
        this.activator = activator;
    }

    @Override
    public void activate(ActivationPurpose activationPurpose) {
        if (null != activator) {
            activator.activate(activationPurpose);
        }
    }

    private boolean drawSpotId = true;
    private double displacementX = 5.0;
    private double displacementY = -5.0;
    private Color fillColor = new Color(255, 255, 255, 64);
    private Color strokeColor = Color.BLACK;
    private Color selectedFillColor = fillColor;
    private Color selectedStrokeColor = Color.ORANGE;
    private Color selectionCrossColor = Color.BLACK;
    private Color textColor = Color.BLACK;
    private Color lineColor = Color.BLACK;
    private double strokeAlpha = 1.0d;
    private double fillAlpha = 0.6d;
    private Font font = Font.decode("Plain-10");
    private boolean drawSpotBox = true;

    public SpotAnnotation(Point2D position, ISpot t) {
        super(position, t);
        setShape(new RoundRectangle2D.Double(position.getX() - 10, position.getY() - 10, 20, 20, 5, 5));
    }

    public static final String PROP_DRAWSPOTBOX = "drawSpotBox";

    public boolean isDrawSpotBox() {
        activate(ActivationPurpose.READ);
        return drawSpotBox;
    }

    public void setDrawSpotBox(boolean b) {
        activate(ActivationPurpose.WRITE);
        boolean old = this.drawSpotBox;
        this.drawSpotBox = b;
        getPropertyChangeSupport().firePropertyChange(PROP_DRAWSPOTBOX, old, b);
    }

    public static final String PROP_DISPLACEMENTX = "displacementX";
    public double getDisplacementX() {
        activate(ActivationPurpose.READ);
        return displacementX;
    }

    public void setDisplacementX(double displacementX) {
        activate(ActivationPurpose.WRITE);
        double old = this.displacementX;
        this.displacementX = displacementX;
        getPropertyChangeSupport().firePropertyChange(PROP_DISPLACEMENTX, old, displacementX);
    }

    public static final String PROP_DISPLACEMENTY = "displacementY";
    public double getDisplacementY() {
        activate(ActivationPurpose.READ);
        return displacementY;
    }

    public void setDisplacementY(double displacementY) {
        activate(ActivationPurpose.WRITE);
        double old = this.displacementY;
        this.displacementY = displacementY;
        getPropertyChangeSupport().firePropertyChange(PROP_DISPLACEMENTY, old, displacementY);
    }

    public static final String PROP_STROKEALPHA = "strokeAlpha";
    public double getStrokeAlpha() {
        activate(ActivationPurpose.READ);
        return strokeAlpha;
    }

    public void setStrokeAlpha(double strokeAlpha) {
        activate(ActivationPurpose.WRITE);
        double old = this.strokeAlpha;
        this.strokeAlpha = strokeAlpha;
        getPropertyChangeSupport().firePropertyChange(PROP_STROKEALPHA, old, strokeAlpha);
    }

    public static final String PROP_FILLALPHA = "fillAlpha";
    public double getFillAlpha() {
        activate(ActivationPurpose.READ);
        return fillAlpha;
    }

    public void setFillAlpha(double fillAlpha) {
        activate(ActivationPurpose.WRITE);
        double old = this.fillAlpha;
        this.fillAlpha = fillAlpha;
        getPropertyChangeSupport().firePropertyChange(PROP_FILLALPHA, old, fillAlpha);
    }

    public static final String PROP_DRAWSPOTID = "drawSpotId";
    public boolean isDrawSpotId() {
        activate(ActivationPurpose.READ);
        return drawSpotId;
    }

    public void setDrawSpotId(boolean drawSpotId) {
        activate(ActivationPurpose.WRITE);
        boolean old = this.drawSpotId;
        this.drawSpotId = drawSpotId;
        getPropertyChangeSupport().firePropertyChange(PROP_DRAWSPOTID, old, drawSpotId);
    }

    public static final String PROP_FILLCOLOR = "fillColor";
    public Color getFillColor() {
        activate(ActivationPurpose.READ);
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        activate(ActivationPurpose.WRITE);
        Color old = this.fillColor;
        this.fillColor = fillColor;
        getPropertyChangeSupport().firePropertyChange(PROP_FILLCOLOR, old, fillColor);
    }

    public static final String PROP_FONT = "font";
    public Font getFont() {
        activate(ActivationPurpose.READ);
        return font;
    }

    public void setFont(Font font) {
        activate(ActivationPurpose.WRITE);
        Font old = this.font;
        this.font = font;
        getPropertyChangeSupport().firePropertyChange(PROP_FONT, old, font);
    }

    public static final String PROP_LINECOLOR = "lineColor";
    public Color getLineColor() {
        activate(ActivationPurpose.READ);
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        activate(ActivationPurpose.WRITE);
        Color old = this.lineColor;
        this.lineColor = lineColor;
        getPropertyChangeSupport().firePropertyChange(PROP_LINECOLOR, old, lineColor);
    }

    public static final String PROP_SELECTEDFILLCOLOR = "selectedFillColor";
    public Color getSelectedFillColor() {
        activate(ActivationPurpose.READ);
        return selectedFillColor;
    }

    public void setSelectedFillColor(Color selectedFillColor) {
        activate(ActivationPurpose.WRITE);
        Color old = this.selectedFillColor;
        this.selectedFillColor = selectedFillColor;
        getPropertyChangeSupport().firePropertyChange(PROP_SELECTEDFILLCOLOR, old, selectedFillColor);
    }

    public static final String PROP_SELECTEDSTROKECOLOR = "selectedStrokeColor";
    public Color getSelectedStrokeColor() {
        activate(ActivationPurpose.READ);
        return selectedStrokeColor;
    }

    public void setSelectedStrokeColor(Color selectedStrokeColor) {
        activate(ActivationPurpose.WRITE);
        Color old = this.selectedStrokeColor;
        this.selectedStrokeColor = selectedStrokeColor;
        getPropertyChangeSupport().firePropertyChange(PROP_SELECTEDSTROKECOLOR,old,selectedStrokeColor);
    }

    public static final String PROP_SELECTIONCROSSCOLOR = "selectionCrossColor";
    public Color getSelectionCrossColor() {
        activate(ActivationPurpose.READ);
        return selectionCrossColor;
    }

    public void setSelectionCrossColor(Color selectionCrossColor) {
        activate(ActivationPurpose.WRITE);
        Color old = selectionCrossColor;
        this.selectionCrossColor = selectionCrossColor;
        getPropertyChangeSupport().firePropertyChange(PROP_SELECTIONCROSSCOLOR, old, selectionCrossColor);
    }

    public static final String PROP_STROKECOLOR = "strokeColor";
    public Color getStrokeColor() {
        activate(ActivationPurpose.READ);
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        activate(ActivationPurpose.WRITE);
        Color old = this.strokeColor;
        this.strokeColor = strokeColor;
        getPropertyChangeSupport().firePropertyChange(PROP_STROKECOLOR, old, strokeColor);
    }

    public static final String PROP_TEXTCOLOR = "textColor";
    public Color getTextColor() {
        activate(ActivationPurpose.READ);
        return textColor;
    }

    public void setTextColor(Color textColor) {
        activate(ActivationPurpose.WRITE);
        Color old = textColor;
        this.textColor = textColor;
        getPropertyChangeSupport().firePropertyChange(PROP_TEXTCOLOR,old,textColor);
    }

    @Override
    public void draw(Graphics2D g) {
        ISpot t = getPayload();
        IWell96 well = t.getWell();
        StringBuilder sb = new StringBuilder();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
        if (well != null) {
            sb.append(well.getParent().getName());
            sb.append(": ");
            sb.append(well.getWellPosition());
            setStrokeColor(well.getStatus().getColor());
        } else {
            setStrokeColor(Color.BLACK);
        }

        if (t.getLabel() != null && !t.getLabel().isEmpty()) {
            if (sb.length() == 0) {
                sb.append("Label: ");
            } else {
                sb.append(" | Label: ");
            }
            sb.append(t.getLabel());
        }

        if (isDrawSpotBox()) {
            if (!isSelected()) {
                g.setPaint(getFillColor());
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)getFillAlpha()));
                g.fill(getShape());
                g.setPaint(getStrokeColor());
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)getStrokeAlpha()));
                g.draw(getShape());
                g.setPaint(Color.BLACK);

            } else {
                g.setPaint(getSelectedFillColor());
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)getFillAlpha()));
                g.fill(getShape());
                g.setPaint(getSelectedStrokeColor());
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)getStrokeAlpha()));
                Stroke stroke = g.getStroke();
                Stroke lineStroke = new BasicStroke(3.0f);
                g.setStroke(lineStroke);
                g.draw(getShape());
                g.setStroke(stroke);
//            PainterTools.drawCrossInBox(g, selectionCrossColor, getShape().getBounds2D(), 2);
            }
        }
        if (isDrawSpotId() && sb.length() > 0) {
            Color fill = getSelectedFillColor();
            Color stroke = getSelectedStrokeColor().darker();
            if (getFont() == null) {
                setFont(g.getFont().deriveFont(10.0f));
            }
            g.setFont(getFont());
            Tuple2D<Rectangle2D, Point2D> tple = PainterTools.getBoundingBox(sb.toString(), g, 5);
            AffineTransform at = AffineTransform.getTranslateInstance(getPosition().getX() + getDisplacementX(), getPosition().getY() + getDisplacementY());
            Point2D textOrigin = at.transform(tple.getSecond(), null);
//            g.setColor(fill);
//            g.fill(s);
            g.setColor(getLineColor());
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)getStrokeAlpha()));
            Line2D.Double line = new Line2D.Double(getPosition(), textOrigin);
            g.draw(line);
            g.setColor(getTextColor());
            g.drawString(sb.toString(), (float) textOrigin.getX(), (float) textOrigin.getY());
        }
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
    }

}
