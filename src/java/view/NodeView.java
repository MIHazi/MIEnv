package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class NodeView {

	private int posx, posy;
	
	public NodeView(int x, int y) {
		posx = x;
		posy = y;
	}
	
	public int getPosX() {
		return posx;
	}
	
	public int getPosY() {
		return posy;
	}
	
	public void draw(Graphics2D g) {
		//super.paint(g);
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(1));
		g.drawOval(posx-8, posy-8, 16, 16);
	}
}
