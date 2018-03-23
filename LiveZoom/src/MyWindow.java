import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class MyWindow extends JFrame {
	private BufferedImage srcImage = null;
	private int dx, dy;
	
	private JPanel mainPanel = new JPanel(){
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (srcImage != null) {
				g.drawImage(srcImage, 0, 0, 150, 150, this);

			}
	}};
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public MyWindow() {
		display();
		menu();
		event();
		
		this.setSize(150, 150);
		this.setResizable(false);
		this.setLocation(0, 0);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		Thread t = new Thread(){
			@Override
			public void run() {
				
				try {
					while (true) {
						Robot r = new Robot();
						int mx, my, sx, sy;
						
						mx = MouseInfo.getPointerInfo().getLocation().x;
						my = MouseInfo.getPointerInfo().getLocation().y;
						
						dx = mx - 175;
						sx = mx - 25;
						if (mx < 175) {
							dx = mx;
							if (mx < 25)
								sx = 0;
						} else if (mx > screenSize.width - 25) {
								sx = screenSize.width - 50;
						}
						
						dy = my - 175;
						sy = my - 25;
						if (my < 175) {
							dy = my;
							if (my < 25)
								sy = 0;
						} else if (my > screenSize.height - 25) {
							sy = screenSize.height - 50;
						}
						
						srcImage = r.createScreenCapture(new Rectangle(sx, sy, 50, 50));
						MyWindow.this.setLocation(dx, dy);
							
						mainPanel.repaint();
						
						Thread.sleep(1000 / 60);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}
		};
		t.setDaemon(true);
		t.start();
	}

	private void event() {
		KeyStroke escKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		Action escAction = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}};
		mainPanel.getInputMap().put(escKey, "exit");
		mainPanel.getActionMap().put("exit", escAction);
	}

	private void menu() {
	}

	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	}

}
