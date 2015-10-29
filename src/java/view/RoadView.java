package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class RoadView {

	/*private int startPosx, startPosy;
	private int endPosx, endPosy;*/
	private NodeView startNode;
	private NodeView endNode;
	
	public RoadView(NodeView sN, NodeView eN)
	{
		startNode = sN;
		endNode = eN;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(200,200,200));
		g.setStroke(new BasicStroke(6));
		g.drawLine(startNode.getPosX(), startNode.getPosY(), endNode.getPosX(), endNode.getPosY());
	}
}
