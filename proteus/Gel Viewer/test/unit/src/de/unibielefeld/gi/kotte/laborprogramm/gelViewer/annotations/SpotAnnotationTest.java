/*
 *  Copyright (C) 2008-2012 Nils Hoffmann
 *  Nils.Hoffmann A T CeBiTec.Uni-Bielefeld.DE
 *
 *  This file is part of Maui.
 *
 *  Maui is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Maui is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Maui.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations;

import de.unibielefeld.gi.kotte.laborprogramm.delta2DProjectImporter.SpotShaper;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpot;
import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.ISpotFactory;
import java.awt.geom.Point2D;
import java.io.IOException;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import java.io.File;
import java.util.Collection;
import net.sf.maltcms.chromaui.db.api.ICredentials;
import net.sf.maltcms.chromaui.db.api.ICrudProvider;
import net.sf.maltcms.chromaui.db.api.ICrudProviderFactory;
import net.sf.maltcms.chromaui.db.api.ICrudSession;
import net.sf.maltcms.chromaui.db.api.NoAuthCredentials;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.netbeans.junit.NbTestCase;

/**
 *
 * @author Konstantin Otte
 */
public class SpotAnnotationTest extends NbTestCase {

    @Rule
    TemporaryFolder temp = new TemporaryFolder();
    
    private final String shapeString = "M1174 4791L1179 4786L1179 4781L1179 4776L1179 4771L1184"
                    + " 4766L1184 4761L1184 4756L1189 4751L1189 4747L1189 4742L1194 4737L1194"
                    + " 4732L1199 4727L1199 4722L1199 4717L1204 4712L1209 4707L1209 4703L1214"
                    + " 4698L1214 4693L1218 4688L1223 4683L1223 4678L1228 4673L1233 4668L1238"
                    + " 4663L1243 4658L1248 4654L1253 4649L1258 4644L1262 4639L1267 4634L1272"
                    + " 4634L1277 4629L1282 4629L1287 4624L1292 4624L1297 4619L1302 4619L1307"
                    + " 4614L1311 4619L1316 4619L1321 4624L1326 4629L1331 4629L1336 4634L1341"
                    + " 4634L1346 4639L1351 4639L1355 4639L1360 4639L1365 4639L1370 4639L1375"
                    + " 4639L1380 4634L1385 4639L1385 4644L1390 4649L1395 4654L1395 4658L1400"
                    + " 4663L1400 4668L1400 4673L1404 4678L1404 4683L1404 4688L1404 4693L1409"
                    + " 4698L1409 4703L1409 4707L1409 4712L1409 4717L1409 4722L1409 4727L1409"
                    + " 4732L1409 4737L1409 4742L1404 4747L1404 4751L1404 4756L1404 4761L1404"
                    + " 4766L1404 4771L1400 4776L1400 4781L1400 4786L1395 4791L1395 4795L1395"
                    + " 4800L1390 4805L1390 4810L1390 4815L1385 4820L1385 4825L1380 4830L1380"
                    + " 4835L1375 4840L1375 4844L1370 4849L1365 4854L1365 4859L1360 4864L1355"
                    + " 4864L1351 4864L1346 4869L1341 4874L1336 4879L1331 4884L1326 4888L1321"
                    + " 4893L1321 4898L1316 4903L1316 4908L1311 4913L1307 4918L1302 4918L1297"
                    + " 4923L1292 4928L1287 4928L1282 4928L1277 4933L1272 4933L1267 4933L1262"
                    + " 4933L1258 4937L1253 4937L1248 4937L1243 4933L1238 4933L1233 4933L1228"
                    + " 4933L1223 4928L1218 4928L1214 4923L1209 4918L1204 4913L1199 4908L1194"
                    + " 4903L1189 4898L1189 4893L1184 4888L1184 4884L1179 4879L1179 4874L1179"
                    + " 4869L1179 4864L1174 4859L1174 4854L1174 4849L1174 4844L1174 4840L1174"
                    + " 4835L1174 4830L1174 4825L1174 4820L1174 4815L1174 4810L1174 4805L1174"
                    + " 4800L1174 4795Z";

    public SpotAnnotationTest(String name) {
        super(name);
    }

    @Test
    public void testConstructor() {
        File dblocation = null;
        try {
			File folder = temp.newFolder();
            dblocation = new File(folder,"testDatabase.db4o");
            if(dblocation.exists()) {
                dblocation.delete();
            }
            //Credentials
            ICredentials ic = new NoAuthCredentials();
            //CrudProvider
            ICrudProviderFactory crf = Lookup.getDefault().lookup(ICrudProviderFactory.class);
            ICrudProvider provider = crf.getCrudProvider(dblocation.toURI().toURL(), ic,Lookup.getDefault().lookup(ClassLoader.class));
            provider.open();
            ICrudSession session = provider.createSession();
            session.open();

            ISpot spot = Lookup.getDefault().lookup(ISpotFactory.class).createSpot();
            spot.setPosX(1174);
            spot.setPosY(4791);
            spot.setShape(SpotShaper.shapeSpot(shapeString));
            SpotAnnotation instance = new SpotAnnotation(new Point2D.Double(1174, 4791), spot);
//            System.out.println("instance bounds "+instance.getShape().getBounds2D());
            //Area a = new Area(instance.getShape());
            session.create(instance);
//            Shape reference = SpotShaper.shapeSpot(shapeString);
//            Shape reference = new Rectangle2D.Double(2131,124,34123,115151);
//            session.create(reference);
            session.close();
            provider.close();

            ICrudProvider newProvider = crf.getCrudProvider(dblocation.toURI().toURL(), ic,Lookup.getDefault().lookup(ClassLoader.class));
            newProvider.open();
            ICrudSession newSession = newProvider.createSession();
            newSession.open();
            Collection<SpotAnnotation> annotations = newSession.retrieve(SpotAnnotation.class);
            //System.out.println(annotations.size());
            //assertSize("No instances in database", annotations, 1);
            SpotAnnotation ann = annotations.iterator().next();
            //System.out.println("ann bounds "+ann.getShape().getBounds2D());
//            GeneralPath s = newSession.retrieve(GeneralPath.class).iterator().next();
            assertEquals(instance.getShape().getBounds2D(),ann.getShape().getBounds2D());
            //assertFalse(a.isEmpty());
            //a.subtract(new Area(ann.getShape()));
            //assertTrue(a.isEmpty());
            //assertEquals(instanceString, ann.getShape().toString());

        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }


    }
}
