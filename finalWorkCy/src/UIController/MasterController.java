package UIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;

public abstract class MasterController extends JFrame implements ActionListener{
	protected Container pane;
	protected HashSet<Object> list_check_fill = new HashSet<>();
	
	public MasterController (int width, int height, String title) {
		this.pane = this.getContentPane();
		
		this.addToPane();
		this.setResizable(false);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.setSize(width, height);
		int x = (int)(toolkit.getScreenSize().getWidth() - this.getWidth())/2;
		int y = (int)(toolkit.getScreenSize().getHeight() - this.getHeight())/2;
		this.setLocation(x, y);
		
		this.setVisible(true);
		this.setTitle(title);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
	
	public abstract void addToPane();
	
	public void showDialog(String strInfo) {
	    JOptionPane.showMessageDialog(null, strInfo);
	}
	
	public boolean isFill() {
		Iterator<Object> iterator = list_check_fill.iterator();
		while(iterator.hasNext()) {
			try {
				JTextField nextObject = (JTextField) iterator.next();
				if(nextObject.getText().equals("")) {
					return true;
				}
				
				if(nextObject.getText().indexOf('|') != -1) {
					return true;
				}
			} catch (Exception e){
				return true;
			}
		}
		
		return false;
	}
	
}
