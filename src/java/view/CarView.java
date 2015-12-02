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
	File img = new File("pictures\\car01.png");

	public CarView(int x, int y, float angle) {
		posx = x;
		posy = y;
		this.angle = angle; //comment
	}
	/*
	public CarView(NodeView startNode, NodeView endNode)
	{
		posx = (startNode.getPosX()+endNode.getPosX())/2;
		posy = (startNode.getPosY()+endNode.getPosY())/2;
		angle = (float)Math.atan(((double)(endNode.getPosX()-startNode.getPosX()))/((double)(startNode.getPosY()-endNode.getPosY())));
	}
	*/
	public CarView(Road road, float roadpos) {
		posx = road.startNode.getPosX()+(road.endNode.getPosX()-road.startNode.getPosX())*(int)roadpos/(int)road.getLength();
		posy = road.startNode.getPosY()+(road.endNode.getPosY()-road.startNode.getPosY())*(int)roadpos/(int)road.getLength();
		angle = (float)Math.atan(((double)(road.endNode.getPosX()-road.startNode.getPosX()))/
				((double)(road.startNode.getPosY()-road.endNode.getPosY())));
	}
	/*
	public void setPosition(int x, int y) {
		posx = x;
		posy = y;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}
	*/
	
	public void draw(Graphics2D g) {
		//super.paint(g);
		try {
			//Graphics2D g2d = (Graphics2D) g;
			BufferedImage buffImg = ImageIO.read(img);
			//Graphics2D g = buffImg.createGraphics();
			AffineTransform at = new AffineTransform();
			at.translate(posx, posy);
			
			at.rotate(angle);
			at.translate(-4,-6);
			g.setTransform(at);
			g.drawImage(buffImg,null,0,0);
			at.translate(4, 6);
			at.rotate(-angle);
			at.translate(-posx, -posy);
			g.setTransform(at);
			
		} catch (IOException e) {
			System.out.println("asd");
		}
	}
}
