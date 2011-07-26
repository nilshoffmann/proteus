/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.BufferedLayerUI;

/**
 *
 * @author nilshoffmann
 */
public class LayerScrollableTest extends JLabel {

    public LayerScrollableTest() {
    }

    public LayerScrollableTest(Icon icon) {
        super(icon);
    }

    public LayerScrollableTest(Icon icon, int i) {
        super(icon, i);
    }

    public LayerScrollableTest(String string) {
        super(string);
    }

    public LayerScrollableTest(String string, int i) {
        super(string, i);
    }

    public LayerScrollableTest(String string, Icon icon, int i) {
        super(string, icon, i);
    }

    
    public static void main(String[] args) {
        final JLabel jl = new LayerScrollableTest("wenig");
        
        final JXLayer<JLabel> layer = new JXLayer<JLabel>(jl, new BufferedLayerUI<JLabel>());
        jl.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent ke) {
                System.out.println("Received key event");
                System.out.println("Prefsize before: "+jl.getPreferredSize());
                jl.setText(jl.getText()+ke.getKeyChar());
                System.out.println("Prefsize after: "+jl.getPreferredSize());
            }
        });
//        layer.setLayerEventMask(0);
        JFrame jf = new JFrame();
        jf.add(new JScrollPane(layer));
        jf.setSize(50,25);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jl.requestFocus();
    }
    
}
