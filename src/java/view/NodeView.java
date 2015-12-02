package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import Environment.Node;

public class NodeView {

	private Node node;
	private int posx, posy;
	private boolean hasCar;
	private CarView carView;
	
	///teszteléshez
	public NodeView(int x, int y) {
		posx = x;
		posy = y;
		carView = new CarView(posx,posy,90);
	}

	public NodeView(Node n) {
		node = n;
		posx = node.getPosX();
		posy = node.getPosY();
		carView = new CarView(posx,posy,90);
	}

	public int getPosX() {
		return posx;
	}

	public int getPosY() {
		return posy;
	}	

	public void update() {
		posx = node.getPosX();
		posy = node.getPosY();
		hasCar = node.hasCar();
	}
	
	public void draw(Graphics2D g) {
		//super.paint(g);
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(1));
		g.drawOval(posx - 8, posy - 8, 16, 16);
		if(hasCar) carView.draw(g);
	}
}
