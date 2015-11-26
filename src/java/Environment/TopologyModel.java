package Environment;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import view.*;

public class TopologyModel {
	//Nodes of the topology
	ArrayList<Node> nodes;
	//List of connections in the topology
	ArrayList<Road> roads;
	ViewPanel panel;
	
	//The current simulation time
	public float time;
	
	//Moves all cars on the roads
	void moveCars(float deltaTime){
		for(Road road : roads){
			road.moveCars(deltaTime);
		}
	}
	
	public TopologyModel(Map topology){
		List jsonNodes = (List)(topology.get("Nodes"));
		List jsonRoads = (List)(topology.get("Roads"));
		nodes = new ArrayList<Node>();
		roads = new ArrayList<Road>();
		panel = new ViewPanel();
		//TODO: DEBUG System.out.println("NODES: \n" + jsonNodes.toString());
		//TODO: DEBUG System.out.println("ROADS: \n" + jsonRoads.toString());
		for(int i = 0; i < jsonNodes.size(); i++){
			Map currentNode = (Map)(jsonNodes.get(i));
			//TODO: DEBUG System.out.println("NODE" + i + ": \n" + currentNode.toString());
			nodes.add(new Node(i, Integer.parseInt((String)currentNode.get("PosX")), Integer.parseInt((String)currentNode.get("PosY"))));
			panel.add(new NodeView(Integer.parseInt((String)currentNode.get("PosX")), Integer.parseInt((String)currentNode.get("PosY"))));
		}
		for(int i = 0; i < jsonRoads.size(); i++){
			Map currentRoad = (Map)(jsonRoads.get(i));
			//TODO: DEBUG System.out.println("NODE" + i + ": \n" + currentRoad.toString());
			int startID = Integer.parseInt((String)currentRoad.get("StartID"));
			int endID = Integer.parseInt((String)currentRoad.get("EndID"));
			roads.add(new Road(nodes.get(startID), nodes.get(endID), Float.parseFloat((String)currentRoad.get("Limit"))));
			panel.add(new RoadView((int)nodes.get(startID).posX, (int)nodes.get(startID).posY, (int)nodes.get(endID).posX, (int)nodes.get(endID).posY));
		}
	}
}
