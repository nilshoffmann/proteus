/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations;

import cross.datastructures.tuple.Tuple2D;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.plate96.IWell96;
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.sf.maltcms.ui.plot.heatmap.Annotation;
import net.sf.maltcms.ui.plot.heatmap.painter.PainterTools;

/**
 *
 * @author nils
 */
public class SpotAnnotation extends Annotation<ISpot> implements PropertyChangeListener {

    private boolean drawSpotID = true;
    private double displacementX = 5.0;
    private double displacementY = -5.0;
    private Color fillColor = new Color(255, 255, 255, 64);
    private Color strokeColor = Color.BLACK;
    private Color selectedFillColor = fillColor;
    private Color selectedStrokeColor = Color.ORANGE;
    private Color selectionCrossColor = Color.BLACK;
    private Color textColor = Color.BLACK;
    private Color lineColor = Color.BLACK;
    private Font font = Font.decode("Plain-10");
    private boolean drawSpotBox = true;

    public boolean isDrawSpotBox() {
        return drawSpotBox;
    }

    public void setDrawSpotBox(boolean b) {
        this.drawSpotBox = b;
    }

    public double getDisplacementX() {
        return displacementX;
    }

    public void setDisplacementX(double displacementX) {
        this.displacementX = displacementX;
    }

    public double getDisplacementY() {
        return displacementY;
    }

    public void setDisplacementY(double displacementY) {
        this.displacementY = displacementY;
    }

    public boolean isDrawSpotID() {
        return drawSpotID;
    }

    public void setDrawSpotID(boolean drawSpotID) {
        this.drawSpotID = drawSpotID;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getSelectedFillColor() {
        return selectedFillColor;
    }

    public void setSelectedFillColor(Color selectedFillColor) {
        this.selectedFillColor = selectedFillColor;
    }

    public Color getSelectedStrokeColor() {
        return selectedStrokeColor;
    }

    public void setSelectedStrokeColor(Color selectedStrokeColor) {
        this.selectedStrokeColor = selectedStrokeColor;
    }

    public Color getSelectionCrossColor() {
        return selectionCrossColor;
    }

    public void setSelectionCrossColor(Color selectionCrossColor) {
        this.selectionCrossColor = selectionCrossColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public SpotAnnotation(Point2D position, ISpot t) {
        super(position, t);
        setShape(new RoundRectangle2D.Double(position.getX() - 10, position.getY() - 10, 20, 20, 5, 5));
    }

    @Override
    public void draw(Graphics2D g) {
        ISpot t = getPayload();
        IWell96 well = t.getWell();
        StringBuilder sb = new StringBuilder();
        if (well != null) {
            sb.append(well.getParent().getName());
            sb.append(": ");
            sb.append(well.getWellPosition());
            strokeColor = well.getStatus().getColor();
        } else {
            strokeColor = Color.BLACK;
        }

        if (t.getLabel() != null && !t.getLabel().isEmpty()) {
            if (sb.length() == 0) {
                sb.append("Label: ");
            } else {
                sb.append(" | Label: ");
            }
            sb.append(t.getLabel());
        }

        if (drawSpotBox) {
            if (!isSelected()) {
                g.setPaint(fillColor);
                g.fill(getShape());
                g.setPaint(strokeColor);
                g.draw(getShape());
                g.setPaint(Color.BLACK);

            } else {
                g.setPaint(selectedFillColor);
                g.fill(getShape());
                g.setPaint(selectedStrokeColor);
                Stroke stroke = g.getStroke();
                Stroke lineStroke = new BasicStroke(3.0f);
                g.setStroke(lineStroke);
                g.draw(getShape());
                g.setStroke(stroke);
//            PainterTools.drawCrossInBox(g, selectionCrossColor, getShape().getBounds2D(), 2);
            }
        }
        if (drawSpotID && sb.length() > 0) {
            Color fill = selectedFillColor;
            Color stroke = selectedStrokeColor.darker();
            if (font == null) {
                font = g.getFont().deriveFont(10.0f);
            }
            g.setFont(font);
            Tuple2D<Rectangle2D, Point2D> tple = PainterTools.getBoundingBox(fill, stroke, sb.toString(), g, 5);
            AffineTransform at = AffineTransform.getTranslateInstance(getPosition().getX() + displacementX, getPosition().getY() + displacementY);
            Point2D textOrigin = at.transform(tple.getSecond(), null);
//            g.setColor(fill);
//            g.fill(s);
            g.setColor(lineColor);
            Line2D.Double line = new Line2D.Double(getPosition(), textOrigin);
            g.draw(line);
            g.setColor(textColor);

            g.drawString(sb.toString(), (float) textOrigin.getX(), (float) textOrigin.getY());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
