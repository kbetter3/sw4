package notepad;

import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MyWindow extends Frame implements WindowListener {
	private TextArea ta ;
	
	private MenuBar bar;
	private Menu[] menu;
	private MenuItem[] fileItem;
	private MenuItem[] viewItem;
	private String[] menuStr = {"파일", "보기"};
	private String[] fileItemStr = {"새파일", "열기", "저장", "종료"};
	private String[] viewItemStr = {"크게", "작게"};
	
	private FileDialog fDialog;
	
	private ActionListener fileListener, viewListener;
	
	
	public MyWindow() {
		this.display();
		this.menu();
		this.event();
		
		
		this.setSize(500, 400);
		this.setResizable(false);
		this.addWindowListener(this);
		this.setVisible(true);
	}
	
	private void display() {
		ta = new TextArea();
		ta.setFont(new Font("굴림", Font.PLAIN, 20));
		this.add(ta);
	}
	
	private void menu() {
		bar = new MenuBar();
		this.setMenuBar(bar);
		
		menu = new Menu[menuStr.length];
		for (int i = 0; i < menuStr.length; i++) {
			menu[i] = new Menu(menuStr[i]);
			bar.add(menu[i]);
		}
		
		fileItem = new MenuItem[fileItemStr.length];
		for (int i = 0; i < fileItemStr.length; i++) {
			fileItem[i] = new MenuItem(fileItemStr[i]);
			fileItem[i].setActionCommand(fileItemStr[i]);
			menu[0].add(fileItem[i]);
		}
		
		viewItem = new MenuItem[viewItemStr.length];
		for (int i = 0; i < viewItemStr.length; i++) {
			viewItem[i] = new MenuItem(viewItemStr[i]);
			menu[1].add(viewItem[i]);
		}
	}
	
	private void event() {
		fileListener = (e)->{
			String ac = e.getActionCommand();
			
			if (ac.equals(fileItemStr[0])) {
				ta.setText("\0");
			} else if (ac.equals(fileItemStr[1])) {
				MyWindow.this.open();
			} else if (ac.equals(fileItemStr[2])) {
				MyWindow.this.save();
			} else {
				MyWindow.this.dispose();
			}
		};
		
		for (MenuItem mi : fileItem)
			mi.addActionListener(fileListener);
		
		
		viewListener = (e)->{
			String ac = e.getActionCommand();
			Font f = ta.getFont();
			
			if (ac.equals(viewItemStr[0])) {
				if (f.getSize() < 100)
					ta.setFont(new Font(f.getName(), Font.PLAIN, f.getSize() + 5));
			} else {
				if (f.getSize() > 10)
					ta.setFont(new Font(f.getName(), Font.PLAIN, f.getSize() - 5));
			}
			
			ta.setText(ta.getText());
		};
		
		for (MenuItem mi : viewItem)
			mi.addActionListener(viewListener);
		
	}

	private void open() {
		fDialog = new FileDialog(MyWindow.this, "파일 열기", FileDialog.LOAD);
		fDialog.setVisible(true);
		
		File f = new File(fDialog.getDirectory() + fDialog.getFile());
		ta.setText("\0");
		
		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
			while (br.ready()) {
				ta.append(br.readLine() + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void save() {
		fDialog = new FileDialog(MyWindow.this, "파일 저장", FileDialog.SAVE);
		fDialog.setVisible(true);
		
		File f = new File(fDialog.getDirectory() + fDialog.getFile());
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			bw.write(ta.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {
		System.exit(0);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		this.dispose();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowOpened(WindowEvent e) {}
}
