package Environment;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Node {

	ArrayDeque<Car> cars = new ArrayDeque<Car>();
	ArrayList<Road> roadsFromHere = new ArrayList<Road>();
	
	int posX, posY;
	int id;
	
	public Node(int id, int x, int y){
		this.id = id;
		posX = x;
		posY = y;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}

	public void addCar(Car car){
		cars.addFirst(car);
		car.onNode = true;
		car.placeID = id;
	}
	
	public Car removeLast(){
		Car ret = cars.removeLast();
		ret.onNode = false;
		return ret;
	}
	
	public void addRoad(Road road){
		roadsFromHere.add(road);
	}
	
	public boolean hasRoad(Road road){
		return roadsFromHere.contains(road);
	}
	
	public boolean hasCar() {
		return !cars.isEmpty();
	}
}
