package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;

class ViewPanel extends JPanel{

	List<RoadView> roadList;
	List<NodeView> nodeList;
	List<CarView> carList;
	
	ViewPanel() {
		super();
		carList = new ArrayList<CarView>();
		roadList = new ArrayList<RoadView>();
		nodeList = new ArrayList<NodeView>();
		CarView cv1 = new CarView(30, 30, (float)Math.PI/2);
		NodeView nv1 = new NodeView(300,30);
		carList.add(cv1);
		nodeList.add(nv1);
		
		for(int i = 0; i<20; i++)
			if(i%2==0)
				carList.add(new CarView(i*20,i*20, i));
			else {
				NodeView nvtemp = new NodeView(i*20, i*20);
				nodeList.add(nvtemp);
				roadList.add(new RoadView(nv1,nvtemp));
			}
		CarView cv2 = new CarView(nodeList.get(0), nodeList.get(1));
		carList.add(cv2);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i<roadList.size(); i++)
			roadList.get(i).draw(g2d);
		for (int i = 0; i<nodeList.size(); i++)
			nodeList.get(i).draw(g2d);
		for (int i = 0; i<carList.size(); i++)
			carList.get(i).draw(g2d);
	}
	
}
