/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import net.sf.maltcms.ui.plot.heatmap.HeatmapPanel;
import org.junit.Test;
import org.openide.util.Exceptions;

/**
 *
 * @author nilshoffmann
 */
public class LayerScrollableTest {// extends JLabel {

//    public LayerScrollableTest() {
//    }
//
//    public LayerScrollableTest(Icon icon) {
//        super(icon);
//    }
//
//    public LayerScrollableTest(Icon icon, int i) {
//        super(icon, i);
//    }
//
//    public LayerScrollableTest(String string) {
//        super(string);
//    }
//
//    public LayerScrollableTest(String string, int i) {
//        super(string, i);
//    }
//
//    public LayerScrollableTest(String string, Icon icon, int i) {
//        super(string, icon, i);
//    }
    @Test
    public void testHeatmapPanel() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                @Override
                public void run() {
                    HeatmapPanel.main(new String[]{});

                }
            });
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        } catch (InvocationTargetException ex) {
            Exceptions.printStackTrace(ex);
        }
        try {
            Thread.sleep(50000);
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    public static void main(String[] args) {
//        final JLabel jl = new LayerScrollableTest("wenig");
//
//        final JXLayer<JLabel> layer = new JXLayer<JLabel>(jl, new BufferedLayerUI<JLabel>());
//        jl.addKeyListener(new KeyAdapter() {
//
//            @Override
//            public void keyTyped(KeyEvent ke) {
//                System.out.println("Received key event");
//                System.out.println("Prefsize before: "+jl.getPreferredSize());
//                jl.setText(jl.getText()+ke.getKeyChar());
//                System.out.println("Prefsize after: "+jl.getPreferredSize());
//            }
//        });
////        layer.setLayerEventMask(0);
//        JFrame jf = new JFrame();
//        jf.add(new JScrollPane(layer));
//        jf.setSize(50,25);
//        jf.setVisible(true);
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jl.requestFocus();
        HeatmapPanel.main(args);
    }
}
