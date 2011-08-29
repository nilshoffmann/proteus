/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.Date;
import net.sf.maltcms.ui.plot.heatmap.HeatmapPanel;
import net.sf.maltcms.ui.plot.heatmap.painter.AnnotationPainter;
import org.netbeans.spi.print.PrintPage;
import org.netbeans.spi.print.PrintProvider;

/**
 *
 * @author hoffmann
 */
public class GelPrintProvider implements PrintProvider {

    private final HeatmapPanel hp;
    private final AnnotationPainter ap;
    private final IGel gel;
    
    public GelPrintProvider(IGel gel, HeatmapPanel hp, AnnotationPainter ap) {
        this.hp = hp;
        this.ap = ap;
        this.gel = gel;
    }
    
    @Override
    public PrintPage[][] getPages(final int width, final int height, final double zoom) {
        PrintPage printPage = new PrintPage() {

            @Override
            public void print(Graphics g) {
                System.out.println("Generating printing page");
                Shape clip = g.getClip();
                BufferedImage image = hp.getImage();
                Graphics2D g2 = (Graphics2D)g.create();
                g2.drawImage(image, 0, 0, clip.getBounds().width, clip.getBounds().height, null);
                ap.paint(g2, null, width, height);
                g2.dispose();
            }
        };
        return new PrintPage[][]{new PrintPage[]{printPage}};
    }

    @Override
    public String getName() {
        return gel.getName();
    }

    @Override
    public Date lastModified() {
        return new Date();
    }
    
}
