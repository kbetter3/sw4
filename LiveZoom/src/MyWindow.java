import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class MyWindow extends JFrame {
	private BufferedImage backgroundImage = null;
	private int dx, dy, sx, sy;
	private boolean mouseMoved = false;
	
	private JPanel mainPanel = new JPanel(){
		@Override
		protected void paintBorder(Graphics g) {
			// TODO Auto-generated method stub
			super.paintBorder(g);
			g.drawLine(dx, dy, dx + 150, dy);
			g.drawLine(dx + 150, dy, dx + 150, dy + 150);
			g.drawLine(dx, dy, dx, dy + 150);
			g.drawLine(dx, dy + 150, dx + 150, dy + 150);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (backgroundImage != null) {
				g.drawImage(backgroundImage, 0, 0, this);
				
				if (mouseMoved) {
					g.drawImage(backgroundImage.getSubimage(sx, sy, 50, 50), dx, dy, 150, 150, this);
				}
			}
	}};
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public MyWindow() {
		display();
		menu();
		event();
	
		try {
			Robot robot = new Robot();
			backgroundImage = robot.createScreenCapture(new Rectangle(0, 0, screenSize.width, screenSize.height));
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		
		this.setSize(screenSize.width, screenSize.height);
		this.setResizable(false);
		this.setLocation(0, 0);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void event() {
		KeyStroke escKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		Action escAction = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				MyWindow.this.dispose();
			}};
		mainPanel.getInputMap().put(escKey, "exit");
		mainPanel.getActionMap().put("exit", escAction);
		
		mainPanel.addMouseMotionListener(new MouseMotionListener(){
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				int mx = arg0.getX();
				int my = arg0.getY();
				
				if (mouseMoved) {
					dx = mx - 150;
					sx = mx - 25;
					if (mx < 150) {
						dx = 0;
						if (mx < 25)
							sx = 0;
					} else if (mx > screenSize.width - 25) {
						sx = screenSize.width - 50;
					}
					
					dy = my - 150;
					sy = my - 25;
					if (my < 150) {
						dy = 0;
						if (my < 25)
							sy = 0;
					} else if (my > screenSize.height - 25) {
						sy = screenSize.height - 50;
					}
				}
				
				mouseMoved = true;
				mainPanel.repaint();
			}});
	}

	private void menu() {
	}

	private void display() {
		this.setContentPane(mainPanel);
	}

}
