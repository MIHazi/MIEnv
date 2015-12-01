package Environment;

import java.util.ArrayList;
import java.util.HashMap;

import view.*;

public class TopologyModel {
	//Nodes of the topology
	ArrayList<Node> nodes;
	//List of connections in the topology
	ArrayList<Road> roads;
	HashMap<String, Car> cars = new HashMap<String, Car>();
	ViewPanel panel;
	
	//The current simulation time
	public float time;
	
	//Moves all cars on the roads
	void moveCars(float deltaTime){
		for(Road road : roads){
			road.moveCars(deltaTime);
		}
	}
	
	public TopologyModel(ConfigParser config){
		nodes = new ArrayList<Node>();
		roads = new ArrayList<Road>();
		panel = new ViewPanel();
		for(int i = 0; i < config.nNodes(); i++){
			nodes.add(new Node(i, config.getNodePosX(i), config.getNodePosY(i)));
			panel.add(new NodeView(config.getNodePosX(i), config.getNodePosY(i)));
		}
		for(int i = 0; i < config.nRoads(); i++){
			int startID = config.getRoadStartID(i);
			int endID = config.getRoadEndID(i);
			roads.add(new Road(nodes.get(startID), nodes.get(endID), config.getRoadLimit(i), i));
			panel.add(new RoadView((int)nodes.get(startID).posX, (int)nodes.get(startID).posY, (int)nodes.get(endID).posX, (int)nodes.get(endID).posY));
		}
	}
	
	public boolean carCanTurn(String name, int roadID){
		Car car = cars.get(name);
		return (!car.onNode || !nodes.get(car.placeID).hasRoad(roads.get(roadID)));
	}
}
