/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rojava;

import java.awt.Dimension;
import org.mt4j.MTApplication;

/**
 *
 * @author jolak
 */
public class StartMTGestures extends MTApplication {
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		initialize();
	}
	@Override
	public void startUp() {
                int h= this.getHeight();
                int w= this.getWidth();
                System.out.println("Dimension H:"+h +" Width W:"+w);
		addScene(new MTGesturesExampleScene(this, "Multi-touch Gestures Example Scene"));
	}
}