package Environment;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Node {

	ArrayDeque<Car> cars;
	ArrayList<Road> roadsFromHere;
	
	float posX, posY;
	int id;
	
	public Node(int id, float x, float y){
		this.id = id;
		posX = x;
		posY = y;
	}
	
	public void addCar(Car car){
		cars.addFirst(car);
		car.onNode = true;
	}
	
	public Car removeLast(){
		return cars.removeLast();
	}
}
