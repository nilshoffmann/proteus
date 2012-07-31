/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.io.xml.serialization.api;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.PersistenceDelegate;
import java.beans.Statement;
import java.util.ArrayList;
import org.openide.util.lookup.ServiceProvider;

/**
 * FIXME reimplement
 * @author hoffmann
 */
@ServiceProvider(service=IPersistenceDelegateRegistration.class)
public class GeneralPathPersistenceDelegate extends DefaultPersistenceDelegate implements IPersistenceDelegateRegistration {

    @Override
    protected void initialize( Class type, Object oldInstance, Object newInstance, Encoder out ) {
        GeneralPath path = (GeneralPath)oldInstance;
        GeneralPathDescriptor descriptor = new GeneralPathDescriptor(path);
        descriptor.generate(out,path);
    }

    @Override
    public Class<?> appliesTo() {
        return GeneralPath.class;
    }

    @Override
    public PersistenceDelegate getPersistenceDelegate() {
        return this;
    }


    /////////////////////////////////
    // Inner classes
    //

    /**
     * A Jaba Bean conformant class that contains the information needed
     * to persist and restor a GeneralPath.
     */
    class GeneralPathDescriptor {
        int windingRule;
        ArrayList segments = new ArrayList();
        int[] segTypes;
        float[][] segCoords;
        // Number of segments in the path
        int segCnt = 0;

        GeneralPathDescriptor( GeneralPath path ) {
            // Winding rule
            this.windingRule = path.getWindingRule();

            // Determine how many segments there are in the path
            PathIterator pi = path.getPathIterator( new AffineTransform() );
            while( !pi.isDone() ) {
                pi.next();
                segCnt++;
            }
            // Create an internal representation of the segment types and their
            // coordinates
            segTypes = new int[segCnt];
            segCoords = new float[segCnt][];
            PathIterator pi2 = path.getPathIterator( new AffineTransform() );
            int i = 0;
            while( !pi2.isDone() ) {
                float[] c = new float[6];
                segTypes[i] = pi2.currentSegment( c );
                segCoords[i] = c;
                pi2.next();
                i++;
            }
        }

        /**
         * Generates the statements needed for generating the restored state of the GeneralPath
         * used to construct this descriptor
         *
         * @param out
         * @param path
         */
        public void generate( Encoder out, GeneralPath path ) {
            out.writeStatement( new Statement( path,
                                               "setWindingRule",
                                               new Object[]{new Integer( path.getWindingRule() )} ) );
            for( int i = 0; i < segTypes.length; i++ ) {
                int segType = segTypes[i];
                float[] coords = segCoords[i];
                String statementName = null;
                Object[] parms = null;
                switch( segType ) {
                    case PathIterator.SEG_CLOSE:
                        statementName = "closePath";
                        parms = new Object[]{};
                        break;
                    case PathIterator.SEG_CUBICTO:
                        statementName = "curveTo";
                        parms = new Object[]{new Float( coords[0] ), new Float( coords[1] ), new Float( coords[2] ),
                                             new Float( coords[3] ), new Float( coords[4] ), new Float( coords[5] )};
                        break;
                    case PathIterator.SEG_LINETO:
                        statementName = "lineTo";
                        parms = new Object[]{new Float( coords[0] ), new Float( coords[1] )};
                        break;
                    case PathIterator.SEG_MOVETO:
                        statementName = "moveTo";
                        parms = new Object[]{new Float( coords[0] ), new Float( coords[1] )};
                        break;
                    case PathIterator.SEG_QUADTO:
                        statementName = "quadTo";
                        parms = new Object[]{new Float( coords[0] ), new Float( coords[1] ), new Float( coords[2] ),
                                             new Float( coords[3] )};
                        break;
                }
                out.writeStatement( new Statement( path, statementName, parms ) );
            }
        }
    }
}
