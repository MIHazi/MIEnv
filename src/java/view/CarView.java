package view;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import Environment.Car;
import Environment.Road;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CarView {

	private int posx, posy;
	private float angle;
	private static BufferedImage img;

	public CarView(int x, int y, float angle) {
		posx = x;
		posy = y;
		this.angle = angle; // comment
		if (img == null)
			try {
				img = ImageIO.read(new File("pictures\\car01.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/*
	 * public CarView(NodeView startNode, NodeView endNode) { posx =
	 * (startNode.getPosX()+endNode.getPosX())/2; posy =
	 * (startNode.getPosY()+endNode.getPosY())/2; angle =
	 * (float)Math.atan(((double
	 * )(endNode.getPosX()-startNode.getPosX()))/((double
	 * )(startNode.getPosY()-endNode.getPosY()))); }
	 */
	public CarView(Road road, float roadpos) {
		posx = road.startNode.getPosX()
				+ (road.endNode.getPosX() - road.startNode.getPosX())
				* (int) roadpos / (int) road.getLength();
		posy = road.startNode.getPosY()
				+ (road.endNode.getPosY() - road.startNode.getPosY())
				* (int) roadpos / (int) road.getLength();
		angle = (float) Math.atan2(road.endNode.getPosX()- road.startNode.getPosX(),
				road.startNode.getPosY() - road.endNode.getPosY());
		if (img == null)
			try {
				img = ImageIO.read(new File("pictures\\car01.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/*
	 * public void setPosition(int x, int y) { posx = x; posy = y; }
	 * 
	 * public void setAngle(float angle) { this.angle = angle; }
	 */

	public void draw(Graphics2D g) {
		// super.paint(g);
		// Graphics2D g2d = (Graphics2D) g;
		// Graphics2D g = buffImg.createGraphics();
		AffineTransform at = new AffineTransform();
		at.translate(posx, posy);
		at.rotate(angle);
		at.translate(-4, -6);
		g.setTransform(at);
		g.drawImage(img, null, 0, 0);
		at.translate(4, 6);
		at.rotate(-angle);
		at.translate(-posx, -posy);
		g.setTransform(at);
	}
}
