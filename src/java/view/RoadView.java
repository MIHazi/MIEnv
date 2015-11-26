package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class RoadView {

	private int startPosx, startPosy;
	private int endPosx, endPosy;
	
	public RoadView(NodeView sN, NodeView eN)
	{
		startPosx = sN.getPosX();
		startPosy = sN.getPosY();
		endPosx = eN.getPosX();
		endPosy = eN.getPosY();
	}
	
	public RoadView(int startPosX, int startPosY, int endPosX, int endPosY){
		this.startPosx = startPosX;
		this.startPosy = startPosY;
		this.endPosx = endPosX;
		this.endPosy = endPosY;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(200,200,200));
		g.setStroke(new BasicStroke(6));
		g.drawLine(startPosx, startPosy, endPosx, endPosy);
	}
}
