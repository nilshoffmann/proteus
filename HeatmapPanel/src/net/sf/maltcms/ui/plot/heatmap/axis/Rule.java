/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.sf.maltcms.ui.plot.heatmap.axis;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.sf.maltcms.ui.plot.heatmap.HeatmapDataset;

/* Rule.java is used by ScrollDemo.java. */
public class Rule extends JComponent implements PropertyChangeListener {

    public static final int INCH = Toolkit.getDefaultToolkit().
            getScreenResolution();
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int SIZE = 50;
    public int orientation;
    public boolean isMetric;
    private int increment = 1;
    private int majorUnits;
    private int minorUnits;
    private HeatmapDataset jc = null;
    private Rectangle2D dataRect = null;
//    private Rectangle2D rangeToPlot= null;
    private AffineTransform at = AffineTransform.getTranslateInstance(0, 0);

    public Rule(HeatmapDataset hd, int o, boolean m) {
        this.jc = hd;
        this.jc.addPropertyChangeListener(this);
        orientation = o;
        isMetric = m;
//        setIncrementAndUnits();
        majorUnits = 100;
        minorUnits = 10;
        this.dataRect = hd.getDataBounds();
    }

    public boolean isMetric() {
        return this.isMetric;
    }

    public int getIncrement() {
        return increment;
    }

    public void setPreferredHeight(int ph) {
        setPreferredSize(new Dimension(SIZE, ph));
    }

    public void setPreferredWidth(int pw) {
        setPreferredSize(new Dimension(pw, SIZE));
    }

    protected void paintComponent(Graphics g) {
        Rectangle drawHere = g.getClipBounds();

//        Rectangle2D dataRect = jc.getDataBounds();
//        Graphics2D g2 = (Graphics2D) g;
//        AffineTransform oldTrans = g2.getTransform();
////        g2.setTransform(at);
//        try {
//            dataRect = at.createInverse().createTransformedShape(dataRect).getBounds2D();
//        } catch (NoninvertibleTransformException ex) {
//            Logger.getLogger(Rule.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Graphics2D g2 = ((Graphics2D)g);
//        AffineTransform transf = g2.getTransform();
//        g2.setTransform(at);
        // Fill clipping area with dirty brown/orange.
        g.setColor(new Color(255, 255, 255));
        g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);

        // Do the ruler labels in a small font that's black.
        g.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g.setColor(Color.black);

        // Some vars we need.
        int end = 0;
        int start = 0;
        double dataStart = 0;
        double dataEnd = 0;
        int tickLength = 0;
        String text = null;
        int decimalDigits = 2;
        if (orientation == HORIZONTAL) {
            dataStart = dataRect.getMinX();
            dataEnd = dataRect.getMaxX() + 1;
        } else {
            dataStart = dataRect.getMinY();
            dataEnd = dataRect.getMaxY() + 1;
        }

        decimalDigits = (int) Math.log10(dataEnd - dataStart);

        System.out.println("Displaying data range: " + dataStart + " to " + dataEnd + " with " + decimalDigits + " decimal digits");

        // Use clipping bounds to calculate first and last tick locations.
        if (orientation == HORIZONTAL) {
            start = (drawHere.x / increment) * increment;
            end = (((drawHere.x + drawHere.width) / increment) + 1)
                    * increment;
        } else {
            start = (drawHere.y / increment) * increment;
            end = (((drawHere.y + drawHere.height) / increment) + 1)
                    * increment;
        }

        System.out.println("Start: " + start + " end: " + end + "; dataStart: " + dataStart + " dataEnd: " + dataEnd);
        //double viewToData = (dataEnd - dataStart) / (end - start);

        // Make a special case of 0 to display the number
        // within the rule and draw a majorUnits label.
        double dataValue = dataStart + (dataEnd - dataStart) * (start / (end - start));
        if (start == 0) {
            text = String.format("%.2f", dataValue);// + (isMetric ? " cm" : " in");
            tickLength = 10;
            if (orientation == HORIZONTAL) {
                g.drawLine(0, SIZE - 1, 0, SIZE - tickLength - 1);
                g.drawString(text, 2, 21);
            } else {
                g.drawLine(SIZE - 1, 0, SIZE - tickLength - 1, 0);
                g.drawString(text, 9, 10);
            }
            text = null;
            start = increment;
        }

        // ticks and labels
        for (int i = start; i < end; i++) {
            double relIdx = (i / (end - start));
            dataValue = (dataEnd - dataStart) * ((double) i / (double) (end - start));
//            System.out.println("Orientation: " + ((orientation == HORIZONTAL) ? "HORIZONTAL" : "VERTICAL") + " Relative view index " + relIdx + " maps to data value: " + dataValue);
//            System.out.println(""+(dataValue % majorUnits));
//            System.out.println("DataValue: "+dataValue);
            if (i % majorUnits == 0) {
//                System.out.println("Major");
                tickLength = 10;
//                System.out.println("dataValue: "+dataValue);
                text = String.format("%.2f", (float) ((int) ((Math.round(dataValue * 1000000.0d)) / 1000000)));

            } else if (tickLength != 10 && i % minorUnits == 0) {
//                System.out.println("Minor");
                tickLength = 7;
                text = null;
            } else {
//                System.out.println("None");
                tickLength = 0;
                text = null;
            }

            if (tickLength != 0) {
                if (orientation == HORIZONTAL) {
                    g.drawLine(i, SIZE - 1, i, SIZE - tickLength - 1);
                    if (text != null) {
                        g.drawString(text, i - 3, 21);
                    }
                } else {
                    g.drawLine(SIZE - 1, i, SIZE - tickLength - 1, i);
                    if (text != null) {
                        g.drawString(text, 9, i + 3);
                    }
                }
            }
        }
//        g2.setTransform(oldTrans);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        System.out.println("Received PropertyChangeEvent: " + pce.getPropertyName());
        if (pce.getPropertyName().equals(HeatmapDataset.PROP_TRANSFORM)) {
            this.at = (AffineTransform) pce.getNewValue();
            try {
                dataRect = at.createInverse().createTransformedShape(dataRect).getBounds2D();
            } catch (NoninvertibleTransformException ex) {
                Logger.getLogger(Rule.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (orientation == HORIZONTAL) {
                setPreferredWidth(dataRect.getBounds().width);
            } else {
                setPreferredHeight(dataRect.getBounds().height);
            }
            revalidate();
        }
    }
}
