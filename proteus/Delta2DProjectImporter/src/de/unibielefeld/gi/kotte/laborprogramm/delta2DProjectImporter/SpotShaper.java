package de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a function to parse spot shapes from Delta2D data file entries.
 * 
 * @author hoffmann, kotte
 */
public class SpotShaper {
    public static Shape shapeSpot(String shapeString) {
//        System.out.println("Shape boundaries: '" + shapeString + "'");
       String[] segments = shapeString.split("L");
       String[] start = segments[0].substring(1).split(" ");
       List<Point2D> points = new ArrayList<Point2D>();

       java.awt.geom.Point2D.Double startPoint = new java.awt.geom.Point2D.Double(Double.parseDouble(start[0]),Double.parseDouble(start[1]));
       points.add(startPoint);
       for(int i = 1;i<segments.length-1; i++) {
           String[] split = segments[i].split(" ");
//           System.out.println("Adding point for spot shape: "+split[0]+" "+split[1]);
           points.add(new java.awt.geom.Point2D.Double(Double.parseDouble(split[0]),Double.parseDouble(split[1])));
       }
       String[] stop = segments[segments.length-1].split(" ");
       //println stop
       double x = Double.parseDouble(stop[0]);
       double y = Double.parseDouble(stop[1].substring(0,stop[1].length()-1));
       Point2D.Double point = new Point2D.Double(x,y);
       points.add(point);

       GeneralPath path = new GeneralPath();
       path.moveTo(points.get(0).getX(),points.get(0).getY());
       for(int i = 1;i<points.size();i++) {
           Point2D pt = points.get(i);
           path.lineTo(pt.getX(),pt.getY());
       }
       path.closePath();
       return path;
    }
}
