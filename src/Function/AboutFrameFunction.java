package Function;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AboutFrameFunction {
	
	public void AboutFrame() {}
    
	public static JLabel createLabel(ImageIcon icon, int x, int y) {
        JLabel la = new JLabel(icon);
        la.setLocation(x,y);
        la.setSize(icon.getIconWidth(),icon.getIconHeight());
        return la;
   }
    
}
