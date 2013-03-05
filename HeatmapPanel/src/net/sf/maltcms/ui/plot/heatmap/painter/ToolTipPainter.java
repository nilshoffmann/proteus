/*
 *  Copyright (C) 2008-2012 Nils Hoffmann
 *  Nils.Hoffmann A T CeBiTec.Uni-Bielefeld.DE
 *
 *  This file is part of Maui.
 *
 * Maui is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * Maui is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Maui.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.maltcms.ui.plot.heatmap.painter;

import java.awt.*;
import java.awt.geom.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import net.sf.maltcms.ui.plot.heatmap.IAnnotation;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;
import net.sf.maltcms.ui.plot.heatmap.Tuple2D;
import net.sf.maltcms.ui.plot.heatmap.event.IProcessorResultListener;
import net.sf.maltcms.ui.plot.heatmap.event.mouse.MouseEvent;
import org.jdesktop.swingx.painter.AbstractPainter;

/**
 * FIXME resolve shift + mouse click on annotation, shift + mouse move etc.
 * behaviour
 *
 * @author nilshoffmann
 */
public abstract class ToolTipPainter<T, U extends JComponent> extends AbstractPainter<U>
		implements IProcessorResultListener<Point2D>, PropertyChangeListener {

	private Tuple2D<Point2D, IAnnotation<T>> a = null;
	private boolean drawLabels = true;
	private boolean drawCrossHair = true;
	private float labelFontSize = 14.0f;
	private double margin = 25.0f;
	private HeatmapDataset<T> hm;

	public ToolTipPainter(HeatmapDataset<T> hm) {
		setCacheable(false);
		this.hm = hm;
	}

	public boolean isDrawCrossHair() {
		return drawCrossHair;
	}

	public void setDrawCrossHair(boolean drawCrossHair) {
		boolean oldValue = this.drawCrossHair;
		this.drawCrossHair = drawCrossHair;
		setDirty(true);
		firePropertyChange("crossHair", oldValue, drawCrossHair);
	}

	public boolean isDrawLabels() {
		return drawLabels;
	}

	public void setDrawLabels(boolean drawLabels) {
		boolean oldValue = this.drawLabels;
		this.drawLabels = drawLabels;
		setDirty(true);
		firePropertyChange("labels", oldValue, drawLabels);
	}

	@Override
	protected void doPaint(Graphics2D g, JComponent t, int width, int height) {
		if (a != null) {
//			Shape annotationShape = a.getSecond().getShape();
//            System.out.println("ToolTipPainter: drawing annotations");
			AffineTransform originalTransform = g.getTransform();
			Font currentFont = g.getFont();
			g.setTransform(hm.getTransform());
			Point2D lineCross = new Point2D.Double(a.getSecond().getPosition().getX(), a.getSecond().getPosition().getY());
			if (drawCrossHair) {
				Line2D.Double l1 = new Line2D.Double(0, lineCross.getY(),
						hm.getDataBounds().getWidth(), lineCross.getY());
				Line2D.Double l2 = new Line2D.Double(lineCross.getX(), 0, lineCross.
						getX(), hm.getDataBounds().getHeight());
				//            System.out.println("Line cross at " + lineCross);
				g.setColor(Color.BLACK);
				g.draw(l1);
				g.draw(l2);
			}
			if(drawLabels) {
				Paint fill = new Color(250, 250, 210, 240);
				Paint border = new Color(218, 165, 32, 240);
				String label = getStringFor(a.getSecond());
				Font labelFont = currentFont.deriveFont((float) (labelFontSize / hm.
						getTransform().getScaleX()));
				g.setFont(labelFont);
				Tuple2D<Rectangle2D, Point2D> tple = PainterTools.getBoundingBox(
						label, g, 10);
				Rectangle2D r = tple.getFirst();
				double lshift = margin;
				double ushift = margin;
				if (lineCross.getX() + margin + r.getWidth() >= t.getVisibleRect().
						getMaxX()) {
					lshift = -r.getWidth() - margin;
				}
				if (lineCross.getY() + margin + r.getHeight() >= t.getVisibleRect().
						getMaxY()) {
					ushift = -r.getHeight() - margin;
				}
				double finalx = Math.max(margin,
						lineCross.getX() + lshift - r.getX());
				double finaly = Math.max(margin,
						lineCross.getY() + ushift - r.getY());
				AffineTransform transl = AffineTransform.getTranslateInstance(finalx,
						finaly);
				transl.preConcatenate(hm.getTransform());
				g.setTransform(transl);
				drawLabelBox(g, fill, r, border, label, tple);
			}

			g.setFont(currentFont);
			g.setTransform(originalTransform);
		}
	}

	private void drawLabelBox(Graphics2D g, Paint fill, Rectangle2D r,
			Paint border, String label, Tuple2D<Rectangle2D, Point2D> tple) {
		g.setPaint(fill);
		double radius = 10.0d / g.getTransform().getScaleX();
		RoundRectangle2D boxArea = new RoundRectangle2D.Double(r.getX(),
				r.getY(), r.getWidth(), r.getHeight(), radius, radius);
		g.fill(boxArea);
		g.setPaint(border);
		g.draw(boxArea);
		g.setPaint(Color.BLACK);
		g.drawString(label, (float) (tple.getSecond().getX()), (float) (tple.
				getSecond().getY()));
	}

	@Override
	public void listen(Point2D t, MouseEvent et) {
//        setPoint(t);
	}

	public abstract String getStringFor(IAnnotation<T> t);

	/**
	 * FIXME selection issue when selecting the same annotation
	 *
	 * @param pce
	 */
	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if (pce.getPropertyName().equals("annotationPointSelection")) {
			Tuple2D<Point2D, IAnnotation<T>> annotation = (Tuple2D<Point2D, IAnnotation<T>>) pce.
					getNewValue();
			if (annotation == null) {
			} else {
				a = annotation;

			}
			setDirty(true);
		}
		setDirty(true);
	}
}
