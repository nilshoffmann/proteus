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
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.actions;

import de.unibielefeld.gi.kotte.laborprogramm.proteomik.api.gel.IGel;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import net.sf.maltcms.ui.plot.heatmap.HeatmapPanel;
import net.sf.maltcms.ui.plot.heatmap.painter.AnnotationPainter;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;

/**
 *
 * @author hoffmann
 */
public class ExportGelAction extends AbstractAction {

    private HeatmapPanel hp;
    private AnnotationPainter ap;
    private IGel gel;

    public ExportGelAction(String name, Icon icon, HeatmapPanel hp, AnnotationPainter ap, IGel gel) {
        super(name, icon);
        this.hp = hp;
        this.ap = ap;
        this.gel = gel;
    }

    public ExportGelAction(String name, HeatmapPanel hp, AnnotationPainter ap, IGel gel) {
        super(name);
        this.hp = hp;
        this.ap = ap;
        this.gel = gel;
    }

    public ExportGelAction(HeatmapPanel hp, AnnotationPainter ap, IGel gel) {
        this.hp = hp;
        this.ap = ap;
        this.gel = gel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        jfc.setSelectedFile(new File(gel.getName() + ".png"));
        int result = jfc.showSaveDialog(hp);
        if (result == JFileChooser.APPROVE_OPTION) {
            final ProgressHandle ph = ProgressHandleFactory.createHandle("Saving to image file");
            final File targetFile = jfc.getSelectedFile();
            RequestProcessor rp = new RequestProcessor(ExportGelAction.class);
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    ph.start(4);
                    ph.progress("Creating image", 1);
                    BufferedImage bi = new BufferedImage(hp.getImage().getWidth(), hp.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = bi.createGraphics();
                    ph.progress("Drawing image", 2);
                    g2.drawImage(hp.getImage(), 0, 0, bi.getWidth(), bi.getHeight(), null);
                    ph.progress("Adding spots", 3);
                    ap.deselectAnnotation();
                    ap.paint(g2, null, bi.getWidth(), bi.getHeight());
                    ap.selectAnnotation();
                    g2.dispose();
                    g2.dispose();
                    ph.progress("Saving to file", 4);
                    try {
                        ImageIO.write(bi, targetFile.getName().substring(targetFile.getName().lastIndexOf(".") + 1), targetFile);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    ph.finish();
                }
            };
            rp.post(r);
        }
    }
}
