package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import Environment.Car;
import Environment.Road;

public class RoadView {

	private int startPosx, startPosy;
	private int endPosx, endPosy;
	private Road road;
	private ArrayList<CarView> carViewList = new ArrayList<CarView>();
	
	/*public RoadView(NodeView sN, NodeView eN)
	{
		startPosx = sN.getPosX();
		startPosy = sN.getPosY();
		endPosx = eN.getPosX();
		endPosy = eN.getPosY();
	}
	*/
	public RoadView(Road r)
	{
		road = r;
		startPosx = road.startNode.getPosX();
		startPosy = road.startNode.getPosY();
		endPosx = road.endNode.getPosX();
		endPosy = road.endNode.getPosY();
	}
	
	public RoadView(int startPosX, int startPosY, int endPosX, int endPosY){
		this.startPosx = startPosX;
		this.startPosy = startPosY;
		this.endPosx = endPosX;
		this.endPosy = endPosY;
	}
	
	public void update() {
		carViewList.clear();
		Iterator<Car> it = road.cars.iterator();
		while (it.hasNext()) {
			carViewList.add(new CarView(road, it.next().roadPos));
		}
	}
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(200,200,200));
		g.setStroke(new BasicStroke(6));
		g.drawLine(startPosx, startPosy, endPosx, endPosy);
		for (int i = 0; i<carViewList.size(); i++)
			carViewList.get(i).draw(g);
	}
}
