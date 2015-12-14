package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;

import Environment.Node;
import Environment.Road;

public class ViewPanel extends JPanel{

	
	ArrayList<RoadView> roadViewList = new ArrayList<RoadView>();
	ArrayList<NodeView> nodeViewList = new ArrayList<NodeView>();
	
	public ViewPanel() {
		super();

		/*
		///test
		carViewList = new ArrayList<CarView>();
		roadViewList = new ArrayList<RoadView>();
		nodeViewList = new ArrayList<NodeView>();
		
		CarView cv1 = new CarView(30, 30, (float)Math.PI/2);
		NodeView nv1 = new NodeView(300,30);
		carviewList.add(cv1);
		nodeviewList.add(nv1);
		
		for(int i = 0; i<20; i++)
			if(i%2==0)
				carviewList.add(new CarView(i*20,i*20, i));
			else {
				NodeView nvtemp = new NodeView(i*20, i*20);
				nodeviewList.add(nvtemp);
				roadviewList.add(new RoadView(nv1,nvtemp));
			}
		CarView cv2 = new CarView(nodeviewList.get(0), nodeviewList.get(1));
		carviewList.add(cv2);
		///testvege
		 */
	}
	
	///test ki kell majd szedni valszeg
	public void add(CarView car){
		//carviewList.add(car);
	}
	
	///test
	public void add(NodeView node){
		//nodeviewList.add(node);
	}
	
	///test
	public void add(RoadView road){
		//roadviewList.add(road);
	}
	
	
	
	public void add(Node node) {
		nodeViewList.add(new NodeView(node));
	}
	public void add(Road road) {
		roadViewList.add(new RoadView(road));
	}
	
	public void update() {
		for(int i=0; i<roadViewList.size(); i++) 
			roadViewList.get(i).update();
		for(int i=0; i<nodeViewList.size(); i++) 
			nodeViewList.get(i).update();
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i<roadViewList.size(); i++)
			roadViewList.get(i).draw(g2d);
		for (int i = 0; i<roadViewList.size(); i++)
			roadViewList.get(i).drawCars(g2d);
		for (int i = 0; i<nodeViewList.size(); i++)
			nodeViewList.get(i).draw(g2d);
	}
	
}
