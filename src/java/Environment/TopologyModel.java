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
		List jsonNodes = (List)topology.get("Nodes");
		List jsonRoads = (List)topology.get("Roads");
		for(int i = 0; i < jsonNodes.size(); i++){
			Map currentNode = (Map)(jsonNodes.get(i));
			nodes.add(new Node(i, (Integer)currentNode.get("PosX"), (Integer)currentNode.get("PosY")));
			panel.add(new NodeView((Integer)currentNode.get("PosX"), (Integer)currentNode.get("PosY")));
		}
		for(int i = 0; i < jsonRoads.size(); i++){
			Map currentRoad = (Map)(jsonRoads.get(i));
			int startID = (Integer)currentRoad.get("StartID");
			int endID = (Integer)currentRoad.get("EndID");
			roads.add(new Road(nodes.get(startID), nodes.get(endID), (Float)currentRoad.get("Limit")));
			panel.add(new RoadView((int)nodes.get(startID).posX, (int)nodes.get(startID).posY, (int)nodes.get(endID).posX, (int)nodes.get(endID).posY));
		}
	}
}
