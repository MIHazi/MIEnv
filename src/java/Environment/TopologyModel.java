package Environment;

import java.util.HashMap;
import java.util.Collection;

public class TopologyModel {
	//Nodes of the topology
	HashMap<Integer, Node> nodes;
	//List of connections in the topology
	HashMap<Integer, Road> roads;
	
	//The current simulation time
	public float time;
	
	//Moves all cars on the roads
	void moveCars(float deltaTime){
		Collection<Road> roadList = roads.values();
		for(Road road : roadList){
			road.moveCars(deltaTime);
		}
	}
}
