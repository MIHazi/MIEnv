package Environment;

import java.util.ArrayList;
import java.util.HashMap;

import view.*;

public class TopologyModel {
	//Nodes of the topology
	ArrayList<Node> nodes;
	//List of connections in the topology
	ArrayList<Road> roads;
	ArrayList<ArrayList<Car>> cars;
	TopologyView view;
	
	ConfigParser config;
	
	//The current simulation time
	public float time;
	
	public TopologyModel(ConfigParser config){
		this.config = config;
		nodes = new ArrayList<Node>();
		roads = new ArrayList<Road>();
		cars = new ArrayList<ArrayList<Car>>();
		view = new TopologyView();
		ViewPanel panel = view.getPanel();
		for(int i = 0; i < config.nNodes(); i++){
			System.out.println("Node " + i + " added");
			Node node = new Node(i, config.getNodePosX(i), config.getNodePosY(i));
			nodes.add(node);
			panel.add(node);
		}
		for(int i = 0; i < config.nRoads(); i++){
			int startID = config.getRoadStartID(i);
			int endID = config.getRoadEndID(i);
			System.out.println("Start: " + startID + " End: " + endID);
			Road road = new Road(nodes.get(startID), nodes.get(endID), config.getRoadLimit(i), i);
			roads.add(road);
			panel.add(road);
		}
		int n = 1;
		String lastName = "";
		for(int i = 0; i < config.nAgents(); i++){
			cars.add(new ArrayList<Car>());
			if(!lastName.equals(config.getAgName(i))){
				lastName = config.getAgName(i);
				n = 1;
			}
			for(int j = 0; j < config.getAgSpawnCount(i); j++){
				Car car = new Car();
				car.startNode = config.getAgStartNode(i);
				car.endNode = config.getAgEndNode(i);
				car.name = lastName + n;
				car.speed = config.getAgSpeed(i);
				cars.get(i).add(car);
				n++;
			}
		}
	}
	
	public TopologyView getView(){
		return view;
	}
	
	//Moves all cars on the roads
	public void update(float deltaTime, int step){
		spawnCars(step);
		for(Road road : roads){
			road.moveCars(deltaTime);
		}
		//printCars();	//TODO: JUST FOR DEBUG
		view.updateAll();
	}
	
	void spawnCars(int step){
		for(int i = 0; i < config.nAgents(); i++){
			if(step % config.getAgSpawnDelay(i) == 0){
				ArrayList<Car> carList = cars.get(i);
				for(int j = 0; j < carList.size(); j++){
					Car car = carList.get(j);
					if(!car.started){
						if(!nodes.get(car.startNode).hasCar()){ //TODO: uncomment if node can only hold one car
							car.started = true;
							nodes.get(car.startNode).addCar(car);
						}
						break;
					}
				}
			}
		}
	}
	
	//TODO: JUST FOR DEBUG
	void printCars(){
		for(ArrayList<Car> carList : cars){
			for(Car car : carList){
				if(car.started)
					System.out.println(car.toString() + "\n");
			}
		}
	}
	
	public boolean moveCarToRoad(String name, int roadID){
		Road toRoad = roads.get(roadID);
		Car car = getCarByName(name);
		if(car != null && toRoad != null && toRoad.canAcceptCars() && carCanTurn(car, roadID)){
			toRoad.addCar(nodes.get(car.placeID).removeLast());
			return true;
		}
		return false;
	}
	
	public boolean carCanTurn(String name, int roadID){
		Car car = getCarByName(name);
		if(car == null)
			return false;
		return carCanTurn(car, roadID);
	}
	
	public boolean carCanTurn(Car car, int roadID){
		return (car.onNode && nodes.get(car.placeID).hasRoad(roads.get(roadID)));
	}
	
	public boolean allCarsHaveRoute(){
		for(ArrayList<Car> carList : cars)
			for(Car car : carList)
				if(!car.hasRoute)
					return false;
		return true;
	}
	
	public boolean exitCar(String name){
		Car car = getCarByName(name);
		if(car.onNode && car.placeID == car.endNode){
			car.finished = true;
			nodes.get(car.endNode).removeLast();
			return true;
		}
		return false;
	}
	
	public boolean allCarsFinished(){
		for(ArrayList<Car> carList : cars){
			for(Car car : carList)
				if(!car.finished)
					return false;
		}
		return true;
	}
	
	public Car getCarByName(String name){
		for(ArrayList<Car> carList : cars)
			for(Car car : carList)
				if(car.name.equals(name))
					return car;
		return null;
	}
}
