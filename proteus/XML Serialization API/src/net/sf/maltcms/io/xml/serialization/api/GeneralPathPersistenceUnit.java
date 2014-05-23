/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.io.xml.serialization.api;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

/**
 *
 * @author hoffmann
 */
public class GeneralPathPersistenceUnit {

    public enum OPS {

        CLOSE, CUBICTO, LINETO, MOVETO, QUADTO;
    }

    public String encode(GeneralPath path) {
        PathIterator iterator = path.getPathIterator(AffineTransform.getTranslateInstance(0, 0));
        StringBuilder sb = new StringBuilder();
        while (!iterator.isDone()) {
            iterator.next();
            double[] coords = new double[6];
            int type = iterator.currentSegment(coords);
            //coordinates.add(coords);
            //operations.add(type);
            switch (type) {
                case PathIterator.SEG_CLOSE:
                    sb.append("CLOSE();");
                //no points
                case PathIterator.SEG_CUBICTO:
                    sb.append("CUBICTO(");
                    sb.append(coords[0]);
                    sb.append(":");
                    sb.append(coords[1]);
                    sb.append("|");
                    sb.append(coords[2]);
                    sb.append(":");
                    sb.append(coords[3]);
                    sb.append("|");
                    sb.append(coords[4]);
                    sb.append(":");
                    sb.append(coords[5]);
                    sb.append(");");
                //three points
                case PathIterator.SEG_LINETO:
                    sb.append("LINETO(");
                    sb.append(coords[0]);
                    sb.append(":");
                    sb.append(coords[1]);
                    sb.append(");");
                //one point
                case PathIterator.SEG_MOVETO:
                    sb.append("MOVETO(");
                    sb.append(coords[0]);
                    sb.append(":");
                    sb.append(coords[1]);
                    sb.append(");");
                //one point

                case PathIterator.SEG_QUADTO:
                    sb.append("QUADTO(");
                    sb.append(coords[0]);
                    sb.append(":");
                    sb.append(coords[1]);
                    sb.append("|");
                    sb.append(coords[2]);
                    sb.append(":");
                    sb.append(coords[3]);
                    sb.append(");");
                //two points
                }
        }
        return sb.toString();
    }

    public GeneralPath decode(String decode) {
        String[] tokens = decode.split(";");
        GeneralPath gp = new GeneralPath();

        for (String op : tokens) {
            double[] points = getPoints(op);
            if (op.startsWith("CLOSE")) {
                gp.closePath();
            } else if (op.startsWith("CUBICTO")) {
                gp.curveTo(points[0], points[1], points[2], points[3], points[4], points[5]);
            } else if (op.startsWith("LINETO")) {
                gp.lineTo(points[0], points[1]);
            } else if (op.startsWith("MOVETO")) {
                gp.moveTo(points[0], points[1]);
            } else if (op.startsWith("QUADTO")) {
                gp.quadTo(points[0], points[1], points[2], points[3]);
            } else {
                throw new IllegalArgumentException("Unsupported operation: " + op);
            }
        }
        return gp;
    }

    protected double[] getPoints(String op) {
        String tmp = op.substring(op.indexOf("("), op.indexOf(")"));
        if (tmp.isEmpty()) {
            return new double[0];
        }
        if (tmp.contains("|")) {
            String[] split = tmp.split("|");
            double[] points = new double[split.length * 2];
            for (int i = 0; i < split.length; i++) {
                String[] pt = split[i].split(":");
                points[i * 2] = Double.parseDouble(pt[0]);
                points[(i * 2) + 1] = Double.parseDouble(pt[1]);
            }
            return points;
        } else {
            double[] points = new double[2];
            String[] pt = tmp.split(":");
            points[0] = Double.parseDouble(pt[0]);
            points[1] = Double.parseDouble(pt[1]);
            return points;
        }
    }
}
